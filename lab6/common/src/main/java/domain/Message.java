package domain;

import Service.Service;

import java.io.*;
import java.util.Set;
import java.util.stream.StreamSupport;

public class Message {
    public static final int PORT = 1234;
    public static final String HOST = "localhost";

    private String header;
    private Object body;

    public Message() {
    }

    public Message(String header, Object body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }


    public void writeTo(ObjectOutputStream os) throws IOException {
        os.writeObject(this.header);
        os.writeObject(this.body);
        os.flush();
    }
    public void readFrom(ObjectInputStream is) throws IOException {
        try{
            this.header=(String) is.readObject();
            this.body=is.readObject();
            int x=3;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
