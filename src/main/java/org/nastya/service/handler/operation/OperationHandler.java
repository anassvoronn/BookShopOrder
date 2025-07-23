package org.nastya.service.handler.operation;

import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.enums.OperationType;

public interface OperationHandler {
    OperationType getOperationType();

    TransactionsHistoryDTO handle(double amount,
                                  double currentBalance,
                                  Integer userId);
}