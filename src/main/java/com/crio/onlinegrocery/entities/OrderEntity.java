package com.crio.onlinegrocery.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "orders")
public class OrderEntity {

    @Id
    private String orderId;

    @NotNull
    private String customerId;   

    @NotNull
    private List<String> itemIds; 

    @NotNull
    private LocalDate orderDate;

    @NotNull
    private Double totalPrice;
}
