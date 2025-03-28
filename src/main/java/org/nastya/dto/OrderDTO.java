package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.nastya.enums.OrderStatus;

import java.util.List;

@Value
public class OrderDTO {
    Integer id;
    Integer userId;
    OrderStatus status;
    List<OrderItemDTO> items;

    @JsonCreator
    public OrderDTO(
            @JsonProperty("id") Integer id,
            @JsonProperty("userId") Integer userId,
            @JsonProperty("status") OrderStatus status,
            @JsonProperty("items") List<OrderItemDTO> items) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.items = items;
    }
}