package org.nastya.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orderitem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem(Integer id, Integer bookId, Integer quantity, BigDecimal price, Order order) {
        this.id = id;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public OrderItem(Integer id, Integer bookId, Integer quantity, BigDecimal price) {
        this.id = id;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItem() {
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
