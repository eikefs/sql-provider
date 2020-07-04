package io.github.eikefs.sql.provider;

import io.github.eikefs.sql.provider.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Provider {

    private Provider() {}

    public static Provider get() {
        return new Provider();
    }

    private Connection get(String url) {
        try {
            Class.forName("org.sqlite.JDBC");

            url = url.startsWith("jdbc:sqlite:") ? url : "jdbc:sqlite:" + url;

            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Connection get(String url, String user, String pass) {
        try {
            Class.forName("org.sqlite.JDBC");

            url = url.startsWith("jdbc:mysql://") ? "jdbc:mysql://" + url : url;

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Database submit(String url) {
        return new Database(get(url));
    }

    public Database submit(String url, String user, String pass) {
        return new Database(get(url, user, pass));
    }

}
