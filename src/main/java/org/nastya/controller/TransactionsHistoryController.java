package org.nastya.controller;

import lombok.RequiredArgsConstructor;
import org.nastya.dto.BalanceOperationDTO;
import org.nastya.dto.CurrentBalanceDTO;
import org.nastya.dto.TransactionsHistoryDTO;
import org.nastya.lib.auth.AuthorizationValidator;
import org.nastya.lib.auth.exception.UserAuthorizationValidationException;
import org.nastya.service.TransactionsHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactionsHistory")
public class TransactionsHistoryController {
    private final TransactionsHistoryService transactionsHistoryService;
    private final AuthorizationValidator authorizationValidator;

    @GetMapping("/{userId}")
    public CurrentBalanceDTO getBalance(@RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        return transactionsHistoryService.getCurrentBalance(userId);
    }

    @GetMapping("/{userId}/history")
    public List<TransactionsHistoryDTO> getHistory(@RequestHeader(HeaderConstants.SESSION_ID) String sessionId)
            throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        return transactionsHistoryService.getTransactionHistory(userId);
    }

    @PostMapping("/deposit")
    public TransactionsHistoryDTO deposit(
            @RequestBody BalanceOperationDTO operationDTO,
            @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        return transactionsHistoryService.deposit(operationDTO, userId);
    }

    @PostMapping("/withdraw")
    public TransactionsHistoryDTO withdraw(
            @RequestBody BalanceOperationDTO operationDTO,
            @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        return transactionsHistoryService.withdraw(operationDTO, userId);
    }
}