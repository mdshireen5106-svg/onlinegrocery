package com.crio.onlinegrocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crio.onlinegrocery.entities.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerId(Long customerId);
}
