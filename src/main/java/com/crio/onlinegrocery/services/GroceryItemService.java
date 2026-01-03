package com.crio.onlinegrocery.services;

import java.util.List;
import com.crio.onlinegrocery.entities.GroceryItemEntity;

public interface GroceryItemService {

    GroceryItemEntity createItem(GroceryItemEntity item);

    GroceryItemEntity getItemById(Long itemId);

    List<GroceryItemEntity> getAllItems();

    GroceryItemEntity updateItem(Long itemId, GroceryItemEntity item);

    void deleteItem(Long itemId);
}
