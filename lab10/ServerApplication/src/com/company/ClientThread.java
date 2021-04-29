package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientThread extends Thread {
    private Socket socket = null ;
    public ClientThread (Socket socket) { this.socket = socket ; }
    public static boolean ok=true;

    public void run () {
        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String request=in.readLine();
            //int player_nr=Integer.parseInt(nr);
            System.out.println(request);


            String raspuns;
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            //if the request is "stop", the server stops
            if(request.equals("stop"))
            {raspuns="Server stopped";
            ok=false;}
            else {raspuns ="Server received the request "+ request;
            ok=true;}
            if(request.equals("register"))
            {

            }

            out.println(raspuns);
            out.flush();



        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
    }
    //verify if the server must be stopped or not
    public boolean server_on()
    {
        return ok;
    }


}