package pl.niewiel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UserHandler implements Runnable {
    private Socket userSocket;
    private static int userCount = 0;
    private boolean isQuit = false;
    private static int invertCounter = 0;

    UserHandler(Socket userSocket) {
        this.userSocket = userSocket;
        userCount++;
    }

    @Override
    public void run() {
        try (InputStream is = userSocket.getInputStream();
             OutputStream os = userSocket.getOutputStream();
             Scanner sc = new Scanner(is);
             PrintWriter pw = new PrintWriter(os, true)) {
            pw.println("Witam na serwerze INVERT COUNT");
            pw.println("jesteś " + userCount + " podłączonym użytkownikiem");
            printMonit(pw);
            while (sc.hasNextLine() && !isQuit) {
                String line = sc.nextLine();
                switch (line) {
                    case "end":
                        pw.println("serwer zostanie wyłączony");
                        System.exit(1);
                    case "stop":
                        pw.println("sesja zostanie zakończona");
                        isQuit = true;
                        userCount--;
                    case "invert":
                        invertCounter++;
                        String s1 = sc.next();
                        String s2 = sc.next();
                        pw.println(s2 + " " + s1);
                        printMonit(pw);

                    case "count":
                        pw.println("liczba wywołań polecenia invert: " + invertCounter);
                        printMonit(pw);
                    default:
                        printMonit(pw);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printMonit(PrintWriter pw) {
        pw.print(">");
    }
}
