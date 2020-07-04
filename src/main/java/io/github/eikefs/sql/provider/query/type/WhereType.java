package io.github.eikefs.sql.provider.query.type;

public enum WhereType {

    EQUALS(0, "="),
    LIKE(1, "like");

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
