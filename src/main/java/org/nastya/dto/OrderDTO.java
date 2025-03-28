package org.nastya.dto;

import lombok.Value;
import org.nastya.enums.OrderStatus;

import java.util.List;

@Value
public class OrderDTO {
    Integer id;
    Integer userId;
    OrderStatus status;
    List<OrderItemDTO> items;
}