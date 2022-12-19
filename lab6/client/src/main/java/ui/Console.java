package ui;

import Service.Service;
import domain.Book;
import domain.Client;
import domain.Message;
import domain.Purchased;
import domain.validators.ValidatorException;
import serviceclient.BookServiceClient;
import serviceclient.ClientServiceClient;
import serviceclient.PurchasedServiceClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Console {
    private BookServiceClient bookServiceClient;
    private ClientServiceClient clientServiceClient;
    private PurchasedServiceClient purchasedServiceClient;

    private BufferedReader bufferedReader;

    public Console(BookServiceClient _bookServiceClient, ClientServiceClient _clientServiceClient, PurchasedServiceClient _purchasedServiceClient) {
        bookServiceClient = _bookServiceClient;
        clientServiceClient=_clientServiceClient;
        purchasedServiceClient=_purchasedServiceClient;
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
            }catch (IOException| IllegalArgumentException exception){
                System.out.println(exception.getMessage());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
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
                        filterBooksByAuthor();
                        break;
                    case 5:
                        printAllBooks();
                        break;
                    case 6:
                        sortBooks();
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
            CompletableFuture<Object> books= bookServiceClient.getAll();
            books.thenApplyAsync(result-> {

                Set<Object> x = (Set<Object>)result;
                x.forEach(System.out::println);

                return null;
            });

    }

    private void filterBooksByAuthor() throws IOException {
        String author = bufferedReader.readLine();

        CompletableFuture<Object> books= bookServiceClient.filterBooksByAuthor(author);
        books.thenApplyAsync(result-> {

            Set<Object> x = (Set<Object>)result;
            x.forEach(System.out::println);

            return null;
        });
    }

    private void sortBooks()  {
        CompletableFuture<Object> books= bookServiceClient.sortBooks();
        books.thenApplyAsync(result-> {

            List<Object> x = (List<Object>)result;
            x.forEach(System.out::println);

            return null;
        });
    }

    private void addBook() throws ValidatorException{
        Book book = readBook();

        CompletableFuture<Object> returnedMessage= bookServiceClient.add(book);
        returnedMessage.thenApplyAsync(result -> {
            Message message=(Message)result;
            if(message.getHeader().equals(Service.success)){
                System.out.println("Book added!");
            }
            else{
                System.out.println(message.getHeader()+" "+message.getBody());
            }
            return null;
        });
    }

    private void deleteBook() throws IllegalArgumentException, IOException {
        Long id=Long.parseLong(bufferedReader.readLine());

        CompletableFuture<Object> returnedMessage= bookServiceClient.delete(id);
        returnedMessage.thenApplyAsync(result -> {
            Message message=(Message)result;
            if(message.getHeader().equals(Service.success)){
                System.out.println("Book deleted!");
            }
            else{
                System.out.println(message.getHeader()+" "+message.getBody());
            }
            return null;
        });
    }


    private void updateBook() throws IOException{
        try {
            Long id=Long.parseLong(bufferedReader.readLine());
            String title = bufferedReader.readLine();
            String author = bufferedReader.readLine();
            double price = Double.parseDouble(bufferedReader.readLine());

            Book updatedBook = new Book(title, author, price);
            updatedBook.setId(id);

            CompletableFuture<Object> returnedMessage= bookServiceClient.update(updatedBook);
            returnedMessage.thenApplyAsync(result -> {
                Message message=(Message)result;
                System.out.println(message.getHeader()+" "+message.getBody());
                return null;
            });
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

    private void addClient() throws ValidatorException{
        Client client=readClient();

        CompletableFuture<Object> returnedMessage= clientServiceClient.add(client);
        returnedMessage.thenApplyAsync(result -> {
            Message message=(Message)result;
            System.out.println(message.getHeader()+" "+message.getBody());
            return null;
        });
    }

    private void deleteClient() throws IOException {
        Long id = Long.valueOf(bufferedReader.readLine());

        CompletableFuture<Object> returnedMessage= clientServiceClient.delete(id);
        returnedMessage.thenApplyAsync(result -> {
            Message message=(Message)result;
            System.out.println(message.getHeader()+" "+message.getBody());
            return null;
        });
    }

    private void updateClient() throws IOException{
        try {
            Long id = Long.valueOf(bufferedReader.readLine());
            String name1 = bufferedReader.readLine();
            String name2 = bufferedReader.readLine();
            int age = Integer.parseInt(bufferedReader.readLine());

            Client client = new Client(name1, name2, age);
            client.setId(id);

            CompletableFuture<Object> returnedMessage= clientServiceClient.update(client);
            returnedMessage.thenApplyAsync(result -> {
                Message message=(Message)result;
                System.out.println(message.getHeader()+" "+message.getBody());
                return null;
            });
        } catch (ValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printAllClients() {
        CompletableFuture<Object> clients= clientServiceClient.getAll();
        clients.thenApplyAsync(result-> {

            Set<Object> x = (Set<Object>)result;
            x.forEach(System.out::println);

            return null;
        });
    }

    private void filterClientsByAge() throws IOException {
        int age= Integer.parseInt(bufferedReader.readLine());

        CompletableFuture<Object> clients= clientServiceClient.filterClientsByAge(age);
        clients.thenApplyAsync(result-> {

            Set<Object> x = (Set<Object>)result;
            x.forEach(System.out::println);

            return null;
        });
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

    private void buyBook() throws IllegalArgumentException{
        Purchased purchased=readPurchased();

        CompletableFuture<Object> returnedMessage= purchasedServiceClient.add(purchased);
        returnedMessage.thenApplyAsync(result -> {
            Message message=(Message)result;
            System.out.println(message.getHeader()+" "+message.getBody());
            return null;
        });

    }

    private void printPurchased() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> purchased= purchasedServiceClient.getAll();
        purchased.thenApplyAsync(result-> {

            Set<Object> x = (Set<Object>)result;
            x.forEach(System.out::println);

            return null;
        });
    }

}
