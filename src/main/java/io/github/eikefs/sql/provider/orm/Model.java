package io.github.eikefs.sql.provider.orm;

import io.github.eikefs.sql.provider.database.Database;
import io.github.eikefs.sql.provider.query.Query;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class provides an easy mode to create models with methods create,
 * update and delete that will make the binding with Provider.
 */
public abstract class Model<T extends Model<?>> {

    private final String tableName;

    /**
     * Cache of field setter methods
     */
    private final Set<Method> setterMethods = new HashSet<>();

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
     * @return the model instance
     */
    public T update(final Database database, final Map<String, Object> data) {
        final Map<String, Object> filteredData = data.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        database.query(new Query()
                .from(tableName)
                .update()
                .sets(filteredData));


        // That will be suppressed, 'cause this will be T when extend this class.
        // noinspection unchecked
        return (T) this;
    }

}
