package serviceclient;

import Service.Service;
import domain.BaseEntity;
import domain.Book;
import domain.Message;
import domain.validators.ValidatorException;
import tcp.TcpClient;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServiceClient<ID, T extends BaseEntity<ID>> implements Service<ID, T>{
    protected ExecutorService executorService;
    protected TcpClient tcpClient;

    public ServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<Object> add(T entity) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {

            Message request = new Message(add, entity);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response;
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> delete(ID id) throws IllegalArgumentException {
       return null;
    }

    @Override
    public CompletableFuture<Object> update(T entity) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {

            Message request = new Message(Service.update, entity);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> getOne(ID id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public CompletableFuture<Object> getAll() {
        return null;
    }
}
