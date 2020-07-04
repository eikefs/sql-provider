package io.github.eikefs.sql.provider.query;

import io.github.eikefs.sql.provider.query.field.TableField;

public class TableQuery extends Query {

    private final StringBuilder builder = getStringBuilder();

    public TableQuery fields(TableField... fields) {
        builder.append(" (");

        for (int index = 0; index < fields.length; index++) {
            builder.append(fields[index].build());

            if (index + 1 < fields.length) builder.append(",");
        }

        return this;
    }

    public TableQuery close() {
        builder.append(")");

        return this;
    }

    public TableQuery primaryKey(String fieldName) {
        builder.append(",")
                .append("primary key (")
                .append(fieldName)
                .append(")");

        return this;
    }

    public TableQuery foreignKey(String fieldName, String reference, String tableReference) {
        fieldName = '`' + fieldName + '`';
        reference = '`' + reference + '`';

        builder.append("foreign key ")
                .append(fieldName)
                .append(" references ")
                .append(tableReference)
                .append("(")
                .append(reference)
                .append(")");

        return this;
    }

    public TableQuery name(String name, boolean unique) {
        builder.append("create table ");

        if (unique) builder.append("if not exists ");

        builder.append(name);

        return this;
    }

    public TableQuery name(String name) {
        return name(name, false);
    }

    @Override
    public String raw() {
        return builder.toString().trim() + ";";
    }

}
