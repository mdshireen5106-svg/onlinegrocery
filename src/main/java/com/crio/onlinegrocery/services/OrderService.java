package com.crio.onlinegrocery.services;

import java.util.List;
import com.crio.onlinegrocery.entities.OrderEntity;

public interface OrderService {

    OrderEntity createOrder(OrderEntity order);

    OrderEntity getOrderById(String orderId);

    List<OrderEntity> getAllOrders();

    List<OrderEntity> getOrdersByCustomerId(String customerId);

    void deleteOrder(String orderId);
}
