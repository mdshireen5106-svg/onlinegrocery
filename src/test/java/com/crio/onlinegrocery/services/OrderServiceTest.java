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

    private OrderEntity getOrder() {
        OrderEntity order = new OrderEntity();
        order.setOrderId(1L);
        order.setCustomerId(1L);
        order.setItemIds(List.of(101L, 102L)); 
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
        assertEquals(1L, created.getCustomerId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void getOrderById_success() {
        OrderEntity order = getOrder();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderEntity found = orderService.getOrderById(1L);

        assertEquals(100.0, found.getTotalPrice());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderById_notFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> orderService.getOrderById(999L));

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).findById(999L);
    }

    @Test
    void getAllOrders_success() {
        when(orderRepository.findAll()).thenReturn(List.of(getOrder()));

        List<OrderEntity> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
        assertEquals(1L, orders.get(0).getOrderId());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrdersByCustomerId_success() {
        OrderEntity order = getOrder();
        when(orderRepository.findByCustomerId(1L)).thenReturn(List.of(order));

        List<OrderEntity> orders = orderService.getOrdersByCustomerId(1L);

        assertEquals(1, orders.size());
        assertEquals(1L, orders.get(0).getOrderId());
        verify(orderRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void deleteOrder_success() {
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }
}
