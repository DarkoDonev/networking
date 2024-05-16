//package server;
//UNCOMMENT THE PACKAGE IF YOU WANT TO RUN IT WITHOUT DOCKER COMPOSE
import com.sun.net.httpserver.Request;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worker extends Thread{
    private Socket socket;
    public Worker(Socket socket) {
       this.socket = socket;
    }
    @Override
    public void run(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("HTTP/1.1 200 OK\n\n");
            RequestInfo requestInfo = RequestInfo.of(bufferedReader);
            if(requestInfo.fullName.size()==2){
                bufferedWriter.write("Hello world," + capitalizeFirstChar(requestInfo.fullName.get("name")) + " " + capitalizeFirstChar(requestInfo.fullName.get("surname")));
            }else if (requestInfo.fullName.size()==1 && requestInfo.fullName.containsKey("name")){
                bufferedWriter.write("Hello world," + capitalizeFirstChar(requestInfo.fullName.get("name")));
            } else if (requestInfo.fullName.size()==1 && requestInfo.fullName.containsKey("surname")) {
                bufferedWriter.write("Hello world," + capitalizeFirstChar(requestInfo.fullName.get("surname")));
            }else{
                bufferedWriter.write("Hello world, stranger");

            }
            bufferedWriter.flush();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String capitalizeFirstChar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private static class RequestInfo{
        Map<String,String> fullName;

        public RequestInfo(Map<String,String> fullName) {
            this.fullName = fullName;
        }

        public static RequestInfo of(BufferedReader bufferedReader) {
            Map<String, String> nameAndSurnameMap = new HashMap<>();
            try {
                String fullInfo = bufferedReader.readLine();
                String[] info = fullInfo.split("\\s+");
                if (info[1] != null && info[1].contains("?")) {
                    info[1] = info[1].replace("/?", "");
                    nameAndSurnameMap = new HashMap<>();
                    String[] nameAndSurname = info[1].split("&");
                    nameAndSurnameMap.put(nameAndSurname[0].split("=")[0], nameAndSurname[0].split("=")[1]);
                    if (nameAndSurname.length == 2) {
                        nameAndSurnameMap.put(nameAndSurname[1].split("=")[0], nameAndSurname[1].split("=")[1]);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new RequestInfo(nameAndSurnameMap);
        }
    }
}
