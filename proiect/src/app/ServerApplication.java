package app;

import app.server.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        var ss = new ServerSocket(35000);

        while (true) {
            new ClientThread(ss.accept()).start();
        }
    }
}
