package io.github.eikefs.sql.provider.orm;

import io.github.eikefs.sql.provider.orm.annotations.Field;
import io.github.eikefs.sql.provider.orm.annotations.Table;

import java.util.Arrays;

public class ORM {

    public static String create(Class<?> tableClass) {
        StringBuilder sb = new StringBuilder();

        if (!tableClass.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Cannot create a create query from a not-queryable class.");
        }

        Table table = tableClass.getAnnotation(Table.class);

        sb.append("create table ");

        if (table.unique()) sb.append("if not exists ");

        sb.append("`")
          .append(table.name())
          .append("` (");

        Arrays.stream(tableClass.getDeclaredFields())
              .filter((field) -> field.isAnnotationPresent(Field.class))
              .forEach((field) -> {
                  Field tableField = field.getAnnotation(Field.class);

                  sb.append("`")
                      .append(field.getName())
                      .append("` ");

                  sb.append(tableField.type());

                  if (tableField.size() > 0) {
                      sb.append(" (").append(tableField.size()).append(") ");
                  }

                  if (tableField.unique()) sb.append("unique ");
                  if (!tableField.nullable()) sb.append("not null ");
                  if (tableField.autoIncrement()) sb.append("auto_increment ");

                  sb.append(",");
              });

        sb.append("primary key (`" + table.primary() + "`)");

        return sb.toString().trim() + ");";
    }

}
