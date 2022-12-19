package configServer;

import Service.BookService;
import Service.ClientService;
import Service.PurchasedService;
import domain.Book;
import domain.Client;
import domain.Purchased;
import domain.validators.BookValidator;
import domain.validators.ClientValidator;
import domain.validators.PurchasedValidator;
import domain.validators.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repo.*;
import serviceserver.BookServiceServer;
import serviceserver.ClientServiceServer;
import serviceserver.PurchasedServiceServer;

@Configuration
public class ServerConfig {
    private BookServiceServer bookServiceServer;
    private ClientServiceServer clientServiceServer;

    @Bean
    RmiServiceExporter rmiServiceExporter1() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("BookService");
        rmiServiceExporter.setServiceInterface(BookService.class);
        rmiServiceExporter.setService(bookServiceServer());
        return rmiServiceExporter;
    }

    @Bean
    BookServiceServer bookServiceServer() {

        SortingRepository<Long, Book> repository=BookSortingRepository();
        bookServiceServer=new BookServiceServer(repository);
        return bookServiceServer;
    }
    @Bean
    SortingRepository<Long,Book> BookSortingRepository(){
        BookValidator bookValidator=new BookValidator();
        return new BookDBRepository(bookValidator);
    }

    @Bean
    RmiServiceExporter rmiServiceExporter2() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("ClientService");
        rmiServiceExporter.setServiceInterface(ClientService.class);
        rmiServiceExporter.setService(clientServiceServer());
        return rmiServiceExporter;
    }

    @Bean
    ClientServiceServer clientServiceServer() {

        SortingRepository<Long,Client> clientRepository=ClientSortingRepository();
        clientServiceServer=new ClientServiceServer(clientRepository);
        return clientServiceServer;
    }
    @Bean
    SortingRepository<Long,Client> ClientSortingRepository(){
        Validator<Client> clientValidator=new ClientValidator();
        return new ClientDBRepository(clientValidator);
    }
    @Bean
    RmiServiceExporter rmiServiceExporter3() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("PurchasedService");
        rmiServiceExporter.setServiceInterface(PurchasedService.class);
        rmiServiceExporter.setService(purchasedServiceServer());
        return rmiServiceExporter;
    }

    @Bean
    PurchasedServiceServer purchasedServiceServer() {

        SortingRepository<Long,Purchased> purchasedRepository=PurchasedSortingRepository();
        return new PurchasedServiceServer(purchasedRepository, bookServiceServer, clientServiceServer);
    }
    @Bean
    SortingRepository<Long,Purchased> PurchasedSortingRepository(){
        Validator<Purchased> purchasedValidator=new PurchasedValidator();
        return new PurchasedDBRepository(purchasedValidator);
    }
}
