package com.crio.onlinegrocery.services;

import java.util.List;
import com.crio.onlinegrocery.entities.CustomerEntity;

public interface CustomerService {

    CustomerEntity createCustomer(CustomerEntity customer);

    CustomerEntity getCustomerById(Long customerId);

    List<CustomerEntity> getAllCustomers();

    CustomerEntity updateCustomer(Long customerId, CustomerEntity customer);

    void deleteCustomer(Long customerId);
}
