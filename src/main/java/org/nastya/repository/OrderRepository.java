package org.nastya.repository;

import org.nastya.entity.Order;
import org.nastya.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByUserIdAndStatus(Integer userId, OrderStatus status);
}
