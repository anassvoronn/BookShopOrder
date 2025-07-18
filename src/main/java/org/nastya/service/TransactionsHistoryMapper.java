package org.nastya.service;

import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.entity.TransactionsHistory;

public interface TransactionsHistoryMapper {

    TransactionsHistoryDTO mapToDto(TransactionsHistory transactionsHistory);
}