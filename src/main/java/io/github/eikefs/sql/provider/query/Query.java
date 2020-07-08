package io.github.eikefs.sql.provider.query;

import io.github.eikefs.sql.provider.query.type.OrderType;
import io.github.eikefs.sql.provider.query.type.WhereType;

import java.util.Map;
import java.util.Map.Entry;

public class Query {

    private final StringBuilder stringBuilder;

    public Query() {
        this.stringBuilder = new StringBuilder();
    }

    public Query(String s) {
        this.stringBuilder = new StringBuilder(s);
    }

    public String raw() {
        return stringBuilder.toString().trim() + ";";
    }

    public Query select(String... fields) {
        stringBuilder.append("select ");

        for (int index = 0; index < fields.length; index++) {
            String field = '`' + fields[index] + '`';

            if (index + 1 < fields.length) field += ',';

            stringBuilder.append(field);
        }

        stringBuilder
                .append(" ")
                .append("from");

        return this;
    }

    public Query selectAll() {
       stringBuilder.append("select * from");

       return this;
    }

    public Query from(String table) {
        table = '`' + table + '`';

        stringBuilder.append(" ").append(table).append(" ");

        return this;
    }

    public Query update() {
        stringBuilder.append("update");

        return this;
    }

    public Query sets(Map<String, Object> sets) {
        for (Entry<String, Object> entry : sets.entrySet()) {
            String set = '`' + entry.getKey() + '`';

            stringBuilder.append(set).append("=").append(entry.getValue());
        }

        return this;
    }

    public Query where(String key, Object value) {
        return where(key, WhereType.EQUALS, value);
    }

    public Query where(String key, String type, Object value) {
        key = " `" + key + "` ";
        String val = " '" + value + "'";

        stringBuilder
                .append("where")
                .append(key)
                .append(type)
                .append(val);

        return this;
    }

    public Query where(String key, WhereType type, Object value) {
        return where(key, type.getContext(), value);
    }

    public Query and() {
        stringBuilder
                .append(" ")
                .append("and")
                .append(" ");

        return this;
    }

    public Query orderBy(String field, OrderType orderType) {
        field = " `" + field + "` ";

        stringBuilder
                .append("order by ")
                .append(field)
                .append(" ")
                .append(orderType.getContext());

        return this;
    }

    public Query insert(String into, Object... values) {
        stringBuilder
                .append("insert into ")
                .append(into)
                .append(" values (");

        for (int index = 0; index < values.length; index++) {
            String value = "'" + values[index] + "'";

            if (index + 1 < values.length) value += ',';

            stringBuilder.append(value);
        }

        stringBuilder.append(")");

        return this;
    }

    public Query limit(int limit) {
        stringBuilder
                .append("limit")
                .append(" ")
                .append(limit);

        return this;
    }

    public Query drop(String name, String type) {
        stringBuilder
                .append("drop ")
                .append(type)
                .append(" ")
                .append(name);

        return this;
    }

    public Query delete(String... sets) {
        stringBuilder.append("delete ");

        for (int index = 0; index < sets.length; index++) {
            String set = "`" + sets[index] + "`";

            if (index + 1 < sets.length) set += ", ";

            stringBuilder.append(set);
        }

        return this;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

}
