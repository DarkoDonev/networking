//package server;
//UNCOMMENT THE PACKAGE IF YOU WANT TO RUN IT WITHOUT DOCKER COMPOSE

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private int port;

    public Server(int port) {
        this.port = port;
    }
    @Override
    public void run(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started at port: " + port);
        while (true){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client");
                Worker worker = new Worker(socket);
                worker.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8522);
        server.start();
    }
}
