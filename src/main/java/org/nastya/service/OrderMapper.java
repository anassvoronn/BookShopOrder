package org.nastya.service;

import org.nastya.dto.OrderDTO;
import org.nastya.entity.Order;

public interface OrderMapper {

    OrderDTO mapToDTO(Order order);

    Order mapToEntity(OrderDTO dto);
}