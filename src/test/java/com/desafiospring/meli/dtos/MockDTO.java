package com.desafiospring.meli.dtos;


import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockDTO {


    public List<ProductDTO> getProduct(){
        List<ProductDTO> list = new ArrayList<>();
        ProductDTO p1 = new ProductDTO(1,"Desmalezadora","Herramientas","Makita",9600f,72,true,"****");
        list.add(p1);
        return list;
    }
}
