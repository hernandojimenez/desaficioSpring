# desaficioSpring
Acomtinuaciòn se relacionan los siguientes endpoint para el consumo de las diferentes apis

1. localhost:8080/api/v1/createclient, es un servicio Post para crear clientes.
   {
   "id":3,
   "nombre":"Juan",
   "apellido":"peres",
   "provincia":"costa"
   }
2. localhost:8080/api/v1/client, endpoint para obtener lista total de cliente
3. localhost:8080/api/v1/client?provincia=bogota, Obtener lista de clientes filtrados por provincia
3. localhost:8080/api/v1/client?id=2, Obtener lista de clientes filtrados por ID del clientegit 


=======================
User->>+ClienteController: create(usuario, 123456, minombre, miapellido, mail)
ClienteController->>+ClienteService: create(clientDTO)
ClienteService->>+ClienteService: validateClient(clientDTO)
ClienteService->>+ClienteService: mapToDAO(clientDTO)
ClienteService->>+ClienteRepository: createClient(clientDAO)
ClienteRepository-->>-ClienteService: Optional ClientDAO
alt client created
ClienteService->>ClienteService: mapToDTO(clientDAO)
ClienteService-->>-ClienteController: ClientDTO
else something wrong
ClienteService-->>-ClienteController: Exception
end
ClienteController-->>-User: Response
=========================
