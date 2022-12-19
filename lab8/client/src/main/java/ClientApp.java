import Service.BookService;
import Service.ClientService;
import Service.PurchasedService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import serviceclient.BookServiceClient;
import serviceclient.ClientServiceClient;
import serviceclient.PurchasedServiceClient;
import serviceclient.ServiceClient;
import ui.Console;

public class ClientApp {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "configClient"
                );

        BookService bookServiceClient=context.getBean(BookService.class);
        ClientService clientServiceClient=context.getBean(ClientService.class);
        PurchasedService purchasedServiceClient=context.getBean(PurchasedService.class);

        Console console=new Console(bookServiceClient, clientServiceClient, purchasedServiceClient);
        console.runMainConsole();

        System.out.println("bye client");
    }
}
