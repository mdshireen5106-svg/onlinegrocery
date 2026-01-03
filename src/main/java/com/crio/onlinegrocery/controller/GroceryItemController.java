package com.crio.onlinegrocery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.onlinegrocery.entities.GroceryItemEntity;
import com.crio.onlinegrocery.repositories.GroceryItemRepository;

@RestController
@RequestMapping("/items")
public class GroceryItemController {

    private final GroceryItemRepository repo;

    public GroceryItemController(GroceryItemRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<GroceryItemEntity> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public GroceryItemEntity create(@RequestBody GroceryItemEntity item) {
        return repo.save(item);
    }
}
