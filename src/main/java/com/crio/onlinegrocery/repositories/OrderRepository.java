package com.crio.onlinegrocery.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.crio.onlinegrocery.entities.OrderEntity;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    List<OrderEntity> findByCustomerId(String customerId);
}
