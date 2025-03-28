package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderItemDTO {
    Integer id;
    Integer bookId;
    Integer quantity;
    BigDecimal price;

    public OrderItemDTO(
            @JsonProperty("id") Integer id,
            @JsonProperty("bookId") Integer bookId,
            @JsonProperty("quantity") Integer quantity,
            @JsonProperty("price") BigDecimal price) {
        this.id = id;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }
}