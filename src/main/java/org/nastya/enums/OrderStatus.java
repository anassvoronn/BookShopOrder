package org.nastya.enums;

public enum OrderStatus {
    NEW("New"),
    COMPLETE("Complete");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}