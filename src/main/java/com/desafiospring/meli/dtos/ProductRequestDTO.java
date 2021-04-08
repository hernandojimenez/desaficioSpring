package com.desafiospring.meli.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDTO {
    private String name;
    private String category;
    private String brand;
    private String price;
    private String quantity;
    private String freeShipping;
    private String prestige;
    private String order;

    public ProductRequestDTO(String name, String category, String brand, String price, String quantity, String freeShipping, String prestige, String order) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.freeShipping = freeShipping.equals("true")?"SI":"NO";
        this.prestige = prestige;
        this.order = order;
    }
}
