package org.nastya.dto;

import org.nastya.enums.OrderStatus;

import java.util.List;

public class OrderDTO {
    private Integer id;
    private Integer userId;
    private OrderStatus status;
    private List<OrderItemDTO> items;

    public OrderDTO(Integer id, Integer userId, OrderStatus status, List<OrderItemDTO> items) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.items = items;
    }

    public OrderDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}