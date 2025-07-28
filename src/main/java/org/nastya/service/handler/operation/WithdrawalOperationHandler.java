package org.nastya.service.handler.operation;

import lombok.extern.log4j.Log4j2;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.enums.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Log4j2
@Component
public class WithdrawalOperationHandler extends AbstractOperationHandler implements OperationHandler {

    @Override
    public OperationType getOperationType() {
        return OperationType.WITHDRAWAL;
    }

    @Override
    public TransactionsHistoryDTO handle(BigDecimal amount, Integer userId) {
        BigDecimal currentBalance = getCurrentBalance(userId);
        validateSufficientFunds(currentBalance, amount);
        BigDecimal newBalance = currentBalance.subtract(amount);
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