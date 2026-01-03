package com.crio.onlinegrocery.services;

import java.util.List;
import com.crio.onlinegrocery.entities.OrderEntity;

public interface OrderService {

    OrderEntity createOrder(OrderEntity order);

    OrderEntity getOrderById(Long orderId);

    List<OrderEntity> getAllOrders();

    List<OrderEntity> getOrdersByCustomerId(Long customerId);

    void deleteOrder(Long orderId);
}
