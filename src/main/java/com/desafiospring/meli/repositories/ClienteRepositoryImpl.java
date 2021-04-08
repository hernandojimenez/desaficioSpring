package com.desafiospring.meli.repositories;

import com.desafiospring.meli.dtos.ClientDTO;
import com.desafiospring.meli.dtos.TicketCreateDTO;
import com.desafiospring.meli.exceptions.ClientException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository{

    //Crea cliente si los datos obligatorios son diferentes de null o el ID no se encuentra registrado
    @Override
    public ClientDTO createClient(ClientDTO clientDTO,List<ClientDTO> client) throws IOException, ClientException {
        FileWriter writer = new FileWriter("src/main/resources/client.csv",true);
        BufferedWriter buffer = new BufferedWriter(writer);
        PrintWriter pw = new PrintWriter(buffer);
        if(clientDTO.getId()==null || clientDTO.getNombre()==null){
           throw new ClientException("Cliente con datos incompletos");
        }
        List<ClientDTO> client2= getClientById(clientDTO.getId(),client);
        if(client2.size()>0) throw new ClientException("Cliente ya existe");
        pw.println(clientDTO.getId()+","+ clientDTO.getNombre()+","+clientDTO.getApellido()+","+clientDTO.getProvincia());
        pw.flush();
        pw.close();
        return clientDTO;
    }
    //Lista todos los clientes
    @Override
    public List<ClientDTO> listClient() {
        return loadDataClient();
    }

    //Filtra cliente por ID
    @Override
    public List<ClientDTO> getClientById(int i,List<ClientDTO> client) {
        List<ClientDTO>  client1 = new ArrayList<>();
        for(ClientDTO clientDTO: client){
            if(clientDTO.getId()==i){
                client1.add(clientDTO);
            }
        }
        return client1;
    }

    //Filtra cliente por provincia
    @Override
    public List<ClientDTO> getClientByProvince(String provincia,List<ClientDTO> client) {
        List<ClientDTO>  client1 = new ArrayList<>();
        for(ClientDTO clientDTO: client){
            if(clientDTO.getProvincia().contains(provincia)){
                client1.add(clientDTO);
            }
        }
        return client1;
    }


    //Cargar listado de cliente
    private List<ClientDTO> loadDataClient() {

        List<ClientDTO> clientdata = new ArrayList<>();
        BufferedReader bufferLectura = null;
        try {
            // Abrir el .csv en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader("src/main/resources/client.csv"));

            // Leer una linea del archivo
            String linea = bufferLectura.readLine();
            int cont=0;
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(",");

                if (cont>0){
                    ClientDTO client = new ClientDTO();
                    client.setId(Integer.parseInt(campos[0]));
                    client.setNombre(campos[1]);
                    client.setApellido(campos[2]);
                    client.setProvincia(campos[3]);
                    clientdata.add(client);

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
        return clientdata;
    }
}
