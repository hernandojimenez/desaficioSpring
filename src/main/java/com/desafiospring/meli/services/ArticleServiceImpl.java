package com.desafiospring.meli.services;

import com.desafiospring.meli.dtos.ArticleRequestDTO;
import com.desafiospring.meli.dtos.ProductDTO;
import com.desafiospring.meli.dtos.ProductRequestDTO;
import com.desafiospring.meli.dtos.TicketResponseDTO;
import com.desafiospring.meli.exceptions.ArticleInsuficientException;
import com.desafiospring.meli.exceptions.ArticleNotFounException;
import com.desafiospring.meli.exceptions.ArticleOrderException;
import com.desafiospring.meli.exceptions.FilterPermittedException;
import com.desafiospring.meli.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleRepository articleRepository;

    //Realiza los diferentes validaciones, para asi obtener la respuesta solicitada
    @Override
    public List<ProductDTO> listFilter(ProductRequestDTO request) throws ArticleOrderException, FilterPermittedException, ArticleNotFounException {
        List<ProductDTO> dataProduct = articleRepository.getArticles();
        int cont=0;
            if(request.getName()!=null && cont<2){
                dataProduct = articleRepository.getArticleByName(request.getName(),dataProduct);
                cont++;
            }
            if(request.getCategory()!=null && cont<2){
                dataProduct = articleRepository.getArticleByCategory(request.getCategory(),dataProduct);
                cont++;
            }
            if(request.getBrand()!=null && cont<2){
                dataProduct = articleRepository.getArticleByBrand(request.getBrand(),dataProduct);
                cont++;
            }if(request.getPrice() !=null && cont<2){
                dataProduct = articleRepository.getArticleByPrice(Float.parseFloat(request.getPrice()),dataProduct);
                cont++;
            }if(request.getQuantity() !=null && cont<2){
                dataProduct = articleRepository.getArticleByQuantity(Integer.parseInt(request.getQuantity()),dataProduct);
                cont++;
            }if(request.getFreeShipping()!=null && cont<2){
                dataProduct = articleRepository.getArticleByFreeShipping(request.getFreeShipping().equals("SI")?true:false,dataProduct);
                cont++;
            }if(request.getPrestige() !=null && cont<2){
                dataProduct = articleRepository.getArticleByPrestige(request.getPrestige(),dataProduct);
            }
            if(request.getOrder()!=null){
                dataProduct = articleRepository.orderArticles(dataProduct,Integer.parseInt(request.getOrder()));
            }
            if(cont>1 && request.getOrder()==null) throw new FilterPermittedException("Supero el maximo de filtros permitidos");
        return dataProduct;
    }

    //Crea la solicitud de compra
    @Override
    public List<TicketResponseDTO> createRequestPurchase(ArticleRequestDTO articleRequestDTO) throws IOException, ArticleNotFounException, ArticleInsuficientException {
        return articleRepository.createRequestPurchase(articleRequestDTO);
    }

}
