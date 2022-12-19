import Service.Service;
import domain.Book;
import domain.Client;
import domain.Message;
import domain.Purchased;
import domain.validators.BookValidator;
import domain.validators.ClientValidator;
import domain.validators.PurchasedValidator;
import domain.validators.Validator;
import repo.*;
import serviceserver.BookServiceServer;
import serviceserver.ClientServiceServer;
import serviceserver.PurchasedServiceServer;
import tcp.TcpServer;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {
        try {


            System.out.println("server started");
            ExecutorService executorService = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors()
            );
            Validator<Book> bookValidator=new BookValidator();
            Validator<Client> clientValidator=new ClientValidator();
            Validator<Purchased> purchasedValidator=new PurchasedValidator();
            SortingRepository<Long, Book> bookRepository=new BookDBRepository(bookValidator);
            SortingRepository<Long,Client> clientRepository=new ClientDBRepository(clientValidator);
            SortingRepository<Long,Purchased> purchasedRepository=new PurchasedDBRepository(purchasedValidator);
            BookServiceServer bookServiceServer=new BookServiceServer(bookRepository, executorService);
            ClientServiceServer clientServiceServer=new ClientServiceServer(clientRepository,executorService);
            PurchasedServiceServer purchasedServiceServer=new PurchasedServiceServer(purchasedRepository,executorService,bookServiceServer,clientServiceServer);
            TcpServer tcpServer = new TcpServer(executorService);

            tcpServer.addHandler(Service.add, (request) -> {
                Object entity = request.getBody();
                Future<Object> future = null;
                if(entity instanceof Book){
                    Book book=(Book) entity;
                    future=bookServiceServer.add(book);
                }
                else if(entity instanceof Client){
                    Client client=(Client) entity;
                    future = clientServiceServer.add(client);
                }
                else if(entity instanceof Purchased){
                    Purchased purchased=(Purchased) entity;
                    future = purchasedServiceServer.add(purchased);
                }
                try{
                    assert future != null;
                    Object result = future.get();
                    return new Message(Service.success, result);
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Service.error, e.getMessage());
                }
            });


            tcpServer.addHandler(Service.getAll, (request) -> {
                Object entity = request.getBody();
                Future<Object> future = null;
                if (entity instanceof Book) {
                    future = bookServiceServer.getAll();
                }
                else if (entity instanceof Client){
                    future=clientServiceServer.getAll();
                }
                else if (entity instanceof Purchased){
                    future=purchasedServiceServer.getAll();
                }
                try{
                    assert future != null;
                    Object result = future.get();
                    return new Message(Service.success, result);
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Service.error, e.getMessage());
                }

            });

            tcpServer.addHandler(Service.delete, (request) -> {
                Object entity = request.getBody();
                Future<Object> future=null;
                Future<Object> future2=purchasedServiceServer.getAll();
                Set<Purchased> aux= null;
                try {
                    aux = (Set<Purchased>) future2.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                if (entity instanceof Book) {
                    assert aux != null;
                    aux.forEach(purchased -> {
                            if(purchased.getBookID()==((Book) entity).getId())
                                purchasedServiceServer.delete(purchased.getId());
                        });
                    future = bookServiceServer.delete(((Book) entity).getId());
                } //todo maybe purchased :-?
                else if(entity instanceof Client){
                    assert aux != null;
                    aux.forEach(purchased -> {
                            if(purchased.getClientID()==((Client) entity).getId())
                                purchasedServiceServer.delete(purchased.getId());
                        });
                    future=clientServiceServer.delete(((Client) entity).getId());
                }
                try{
                    assert future != null;
                    Object result = future.get();
                    return new Message(Service.success, result);
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Service.error, e.getMessage());
                }
            });

            tcpServer.addHandler(Service.update, (request) -> {
                Object entity = request.getBody();
                Future<Object> future =null;
                if (entity instanceof Book) {
                    future = bookServiceServer.update((Book) entity);
                } //todo maybe purchased
                else if(entity instanceof Client){
                    future=clientServiceServer.update((Client) entity);
                }
                try{
                    assert future != null;
                    Object result = future.get();
                    return new Message(Service.success, result);
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Service.error, e.getMessage());
                }
            });

            tcpServer.addHandler(Service.getOne, (request) -> {
                Object entity = request.getBody();
                Future<Object> future = null;
                if (entity instanceof Book) {
                    future = bookServiceServer.getOne(((Book) entity).getId());
                }
                else if (entity instanceof Client){
                    future=clientServiceServer.getOne(((Client) entity).getId());
                }
                try{
                    assert future != null;
                    Object result = future.get();
                    return new Message(Service.success, result);
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Service.error, e.getMessage());
                }
            });

            tcpServer.addHandler(BookServiceServer.filterBooksByAuthor, (request) -> {
                Object entity = request.getBody();

                Future<Object> future = bookServiceServer.filterBooksByAuthor((String)entity);
                try{
                    Object result = future.get();
                    return new Message(Service.success, result);
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Service.error, e.getMessage());
                }

            });

            tcpServer.addHandler(ClientServiceServer.filterClientsByAge, (request) -> {
                Object entity = request.getBody();

                Future<Object> future = clientServiceServer.filterClientsByAge((int)entity);
                try{
                    Object result = future.get();
                    return new Message(Service.success, result);
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Service.error, e.getMessage());
                }

            });

            tcpServer.addHandler(BookServiceServer.sortBooks, (request) -> {

                Future<Object> future = bookServiceServer.sortBooks();
                try{
                    Object result = future.get();
                    return new Message(Service.success, result);
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message(Service.error, e.getMessage());
                }

            });

            tcpServer.startServer();

            executorService.shutdown();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

    }

}
