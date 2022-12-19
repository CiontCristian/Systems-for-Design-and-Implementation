package ui;

import domain.Book;
import domain.Client;
import domain.Purchased;
import domain.validators.ValidatorException;
import service.BookService;
import service.ClientService;
import service.PurchasedService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Console {
    private BookService bookService;
    private ClientService clientService;
    private PurchasedService purchasedService;
    private BufferedReader bufferedReader;

    public static int chooseRepo(){
        int repoNumber=1;
        System.out.println("1.In-Memory Repo\n2.File Repo\n3.XML Repo\n4.DB Repo");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(">>");
            repoNumber = Integer.parseInt(bufferRead.readLine());// ...
        } catch (IOException |RuntimeException ex) {
            System.out.println("Error!");

        }
        return repoNumber;
    }
    public Console(BookService bookService, ClientService clientService, PurchasedService purchasedService) {
        this.bookService = bookService;
        this.clientService=clientService;
        this.purchasedService=purchasedService;
        bufferedReader=new BufferedReader(new InputStreamReader(System.in));

    }

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
            }catch (IOException exception){
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
                        printFilteredBooks();
                        break;
                    case 5:
                        printAllBooks();
                        break;
                    case 6:
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
                        filterClientsByAge();
                        break;
                    case 5:
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


    private void printAllBooks() {
        Set<Book> books = bookService.getAll();
        books.forEach(System.out::println);
    }

    private void printFilteredBooks() throws IOException{
        String string = bufferedReader.readLine();
        Set<Book> filteredBooks = bookService.filterBooksByAuthor(string);
        filteredBooks.forEach(System.out::println);
    }

    private void printSortedBooks(){
        List<Book> sortedBooks=bookService.sortBooks();
        sortedBooks.forEach(System.out::println);
    }


    private void addBook() {
        Book book = readBook();
        try {
            bookService.add(book);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteBook() throws IOException{
        Long id = Long.valueOf(bufferedReader.readLine());

        purchasedService.getAll().forEach(purchased ->
        {if(purchased.getBookID()==id)purchasedService.delete(purchased.getId());});

        bookService.delete(id);
    }

    private void updateBook() throws IOException{
        try {
            Long id=Long.parseLong(bufferedReader.readLine());
            String title = bufferedReader.readLine();
            String author = bufferedReader.readLine();
            double price = Double.parseDouble(bufferedReader.readLine());

            Book updatedBook = new Book(title, author, price);
            updatedBook.setId(id);

            bookService.update(updatedBook);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private Book readBook() {
            System.out.println("Read book {id,title, author, price}");

            try {
                Long id = Long.valueOf(bufferedReader.readLine());
                String title = bufferedReader.readLine();
                String author = bufferedReader.readLine();
                double price = Double.parseDouble(bufferedReader.readLine());

                Book book = new Book(title, author, price);
                book.setId(id);

                return book;
            } catch (IOException |RuntimeException ex) {
                System.out.println("Error!");
            }
            return null;
    }

    private Client readClient() {
        System.out.println("Read client {id, name1, name2, age}");

        try {
            Long id = Long.valueOf(bufferedReader.readLine());
            String name1 = bufferedReader.readLine();
            String name2 = bufferedReader.readLine();
            int age = Integer.parseInt(bufferedReader.readLine());

            Client client=new Client(name1, name2, age);
            client.setId(id);

            return client;
        } catch (IOException |RuntimeException ex) {
            System.out.println("Error!");
        }
        return null;
    }

    private void addClient() {
        Client client=readClient();
        try {
            clientService.add(client);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteClient() throws IOException {
        Long id = Long.valueOf(bufferedReader.readLine());

        purchasedService.getAll().forEach(purchased ->
        {if(purchased.getClientID()==id)purchasedService.delete(purchased.getId());});

        clientService.delete(id);
    }

    private void updateClient() throws IOException{
        try {
            Long id = Long.valueOf(bufferedReader.readLine());
            String name1 = bufferedReader.readLine();
            String name2 = bufferedReader.readLine();
            int age = Integer.parseInt(bufferedReader.readLine());

            Client client = new Client(name1, name2, age);
            client.setId(id);

            clientService.update(client);
        } catch (ValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printAllClients() {
        Set<Client> clients = clientService.getAll();
        clients.forEach(System.out::println);
    }

    private void filterClientsByAge() throws IOException {
        int age= Integer.parseInt(bufferedReader.readLine());

        Set<Client> clients=clientService.filterClientsByAge(age);
        clients.forEach(System.out::println);
    }

    private Purchased readPurchased(){
        System.out.println("Read purchased {id, bookID, clientID}");

        try {
            Long id = Long.valueOf(bufferedReader.readLine());
            long bookID= Long.parseLong(bufferedReader.readLine());
            long clientID= Long.parseLong(bufferedReader.readLine());

            Purchased purchased=new Purchased(bookID, clientID);
            purchased.setId(id);

            return purchased;
        } catch (IOException |RuntimeException ex) {
            System.out.println("Error!");
        }
        return null;
    }

    private void buyBook(){
        Purchased purchased=readPurchased();
        try{
            bookService.getOne(purchased.getBookID());
            clientService.getOne(purchased.getClientID());

            purchasedService.add(purchased);

        }
        catch (IllegalArgumentException e){
            System.out.println("BookID or ClientID does not exist!");
        }
    }

    private void printPurchased() {
        Set<Purchased> purchaseds = purchasedService.getAll();
        purchaseds.forEach(System.out::println);
    }
}
