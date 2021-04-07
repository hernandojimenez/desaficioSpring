package com.desafiospring.meli.controller;

import com.desafiospring.meli.dtos.DataProductDTO;
import com.desafiospring.meli.dtos.ProductDTO;
import com.desafiospring.meli.dtos.ProductRequestDTO;
import com.desafiospring.meli.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity<DataProductDTO> getArticleByCategory(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String category,
                                                               @RequestParam(required = false) String brand,
                                                               @RequestParam(required = false) String price,
                                                               @RequestParam(required = false) String quantity,
                                                               @RequestParam(required = false) String freeShipping,
                                                               @RequestParam(required = false) String prestige,
                                                               @RequestParam(required = false) String order){
        List<ProductDTO> products = new ArrayList<>();
        DataProductDTO productsData = new DataProductDTO();
        ProductRequestDTO request = new ProductRequestDTO(name,category,brand,price,quantity,freeShipping,prestige,order);
        products = articleService.listFilter(request);
        productsData.setDataProduct(products);
        return new ResponseEntity(productsData, HttpStatus.OK);
    }
}
