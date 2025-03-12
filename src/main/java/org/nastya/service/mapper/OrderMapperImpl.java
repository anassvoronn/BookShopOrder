package org.nastya.service.mapper;

import org.nastya.dto.OrderDTO;
import org.nastya.dto.OrderItemDTO;
import org.nastya.entity.Order;
import org.nastya.entity.OrderItem;
import org.nastya.service.OrderItemMapper;
import org.nastya.service.OrderMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderMapperImpl(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderDTO mapToDTO(Order order) {
        Assert.notNull(order, "Entity must not be null");
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(orderItemMapper::mapToDTO)
                .collect(Collectors.toList());
        return new OrderDTO(order.getId(), order.getUserId(), order.getStatus(), itemDTOs);
    }

    @Override
    public Order mapToEntity(OrderDTO dto) {
        Assert.notNull(dto, "DTO must not be null");
        List<OrderItem> items = dto.getItems().stream()
                .map(orderItemMapper::mapToEntity)
                .collect(Collectors.toList());
        Order order = new Order();
        order.setId(dto.getId());
        order.setUserId(dto.getUserId());
        order.setStatus(dto.getStatus());
        order.setItems(items);
        return order;
    }
}