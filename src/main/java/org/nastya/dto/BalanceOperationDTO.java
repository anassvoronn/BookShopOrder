package org.nastya.dto;

import lombok.*;
import org.nastya.enums.OperationType;

@Value
public class BalanceOperationDTO {
    double amount;
    OperationType operationType;
}