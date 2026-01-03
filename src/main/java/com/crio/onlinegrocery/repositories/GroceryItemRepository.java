package com.crio.onlinegrocery.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.crio.onlinegrocery.entities.GroceryItemEntity;

public interface GroceryItemRepository extends MongoRepository<GroceryItemEntity, String> {
}
