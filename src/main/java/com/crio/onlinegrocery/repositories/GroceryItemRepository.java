package com.crio.onlinegrocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crio.onlinegrocery.entities.GroceryItemEntity;

public interface GroceryItemRepository extends JpaRepository<GroceryItemEntity, Long> {
}
