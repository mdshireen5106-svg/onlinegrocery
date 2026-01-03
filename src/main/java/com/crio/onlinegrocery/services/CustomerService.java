package com.crio.onlinegrocery.services;

import java.util.List;
import com.crio.onlinegrocery.entities.CustomerEntity;

public interface CustomerService {

    CustomerEntity createCustomer(CustomerEntity customer);

    CustomerEntity getCustomerById(String customerId);

    List<CustomerEntity> getAllCustomers();

    CustomerEntity updateCustomer(String customerId, CustomerEntity customer);

    void deleteCustomer(String customerId);
}
