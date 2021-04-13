package com.desafiospring.meli.controller;

import com.desafiospring.meli.dtos.*;
import com.desafiospring.meli.services.ArticleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ProductControllerTest {

    @MockBean
    private ArticleService articleService;

    @Mock
    private MockDTO mockDTO;

    @Autowired
    private MockMvc mockMvc;

    private static List<ProductDTO> articlesIndumentaria;
    private static List<TicketResponseDTO> resposePurchase;
    private static ArticleRequestDTO articleRequestDTO;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {

        articlesIndumentaria =
                objectMapper.readValue(new File("src/main/resources/indumentaria.json"),
                        new TypeReference<>() {
                        });
        articleRequestDTO =
                objectMapper.readValue(new File("src/main/resources/jsonRequest.json"),
                        new TypeReference<>() {
                        });
        resposePurchase =
                objectMapper.readValue(new File("src/main/resources/purchaseResponse.json"),
                        new TypeReference<>() {
                        });

    }

    @Test
    public void getProduct() throws Exception {
        when(articleService.listFilter(any())).thenReturn(articlesIndumentaria);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/articles"))
                .andExpect(status().isOk()).andReturn();
        List<ProductDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {
                });
        //assert equals
        DataProductDTO p = new DataProductDTO();
        p.setData(articlesIndumentaria);
        DataProductDTO p2 = new DataProductDTO();
        p2.setData(responseArticles);
        Assertions.assertEquals(p, p2);
    }
    @Test
    public void createPurchaseRequestTest() throws Exception {
        when(articleService.createRequestPurchase(any())).thenReturn(resposePurchase);
        String jsonRequest = objectMapper.writeValueAsString(articleRequestDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/purchase-request").content(jsonRequest)
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        List<TicketResponseDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });
        Assertions.assertEquals(resposePurchase,responseArticles);

    }
}
