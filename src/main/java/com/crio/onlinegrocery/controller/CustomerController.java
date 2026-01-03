package com.crio.onlinegrocery.controller;

import com.crio.onlinegrocery.entities.CustomerEntity;
import com.crio.onlinegrocery.repositories.CustomerRepository;
import com.crio.onlinegrocery.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<CustomerEntity> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public CustomerEntity getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @PostMapping
    public CustomerEntity create(@RequestBody CustomerEntity customer) {
        return repo.save(customer);
    }

    @PutMapping("/{id}")
    public CustomerEntity update(@PathVariable Long id, @RequestBody CustomerEntity updated) {
        CustomerEntity c = getById(id);
        c.setName(updated.getName());
        c.setEmail(updated.getEmail());
        c.setAddress(updated.getAddress());
        c.setPhone(updated.getPhone());
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.delete(getById(id));
    }
}
