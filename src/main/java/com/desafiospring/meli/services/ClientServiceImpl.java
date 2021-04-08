package com.desafiospring.meli.services;

import com.desafiospring.meli.dtos.ClientDTO;
import com.desafiospring.meli.exceptions.ClientException;
import com.desafiospring.meli.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) throws IOException, ClientException {
        List<ClientDTO> client = clienteRepository.listClient();
        return clienteRepository.createClient(clientDTO,client);
    }

    @Override
    public List<ClientDTO> listClient(int id,String provincia) {
        List<ClientDTO> client = clienteRepository.listClient();
        if(id!=0) {
            client = clienteRepository.getClientById(id,client);
        }
        if(provincia!=null){
            client = clienteRepository.getClientByProvince(provincia,client);
        }

        return client;
    }

}
