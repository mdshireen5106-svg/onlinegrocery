package com.crio.onlinegrocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crio.onlinegrocery.entities.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
