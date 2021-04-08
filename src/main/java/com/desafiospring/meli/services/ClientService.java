package com.desafiospring.meli.services;

import com.desafiospring.meli.dtos.ClientDTO;
import com.desafiospring.meli.exceptions.ClientException;

import java.io.IOException;
import java.util.List;

public interface ClientService {
    ClientDTO createClient(ClientDTO clientDTO) throws IOException, ClientException;
    List<ClientDTO> listClient(int i, String provincia);
}
