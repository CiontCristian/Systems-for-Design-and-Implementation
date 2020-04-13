import domain.Book;
import domain.Client;
import domain.Purchased;
import domain.validators.BookValidator;
import domain.validators.ClientValidator;
import domain.validators.PurchasedValidator;
import domain.validators.Validator;
import repo.*;
import service.BookService;
import service.ClientService;
import service.PurchasedService;
import ui.Console;


public class Main {
    public static void main(String[] args) {
        Validator<Book> bookValidator = new BookValidator();
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Purchased> purchasedValidator = new PurchasedValidator();

        SortingRepository<Long, Book> bookRepository;
        SortingRepository<Long, Client> clientRepository;
        SortingRepository<Long, Purchased> purchasedRepository;


        bookRepository = new BookDBRepository(bookValidator);
        clientRepository = new ClientDBRepository(clientValidator);
        purchasedRepository = new PurchasedDBRepository(purchasedValidator);

        
        BookService bookService = new BookService(bookRepository);
        ClientService clientService=new ClientService(clientRepository);
        PurchasedService purchasedService = new PurchasedService(purchasedRepository);
        Console console = new Console(bookService, clientService, purchasedService);
        console.runMainConsole();
    }
}