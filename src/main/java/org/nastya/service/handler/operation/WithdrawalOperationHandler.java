package org.nastya.service.handler.operation;

import lombok.extern.log4j.Log4j2;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.enums.OperationType;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class WithdrawalOperationHandler extends AbstractOperationHandler implements OperationHandler {

    @Override
    public OperationType getOperationType() {
        return OperationType.WITHDRAWAL;
    }

    @Override
    public TransactionsHistoryDTO handle(double amount,
                                         double currentBalance,
                                         Integer userId) {
        validateAmount(amount);
        validateSufficientFunds(currentBalance, amount);
        double newBalance = currentBalance - amount;
        log.info("Withdrawal approved. Deducting {} from account. New balance will be {}",
                amount, newBalance);

        TransactionsHistory transaction = createTransaction(
                userId,
                amount,
                OperationType.WITHDRAWAL,
                newBalance
        );

        return save(transaction);
    }
}