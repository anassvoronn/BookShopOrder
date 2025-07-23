package org.nastya.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nastya.dto.CurrentBalanceDTO;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.enums.OperationType;
import org.nastya.service.handler.operation.OperationHandler;
import org.nastya.repository.TransactionsHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionsHistoryService {
    private final TransactionsHistoryRepository transactionsHistoryRepository;
    private final TransactionsHistoryMapper transactionsHistoryMapper;
    private final List<OperationHandler> operationHandlers;
    private final Map<OperationType, OperationHandler> operationHandlerMap;

    @PostConstruct
    private void init() {
        for (OperationHandler operationHandler: operationHandlers){
            operationHandlerMap.put(operationHandler.getOperationType(), operationHandler);
        }
    }

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
    public TransactionsHistoryDTO processTransaction(OperationType operationType,
                                                     double amount,
                                                     Integer userId) {
        double currentBalance = getCurrentBalance(userId).getBalance();
        OperationHandler handler = operationHandlerMap.get(operationType);

        if (handler == null) {
            log.error("No handler found for operation type: {}", operationType);
            throw new IllegalArgumentException("Unknown operation type: " + operationType);
        }

        TransactionsHistoryDTO dto = handler.handle(amount, currentBalance, userId);

        log.info("Transaction completed successfully. Type: {}, User ID: {}, Amount: {}",
                operationType, userId, amount);
        return dto;
    }
}