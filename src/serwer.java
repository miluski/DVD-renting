import java.io.*;
import java.net.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class serwer {
    public static String nick, pass, rank, operation, pass_operation, management_operation;
    private static int j = 1;
    private static boolean running = true;
    public static List<String> panel_data = new ArrayList<>();
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
                operation = receive.readUTF();
                System.out.println("\nDo serwera podlaczono sie z parametrow:\n" + client);
                switch (operation) {
                    case "Login" -> {
                        pass_operation = "Login";
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
                    }
                    case "Logout" -> {
                        pass_operation = "Logout";
                        nick = receive.readUTF();
                        baza_danych.polacz_z_baza();
                        send.writeUTF(EkranGlownyAdmin.message);
                        send.flush();
                        send.close();
                        receive.close();
                        client.close();
                        running = true;
                    }
                    case "Register" -> {
                        pass_operation = "Register";
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
                    }
                    case "Management" -> {
                        pass_operation = "Management";
                        management_operation = receive.readUTF();
                        baza_danych.polacz_z_baza();
                        send.writeUTF(EkranGlownyAdmin.message);
                        running = true;
                    }
                    case "Recovery" -> {
                        pass_operation = "Recovery";
                        int list_size = Integer.parseInt(receive.readUTF());
                        for(int i=0; i < list_size; i++){
                            if(i == 0) baza_danych.nick = receive.readUTF();
                            else if(i == 1) baza_danych.coverage_key = receive.readUTF();
                            else if (i == 2) baza_danych.pass = receive.readUTF();
                        }
                        baza_danych.polacz_z_baza();
                        send.writeUTF(EkranPrzywrocHaslo.message);
                        send.flush();
                        receive.close();
                        send.close();
                        client.close();
                        running = true;
                    }
                    case "DataPass" -> {
                        pass_operation = receive.readUTF();
                        int size = Integer.parseInt(receive.readUTF());
                        for(int i=0; i<size; i++){
                            panel_data.add(receive.readUTF());
                        }
                        baza_danych.polacz_z_baza();
                        send.writeUTF(DialogDodajDVD.message);
                        send.flush();
                        send.close();
                        receive.close();
                        panel_data.clear();
                        size = 0;
                        running = true;
                    }
                    default -> System.out.print("Inna opcja");
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
