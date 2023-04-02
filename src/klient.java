/** @file Klient.java
 * @brief plik zawierający kod ułatwiający zbieranie danych od dialog boxów
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Klient {
    protected static final List<String> panelData = new ArrayList<>();
    private static Socket sock;
    private static String IP = "localhost";
    protected static String filmName, nickname = null;
    private static final String ipPatt = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    private static final Pattern patt = Pattern.compile(ipPatt);
    protected static boolean existReservation = false;
    public static String hashPassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    protected static boolean walidacjaIP(final String IP){
        Matcher match = patt.matcher(IP);
        return match.matches();
    }
    protected static void setKlientIP(String IP){
        Klient.IP = IP;
    }
    protected static void zakonczPolaczenie(){
        try {
            sock.close();
        }
        catch (IOException except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void polacz(){
        try {
            Klient.sock = new Socket(IP, 1522);
        }
        catch (IOException except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static int dialogTakNie(String message){
        Object[] takNie = {"Tak", "Nie"};
        return JOptionPane.showOptionDialog(null,message,"Potwierdzenie",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, takNie, null);
    }
    protected  static void odzyskaj(){
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
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
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void zarejestruj() {
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
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
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void zaloguj() {
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
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
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void wylogujWszystkich(){
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("LogoutAll");
            send.flush();
            EkranGlownyAdmin.message = receive.readUTF();
            send.close();
            receive.close();
        }
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void wyloguj(){
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("Logout");
            send.flush();
            send.writeUTF(EkranLogowania.login_uzytkownika);
            send.flush();
            EkranGlownyAdmin.message = receive.readUTF();
            EkranGlownyUzytkownik.message = EkranGlownyAdmin.message;
            send.close();
            receive.close();
        }
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void zarzadzaj(String management_option){
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("Management");
            send.flush();
            send.writeUTF(management_option);
            send.flush();
            EkranGlownyAdmin.message = receive.readUTF();
            send.close();
            receive.close();
        }
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void wysylajDane(String option){
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("DataPass");
            send.flush();
            send.writeUTF(option);
            send.flush();
            send.writeUTF((Integer.toString(Klient.panelData.size())));
            send.flush();
            for(String data: Klient.panelData){
                send.writeUTF(data);
                send.flush();
            }
            EkranSerwer.message = receive.readUTF();
            send.close();
            receive.close();
            Klient.panelData.clear();
        }
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void otrzymujDane(String option){
        try{
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("DataReceive");
            send.flush();
            send.writeUTF(option);
            send.flush();
            switch (option) {
                case "ReviewMyRents" -> {
                    send.writeUTF(DialogMojeWypozyczenia.userID);
                    send.flush();
                }
                case "ReviewMyReturns" -> {
                    send.writeUTF(DialogMojeZwroty.userID);
                    send.flush();
                }
                case "ReviewRents" -> {
                    send.writeUTF(DialogPrzegladajWypozyczenia.userID);
                    send.flush();
                }
                case "ReviewReturns" -> {
                    send.writeUTF(DialogPrzegladajZwroty.userID);
                    send.flush();
                }
            }
            int size = Integer.parseInt(receive.readUTF());
            for(int i=0; i<size; i++){
                EkranSerwer.panelData.add(receive.readUTF());
            }
            receive.close();
            send.close();
        } catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static String pobierzIDKlienta(String whichClient){
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("GetClientID");
            send.flush();
            send.writeUTF(whichClient);
            send.flush();
            if(whichClient.equals("selectedUser")){
                send.writeUTF(EkranSerwer.selectedNick);
                send.flush();
            }
            EkranSerwer.userID = receive.readUTF();
            send.close();
            receive.close();
        } catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
        return EkranSerwer.userID;
    }
    protected static int pobierzIDDVD() {
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("GetDVDID");
            send.flush();
            send.writeUTF(EkranSerwer.filmName);
            EkranSerwer.dvdID = receive.readInt();
            send.close();
            receive.close();
        } catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
        return EkranSerwer.dvdID;
    }
    protected static void zaktualizujStan(){
        try {
            OutputStream socketSend = sock.getOutputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            send.writeUTF("UpdateCount");
            send.flush();
            send.writeUTF("dvds_data");
            send.flush();
            send.writeInt(EkranSerwer.updatingID);
            send.flush();
            send.writeUTF(EkranSerwer.updatingItem);
            send.flush();
            send.close();
        }
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static void usunRezerwacje(){
        try{
            OutputStream socketSend = sock.getOutputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            send.writeUTF("DeleteReservation");
            send.flush();
            send.close();
        }
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    protected static boolean checkReservations(){
        try{
            existReservation = false;
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("CheckReservations");
            send.flush();
            send.writeUTF(filmName);
            send.flush();
            existReservation = receive.readBoolean();
            receive.close();
            send.close();
        }
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
        return existReservation;
    }
    protected static String downloadNickname(String userID){
        try {
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            DataOutputStream send = new DataOutputStream(socketSend);
            DataInputStream receive = new DataInputStream(socketReceive);
            send.writeUTF("DownloadNickname");
            send.flush();
            send.writeUTF(userID);
            send.flush();
            nickname = receive.readUTF();
            receive.close();
            send.close();
        }
        catch (Exception except) {
            JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
        return nickname;
    }
}
