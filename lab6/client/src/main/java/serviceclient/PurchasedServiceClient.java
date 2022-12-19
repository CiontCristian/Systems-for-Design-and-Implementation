package serviceclient;

import Service.PurchasedService;
import domain.Book;
import domain.Message;
import domain.Purchased;
import tcp.TcpClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class PurchasedServiceClient extends ServiceClient<Long, Purchased> implements PurchasedService {
    public PurchasedServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        super(executorService, tcpClient);
    }

    @Override
    public CompletableFuture<Object> delete(Long id) throws IllegalArgumentException {
        return CompletableFuture.supplyAsync(() -> {
            Purchased purchased=new Purchased();
            purchased.setId(id);
            Message request = new Message(delete, purchased);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(getAll, new Purchased());
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> getOne(Long id) throws IllegalArgumentException {
        return CompletableFuture.supplyAsync(() -> {
            Purchased purchased=new Purchased();
            purchased.setId(id);
            Message request = new Message(getOne, purchased);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }
}
