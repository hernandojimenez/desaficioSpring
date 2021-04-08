package com.desafiospring.meli.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPurchaseDTO {
    private Integer clientId;
    private List<ArticleDTO> articles;
}
