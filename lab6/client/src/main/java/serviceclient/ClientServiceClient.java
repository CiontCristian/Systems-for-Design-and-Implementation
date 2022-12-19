package serviceclient;

import Service.ClientService;
import domain.Client;
import domain.Message;
import tcp.TcpClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientServiceClient extends ServiceClient<Long, Client> implements ClientService {
    public ClientServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        super(executorService, tcpClient);
    }
    @Override
    public CompletableFuture<Object> delete(Long id) throws IllegalArgumentException {
        return CompletableFuture.supplyAsync(() -> {
            Client client = new Client();
            client.setId(id);
            Message request = new Message(delete, client);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(getAll, new Client());
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }

    @Override
    public CompletableFuture<Object> getOne(Long id) throws IllegalArgumentException {
        return CompletableFuture.supplyAsync(() -> {
            Client client = new Client();
            client.setId(id);
            Message request = new Message(getOne, client);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }
    @Override
    public CompletableFuture<Object> filterClientsByAge(int age) {
        return CompletableFuture.supplyAsync(() -> {
            Message request = new Message(filterClientsByAge, age);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        }, executorService);
    }
}
