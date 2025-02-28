package org.nastya.controller;

import org.nastya.dto.AddToOrderDTO;
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
    public ResponseEntity<String> addBookToCart(@RequestBody AddToOrderDTO addToOrderDTO,
                                                @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        try {
            Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
            orderService.addBookToCart(addToOrderDTO.getBookId(), userId);
            return ResponseEntity.ok("Book with ID " + addToOrderDTO + " successfully added to cart.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error adding book to cart: " + e.getMessage());
        }

    }
}
