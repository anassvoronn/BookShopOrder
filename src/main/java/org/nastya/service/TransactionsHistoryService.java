package org.nastya.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nastya.dto.CurrentBalanceDTO;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.enums.OperationType;
import org.nastya.repository.TransactionsHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionsHistoryService {
    private final TransactionsHistoryRepository transactionsHistoryRepository;
    private final TransactionsHistoryMapper transactionsHistoryMapper;

    public CurrentBalanceDTO getCurrentBalance(Integer userId) {
        log.info("Getting current balance for user ID: {}", userId);
        double balance = transactionsHistoryRepository.findCurrentBalanceByUserId(userId)
                .orElse(0.0);
        log.info("Returning balance for user: {}", balance);
        return new CurrentBalanceDTO(balance);
    }

    @Transactional
    public List<TransactionsHistoryDTO> getTransactionHistory(Integer userId) {
        log.info("Getting transaction history for user ID: {}", userId);

        List<TransactionsHistory> transactions = transactionsHistoryRepository.findByUserIdOrderByDateDesc(userId);

        log.info("Found {} transactions for user {}", transactions.size(), userId);
        return transactions.stream()
                .map(transactionsHistoryMapper::mapToDto)
                .toList();
    }

    @Transactional
    public TransactionsHistoryDTO deposit(double operationDTO,
                                          Integer userId) {
        validateAmount(operationDTO);
        double currentBalance = getCurrentBalance(userId).getBalance();
        log.info("Current balance for user: {}", currentBalance);
        double newBalance = currentBalance + operationDTO;
        log.info("New balance after deposit: {}", newBalance);
        TransactionsHistory transaction = createTransaction(
                userId,
                operationDTO,
                OperationType.DEPOSIT,
                newBalance
        );
        TransactionsHistory savedTransaction = transactionsHistoryRepository.save(transaction);
        log.info("Deposit completed. Transaction ID: {}, New balance: {}",
                savedTransaction.getId(), newBalance);
        return transactionsHistoryMapper.mapToDto(savedTransaction);
    }

    @Transactional
    public TransactionsHistoryDTO payment(double operationDTO,
                                          Integer userId) {
        validateAmount(operationDTO);

        double currentBalance = getCurrentBalance(userId).getBalance();
        validateSufficientFunds(currentBalance, operationDTO);

        double newBalance = currentBalance - operationDTO;

        TransactionsHistory transaction = createTransaction(
                userId,
                operationDTO,
                OperationType.PAYMENT,
                newBalance
        );

        return transactionsHistoryMapper.mapToDto(
                transactionsHistoryRepository.save(transaction)
        );
    }

    @Transactional
    public TransactionsHistoryDTO withdrawal(double operationDTO,
                                             Integer userId) {
        validateAmount(operationDTO);

        double currentBalance = getCurrentBalance(userId).getBalance();
        validateSufficientFunds(currentBalance, operationDTO);

        double newBalance = currentBalance - operationDTO;

        TransactionsHistory transaction = createTransaction(
                userId,
                operationDTO,
                OperationType.WITHDRAWAL,
                newBalance
        );

        TransactionsHistory savedTransaction = transactionsHistoryRepository.save(transaction);
        return transactionsHistoryMapper.mapToDto(savedTransaction);
    }

    private void validateAmount(double amount) {
        log.info("Validating amount: {}", amount);
        if (amount <= 0) {
            log.error("Invalid amount provided: {}", amount);
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void validateSufficientFunds(double currentBalance, double amount) {
        if (currentBalance < amount) {
            throw new RuntimeException("Insufficient funds");
        }
    }

    private TransactionsHistory createTransaction(Integer userId, double amount,
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
}