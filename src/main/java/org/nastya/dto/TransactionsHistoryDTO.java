package org.nastya.dto;

import lombok.Value;
import org.nastya.enums.OperationType;

import java.time.ZonedDateTime;

@Value
public class TransactionsHistoryDTO {
    Integer id;
    Integer amount;
    OperationType operationType;
    ZonedDateTime date;
    Integer balance;
    Integer userId;
}