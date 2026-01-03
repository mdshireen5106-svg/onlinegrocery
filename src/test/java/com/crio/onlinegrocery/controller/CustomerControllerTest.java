package com.crio.onlinegrocery.controller;

import com.crio.onlinegrocery.OnlinegroceryApplication;
import com.crio.onlinegrocery.entities.CustomerEntity;
import com.crio.onlinegrocery.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {OnlinegroceryApplication.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerEntity getCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAddress("Bangalore");
        customer.setPhone("9999999999");
        return customer;
    }

    @Test
    void getAllCustomers_success() throws Exception {
        Mockito.when(customerRepository.findAll())
                .thenReturn(List.of(getCustomer()));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getCustomerById_success() throws Exception {
        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.of(getCustomer()));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getCustomerById_notFound() throws Exception {
        Mockito.when(customerRepository.findById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/customers/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCustomer_success() throws Exception {
        CustomerEntity customer = getCustomer();

        Mockito.when(customerRepository.save(Mockito.any(CustomerEntity.class)))
                .thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void updateCustomer_success() throws Exception {
        CustomerEntity existing = getCustomer();
        CustomerEntity updated = getCustomer();
        updated.setName("Updated Name");

        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        Mockito.when(customerRepository.save(Mockito.any(CustomerEntity.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void deleteCustomer_success() throws Exception {
        Mockito.when(customerRepository.findById(1L))
                .thenReturn(Optional.of(getCustomer()));

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk());
    }
}
