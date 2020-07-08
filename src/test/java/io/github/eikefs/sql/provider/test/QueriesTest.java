package io.github.eikefs.sql.provider.test;

import io.github.eikefs.sql.provider.Provider;
import io.github.eikefs.sql.provider.database.Database;
import io.github.eikefs.sql.provider.query.Query;
import io.github.eikefs.sql.provider.query.TableQuery;
import io.github.eikefs.sql.provider.query.field.TableField;
import io.github.eikefs.sql.provider.test.orm.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QueriesTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Database database = Provider.getInstance().submit("memory");

        // Creating tables
        database.updateSync(
                new TableQuery()
                .name("users")
                .fields(TableField.get()
                        .name("id")
                        .type("int")
                        .size(8),
                        TableField.get()
                        .name("name")
                        .type("varchar")
                        .size(255))
                .primary("id"));

        // Inserting data
        database.updateSync(new Query().insert("users", 1, "eike"));

        // Getting the data
        List<Object> data = database.querySync(new Query()
                .select("id", "name")
                .from("users"));

        for (Object o : data) {
            System.out.println(o);
        }

        User user = database.build(User.class, new Query()
                .selectAll()
                .from("users")
                .where("id", 1)
                .raw()).get();

        System.out.println("ID: " + user.getId() + ", Name: " + user.getName());

        database.updateSync(new Query().drop("users", "table"));

        database.shutdown();
    }
}
