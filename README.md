# sql-builder
A easy way to use SQL on Java. Create new connections and easy queries.

# Creating tables

```java
Database database = Provider.get().submit(url, user, password); // Or Provider.get().submit(url)
```

# Creating tables
When you end the attributes writing of your table, you must add `.close()`.

```java
Database#update(Query.table()
      .name("users", true)
      .fields(TableFields.get()
              .name("userName")
              .type("varchar")
              .size(32))
      .primaryKey("userName")
      .close());
```

And others examples you may get on `QueriesTest.java` file, on test folder.

