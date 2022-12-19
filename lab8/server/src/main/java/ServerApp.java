import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerApp {
    public static void main(String[] args) {

        System.out.println("server started");

        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(
                        "configServer"
                );

    }
}
