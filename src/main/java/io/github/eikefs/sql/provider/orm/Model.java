package io.github.eikefs.sql.provider.orm;

import io.github.eikefs.sql.provider.database.Database;
import io.github.eikefs.sql.provider.query.Query;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * This class provides an easy mode to create models with methods create,
 * update and delete that will make the binding with Provider.
 */
public abstract class Model {

    private final String tableName;
    private long id;

    protected Model(String tableName) {
        this.tableName = tableName;
    }

    /**
     * This will update the model data, filtering if the values aren't null.
     * That will make easier to update data in database.
     *
     * Example:
     *
     * ```java
     *
     * Database database; // your database instance.
     * Model<User> model; // your model instance.
     *
     * // ImmutableMap in this example is from google's guava.
     * // That will only update the name, that isn't null.
     * model.update(database, ImmutableMap.<String, Object>builder()
     *                  .put("name", "New name")
     *                  .put("email", null)
     *                  .build())
     *
     * ```
     *
     * @param database The database connection
     * @param data The data that you want to change
     * @return the result of query
     */
    public CompletableFuture<Set<Object>> update(final Database database, final Map<String, Object> data) {
        final Map<String, Object> filteredData = data.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return database.query(new Query()
                .from(tableName)
                .update()
                .sets(filteredData));
    }

    /**
     * This will delete the current model using id as the searcher
     *
     * @param database The database connection
     * @return the result of query
     */
    public CompletableFuture<Void> delete(final Database database) {
        return database.update(new Query()
                .from(tableName)
                .delete()
                .where("id", id));
    }

    /**
     * @return The model's id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the model's id
     *
     * @param id New id
     */
    public void setId(final long id) {
        this.id = id;
    }
}
