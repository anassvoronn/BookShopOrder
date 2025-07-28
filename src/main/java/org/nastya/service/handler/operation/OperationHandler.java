package org.nastya.service.handler.operation;

import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.enums.OperationType;

import java.math.BigDecimal;

public interface OperationHandler {
    OperationType getOperationType();

    TransactionsHistoryDTO handle(BigDecimal amount, Integer userId);
}