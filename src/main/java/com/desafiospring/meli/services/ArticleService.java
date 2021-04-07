package com.desafiospring.meli.services;

import com.desafiospring.meli.dtos.ProductDTO;
import com.desafiospring.meli.dtos.ProductRequestDTO;

import java.util.List;

public interface ArticleService {
    List<ProductDTO> listFilter(ProductRequestDTO request);
}
