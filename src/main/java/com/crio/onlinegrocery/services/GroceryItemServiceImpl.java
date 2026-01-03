package com.crio.onlinegrocery.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crio.onlinegrocery.entities.GroceryItemEntity;
import com.crio.onlinegrocery.repositories.GroceryItemRepository;

@Service
public class GroceryItemServiceImpl implements GroceryItemService {

    private final GroceryItemRepository itemRepository;

    public GroceryItemServiceImpl(GroceryItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public GroceryItemEntity createItem(GroceryItemEntity item) {
        return itemRepository.save(item);
    }

    @Override
    public GroceryItemEntity getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Grocery item not found"));
    }

    @Override
    public List<GroceryItemEntity> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public GroceryItemEntity updateItem(Long itemId, GroceryItemEntity updatedItem) {
        GroceryItemEntity existing = getItemById(itemId);

        existing.setName(updatedItem.getName());
        existing.setCategory(updatedItem.getCategory());
        existing.setPrice(updatedItem.getPrice());
        existing.setQuantity(updatedItem.getQuantity());

        return itemRepository.save(existing);
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
