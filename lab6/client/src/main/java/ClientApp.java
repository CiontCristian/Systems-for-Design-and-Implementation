
import Service.PurchasedService;
import serviceclient.BookServiceClient;
import serviceclient.ClientServiceClient;
import serviceclient.PurchasedServiceClient;
import tcp.TcpClient;
import ui.Console;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient();
        BookServiceClient bookServiceClient = new BookServiceClient(executorService, tcpClient);
        ClientServiceClient clientServiceClient=new ClientServiceClient(executorService, tcpClient);
        PurchasedServiceClient purchasedServiceClient=new PurchasedServiceClient(executorService, tcpClient);

        Console console = new Console(bookServiceClient, clientServiceClient, purchasedServiceClient);
        console.runMainConsole();

        executorService.shutdown();

        System.out.println("bye client");
    }
}
