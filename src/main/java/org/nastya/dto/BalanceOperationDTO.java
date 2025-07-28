package org.nastya.dto;

import lombok.*;
import org.nastya.enums.OperationType;

import java.math.BigDecimal;

@Value
public class BalanceOperationDTO {
    BigDecimal amount;
    OperationType operationType;
}