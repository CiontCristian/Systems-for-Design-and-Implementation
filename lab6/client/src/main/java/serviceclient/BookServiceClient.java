package serviceclient;

import Service.BookService;
import domain.Book;
import domain.Message;
import tcp.TcpClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class BookServiceClient extends ServiceClient<Long, Book> implements BookService {
    public BookServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        super(executorService, tcpClient);
    }

    @Override
    public CompletableFuture<Object> delete(Long id) throws IllegalArgumentException {
        return CompletableFuture.supplyAsync(() -> {
            Book book = new Book();
            book.setId(id);
            Message request = new Message(delete, book);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response;
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> getAll() {
        return CompletableFuture.supplyAsync(()->{
            Message request = new Message(getAll, new Book());
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);

    }

    @Override
    public CompletableFuture<Object> getOne(Long id) throws IllegalArgumentException {
        return CompletableFuture.supplyAsync(() -> {
            Book book = new Book();
            book.setId(id);
            Message request = new Message(getOne, book);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> filterBooksByAuthor(String author) {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(filterBooksByAuthor, author);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> sortBooks() {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(sortBooks, null);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }
}
