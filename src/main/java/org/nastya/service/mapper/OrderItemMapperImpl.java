package org.nastya.service.mapper;

import org.nastya.dto.OrderItemDTO;
import org.nastya.entity.OrderItem;
import org.nastya.service.OrderItemMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemDTO mapToDTO(OrderItem orderItem) {
        Assert.notNull(orderItem, "Entity must not be null");
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getBookId(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }

    @Override
    public OrderItem mapToEntity(OrderItemDTO dto) {
        Assert.notNull(dto, "DTO must not be null");
        return new OrderItem(
                dto.getId(),
                dto.getBookId(),
                dto.getQuantity(),
                dto.getPrice()
        );
    }
}