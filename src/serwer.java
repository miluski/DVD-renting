import java.io.*;
import java.net.*;
import java.io.InputStream;
public class serwer {
    public static int serving;
    public static int receiving;
    public static int management;
    public static String nick, pass, rank;
    private static int j = 1;
    public static void server_operations() {
        ServerSocket socket;
        Socket client;
        try {
            socket = new ServerSocket(1522);
            socket.setReuseAddress(true);
            while(true) {
                client = socket.accept();
                InputStream socket_receive = client.getInputStream();
                DataInputStream receive = new DataInputStream(socket_receive);
                String receive2 = receive.readUTF();
                System.out.println("\nPodlaczenie z  " + client);
                if (receive2.equals("1")) {
                    serving = 1;
                    receiving = 1;
                    for (int i = 0; i < 2; i++) {
                        String buffer = receive.readUTF();
                        if (j % 2 == 1) nick = buffer;
                        else pass = buffer;
                        j++;
                    }
                    baza_danych.polacz_z_baza();
                    OutputStream socket_send = client.getOutputStream();
                    DataOutputStream send = new DataOutputStream(socket_send);
                    send.writeUTF(EkranLogowania.wiadomosc);
                    send.flush();
                    send.writeUTF(rank);
                    send.flush();
                    send.close();
                } else if (receive2.equals("2")) {
                    receiving = 2;
                    serving = 2;
                    int list_size = Integer.parseInt(receive.readUTF());
                    for(int i=0; i<list_size; i++){
                        if(i==0) baza_danych.name = receive.readUTF();
                        else if(i==1) baza_danych.surname = receive.readUTF();
                        else if(i==2) baza_danych.nick = receive.readUTF();
                        else if(i==3) baza_danych.email = receive.readUTF();
                        else if(i==4) baza_danych.phone_number = receive.readUTF();
                        else if(i==5) baza_danych.pass = receive.readUTF();
                    }
                    baza_danych.polacz_z_baza();
                    OutputStream socket_send = client.getOutputStream();
                    DataOutputStream send = new DataOutputStream(socket_send);
                    send.writeUTF(EkranUtworzKonto.blad);
                    send.flush();
                    send.close();
                }
                receive.close();
                client.close();
            }
        } catch (IOException except) {
            System.out.println("Kod bledu: " + except);
        }
    }

    public static void main(String[] args) {
        server_operations();
    }
}
