package org.nastya.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.enums.OperationType;
import org.nastya.repository.TransactionsHistoryRepository;
import org.nastya.service.handler.operation.DepositOperationHandler;
import org.nastya.service.handler.operation.PaymentOperationHandler;
import org.nastya.service.handler.operation.WithdrawalOperationHandler;
import org.nastya.service.mapper.TransactionsHistoryMapperImpl;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class HandlerTest {
    @Mock
    private TransactionsHistoryRepository historyRepository;
    @Spy
    private TransactionsHistoryMapperImpl historyMapper;
    @InjectMocks
    private DepositOperationHandler depositHandler;
    @InjectMocks
    private PaymentOperationHandler paymentHandler;
    @InjectMocks
    private WithdrawalOperationHandler withdrawalHandler;

    @Test
    void increaseBalance() {
        Integer userId = 1;
        double amount = 15.9999999999991;
        double currentBalance = 0.00000000000002;

        TransactionsHistory savedTransaction = new TransactionsHistory();
        savedTransaction.setId(1);
        savedTransaction.setUserId(userId);
        savedTransaction.setAmount(amount);
        savedTransaction.setOperationType(OperationType.DEPOSIT);
        savedTransaction.setBalance(currentBalance + amount);
        savedTransaction.setDate(ZonedDateTime.now());

        Mockito.when(historyRepository.save(any(TransactionsHistory.class))).thenReturn(savedTransaction);

        TransactionsHistoryDTO dto = depositHandler.handle(amount, currentBalance, userId);

        assertNotNull(dto);
        assertEquals(savedTransaction.getId(), dto.getId());
        assertEquals(amount, dto.getAmount());
        assertEquals(OperationType.DEPOSIT, dto.getOperationType());
        assertEquals(currentBalance + amount, dto.getBalance());

        Mockito.verify(historyRepository).save(any(TransactionsHistory.class));
        Mockito.verify(historyMapper).mapToDto(savedTransaction);
    }

    @Test
    void exceptionWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> depositHandler.handle(-154, 500, 1));
        assertThrows(IllegalArgumentException.class,
                () -> withdrawalHandler.handle(-120.47, 125, 1));
        assertThrows(IllegalArgumentException.class,
                () -> paymentHandler.handle(-367.001, 400.9991, 1));
    }

    @Test
    void exceptionWhenAmountIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> depositHandler.handle(0, 350, 1));
    }

    @Test
    void paymentOperation() {
        int userId = 1;
        double amount = 195.01;
        double currentBalance = 196.999999999991;

        TransactionsHistory savedTransaction = new TransactionsHistory();
        savedTransaction.setId(1);
        savedTransaction.setUserId(userId);
        savedTransaction.setAmount(amount);
        savedTransaction.setOperationType(OperationType.PAYMENT);
        savedTransaction.setBalance(currentBalance - amount);
        savedTransaction.setDate(ZonedDateTime.now());

        Mockito.when(historyRepository.save(any(TransactionsHistory.class))).thenReturn(savedTransaction);

        TransactionsHistoryDTO result = paymentHandler.handle(amount, currentBalance, userId);

        assertNotNull(result);
        assertEquals(savedTransaction.getId(), result.getId());
        assertEquals(amount, result.getAmount());
        assertEquals(OperationType.PAYMENT, result.getOperationType());
        assertEquals(currentBalance - amount, result.getBalance());

        Mockito.verify(historyRepository).save(any(TransactionsHistory.class));
        Mockito.verify(historyMapper).mapToDto(savedTransaction);
    }

    @Test
    void exceptionWhenInsufficientFunds() {
        assertThrows(RuntimeException.class,
                () -> paymentHandler.handle(780, 220, 1));
        assertThrows(RuntimeException.class,
                () -> withdrawalHandler.handle(999.45, 110, 1));
    }

    @Test
    void withdrawalOfMoney() {
        int userId = 1;
        double amount = 110.44;
        double currentBalance = 111.000000000045;

        TransactionsHistory savedTransaction = new TransactionsHistory();
        savedTransaction.setId(1);
        savedTransaction.setUserId(userId);
        savedTransaction.setAmount(amount);
        savedTransaction.setOperationType(OperationType.WITHDRAWAL);
        savedTransaction.setBalance(currentBalance - amount);
        savedTransaction.setDate(ZonedDateTime.now());

        Mockito.when(historyRepository.save(any(TransactionsHistory.class))).thenReturn(savedTransaction);

        TransactionsHistoryDTO result = withdrawalHandler.handle(amount, currentBalance, userId);

        assertNotNull(result);
        assertEquals(savedTransaction.getId(), result.getId());
        assertEquals(amount, result.getAmount());
        assertEquals(OperationType.WITHDRAWAL, result.getOperationType());
        assertEquals(currentBalance - amount, result.getBalance());

        Mockito.verify(historyRepository).save(any(TransactionsHistory.class));
        Mockito.verify(historyMapper).mapToDto(savedTransaction);
    }
}