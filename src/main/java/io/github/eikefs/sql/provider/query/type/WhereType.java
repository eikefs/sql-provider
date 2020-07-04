package io.github.eikefs.sql.provider.query.type;

public enum WhereType {

    EQUALS(0, "="),
    LIKE(1, "like"),
    BIGGEST_THAN(2, ">"),
    LOWER_THAN(3, "<"),
    BIG_EQUALS(4, ">="),
    LOW_EQUALS(5, "<=");

    private final int index;
    private final String context;

    WhereType(int index, String context) {
        this.index = index;
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public int getIndex() {
        return index;
    }

}
