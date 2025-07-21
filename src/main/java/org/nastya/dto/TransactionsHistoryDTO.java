package org.nastya.dto;

import lombok.Value;
import org.nastya.enums.OperationType;

import java.time.ZonedDateTime;

@Value
public class TransactionsHistoryDTO {
    Integer id;
    double amount;
    OperationType operationType;
    ZonedDateTime date;
    double balance;
}