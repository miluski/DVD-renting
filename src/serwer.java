import java.io.*;
import java.net.*;
import java.io.InputStream;
public class serwer {
    public static int serving, receiving, management, recovering;
    public static String nick, pass, rank;
    private static int j = 1;
    private static boolean running = true;
    public static void server_operations() {
        ServerSocket socket;
        Socket client;
        try {
            socket = new ServerSocket(1522);
            socket.setReuseAddress(true);
            while(running) {
                client = socket.accept();
                InputStream socket_receive = client.getInputStream();
                DataInputStream receive = new DataInputStream(socket_receive);
                OutputStream socket_send = client.getOutputStream();
                DataOutputStream send = new DataOutputStream(socket_send);
                String receive2 = receive.readUTF();
                System.out.println("\nPodlaczenie z  " + client);
                switch(receive2) {
                    case "1":
                        serving = 1;
                        receiving = 1;
                        recovering = 1;
                        management = 1;
                        for (int i = 0; i < 2; i++) {
                            String buffer = receive.readUTF();
                            if (j % 2 == 1) nick = buffer;
                            else pass = buffer;
                            j++;
                        }
                        baza_danych.polacz_z_baza();
                        send.writeUTF(EkranLogowania.wiadomosc);
                        send.flush();
                        send.writeUTF(rank);
                        send.flush();
                        receive.close();
                        send.close();
                        client.close();
                        running = true;
                    break;
                    case "2":
                        receiving = 2;
                        serving = 2;
                        recovering = 2;
                        management = 2;
                        int list_size = Integer.parseInt(receive.readUTF());
                        for (int i = 0; i < list_size; i++) {
                            if (i == 0) baza_danych.name = receive.readUTF();
                            else if (i == 1) baza_danych.surname = receive.readUTF();
                            else if (i == 2) baza_danych.nick = receive.readUTF();
                            else if (i == 3) baza_danych.email = receive.readUTF();
                            else if (i == 4) baza_danych.phone_number = receive.readUTF();
                            else if (i == 5) baza_danych.pass = receive.readUTF();
                            else if (i == 6) baza_danych.coverage_key = receive.readUTF();
                        }
                        baza_danych.polacz_z_baza();
                        send.writeUTF(EkranUtworzKonto.blad);
                        send.flush();
                        receive.close();
                        send.close();
                        client.close();
                        running = true;
                    break;
                    case "3":
                        System.out.print("W budowie ");
                        running = true;
                    break;
                    case "4":
                        recovering = 4;
                        receiving = 4;
                        serving = 4;
                        management = 4;
                        baza_danych.coverage_key = receive.readUTF();
                        send.writeUTF("w budowie");
                        send.flush();
                        receive.close();
                        send.close();
                        client.close();
                        running = true;
                    break;
                    default:
                        System.out.print("Inna opcja");
                    break;
                }
            }
        } catch (IOException except) {
            System.out.println("Kod bledu: " + except);
        }
    }

    public static void main(String[] args) {
        server_operations();
    }
}
