package com.crio.onlinegrocery.services;

import com.crio.onlinegrocery.entities.CustomerEntity;
import com.crio.onlinegrocery.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerRepository customerRepository;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository);
    }

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
    void createCustomer_success() {
        CustomerEntity customer = getCustomer();
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);

        CustomerEntity created = customerService.createCustomer(customer);

        assertNotNull(created);
        assertEquals("John Doe", created.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getCustomerById_success() {
        CustomerEntity customer = getCustomer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerEntity found = customerService.getCustomerById(1L);

        assertNotNull(found);
        assertEquals("john@example.com", found.getEmail());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void getCustomerById_notFound() {
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                customerService.getCustomerById(999L)
        );

        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, times(1)).findById(999L);
    }

    @Test
    void getAllCustomers_success() {
        when(customerRepository.findAll()).thenReturn(List.of(getCustomer()));

        List<CustomerEntity> customers = customerService.getAllCustomers();

        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void updateCustomer_success() {
        CustomerEntity existing = getCustomer();
        CustomerEntity updated = getCustomer();
        updated.setName("Updated Name");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(updated);

        CustomerEntity result = customerService.updateCustomer(1L, updated);

        assertEquals("Updated Name", result.getName());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(existing);
    }

    @Test
    void deleteCustomer_success() {
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}
