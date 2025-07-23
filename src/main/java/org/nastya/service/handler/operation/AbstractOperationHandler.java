package org.nastya.service.handler.operation;

import lombok.extern.log4j.Log4j2;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.enums.OperationType;
import org.nastya.repository.TransactionsHistoryRepository;
import org.nastya.service.TransactionsHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

@Log4j2
public abstract class AbstractOperationHandler {
    @Autowired
    private TransactionsHistoryRepository transactionsHistoryRepository;
    @Autowired
    private TransactionsHistoryMapper transactionsHistoryMapper;

    public void validateAmount(double amount) {
        log.info("Validating amount: {}", amount);
        if (amount <= 0) {
            log.error("Invalid amount provided: {}", amount);
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    public void validateSufficientFunds(double currentBalance, double amount) {
        if (currentBalance < amount) {
            throw new RuntimeException("Insufficient funds");
        }
    }

    public TransactionsHistory createTransaction(Integer userId, double amount,
                                                 OperationType operationType, double balance) {
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

    public TransactionsHistoryDTO save(TransactionsHistory transactionsHistory) {
        TransactionsHistory savedTransaction = transactionsHistoryRepository.save(transactionsHistory);
        log.info("Operation {} completed. Transaction ID: {}, New balance: {}",
                transactionsHistory.getOperationType(),
                       savedTransaction.getId(),
                transactionsHistory.getBalance()
        );

        return transactionsHistoryMapper.mapToDto(savedTransaction);
    }
}