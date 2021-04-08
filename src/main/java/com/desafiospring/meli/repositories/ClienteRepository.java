package com.desafiospring.meli.repositories;

import com.desafiospring.meli.dtos.ClientDTO;
import com.desafiospring.meli.exceptions.ClientException;

import java.io.IOException;
import java.util.List;

public interface ClienteRepository {
    ClientDTO createClient(ClientDTO clientDTO,List<ClientDTO> client) throws IOException, ClientException;
    List<ClientDTO> listClient();
    List<ClientDTO> getClientById(int i,List<ClientDTO> client);
    List<ClientDTO> getClientByProvince(String provincia,List<ClientDTO> client);

}
