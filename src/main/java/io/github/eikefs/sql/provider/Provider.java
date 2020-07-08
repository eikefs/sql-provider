package io.github.eikefs.sql.provider;

import io.github.eikefs.sql.provider.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Provider {

    private Provider() {}

    private static final Provider instance = new Provider();

    public static Provider getInstance() {
        return instance;
    }

    private Connection newConnection(String url) {
        try {
            Class.forName("org.sqlite.JDBC");

            url = url.startsWith("jdbc:sqlite:") ? url : "jdbc:sqlite:" + url;

            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Connection newConnection(String url, String user, String pass) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            url = url.startsWith("jdbc:mysql://") ? "jdbc:mysql://" + url : url;

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Database submit(String url) {
        return new Database(newConnection(url));
    }

    public Database submit(String url, String user, String pass) {
        return new Database(newConnection(url, user, pass));
    }

}
