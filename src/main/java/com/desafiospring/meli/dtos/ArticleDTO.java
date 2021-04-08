package com.desafiospring.meli.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Integer productId;
    private String name;
    private String brand;
    private Integer quantity;
}
