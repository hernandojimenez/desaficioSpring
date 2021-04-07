package com.desafiospring.meli.repositories;

import com.desafiospring.meli.dtos.ProductDTO;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository{
    @Override
    public List<ProductDTO> getArticles() {
        return loadDataBase();
    }

    @Override
    public List<ProductDTO> getArticleByCategory(String category,List<ProductDTO> products) {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getCategory().toLowerCase().contains(category.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return listArticle;
    }
    @Override
    public List<ProductDTO> getArticleByName(String name, List<ProductDTO> products) {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return listArticle;
    }

    @Override
    public List<ProductDTO> getArticleByBrand(String brand, List<ProductDTO> products) {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getBrand().toLowerCase().contains(brand.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return listArticle;
    }

    @Override
    public List<ProductDTO> getArticleByPrice(float price, List<ProductDTO> products) {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            Predicate<ProductDTO> prices = product -> product.getPrice()==price;
            listArticle = products.stream()
                    .filter(prices).collect(Collectors.toList());
        }
        return listArticle;
    }

    @Override
    public List<ProductDTO> getArticleByQuantity(int quantity, List<ProductDTO> products) {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            Predicate<ProductDTO> quanty = product -> product.getQuantity()==quantity;
            listArticle = products.stream()
                    .filter(quanty).collect(Collectors.toList());
        }
        return listArticle;
    }

    @Override
    public List<ProductDTO> getArticleByFreeShipping(Boolean freeShipping, List<ProductDTO> products) {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getFreeShipping().toString().contains(freeShipping.toString()))
                    .collect(Collectors.toList());
        }
        return listArticle;
    }

    @Override
    public List<ProductDTO> getArticleByPrestige(String prestige, List<ProductDTO> products) {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getPrestige().toLowerCase().contains(prestige.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return listArticle;
    }


    private List<ProductDTO> loadDataBase() {

        List<ProductDTO> products = new ArrayList<>();
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .csv en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader("src/main/resources/dbProductos.csv"));

            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            int cont=0;
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(",");

                if (cont>0){
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setName(campos[0]);
                    productDTO.setCategory(campos[1]);
                    productDTO.setBrand(campos[2]);
                    productDTO.setPrice(Float.parseFloat(campos[3].substring(1,campos[3].length())));
                    productDTO.setQuantity(Integer.parseInt(campos[4]));
                    productDTO.setFreeShipping(campos[5].equals("SI")?true:false);
                    productDTO.setPrestige(campos[6]);
                    products.add(productDTO);

                }
                cont++;


                // Volver a leer otra línea del fichero
                linea = bufferLectura.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return products;
    }
}
