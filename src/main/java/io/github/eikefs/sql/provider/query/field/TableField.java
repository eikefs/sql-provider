package io.github.eikefs.sql.provider.query.field;

public class TableField {

    private boolean nullable = false;
    private boolean unique = false;
    private boolean autoIncrement = false;
    private String name;
    private String type;
    private int size;

    public static TableField get() {
        return new TableField();
    }

    public TableField nullable() {
        this.nullable = true;

        return this;
    }

    public TableField name(String name) {
        this.name = name;

        return this;
    }

    public TableField type(String type) {
        this.type = type;

        return this;
    }

    public TableField size(int size) {
        this.size = size;

        return this;
    }

    public TableField unique() {
        this.unique = true;

        return this;
    }

    public TableField autoIncrement() {
        this.autoIncrement = true;

        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        name = "`" + name + "` ";

        sb.append(name)
          .append(type)
          .append("(")
          .append(size)
          .append(") ");

        if (autoIncrement) sb.append("auto_increment ");
        if (unique) sb.append("unique ");
        if (!nullable) sb.append("not null ");

        return sb.toString().trim();
    }


}
