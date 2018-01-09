package pl.niewiel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {


    public static void main(String[] args) {
        try(ServerSocket ss=new ServerSocket(8189) ) {
            while (true){
                Socket s =ss.accept();
                Thread t=new Thread(new UserHandler(s));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
