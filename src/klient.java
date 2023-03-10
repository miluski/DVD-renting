import java.io.*;
import java.net.*;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class klient {
    protected static List<String> panel_data = new ArrayList<>();
    public static void odzyskaj(Socket sock){
        try {
            OutputStream socket_send = sock.getOutputStream();
            InputStream socket_receive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socket_send);
            DataInputStream receive = new DataInputStream(socket_receive);
            send.writeUTF("Recovery");
            send.flush();
            send.writeUTF(Integer.toString(EkranPrzywrocHaslo.recovery_data.size()));
            send.flush();
            for(String data:EkranPrzywrocHaslo.recovery_data){
                send.writeUTF(data);
                send.flush();
            }
            EkranPrzywrocHaslo.message = receive.readUTF();
            receive.close();
            send.close();
        }
        catch (IOException except){
            System.out.println("Kod bledu: " + except);
        }
    }
    public static void zarejestruj(Socket sock) {
        try {
            OutputStream socket_send = sock.getOutputStream();
            InputStream socket_receive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socket_send);
            DataInputStream receive = new DataInputStream(socket_receive);
            send.writeUTF("Register");
            send.flush();
            send.writeUTF((Integer.toString(EkranUtworzKonto.dane.size())));
            send.flush();
            for(String data:EkranUtworzKonto.dane){
                send.writeUTF(data);
                send.flush();
            }
            EkranUtworzKonto.blad = receive.readUTF();
            receive.close();
            send.close();
        }
        catch(IOException except) {
            System.out.println("Kod bledu: " + except);
        }
    }
    public static void zaloguj(Socket sock) {
        try {
            OutputStream socket_send = sock.getOutputStream();
            InputStream socket_receive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socket_send);
            DataInputStream receive = new DataInputStream(socket_receive);
            send.writeUTF("Login");
            send.flush();
            send.writeUTF(EkranLogowania.login_uzytkownika);
            send.flush();
            send.writeUTF(EkranLogowania.haslo_uzytkownika);
            send.flush();
            EkranLogowania.wiadomosc = receive.readUTF();
            EkranLogowania.ranga = receive.readUTF();
            receive.close();
            send.close();
        }
        catch (IOException except) {
            System.out.println("Kod bledu klienta: " + except);
        }
    }
    public static void wyloguj(Socket sock){
        try {
            OutputStream socket_send = sock.getOutputStream();
            InputStream socket_receive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socket_send);
            DataInputStream receive = new DataInputStream(socket_receive);
            send.writeUTF("Logout");
            send.flush();
            send.writeUTF(EkranLogowania.login_uzytkownika);
            send.flush();
            EkranGlownyAdmin.message = receive.readUTF();
            send.close();
            receive.close();
        }
        catch (IOException except) {
            System.out.println("Kod bledu klienta: " + except);
        }
    }
    public static void zarzadzaj(Socket sock, String management_option){
        try {
            OutputStream socket_send = sock.getOutputStream();
            InputStream socket_receive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socket_send);
            DataInputStream receive = new DataInputStream(socket_receive);
            send.writeUTF("Management");
            send.flush();
            send.writeUTF(management_option);
            send.flush();
            EkranGlownyAdmin.message = receive.readUTF();
            send.close();
            receive.close();
        }
        catch (IOException except) {
            System.out.println("Kod bledu klienta: " + except);
        }
    }
    public static void wysylaj_dane(Socket sock, String option){
        try {
            OutputStream socket_send = sock.getOutputStream();
            InputStream socket_receive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socket_send);
            DataInputStream receive = new DataInputStream(socket_receive);
            send.writeUTF("DataPass");
            send.flush();
            send.writeUTF(option);
            send.flush();
            send.writeUTF((Integer.toString(panel_data.size())));
            send.flush();
            for(String data: panel_data){
                send.writeUTF(data);
                send.flush();
            }
            DialogDodajDVD.message = receive.readUTF();
            send.close();
            receive.close();
            panel_data.clear();
        }
        catch (IOException except) {
            System.out.println("Kod bledu klienta: " + except);
        }
    }
}
