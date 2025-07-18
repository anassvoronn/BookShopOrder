package org.nastya.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nastya.dto.BalanceOperationDTO;
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
        int balance = transactionsHistoryRepository.findCurrentBalanceByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Balance not found for user ID: {}", userId);
                    return new RuntimeException("User balance not found");
                });
        log.info("Returning balance for user {}: {}", userId, balance);
        return new CurrentBalanceDTO(userId, balance);
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
    public TransactionsHistoryDTO deposit(BalanceOperationDTO operationDTO,
                                          Integer userId) {
        log.info("Processing deposit request. User ID: {}, Amount: {}",
                operationDTO.getUserId(), operationDTO.getAmount());
        if (!userId.equals(operationDTO.getUserId())) {
            log.warn("Authorization failed! Authorized ID: {}, Request ID: {}",
                    userId, operationDTO.getUserId());
        }
        validateAmount(operationDTO.getAmount());
        int currentBalance = getCurrentBalanceOrZero(operationDTO.getUserId());
        log.info("Current balance for user {}: {}", operationDTO.getUserId(), currentBalance);
        int newBalance = currentBalance + operationDTO.getAmount();
        log.info("New balance after deposit: {}", newBalance);
        TransactionsHistory transaction = createTransaction(
                operationDTO.getUserId(),
                operationDTO.getAmount(),
                OperationType.DEPOSIT,
                newBalance
        );
        TransactionsHistory savedTransaction = transactionsHistoryRepository.save(transaction);
        log.info("Deposit completed. Transaction ID: {}, New balance: {}",
                savedTransaction.getId(), newBalance);
        return transactionsHistoryMapper.mapToDto(savedTransaction);
    }

    @Transactional
    public TransactionsHistoryDTO withdraw(BalanceOperationDTO operationDTO,
                                           Integer userId) {
        log.info("Processing withdraw request. User ID: {}, Amount: {}",
                operationDTO.getUserId(), operationDTO.getAmount());
        if (!userId.equals(operationDTO.getUserId())) {
            log.warn("Authorization failed! Authorized ID: {}, Request ID: {}",
                    userId, operationDTO.getUserId());
        }
        validateAmount(operationDTO.getAmount());

        int currentBalance = getCurrentBalance(operationDTO.getUserId()).getBalance();
        validateSufficientFunds(currentBalance, operationDTO.getAmount());

        int newBalance = currentBalance - operationDTO.getAmount();

        TransactionsHistory transaction = createTransaction(
                operationDTO.getUserId(),
                operationDTO.getAmount(),
                OperationType.WITHDRAWAL,
                newBalance
        );

        return transactionsHistoryMapper.mapToDto(
                transactionsHistoryRepository.save(transaction)
        );
    }

    private int getCurrentBalanceOrZero(Integer userId) {
        boolean userExists = transactionsHistoryRepository.existsByUserId(userId);
        log.info("User {} exists: {}", userId, userExists);
        return userExists
                ? getCurrentBalance(userId).getBalance()
                : 0;
    }

    private void validateAmount(int amount) {
        log.info("Validating amount: {}", amount);
        if (amount <= 0) {
            log.error("Invalid amount provided: {}", amount);
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void validateSufficientFunds(int currentBalance, int amount) {
        if (currentBalance < amount) {
            throw new RuntimeException("Insufficient funds");
        }
    }

    private TransactionsHistory createTransaction(Integer userId, int amount,
                                                  OperationType operationType, int balance) {
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