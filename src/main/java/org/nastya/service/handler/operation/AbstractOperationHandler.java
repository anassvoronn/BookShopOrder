package org.nastya.service.handler.operation;

import lombok.extern.log4j.Log4j2;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.enums.OperationType;
import org.nastya.repository.TransactionsHistoryRepository;
import org.nastya.service.TransactionsHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Log4j2
public abstract class AbstractOperationHandler {
    @Autowired
    private TransactionsHistoryRepository transactionsHistoryRepository;
    @Autowired
    private TransactionsHistoryMapper transactionsHistoryMapper;

    protected void validateSufficientFunds(BigDecimal currentBalance, BigDecimal amount) {
        if (currentBalance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
    }

    protected TransactionsHistory createTransaction(Integer userId, BigDecimal amount,
                                                    OperationType operationType, BigDecimal balance) {
        log.info("Creating transaction. User: {}, Amount: {}, Type: {}",
                userId, amount, operationType);
        TransactionsHistory transaction = new TransactionsHistory();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setOperationType(operationType);
        transaction.setBalance(balance);
        transaction.setDate(ZonedDateTime.now());

        return transaction;
    }

    protected TransactionsHistoryDTO save(TransactionsHistory transactionsHistory) {
        TransactionsHistory savedTransaction = transactionsHistoryRepository.save(transactionsHistory);
        log.info("Operation {} completed. Transaction ID: {}, New balance: {}",
                transactionsHistory.getOperationType(),
                savedTransaction.getId(),
                transactionsHistory.getBalance()
        );

        return transactionsHistoryMapper.mapToDto(savedTransaction);
    }

    protected BigDecimal getCurrentBalance(Integer userId) {
        return transactionsHistoryRepository.findCurrentBalanceByUserId(userId)
                .orElse(BigDecimal.valueOf(0.0));
    }
}