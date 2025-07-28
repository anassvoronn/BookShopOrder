package org.nastya.dto;

import lombok.Value;
import org.nastya.enums.OperationType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
public class TransactionsHistoryDTO {
    Integer id;
    BigDecimal amount;
    OperationType operationType;
    ZonedDateTime date;
    BigDecimal balance;
}