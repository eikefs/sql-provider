package io.github.eikefs.sql.provider.database;

import io.github.eikefs.sql.provider.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Database {

    private final Connection connection;

    public Database(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public CompletableFuture<Set<Object>> query(Query query) {
        return query(query.raw());
    }

    public CompletableFuture<Set<Object>> query(String query) {
        return CompletableFuture.supplyAsync(() -> {
            Set<Object> objects = new HashSet<>();

            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                for (int cursor = 1; resultSet.next(); cursor++) {
                    objects.add(resultSet.getObject(cursor));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return objects;
        });
    }

    public CompletableFuture<Void> update(Query query) {
        return update(query.raw());
    }

    public CompletableFuture<Void> update(String query) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    public void shutdown() {
        try {
            if (!connection.isClosed()) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
