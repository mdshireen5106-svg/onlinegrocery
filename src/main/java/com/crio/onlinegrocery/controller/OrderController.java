package com.crio.onlinegrocery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.onlinegrocery.entities.OrderEntity;
import com.crio.onlinegrocery.repositories.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repo;

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<OrderEntity> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public OrderEntity create(@RequestBody OrderEntity order) {
        return repo.save(order);
    }
}
