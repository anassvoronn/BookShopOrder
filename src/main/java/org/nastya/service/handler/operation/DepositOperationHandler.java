package org.nastya.service.handler.operation;

import lombok.extern.log4j.Log4j2;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.enums.OperationType;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DepositOperationHandler extends AbstractOperationHandler implements OperationHandler {

    @Override
    public OperationType getOperationType() {
        return OperationType.DEPOSIT;
    }

    @Override
    public TransactionsHistoryDTO handle(double amount,
                                         double currentBalance,
                                         Integer userId) {
        validateAmount(amount);
        double newBalance = currentBalance + amount;
        log.info("Current balance: {}, New balance after deposit: {}", currentBalance, newBalance);

        TransactionsHistory transaction = createTransaction(
                userId,
                amount,
                OperationType.DEPOSIT,
                newBalance
        );

        return save(transaction);
    }
}