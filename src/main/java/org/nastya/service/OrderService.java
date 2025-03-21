package org.nastya.service;

import org.nastya.dto.OrderDTO;
import org.nastya.entity.Order;
import org.nastya.entity.OrderItem;
import org.nastya.enums.OrderStatus;
import org.nastya.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public Optional<OrderDTO> getOrderByUserId(Integer userId) {
        return orderRepository.findByUserId(userId)
                .map(orderMapper::mapToDTO);
    }

    public void addBookToCart(Integer bookId, Integer userId) {
        Order order = orderRepository.findByUserId(userId)
                .orElseGet(() -> createNewOrder(userId));
        Optional<OrderItem> existingOrderItem = order.getItems().stream()
                .filter(item -> item.getBookId().equals(bookId))
                .findFirst();
        if (existingOrderItem.isPresent()) {
            OrderItem item = existingOrderItem.get();
            item.setQuantity(item.getQuantity() + 1);
            log.info("We increase the number of books with ID {} in the cart of the user with ID {}", bookId, userId);
        } else {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setBookId(bookId);
            newOrderItem.setQuantity(1);
            BigDecimal price = new BigDecimal("10.00");
            newOrderItem.setPrice(price);
            newOrderItem.setOrder(order);
            order.getItems().add(newOrderItem);
            log.info("A book with ID {} has been added to the cart of a user with ID {}", bookId, userId);
        }
        orderRepository.save(order);
    }

    private Order createNewOrder(Integer userId) {
        log.warn("The order for user ID {} was not found. We create a new order.", userId);
        Order newOrder = new Order();
        newOrder.setUserId(userId);
        newOrder.setItems(new ArrayList<>());
        newOrder.setStatus(OrderStatus.NEW);
        return newOrder;
    }

    public void updateBookQuantity(Order order, Integer bookId, Integer amountToAdd) {
        for (OrderItem item : order.getItems()) {
            if (item.getBookId().equals(bookId)) {
                int newQuantity = item.getQuantity() + amountToAdd;
                if (newQuantity < 0) {
                    throw new IllegalStateException("Quantity cannot be negative");
                }
                item.setQuantity(newQuantity);
                log.info("Updated amountToAdd for book ID {}: new amountToAdd is {}", bookId, newQuantity);
                orderRepository.save(order);
                return;
            }
        }
        throw new EntityNotFoundException("Book not found in the order");
    }

    public void updateBookQuantityForUser(Integer userId, Integer bookId, Integer amountToAdd) {
        Order order = orderRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for user ID " + userId));
        updateBookQuantity(order, bookId, amountToAdd);
    }
}
