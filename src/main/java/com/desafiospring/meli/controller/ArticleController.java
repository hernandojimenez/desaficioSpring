package com.desafiospring.meli.controller;

import com.desafiospring.meli.dtos.*;
import com.desafiospring.meli.exceptions.ArticleInsuficientException;
import com.desafiospring.meli.exceptions.ArticleNotFounException;
import com.desafiospring.meli.exceptions.ArticleOrderException;
import com.desafiospring.meli.exceptions.FilterPermittedException;
import com.desafiospring.meli.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //Obtener listado de articulos, total y por filros
    @GetMapping("/articles")
    public ResponseEntity<DataProductDTO> getArticleByCategory(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String category,
                                                               @RequestParam(required = false) String brand,
                                                               @RequestParam(required = false) String price,
                                                               @RequestParam(required = false) String quantity,
                                                               @RequestParam(required = false, defaultValue = "") String freeShipping,
                                                               @RequestParam(required = false) String prestige,
                                                               @RequestParam(required = false) String order) throws ArticleOrderException, ArticleNotFounException, FilterPermittedException {
        List<ProductDTO> products = new ArrayList<>();
        DataProductDTO productsData = new DataProductDTO();
        ProductRequestDTO request = new ProductRequestDTO(name,category,brand,price,quantity,freeShipping,prestige,order);
        products = articleService.listFilter(request);
        productsData.setData(products);
        return new ResponseEntity(articleService.listFilter(request), HttpStatus.OK);
    }
    //Solicitud de compra de uno o varios articulos y devolver el total de sus compras
    @PostMapping("/purchase-request")
    public ResponseEntity<TicketResponseDTO> createPurchaseRequest(@RequestBody ArticleRequestDTO articleRequestDTO) throws IOException, ArticleNotFounException, ArticleInsuficientException {
        return new ResponseEntity(articleService.createRequestPurchase(articleRequestDTO),HttpStatus.OK);
    }
    @ExceptionHandler(ArticleNotFounException.class)
    public ResponseEntity exceptionHandler(ArticleNotFounException e) {
        return new ResponseEntity(new MessageErrorDTO("Articulo",e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ArticleOrderException.class)
    public ResponseEntity exceptionHandlerOrder(ArticleOrderException e) {
        return new ResponseEntity(new MessageErrorDTO("Order no valid",e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ArticleInsuficientException.class)
    public ResponseEntity exceptionHandlerStoch(ArticleInsuficientException e) {
        return new ResponseEntity(new MessageErrorDTO("El stock no se encuentra completo",e.getMessage()),HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(FilterPermittedException.class)
    public ResponseEntity exceptionHandlerStoch(FilterPermittedException e) {
        return new ResponseEntity(new MessageErrorDTO("Filter",e.getMessage()),HttpStatus.BAD_REQUEST);
    }


}
