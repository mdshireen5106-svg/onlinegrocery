package com.crio.onlinegrocery.services;

import java.util.List;
import com.crio.onlinegrocery.entities.GroceryItemEntity;

public interface GroceryItemService {

    GroceryItemEntity createItem(GroceryItemEntity item);

    GroceryItemEntity getItemById(String itemId);

    List<GroceryItemEntity> getAllItems();

    GroceryItemEntity updateItem(String itemId, GroceryItemEntity item);

    void deleteItem(String itemId);
}
