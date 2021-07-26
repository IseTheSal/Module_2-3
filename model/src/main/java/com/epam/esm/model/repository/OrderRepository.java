package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByUserId(long userId, Pageable pageable);

    Optional<Order> findByIdAndUserId(long orderId, long userId);
}