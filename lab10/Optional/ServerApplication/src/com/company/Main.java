package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Currency;

public class Main {
        // Define the port on which the server is listening
        public static final int PORT = 8081;
        public static boolean ok=true;
        public static Network network;
        public static int permission=0;
        public static Client current;
    public Main() throws IOException {
            try (ServerSocket serverSocket = new ServerSocket(PORT);) {

                while(ok){
                    //System.out.println("Waiting for a client ...");
                    Socket socket = serverSocket.accept();

                    // Execute the client's request in a new thread
                        ClientThread client =new ClientThread(socket);

                    client.start();
                    ok=client.server_on();


                }
            } catch (IOException e) {
                System.err.println("Ooops... " + e);
            }
        }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        network=new Network();
        current=new Client();
        Main server=new Main();}

    }

