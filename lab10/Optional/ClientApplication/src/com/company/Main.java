package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8081; // The server's port

        int ok=1;

        while(ok>0)
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader (
                        new InputStreamReader(socket.getInputStream())) ) {

            // Send a request to the server
            Scanner scan= new Scanner(System.in);
            String request = scan.nextLine();

            //if the request is "exit" , the client stops
            if(request.equals("exit"))
            {   System.out.println("Stop");
                 ok=-1;
            }


            if(request.equals("register"))
            {
                String arg=scan.next();
                request=request+" "+arg;

            }
            else if(request.equals("login"))
            {
                String arg=scan.next();
                request=request+" "+arg;
            }
            else if(request.equals("friend"))
            {
                int nr=scan.nextInt();
                request=request+" "+Integer.toString(nr);
                while(nr>0){
                String arg=scan.next();
                request=request+" "+arg;
                nr--;}
            }
            else if(request.equals("send"))
            {
                String arg=scan.next();
                request=request+" "+arg;
            }
            else if(request.equals("read"))
            {

            }
            out.println(request);
            // Wait the response from the server
            String response = in.readLine ();
            System.out.println(response);
            if(response.equals("Server stopped")){
                out.println("ok");
                ok=0;
            }

        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }
}

