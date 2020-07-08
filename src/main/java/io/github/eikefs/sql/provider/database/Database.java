package io.github.eikefs.sql.provider.database;

import io.github.eikefs.sql.provider.query.Query;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Database {

    private final Connection connection;

    public Database(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public CompletableFuture<List<Object>> query(Query query) {
        return query(query.raw());
    }

    public CompletableFuture<List<Object>> query(String query) {
        return CompletableFuture.supplyAsync(() -> querySync(query));
    }

    public List<Object> querySync(Query query) {
        return querySync(query.raw());
    }

    public List<Object> querySync(String query) {
        List<Object> objects = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                for (int field = 1; field <= resultSet.getMetaData().getColumnCount(); field++) {
                    objects.add(resultSet.getObject(field));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objects;
    }

    public CompletableFuture<Void> update(Query query) {
        return update(query.raw());
    }

    public CompletableFuture<Void> update(String query) {
        return CompletableFuture.supplyAsync(() -> {
            updateSync(query);

            return null;
        });
    }

    public void updateSync(Query query) {
        updateSync(query.raw());
    }

    public void updateSync(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> CompletableFuture<T> build(Class<? extends T> type, String query) {
        return CompletableFuture.supplyAsync(() -> buildSync(type, query));
    }

    public <T> T buildSync(Class<? extends T> type, String query) {
        Constructor constructor = type.getConstructors()[0];
        List<Object> data = querySync(query);

        try {
            return (T) constructor.newInstance(data.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void shutdown() {
        try {
            if (!connection.isClosed()) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
