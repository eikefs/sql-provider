package io.github.eikefs.sql.provider.test.orm;

import io.github.eikefs.sql.provider.orm.ORM;
import io.github.eikefs.sql.provider.orm.annotations.Field;
import io.github.eikefs.sql.provider.orm.annotations.Table;

@Table(name = "user", primary = "id")
public class User extends ORM {

    @Field(sqlType = "int", size = 8)
    private int id;

    @Field(size = 32)
    private String name;

    @Field(sqlType = "real", size = 8)
    private double money;

    public static String create() {
        return create(User.class);
    }

}
