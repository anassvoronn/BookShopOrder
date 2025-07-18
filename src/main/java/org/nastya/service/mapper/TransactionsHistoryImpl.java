package org.nastya.service.mapper;

import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;
import org.nastya.service.TransactionsHistoryMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TransactionsHistoryImpl implements TransactionsHistoryMapper {
    @Override
    public TransactionsHistoryDTO mapToDto(TransactionsHistory transactionsHistory) {
        Assert.notNull(transactionsHistory, "Entity must not be null");
        return new TransactionsHistoryDTO(
                transactionsHistory.getId(),
                transactionsHistory.getAmount(),
                transactionsHistory.getOperationType(),
                transactionsHistory.getDate(),
                transactionsHistory.getBalance(),
                transactionsHistory.getUserId()
        );
    }
}