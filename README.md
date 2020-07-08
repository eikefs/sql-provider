# sql-provider
A easy way to use SQL on Java. Create new connections and easy queries.

# Creating database-connections

```java
Database database = Provider.getInstance().submit(url, user, password); // Or Provider.getInstance().submit(url)
```

# Creating tables

```java
Database#update(new TableQuery()
      .name("users", true)
      .fields(new TableField()
              .name("id")
              .type("int")
              .size(8)
              .autoIncrement())
      .primary("id"));
```

And others examples you may get on `QueriesTest.java` file, on test folder.

# How to install

```xml
<repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
      <groupId>com.github.eikefs</groupId>
      <artifactId>sql-provider</artifactId>
      <version>1.0.1</version>
<dependency>
```

