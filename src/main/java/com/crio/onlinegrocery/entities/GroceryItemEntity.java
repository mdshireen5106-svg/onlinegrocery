package com.crio.onlinegrocery.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "grocery_items")
public class GroceryItemEntity {

    @Id
    private String itemId;

    @NotNull
    private String name;

    @NotNull
    private String category;

    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;
}
