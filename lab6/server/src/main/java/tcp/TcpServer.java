package tcp;

import Service.Service;
import domain.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TcpServer {
    private ExecutorService executorService;
    private Map<String, UnaryOperator<Message>> methodHandlers;

    public TcpServer(ExecutorService executorService) {
        this.methodHandlers = new HashMap<>();
        this.executorService = executorService;
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(Message.PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException e) {
            throw new RuntimeException("error connecting clients", e);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket client) {
            this.socket = client;
        }

        @Override
        public void run() {
            try (
                 ObjectInputStream is = new ObjectInputStream( socket.getInputStream());
                 ObjectOutputStream os =new ObjectOutputStream( socket.getOutputStream());
            ) {
                Message request = new Message();
                request.readFrom(is);
                //System.out.println("received request: " + request);

                Message response = methodHandlers.get(request.getHeader())
                        .apply(request);
//                Message response = new Message();
                response.writeTo(os);

            } catch (IOException e) {
                throw new RuntimeException("error processing client", e);
            }
        }
    }
}
