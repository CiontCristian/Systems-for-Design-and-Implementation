package controller;

import converter.ClientConverter;
import dto.ClientDTO;
import lab10.core.domain.Client;
import lab10.core.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    List<ClientDTO> getClients(){
        List<Client> clients = clientService.getAll();

        return new ArrayList<>(clientConverter.convertModelsToDtos(clients));
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
