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
    public Client current;
    public static boolean time=true;
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

            if(request.contains("register"))
            {
                //System.out.println("Yes");
                String[] parts = request.split(" ");
                String name=parts[1];
                //System.out.println(name);
                Client client=new Client(name);
                Main.network.addUser(client);
            }
            else if(request.contains("list")){
                Main.network.printUsers();
            }
            else if(request.contains("login")){
                String[] parts = request.split(" ");
                String name=parts[1];
                for(Client c: Main.network.users) {
                    if (c.name.equals(name)) {
                        Main.permission = 1;
                        raspuns = raspuns + " welcome back!";
                        current=new Client(c);
                        Main.current=c;
                        //System.out.println(current.friends);
                    }
                }
                if(Main.permission==0)
                    raspuns=raspuns+ " register first";
            }
            else if(request.contains("friend")){
                if(Main.permission==1){
                String[] parts = request.split(" ");
                String number=parts[1];
                int k=Integer.parseInt(number);
                //System.out.println(k);
                int i, done=0;
                String friend;
                for(i=1;i<=k;i++){
                    friend=parts[1+i];
                    for(Client c: Main.network.users) {
                        if (c.name.equals(friend)) {
                            Main.current.addFriend(c);
                            done=1;
                            //System.out.println(current.friends);
                        }
                    }
                    if(done==0){
                        Client other=new Client(friend);
                        Main.current.addFriend(other);
                    }
                    done=0;
                }
                raspuns=raspuns+" added friends";}
                else
                    raspuns=raspuns+" register first";
            }
            else if(request.contains("friendlist")){
                System.out.println(Main.current.friends);
            }
            else if(request.contains("send")){
                String[] parts = request.split(" ");
                String message=parts[1];
                for(Client c: Main.current.getFriends()){
                    c.addMessage(Main.current,message);
                }
                raspuns=raspuns+ " message sended";
            }
            else if(request.contains("read")){
                raspuns=raspuns+ Main.current.messages;
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