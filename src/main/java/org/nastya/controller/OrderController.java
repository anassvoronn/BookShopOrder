package org.nastya.controller;

import lombok.RequiredArgsConstructor;
import org.nastya.dto.AddToOrderDTO;
import org.nastya.dto.OrderDTO;
import org.nastya.dto.UpdateBookQuantityDTO;
import org.nastya.service.OrderService;
import org.nastya.service.exception.UserAuthorizationValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderService orderService;
    private final AuthorizationValidator authorizationValidator;

    @PutMapping
    public void addBookToCart(@RequestBody AddToOrderDTO addToOrderDTO,
                              @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        orderService.addBookToCart(addToOrderDTO.getBookId(), userId);
    }

    @GetMapping
    public OrderDTO getOrderById(@RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        return orderService.getOrderByUserId(userId).get();
    }

    @PutMapping("/updateQuantity")
    public void updateBookQuantity(
            @RequestBody UpdateBookQuantityDTO updateBookQuantityDTO,
            @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        orderService.updateBookQuantityForUser(userId, updateBookQuantityDTO.getBookId(), updateBookQuantityDTO.getAmountToAdd());
    }

    @DeleteMapping("/delete")
    public void deleteOrderItem(@RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        orderService.deleteOrderItems(userId);
    }

    @DeleteMapping("/delete/{itemId}")
    public void deleteOrderItem(@PathVariable Integer itemId, @RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        orderService.deleteOrderItem(userId, itemId);
    }

    @PutMapping("/complete")
    public void completeOrder(@RequestHeader(HeaderConstants.SESSION_ID) String sessionId) throws UserAuthorizationValidationException {
        Integer userId = authorizationValidator.getUserIdIfAuthorized(sessionId);
        orderService.completeOrder(userId);
    }
}
