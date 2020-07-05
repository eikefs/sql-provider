package io.github.eikefs.sql.provider.test.orm;

import io.github.eikefs.sql.provider.database.Database;
import io.github.eikefs.sql.provider.orm.Model;
import io.github.eikefs.sql.provider.query.Query;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.CompletableFuture.runAsync;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public final class ModelTests extends TestCase {

    // Dummy class
    private static class User extends Model {
        public User() {
            super("users");
        }
    }

    public void testShouldExecuteQueryCorrectlyWhenUpdateModel() {
        final Database databaseMock = mock(Database.class);
        final Model model = new User();

        when(databaseMock.update(any(Query.class))).thenReturn(runAsync(doNothing()));

        final Map<String, Object> fakeData = new HashMap<>();
        fakeData.put("name", "New name");
        fakeData.put("email", null);

        model.update(databaseMock, fakeData);

        verify(databaseMock, times(1)).update(any(Query.class));
    }

    public void testShouldExecuteQueryCorrectlyWhenDeleteModel() {
        final Database databaseMock = mock(Database.class);
        final Model model = new User();

        when(databaseMock.update(any(Query.class))).thenReturn(runAsync(doNothing()));

        model.delete(databaseMock);

        verify(databaseMock, times(1)).update(any(Query.class));
    }

    private static Runnable doNothing() {
        return () -> {
            // do nothing
        };
    }

}
