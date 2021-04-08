package com.desafiospring.meli.controller;

import com.desafiospring.meli.dtos.ClientDTO;
import com.desafiospring.meli.dtos.MessageErrorDTO;
import com.desafiospring.meli.exceptions.ArticleInsuficientException;
import com.desafiospring.meli.exceptions.ClientException;
import com.desafiospring.meli.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {
    @Autowired
    private ClientService clientService;

    //Crear cliente
    @PostMapping("/createclient")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) throws IOException, ClientException {
        return new ResponseEntity(clientService.createClient(clientDTO), HttpStatus.OK);
    }
    //Obtener lista total de cliente y por diferentes filtro
    @GetMapping("/client")
    public ResponseEntity<ClientDTO> getClient(@RequestParam(required = false, defaultValue = "0") Integer id,
                                               @RequestParam(required = false) String provincia){
        List<ClientDTO> client = new ArrayList<>();
        client = clientService.listClient(id,provincia);
        return new ResponseEntity(client,HttpStatus.OK);
    }
    //Excepciones para cuando el cliente existe, falta algùn parametro obligatorio
    @ExceptionHandler(ClientException.class)
    public ResponseEntity exceptionHandlerClient(ClientException e) {
        return new ResponseEntity(new MessageErrorDTO("Valir los datos",e.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
