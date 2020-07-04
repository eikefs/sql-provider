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
                int cursor = 1;

                while (resultSet.next()) {
                    objects.add(resultSet.getObject(cursor));

                    cursor++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return objects;
        });
    }

    public void update(Query query) {
        update(query.raw());
    }

    public void update(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            if (!connection.isClosed()) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
