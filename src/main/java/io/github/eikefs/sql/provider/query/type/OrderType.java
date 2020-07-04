package io.github.eikefs.sql.provider.query.type;

public enum OrderType {

    ASCENDING(0, "asc"),
    DESCENDING(1, "desc");

    private final int index;
    private final String context;

    OrderType(int index, String context) {
        this.index = index;
        this.context = context;
    }

    public int getIndex() {
        return index;
    }

    public String getContext() {
        return context;
    }

}
