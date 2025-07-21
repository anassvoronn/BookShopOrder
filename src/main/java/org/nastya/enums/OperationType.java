package org.nastya.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    DEPOSIT("Deposit"),
    PAYMENT("Payment"),
    WITHDRAWAL("Withdrawal");

    private final String label;

    OperationType(String label) {
        this.label = label;
    }
}