package com.crio.onlinegrocery.controller;

import com.crio.onlinegrocery.OnlinegroceryApplication;
import com.crio.onlinegrocery.entities.OrderEntity;
import com.crio.onlinegrocery.repositories.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {OnlinegroceryApplication.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper method to create a dummy order
    private OrderEntity getOrder() {
        OrderEntity order = new OrderEntity();
        order.setOrderId("order123");
        order.setCustomerId("cust123");  // assuming you store customerId in OrderEntity
        order.setItemIds(List.of("item1", "item2")); // assuming you store itemIds in OrderEntity
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(100.0);
        return order;
    }

    @Test
    void getAllOrders_success() throws Exception {
        Mockito.when(orderRepository.findAll())
                .thenReturn(List.of(getOrder()));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("cust123"))
                .andExpect(jsonPath("$[0].totalPrice").value(100.0));
    }

    @Test
    void createOrder_success() throws Exception {
        OrderEntity order = getOrder();

        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class)))
                .thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("order123"))
                .andExpect(jsonPath("$.totalPrice").value(100.0));
    }
}
