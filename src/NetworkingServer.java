import java.net.*;
import java.io.*;

public class NetworkingServer {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client = null;

        int portnumber = 23456;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }
        //CHANGE BLA BLA
        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie) {
            System.err.println("Kunde inte öppna socket på port " + portnumber + ": " + ie.getMessage());
            System.exit(1);
        }
        System.out.println("ServerSocket skapad på " + server);


        while (true) {
            try {
                System.out.println("Väntar på klientanslutning...");
                client = server.accept();
                System.out.println("Anslutning accepterad från " +
                        client.getInetAddress().getHostAddress() +
                        ":" + client.getPort());

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                client.getInputStream()));
                String msgFromClient = br.readLine();
                System.out.println("Mottaget meddelande: " + msgFromClient);

                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
                    PrintWriter pw = new PrintWriter(
                            client.getOutputStream(), true);
                    String ansMsg = "Hello, " + msgFromClient;
                    pw.println(ansMsg);

                    pw.close();
                }

                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    System.out.println("Klient bad om avstängning. Stänger server...");
                    br.close();
                    client.close();
                    server.close();
                    break;
                }

                br.close();
                client.close();

            } catch (IOException ie) {
                System.err.println("I/O-fel: " + ie.getMessage());
            }
        }
    }
}
