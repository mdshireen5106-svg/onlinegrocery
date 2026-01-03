package com.crio.onlinegrocery.controller;

import com.crio.onlinegrocery.OnlinegroceryApplication;
import com.crio.onlinegrocery.entities.GroceryItemEntity;
import com.crio.onlinegrocery.repositories.GroceryItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {OnlinegroceryApplication.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
class GroceryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroceryItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllItems_success() throws Exception {
        Mockito.when(itemRepository.findAll())
                .thenReturn(List.of(getItem()));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Milk"))
                .andExpect(jsonPath("$[0].category").value("Dairy"));
    }

    @Test
    void createItem_success() throws Exception {
        GroceryItemEntity item = getItem();

        Mockito.when(itemRepository.save(Mockito.any(GroceryItemEntity.class)))
                .thenReturn(item);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Milk"))
                .andExpect(jsonPath("$.price").value(45.0));
    }

    @Test
    void getItemById_success() throws Exception {
        Mockito.when(itemRepository.findById(1L))
                .thenReturn(java.util.Optional.of(getItem()));

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Milk"))
                .andExpect(jsonPath("$.category").value("Dairy"));
    }

    @Test
    void updateItem_success() throws Exception {
        GroceryItemEntity existing = getItem();
        GroceryItemEntity updated = getItem();
        updated.setName("Cheese");

        Mockito.when(itemRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existing));
        Mockito.when(itemRepository.save(Mockito.any(GroceryItemEntity.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cheese"));
    }

    @Test
    void deleteItem_success() throws Exception {
        Mockito.when(itemRepository.findById(1L))
                .thenReturn(java.util.Optional.of(getItem()));

        mockMvc.perform(delete("/items/1"))
                .andExpect(status().isOk());
    }
}
