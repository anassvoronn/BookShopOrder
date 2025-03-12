package org.nastya.controller;

import org.nastya.dto.AddToOrderDTO;
import org.nastya.dto.OrderDTO;
import org.nastya.service.OrderService;
import org.nastya.service.exception.UserAuthorizationValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderService orderService;
    private final AuthorizationValidator authorizationValidator;

    public OrderController(OrderService orderService, AuthorizationValidator authorizationValidator) {
        this.orderService = orderService;
        this.authorizationValidator = authorizationValidator;
    }

    @PutMapping
    public void addBookToCart(@RequestBody AddToOrderDTO addToOrderDTO,
                              @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        try {
            Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
            orderService.addBookToCart(addToOrderDTO.getBookId(), userId);
        } catch (RuntimeException e) {
            log.error("Error occurred while adding the book to cart", e);
            throw e;
        }

    }

    @GetMapping
    public ResponseEntity<OrderDTO> getOrderById(@RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        return orderService.getOrderByUserId(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
