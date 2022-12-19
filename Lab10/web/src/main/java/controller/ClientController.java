package controller;

import converter.ClientConverter;
import dto.ClientDTO;
import dto.ClientsDTO;
import lab10.core.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientsDTO getClients(){
        return new ClientsDTO(clientConverter.convertModelsToDtos(clientService.getAll()));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDTO saveClient(@RequestBody ClientDTO clientDTO){
        return clientConverter.convertModelToDto(clientService.save(clientConverter.convertDtoToModel(clientDTO)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDTO updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO){
        return clientConverter.convertModelToDto(clientService.update(id, clientConverter.convertDtoToModel(clientDTO)));
    }

    @RequestMapping(value = "clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
