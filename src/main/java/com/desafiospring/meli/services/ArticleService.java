package com.desafiospring.meli.services;

import com.desafiospring.meli.dtos.ArticleRequestDTO;
import com.desafiospring.meli.dtos.ProductDTO;
import com.desafiospring.meli.dtos.ProductRequestDTO;
import com.desafiospring.meli.dtos.TicketResponseDTO;
import com.desafiospring.meli.exceptions.ArticleInsuficientException;
import com.desafiospring.meli.exceptions.ArticleNotFounException;
import com.desafiospring.meli.exceptions.ArticleOrderException;
import com.desafiospring.meli.exceptions.FilterPermittedException;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    List<ProductDTO> listFilter(ProductRequestDTO request) throws ArticleOrderException, ArticleNotFounException, FilterPermittedException;
    List<TicketResponseDTO> createRequestPurchase(ArticleRequestDTO articleRequestDTO) throws IOException, ArticleNotFounException, ArticleInsuficientException;
}
