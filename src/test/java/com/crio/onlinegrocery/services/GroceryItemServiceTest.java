package com.crio.onlinegrocery.services;

import com.crio.onlinegrocery.entities.GroceryItemEntity;
import com.crio.onlinegrocery.repositories.GroceryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroceryItemServiceImplTest {

    private GroceryItemRepository itemRepository;
    private GroceryItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        itemRepository = Mockito.mock(GroceryItemRepository.class);
        itemService = new GroceryItemServiceImpl(itemRepository);
    }

    private GroceryItemEntity getItem() {
        GroceryItemEntity item = new GroceryItemEntity();
        item.setItemId(1L); 
        item.setName("Milk");
        item.setCategory("Dairy");
        item.setPrice(45.0);
        item.setQuantity(10);
        return item;
    }

    @Test
    void createItem_success() {
        GroceryItemEntity item = getItem();
        when(itemRepository.save(any(GroceryItemEntity.class))).thenReturn(item);

        GroceryItemEntity created = itemService.createItem(item);

        assertNotNull(created);
        assertEquals("Milk", created.getName());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void getItemById_success() {
        GroceryItemEntity item = getItem();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        GroceryItemEntity found = itemService.getItemById(1L);

        assertEquals("Dairy", found.getCategory());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void getItemById_notFound() {
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> itemService.getItemById(999L));

        assertEquals("Grocery item not found", exception.getMessage());
        verify(itemRepository, times(1)).findById(999L);
    }

    @Test
    void getAllItems_success() {
        when(itemRepository.findAll()).thenReturn(List.of(getItem()));

        List<GroceryItemEntity> items = itemService.getAllItems();

        assertEquals(1, items.size());
        assertEquals("Milk", items.get(0).getName());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void updateItem_success() {
        GroceryItemEntity existing = getItem();
        GroceryItemEntity updated = getItem();
        updated.setPrice(50.0);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(itemRepository.save(any(GroceryItemEntity.class))).thenReturn(updated);

        GroceryItemEntity result = itemService.updateItem(1L, updated);

        assertEquals(50.0, result.getPrice());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(existing);
    }

    @Test
    void deleteItem_success() {
        doNothing().when(itemRepository).deleteById(1L);

        itemService.deleteItem(1L);

        verify(itemRepository, times(1)).deleteById(1L);
    }
}
