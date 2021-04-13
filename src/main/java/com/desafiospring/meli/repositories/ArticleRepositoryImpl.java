package com.desafiospring.meli.repositories;

import com.desafiospring.meli.dtos.*;
import com.desafiospring.meli.exceptions.ArticleInsuficientException;
import com.desafiospring.meli.exceptions.ArticleNotFounException;
import com.desafiospring.meli.exceptions.ArticleOrderException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.invoke.SwitchPoint;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository{
    //Obtiene la lista de todos los productos
    @Override
    public List<ProductDTO> getArticles() {
        return loadDataBase();
    }

    //Filtra articulos por la categoria
    @Override
    public List<ProductDTO> getArticleByCategory(String category,List<ProductDTO> products) throws ArticleNotFounException {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getCategory().toLowerCase().contains(category.toLowerCase()))
                    .collect(Collectors.toList());
            if(listArticle.size()==0) throw new ArticleNotFounException("El producto con esa categoria no se encuentra");
        }
        return listArticle;
    }
    //Filtra articulos por el nombre
    @Override
    public List<ProductDTO> getArticleByName(String name, List<ProductDTO> products) throws ArticleNotFounException {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
            if(listArticle.size()==0) throw new ArticleNotFounException("El producto con ese nombre no se encuentra");
        }
        return listArticle;
    }
    //Filtra articulos por la marca
    @Override
    public List<ProductDTO> getArticleByBrand(String brand, List<ProductDTO> products) throws ArticleNotFounException {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getBrand().toLowerCase().contains(brand.toLowerCase()))
                    .collect(Collectors.toList());
            if(listArticle.size()==0) throw new ArticleNotFounException("El producto con esa marca no se encuentra");
        }
        return listArticle;
    }
    //Filtra articulos por el precio
    @Override
    public List<ProductDTO> getArticleByPrice(float price, List<ProductDTO> products) throws ArticleNotFounException {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            Predicate<ProductDTO> prices = product -> product.getPrice()==price;
            listArticle = products.stream()
                    .filter(prices).collect(Collectors.toList());
            if(listArticle.size()==0) throw new ArticleNotFounException("El producto con ese precio no se encuentra");
        }
        return listArticle;
    }
    //Filtra articulos por la cantidad solicita
    @Override
    public List<ProductDTO> getArticleByQuantity(int quantity, List<ProductDTO> products) throws ArticleNotFounException {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            Predicate<ProductDTO> quanty = product -> product.getQuantity()==quantity;
            listArticle = products.stream()
                    .filter(quanty).collect(Collectors.toList());
            if(listArticle.size()==0) throw new ArticleNotFounException("El stock no se encuentra");
        }
        return listArticle;
    }
    //Filtra articulos si el envio es o no gratis
    @Override
    public List<ProductDTO> getArticleByFreeShipping(String freeShipping, List<ProductDTO> products) throws ArticleNotFounException {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getFreeShipping().toString().contains(freeShipping.toString()))
                    .collect(Collectors.toList());
            if(listArticle.size()==0) throw new ArticleNotFounException("El producto con envìo gratis no se encuentra");
        }
        return listArticle;
    }
    //Filtra articulos por la calificiòn
    @Override
    public List<ProductDTO> getArticleByPrestige(String prestige, List<ProductDTO> products) throws ArticleNotFounException {
        List<ProductDTO> listArticle = new ArrayList<>();
        if (products != null) {
            listArticle = products.stream()
                    .filter(product -> product.getPrestige().toLowerCase().contains(prestige.toLowerCase()))
                    .collect(Collectors.toList());
            if(listArticle.size()==0) throw new ArticleNotFounException("El producto con esa calificaciòn no se encuentra");
        }
        return listArticle;
    }

    //Filtros dependiento el orden solicitado
    @Override
    public List<ProductDTO> orderArticles(List<ProductDTO> products, int order) throws ArticleOrderException {
        if(order==0) Collections.sort(products, comparing(ProductDTO::getName));
        else if(order==1) Collections.sort(products, comparing(ProductDTO::getName).reversed());
        else if(order==2) Collections.sort(products, comparing(ProductDTO::getPrice).reversed());
        else if(order==3) Collections.sort(products, comparing(ProductDTO::getPrice));
        else throw new ArticleOrderException("Order no permitido");
        return products;
    }

    //Crear solicitud de compra, si el articulo se encuentra en el stock y la cantidad es mayor o igual a la solicitada
    @Override
    public List<TicketResponseDTO> createRequestPurchase(ArticleRequestDTO articleRequestDTO) throws IOException, ArticleNotFounException {
        float total=0;
        int ticketId=0;
        float valor=0;
        Integer quatity=0;
        TicketResponseDTO ticket = new TicketResponseDTO();
        List<TicketResponseDTO> request = new ArrayList<>();
        List<ProductDTO> dataProduct = loadDataBase();
        ProductDTO product1 = new ProductDTO();
        List<TicketCreateDTO> listTicket = loadDataTicket();
        for(ArticleDTO listArticle: articleRequestDTO.getArticles()){
                    product1 = dataProduct.stream()
                    .filter(product -> product.getProductId() == listArticle.getProductId()
                            && product.getName().equals(listArticle.getName())
                            && product.getBrand().equals(listArticle.getBrand()))
                    .findFirst().orElse(null);
            if(product1==null) throw new ArticleNotFounException("Articulo no disponible");
            if (product1.getQuantity()<listArticle.getQuantity())throw new ArticleNotFounException("Articulo no disponible");
            valor = product1.getPrice() * listArticle.getQuantity();
            quatity = product1.getQuantity() - listArticle.getQuantity();

            if(articleRequestDTO.getArticles().size()>0)
            ticketId=maxLength(listTicket) +1;
            createTicket(ticketId,listArticle.getProductId(),valor,1);
            updateStockArticles(listArticle.getProductId().toString(),quatity.toString());
        }
        total=getValortotal(1,listTicket);
        ticket.setTicket(new TicketDTO());
        ticket.getTicket().setId(ticketId);
        ticket.getTicket().setArticles(articleRequestDTO.getArticles());
        ticket.getTicket().setTotal(total);
        ticket.setStatusCode(new StatusCodeDTO());
        ticket.getStatusCode().setCode(200);
        ticket.getStatusCode().setMessage("Compra exitosa !!!");
        request.add(ticket);
        return request;
    }

    //Valor total por cliente
    @Override
    public Float getValortotal(int i,List<TicketCreateDTO> listTicket ) {
        float valorTotal=0;
        for(TicketCreateDTO ticketCreateDTO: listTicket){
            if(ticketCreateDTO.getClientId()==i){
                valorTotal +=ticketCreateDTO.getTotal();
            }
        }
        return valorTotal;
    }


    //Cargar lista de ticket
    private List<TicketCreateDTO> loadDataTicket() {

        List<TicketCreateDTO> ticket = new ArrayList<>();
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .csv en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader("src/main/resources/ticket.csv"));

            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            int cont=0;
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(",");

                if (cont>0){
                    TicketCreateDTO ticketCreateDTO = new TicketCreateDTO();
                    ticketCreateDTO.setTicketId(Integer.parseInt(campos[0]));
                    ticketCreateDTO.setProductId(Integer.parseInt(campos[1]));
                    ticketCreateDTO.setTotal(Float.parseFloat(campos[2]));
                    ticketCreateDTO.setClientId(Integer.parseInt(campos[3]));
                    ticket.add(ticketCreateDTO);

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
        return ticket;
    }
    //Cargar lista de articulos
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
                    productDTO.setProductId(Integer.parseInt(campos[0]));
                    productDTO.setName(campos[1]);
                    productDTO.setCategory(campos[2]);
                    productDTO.setBrand(campos[3]);
                    productDTO.setPrice(Float.parseFloat(campos[4].substring(1,campos[4].length())));
                    productDTO.setQuantity(Integer.parseInt(campos[5]));
                    productDTO.setFreeShipping(campos[6].equals("SI")?true:false);
                    productDTO.setPrestige(campos[7]);
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
    //Actualiza la cantidad de articulos si la solicitud de compra es exitosa
    private  void updateStockArticles(String id, String quantity) throws IOException {
        String filepath="src/main/resources/dbProductos.csv";
        String temppath="src/main/resources/dbProductos1.csv";

        File oldFile = new File(filepath);
        File newFile = new File(temppath);
        FileWriter writer = new FileWriter(temppath,true);
        BufferedWriter buffer = new BufferedWriter(writer);
        PrintWriter pw = new PrintWriter(buffer);
        Scanner x = new Scanner(new File(filepath));
        x.useDelimiter("[,\n]");

        while (x.hasNext()){
            String [] product = new String[8];
            product[0]=x.next();
            product[1]=x.next();
            product[2]=x.next();
            product[3]=x.next();
            product[4]=x.next();
            product[5]=x.next();
            product[6]=x.next();
            product[7]=x.next();
            if(product[0].equals(id)){
                product[5]=quantity;
                pw.println(product[0]+","+product[1]+","+product[2]+","+product[3]+","+product[4]+","+product[5]+","+product[6]+","+product[7]);
            }else{
                pw.println(product[0]+","+product[1]+","+product[2]+","+product[3]+","+product[4]+","+product[5]+","+product[6]+","+product[7]);
            }
        }
        x.close();
        pw.flush();
        pw.close();
        oldFile.delete();
        File dump = new File(filepath);
        newFile.renameTo(dump);

    }

    //Crear el ticket
    private  void createTicket(int id1, int id2, float v,int clientId) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/ticket.csv",true);
        BufferedWriter buffer = new BufferedWriter(writer);
        PrintWriter pw = new PrintWriter(buffer);
        pw.println(id1+","+id2+","+v+","+clientId);
        pw.flush();
        pw.close();
    }
    //Obtener el valor del ultimo ticketId
    private int maxLength(List<TicketCreateDTO> data) {
        int cont=0;
        for(int i=0; i<data.size(); i++){
            if(cont<=data.get(i).getTicketId()){
                cont=data.get(i).getTicketId();
            }
        }
        return cont;
    }
}
