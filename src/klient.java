import java.io.*;
import java.net.*;
import java.io.DataOutputStream;
public class klient {
    public static void odzyskaj(Socket sock){
        try {
            OutputStream socket_send = sock.getOutputStream();
            InputStream socket_receive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socket_send);
            DataInputStream receive = new DataInputStream(socket_receive);
            send.writeUTF(Integer.toString(4));
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
            send.writeUTF(Integer.toString(serwer.serving));
            send.writeUTF((Integer.toString(EkranUtworzKonto.dane.size())));
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
            send.writeUTF(Integer.toString(serwer.receiving));
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
}
