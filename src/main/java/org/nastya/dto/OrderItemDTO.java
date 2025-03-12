package org.nastya.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Integer id;
    private Integer bookId;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemDTO(Integer id, Integer bookId, Integer quantity, BigDecimal price) {
        this.id = id;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}