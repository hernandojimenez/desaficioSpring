package com.desafiospring.meli.repositories;

import com.desafiospring.meli.dtos.*;
import com.desafiospring.meli.exceptions.ArticleInsuficientException;
import com.desafiospring.meli.exceptions.ArticleNotFounException;
import com.desafiospring.meli.exceptions.ArticleOrderException;

import java.io.IOException;
import java.util.List;

public interface ArticleRepository {
    public List<ProductDTO> getArticles();
    List<ProductDTO> getArticleByCategory(String category, List<ProductDTO> products) throws ArticleNotFounException;
    List<ProductDTO> getArticleByName(String name,List<ProductDTO> products) throws ArticleNotFounException;
    List<ProductDTO> getArticleByBrand(String brand, List<ProductDTO> products) throws ArticleNotFounException;
    List<ProductDTO> getArticleByPrice(float price, List<ProductDTO> products) throws ArticleNotFounException;
    List<ProductDTO> getArticleByQuantity(int quantity, List<ProductDTO> products) throws ArticleNotFounException;
    List<ProductDTO> getArticleByFreeShipping(String freeShipping,List<ProductDTO> products) throws ArticleNotFounException;
    List<ProductDTO> getArticleByPrestige(String prestige, List<ProductDTO> products) throws ArticleNotFounException;
    List<ProductDTO> orderArticles(List<ProductDTO> products, int order) throws ArticleOrderException;
    List<TicketResponseDTO> createRequestPurchase(ArticleRequestDTO articleRequestDTO) throws IOException, ArticleNotFounException, ArticleInsuficientException;
    Float getValortotal(int i, List<TicketCreateDTO> listTicket);

}
