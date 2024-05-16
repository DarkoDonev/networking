//package client;
//UNCOMMENT THE PACKAGE IF YOU WANT TO RUN IT WITHOUT DOCKER COMPOSE

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{
    public String serverName;
    public int serverPort;

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(this.serverName),this.serverPort);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("GET /?name=darko&surname=donev HTTP/1.1\n");//YOU CAN CHANGE NAME AND SURNAME AS YOU WISH!
            bufferedWriter.write("Host: developer.mozilla.org\n");
            bufferedWriter.write("User-Agent: OSClient\n");
            bufferedWriter.write("\n");
            bufferedWriter.flush();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Client received: " + line);
            }
            bufferedReader.close();
            socket.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client("server",8522);
        client.start();
    }
}
