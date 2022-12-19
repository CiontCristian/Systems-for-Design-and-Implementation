package console;

import dto.*;
import lab10.core.domain.Book;
import lab10.core.domain.Purchased;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class UI {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public static final String URL_books = "http://localhost:8080/api/books";
    public static final String URL_clients = "http://localhost:8080/api/clients";
    public static final String URL_purchased = "http://localhost:8080/api/tran";

    @Autowired
    private RestTemplate restTemplate;


    public void printMainMenu(){
        String menu="0.Exit"+"\n"+
                "1.Manage Books"+"\n"+
                "2.Manage Clients"+"\n"+
                "3.Buy Books"+"\n" +
                "4.Print Purchased Books";
        System.out.println(menu);
    }

    public void printBookMenu(){
        String menu="0.Return To Main Menu"+'\n'+
                "1.Add Book"+'\n'+
                "2.Delete Book"+'\n'+
                "3.Update Book"+'\n'+
                "4.Filter Book By Author "+"\n"+
                "5.Print Books"+"\n"+
                "6.Print Sorted Books";
        System.out.println(menu);
    }

    public void printClientMenu(){
        String menu="0.Return To Main Menu"+'\n'+
                "1.Add Client"+'\n'+
                "2.Delete Client"+'\n'+
                "3.Update Client"+'\n'+
                "4.Filter Clients By Age"+"\n"+
                "5.Print Clients";
        System.out.println(menu);
    }

    public void runMainConsole(){
        while (true){
            try{
                printMainMenu();
                int input=Integer.parseInt(bufferedReader.readLine());

                switch (input){
                    case 0:
                        System.exit(0);
                    case 1:
                        runBookConsole();
                        break;
                    case 2:
                        runClientConsole();
                        break;
                    case 3:
                        buyBook();
                        break;
                    case 4:
                        printPurchased();
                        break;
                    default:
                        throw new IOException("Invalid option!");
                }
            }catch (RuntimeException | IOException exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    public void runBookConsole() {
        while(true){
            try {
                printBookMenu();
                int input;
                input = Integer.parseInt(bufferedReader.readLine());
                switch (input){
                    case 0:
                        return;
                    case 1:
                        addBook();
                        break;
                    case 2:
                        deleteBook();
                        break;
                    case 3:
                        updateBook();
                        break;
                    case 4:
                        printAllBooks();
                        break;
                    case 5:
                        printSortedBooks();
                        break;
                    default:
                        throw new RuntimeException("Invalid action!");
                }
            }
            catch (IOException | RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void runClientConsole() {
        while(true){
            try {
                printClientMenu();
                int input;
                input = Integer.parseInt(bufferedReader.readLine());
                switch (input){
                    case 0:
                        return;
                    case 1:
                        addClient();
                        break;
                    case 2:
                        deleteClient();
                        break;
                    case 3:
                        updateClient();
                        break;
                    case 4:
                        printAllClients();
                        break;
                    default:
                        throw new RuntimeException("Invalid action!");
                }
            }
            catch (IOException | RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void printAllBooks(){
        BooksDTO allBooks = restTemplate.getForObject(URL_books, BooksDTO.class);
        System.out.println(allBooks);
    }

    public void printSortedBooks(){
        List<BookDTO> sortedBooks = (List<BookDTO>) restTemplate.getForObject(URL_books + "/sorted", BooksDTO.class);
        System.out.println(sortedBooks);
    }

    private BookDTO readBook() throws IOException{
        System.out.println("Read book {id,title, author, price}");

        String title = bufferedReader.readLine();
        String author = bufferedReader.readLine();
        double price = Double.parseDouble(bufferedReader.readLine());

        return new BookDTO(title, author, price);
    }

    public void addBook() throws IOException {
        BookDTO savedBook = restTemplate.postForObject(URL_books, readBook(),BookDTO.class);
        System.out.println("savedBook: " + savedBook);
    }

    public void deleteBook() throws IOException{
        long id = Long.parseLong(bufferedReader.readLine());

        restTemplate.delete(URL_books + "/{id}", id);
    }

    public void updateBook() throws IOException{
        Long id=Long.parseLong(bufferedReader.readLine());
        String title = bufferedReader.readLine();
        String author = bufferedReader.readLine();
        double price = Double.parseDouble(bufferedReader.readLine());

        BookDTO updatedBook = new BookDTO(title, author, price);

        restTemplate.put(URL_books + "/{id}", updatedBook, id);
    }

    public void printAllClients(){
        ClientsDTO clients = restTemplate.getForObject(URL_clients, ClientsDTO.class);
        System.out.println(clients);
    }

    private ClientDTO readClient() throws  IOException{
        System.out.println("Read client {id, name1, name2, age}");

        String name1 = bufferedReader.readLine();
        String name2 = bufferedReader.readLine();
        int age = Integer.parseInt(bufferedReader.readLine());

        return new ClientDTO(name1, name2, age);
    }

    private void addClient() throws IOException{
        ClientDTO savedClient = restTemplate.postForObject(URL_books, readClient(), ClientDTO.class);
        System.out.println(savedClient);
    }

    private void deleteClient() throws IOException{
        long id = Long.parseLong(bufferedReader.readLine());
        restTemplate.delete(URL_books + "/{id}", id);
    }

    private void updateClient() throws IOException{
        Long id=Long.parseLong(bufferedReader.readLine());
        String name1 = bufferedReader.readLine();
        String name2 = bufferedReader.readLine();
        int age = Integer.parseInt(bufferedReader.readLine());

        ClientDTO clientDTO = new ClientDTO(name1, name2, age);

        restTemplate.put(URL_clients + "/{id}", clientDTO, id);
    }

    private void printPurchased(){
        PurchasedsDTO purchasedsDTO = restTemplate.getForObject(URL_purchased, PurchasedsDTO.class);
        System.out.println(purchasedsDTO);
    }

    private PurchasedDTO readPurchased() throws IOException{
        System.out.println("Read purchased {id, bookID, clientID}");

        long bookID= Long.parseLong(bufferedReader.readLine());
        long clientID= Long.parseLong(bufferedReader.readLine());

        return new PurchasedDTO(bookID, clientID);
    }

    private void buyBook() throws IOException {
        PurchasedDTO bought = restTemplate.postForObject(URL_purchased, readPurchased(), PurchasedDTO.class);
        if (bought == null) {
            System.out.println("NULL");
            throw new RuntimeException("Invalid bookID or clientID!");
        } else {
            System.out.println(bought);
        }
    }

}
