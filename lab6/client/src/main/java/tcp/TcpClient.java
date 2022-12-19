package tcp;

import domain.Message;

import java.io.*;
import java.net.Socket;

public class TcpClient {
    public Message sendAndReceive(Message request) {
        try (Socket socket = new Socket(Message.HOST, Message.PORT);
             ObjectOutputStream os =new ObjectOutputStream( socket.getOutputStream());
             ObjectInputStream is = new ObjectInputStream( socket.getInputStream());

        ) {
            //System.out.println("sendAndReceive - sending request: " + request);
            request.writeTo(os);

            //System.out.println("sendAndReceive - received response: ");
            Message response = new Message();
            response.readFrom(is);
            //System.out.println(response);

            return response;
        } catch (IOException e) {
            throw new RuntimeException("error connection to server " + e.getMessage(), e);
        }
    }
}
