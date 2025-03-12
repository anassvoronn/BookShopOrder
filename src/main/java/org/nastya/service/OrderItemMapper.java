package org.nastya.service;

import org.nastya.dto.OrderItemDTO;
import org.nastya.entity.OrderItem;

public interface OrderItemMapper {

    OrderItemDTO mapToDTO(OrderItem orderItem);

    OrderItem mapToEntity(OrderItemDTO dto);
}