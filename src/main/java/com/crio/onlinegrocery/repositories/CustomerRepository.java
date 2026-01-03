package com.crio.onlinegrocery.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.crio.onlinegrocery.entities.CustomerEntity;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
}
