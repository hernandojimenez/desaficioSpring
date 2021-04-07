package com.desafiospring.meli.repositories;

import com.desafiospring.meli.dtos.ProductDTO;

import java.util.List;

public interface ArticleRepository {
    public List<ProductDTO> getArticles();
    List<ProductDTO> getArticleByCategory(String category, List<ProductDTO> products);
    List<ProductDTO> getArticleByName(String name,List<ProductDTO> products);
    List<ProductDTO> getArticleByBrand(String brand, List<ProductDTO> products);
    List<ProductDTO> getArticleByPrice(float price, List<ProductDTO> products);
    List<ProductDTO> getArticleByQuantity(int quantity, List<ProductDTO> products);
    List<ProductDTO> getArticleByFreeShipping(Boolean freeShipping,List<ProductDTO> products);
    List<ProductDTO> getArticleByPrestige(String prestige, List<ProductDTO> products);
}
