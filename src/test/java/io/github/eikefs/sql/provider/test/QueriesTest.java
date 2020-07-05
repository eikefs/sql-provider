package io.github.eikefs.sql.provider.test;

import io.github.eikefs.sql.provider.Provider;
import io.github.eikefs.sql.provider.database.Database;
import io.github.eikefs.sql.provider.query.Query;
import io.github.eikefs.sql.provider.query.field.TableField;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class QueriesTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Database database = Provider.from().submit("memory");

        // Creating tables
        database.update(
                Query.table()
                .name("users")
                .fields(TableField.get()
                        .name("uid")
                        .type("int")
                        .size(8),
                        TableField.get()
                        .name("name")
                        .type("varchar")
                        .size(255))
                .primaryKey("uid")
                .close()
        );

        // Inserting data
        database.update(Query.get().insert("users", 1, "eike"));

        // Getting the data
        Set<Object> data = database.query(Query.get()
                .select("name")
                .from("users")
                .where("uid", 1)
        ).get();

        for (Object o : data) {
            System.out.println(o);
        }

        database.update("drop table `users`;");

        database.shutdown();
    }
}
