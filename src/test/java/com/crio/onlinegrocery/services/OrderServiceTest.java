package com.crio.onlinegrocery.services;

import com.crio.onlinegrocery.entities.OrderEntity;
import com.crio.onlinegrocery.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderServiceImpl(orderRepository);
    }

    // Helper method to create a sample order
    private OrderEntity getOrder() {
        OrderEntity order = new OrderEntity();
        order.setOrderId("order123");
        order.setCustomerId("cust123");
        order.setItemIds(List.of("item1", "item2")); // assuming itemIds list
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(100.0);
        return order;
    }

    @Test
    void createOrder_success() {
        OrderEntity order = getOrder();
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);

        OrderEntity created = orderService.createOrder(order);

        assertNotNull(created);
        assertEquals("cust123", created.getCustomerId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void getOrderById_success() {
        OrderEntity order = getOrder();
        when(orderRepository.findById("order123")).thenReturn(Optional.of(order));

        OrderEntity found = orderService.getOrderById("order123");

        assertEquals(100.0, found.getTotalPrice());
        verify(orderRepository, times(1)).findById("order123");
    }

    @Test
    void getOrderById_notFound() {
        when(orderRepository.findById("invalid")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> orderService.getOrderById("invalid"));

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).findById("invalid");
    }

    @Test
    void getAllOrders_success() {
        when(orderRepository.findAll()).thenReturn(List.of(getOrder()));

        List<OrderEntity> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
        assertEquals("order123", orders.get(0).getOrderId());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrdersByCustomerId_success() {
        OrderEntity order = getOrder();
        when(orderRepository.findByCustomerId("cust123")).thenReturn(List.of(order));

        List<OrderEntity> orders = orderService.getOrdersByCustomerId("cust123");

        assertEquals(1, orders.size());
        assertEquals("order123", orders.get(0).getOrderId());
        verify(orderRepository, times(1)).findByCustomerId("cust123");
    }

    @Test
    void deleteOrder_success() {
        doNothing().when(orderRepository).deleteById("order123");

        orderService.deleteOrder("order123");

        verify(orderRepository, times(1)).deleteById("order123");
    }
}
