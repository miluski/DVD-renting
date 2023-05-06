package com.client;
import com.server.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Klasa zawierająca pola i metody niezbędne do komunikacji aplikacji klienta z aplikacją serwera
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class Klient implements Callable<Void> {
    /**
     * Konstruktor umożliwiający tworzenie instancji klasy
     */
    public Klient(){
    }
    /**
     * Atrybut będący listą ciągów znaków przechowuje dane do wyświetlenia dla klienta po uruchomieniu jakiegoś okna dialogowego w panelu
     */
    protected static final List<String> panelData = new ArrayList<>();
    /**
     * Atrybut będący gniazdem, pod które będzie podłączał się klient
     */
    private static Socket sock;
    /**
     * Atrybut będący ciągiem znaków, w którym zapisywane jest IP, do którego użytkownik będzie chciał się podłączyć
     */
    public static String IP = "localhost";
    /**
     * Atrybut będący nazwą filmu
     */
    protected static String filmName;
    /**
     * Atrybut będący nazwą użytkownika
     */
    public static String nickname = null;
    /**
     * Atrybut będący wzorcem hasła
     */
    private static final String ipPatt = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    /**
     * Atrybut będący skompilowaną wersją wzorca hasła
     */
    private static final Pattern patt = Pattern.compile(ipPatt);
    /**
     * Atrybut, który stwierdza czy rezerwacja istnieje, czy nie
     */
    protected static boolean existReservation = false;
    /**
     * Atrybut będący strumieniem wyjściowych danych dla klienta
     */
    private static DataOutputStream send;
    /**
     * Atrybut będący strumieniem wejściowym danych dla klienta
     */
    private static DataInputStream receive;
    /**
     * Metoda, której głównym zadaniem jest ustawienie parametrów gniazda klienta
     * @return Nic nie zwraca
     */
    @Override
    public Void call() {
        try {
            try {
                setParametres(new Socket(Klient.IP, 1522));
            }
            catch (IOException except) {
                catchServe(except);
            }
            return null;
        }
        catch (Exception except) {
            catchServe(except);
            return null;
        }
    }
    /**
     * Metoda obsługująca postępowanie w razie wystąpienia wyjątku
     * @param except Otrzymany wyjątek
     */
    protected static void catchServe(@NotNull Exception except){
        if(except.getMessage()==null) JOptionPane.showMessageDialog(null, "Unexpected exception", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "Klient", "error");
    }
    /**
     * Metoda ustawiająca podstawowe parametry gniazda i strumieni wejścia/wyjścia danych
     * @param sock Gniazdo ustawione do połączenia klienta i serwera
     */
    public static void setParametres(@NotNull Socket sock){
        try {
            Klient.sock = sock;
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            send = new DataOutputStream(socketSend);
            receive = new DataInputStream(socketReceive);
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której głównym zadaniem jest próba podłączenia się pod serwer, w przypadku gdy po 2 sekundach połączenie nie nastąpi to próba jest przerywana
     */
    public static void polacz() {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Void> future = executor.submit(new Klient());
            future.get(2, TimeUnit.SECONDS);
        }
        catch (Exception except){
            catchServe(except);
        }
    }
    /**
     * Metoda, której głównym zadaniem jest hashing otrzymanego hasła
     * @param passwordToHash Hasło do zahashowania
     * @param salt Ziarno używane do hashowania hasła
     * @return Zwraca zahashowaną wersję hasła
     */
    public static String hashPassword(@NotNull String passwordToHash, @NotNull String salt){
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
        } catch (NoSuchAlgorithmException except) {
            catchServe(except);
        }
        return generatedPassword;
    }
    /**
     * Metoda, której celem jest zwalidowanie adresu IP
     * @param IP IP do zwalidowania
     * @return Zwraca true, gdy dostarczone IP jest prawidłowe
     */
    public static boolean walidacjaIP(@NotNull final String IP){
        Matcher match = patt.matcher(IP);
        return match.matches();
    }
    /**
     * Metoda typu setter, której celem jest ustawienie IP do połączenia z serwerem
     * @param IP IP do ustawienia do połączenia z serwerem
     */
    protected static void setKlientIP(@NotNull String IP){
        Klient.IP = IP;
    }
    /**
     * Metoda, której celem jest zakończenie połączenia z serwerem danego klienta
     */
    public static void zakonczPolaczenie(){
        try {
            Klient.send.close();
            Klient.receive.close();
            Klient.sock.close();
        }
        catch (IOException except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest zwrócenie okna dialogowego tak/nie z customową wiadomością
     * @param message Wiadomość okna dialogowego
     * @return Zwraca okno dialogowe z opcjami tak/nie
     */
    public static int dialogTakNie(@NotNull String message){
        Object[] takNie = {"Tak", "Nie"};
        return JOptionPane.showOptionDialog(null,message,"Potwierdzenie",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, takNie, null);
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu odzyskania hasła dla użytkownika
     */
    protected  static void odzyskaj(){
        try {
            Klient.send.writeUTF("Recovery");
            Klient.send.flush();
            Klient.send.writeUTF(Integer.toString(EkranPrzywrocHaslo.recovery_data.size()));
            Klient.send.flush();
            for(String data:EkranPrzywrocHaslo.recovery_data){
                Klient.send.writeUTF(data);
                Klient.send.flush();
            }
            EkranPrzywrocHaslo.message = receive.readUTF();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu zarejestrowania nowego użytkownika
     */
    protected static void zarejestruj() {
        try {
            Klient.send.writeUTF("Register");
            Klient.send.flush();
            Klient.send.writeUTF((Integer.toString(EkranUtworzKonto.dane.size())));
            Klient.send.flush();
            for(String data:EkranUtworzKonto.dane){
                Klient.send.writeUTF(data);
                Klient.send.flush();
            }
            EkranUtworzKonto.blad = receive.readUTF();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu zalogowania użytkownika
     */
    protected static void zaloguj() {
        try {
            Klient.send.writeUTF("Login");
            Klient.send.flush();
            Klient.send.writeUTF(EkranLogowania.loginUzytkownika);
            Klient.send.flush();
            Klient.send.writeUTF(EkranLogowania.hasloUzytkownika);
            Klient.send.flush();
            EkranLogowania.wiadomosc = receive.readUTF();
            EkranLogowania.ranga = receive.readUTF();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu wylogowania wszystkich użytkowników
     */
    public static void wylogujWszystkich(){
        try {
            Klient.send.writeUTF("LogoutAll");
            Klient.send.flush();
            EkranGlownyAdmin.message = receive.readUTF();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu wylogowania pojedynczego użytkownika
     */
    protected static void wyloguj(){
        try {
            Klient.send.writeUTF("Logout");
            Klient.send.flush();
            Klient.send.writeUTF(EkranLogowania.loginUzytkownika);
            Klient.send.flush();
            EkranGlownyAdmin.message = receive.readUTF();
            EkranGlownyUzytkownik.message = EkranGlownyAdmin.message;
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem, aby zarządzać konstrukcją bazy danych
     * @param managementOption Opcja zarządzania bazą danych
     */
    protected static void zarzadzaj(@NotNull String managementOption){
        try {
            Klient.send.writeUTF("Management");
            Klient.send.flush();
            Klient.send.writeUTF(managementOption);
            Klient.send.flush();
            EkranGlownyAdmin.message = receive.readUTF();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu wysłania danych do bazy z odpowiedniego dialogboxa
     * @param option Typ dialog boxa, z którego wysyłane są dane
     */
    protected static void wysylajDane(@NotNull String option){
        try {
            Klient.send.writeUTF("DataPass");
            Klient.send.flush();
            Klient.send.writeUTF(option);
            Klient.send.flush();
            Klient.send.writeUTF((Integer.toString(Klient.panelData.size())));
            Klient.send.flush();
            for(String data: Klient.panelData){
                Klient.send.writeUTF(data);
                Klient.send.flush();
            }
            EkranSerwer.message = receive.readUTF();
            Klient.panelData.clear();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu otrzymania danych do odpowiedniego dialogboxa
     * @param option Typ dialog boxa, z którego wysyłane są dane
     */
    protected static void otrzymujDane(@NotNull String option){
        try{
            Klient.send.writeUTF("DataReceive");
            Klient.send.flush();
            Klient.send.writeUTF(option);
            Klient.send.flush();
            switch (option) {
                case "ReviewMyRents" -> {
                    Klient.send.writeUTF(DialogMojeWypozyczenia.userID);
                    Klient.send.flush();
                }
                case "ReviewMyReturns" -> {
                    Klient.send.writeUTF(DialogMojeZwroty.userID);
                    Klient.send.flush();
                }
                case "ReviewRents" -> {
                    Klient.send.writeUTF(DialogPrzegladajWypozyczenia.userID);
                    Klient.send.flush();
                }
                case "ReviewReturns" -> {
                    Klient.send.writeUTF(DialogPrzegladajZwroty.userID);
                    Klient.send.flush();
                }
            }
            int size = Integer.parseInt(receive.readUTF());
            for(int i=0; i<size; i++){
                EkranSerwer.panelData.add(receive.readUTF());
            }
        } catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu otrzymania id klienta do odpowiedniego dialogboxa
     * @param whichClient Parametr określający czy chcemy id aktualnie zalogowanego użytkownika, czy wybranego z listy
     * @return Zwraca pobrane ID użytkownika
     */
    protected static String pobierzIDKlienta(@NotNull String whichClient){
        try {
            Klient.send.writeUTF("GetClientID");
            Klient.send.flush();
            Klient.send.writeUTF(whichClient);
            Klient.send.flush();
            if(whichClient.equals("selectedUser")){
                Klient.send.writeUTF(EkranSerwer.selectedNick);
                Klient.send.flush();
            }
            EkranSerwer.userID = receive.readUTF();
        } catch (Exception except) {
            catchServe(except);
        }
        return EkranSerwer.userID;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu otrzymania id płyty DVD do odpowiedniego dialogboxa
     * @return Zwraca ID otrzymanej płyty DVD
     */
    protected static int pobierzIDDVD() {
        try {
            Klient.send.writeUTF("GetDVDID");
            Klient.send.flush();
            Klient.send.writeUTF(EkranSerwer.filmName);
            EkranSerwer.dvdID = receive.readInt();
        } catch (Exception except) {
            catchServe(except);
        }
        return EkranSerwer.dvdID;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu zaktualizowania ilości płyt DVD w bazie danych
     */
    protected static void zaktualizujStan(){
        try {
            Klient.send.writeUTF("UpdateCount");
            Klient.send.flush();
            Klient.send.writeUTF("dvds_data");
            Klient.send.flush();
            Klient.send.writeInt(EkranSerwer.updatingID);
            Klient.send.flush();
            Klient.send.writeUTF(EkranSerwer.updatingItem);
            Klient.send.flush();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu usunięcia wybranej rezerwacji z bazy danych
     */
    protected static void usunRezerwacje(){
        try{
            Klient.send.writeUTF("DeleteReservation");
            Klient.send.flush();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu sprawdzenia, czy rezerwacja istnieje, czy nie
     * @return Zwraca status czy rezerwacja istnieje, czy nie
     */
    protected static boolean checkReservations(){
        try{
            existReservation = false;
            Klient.send.writeUTF("CheckReservations");
            Klient.send.flush();
            Klient.send.writeUTF(filmName);
            Klient.send.flush();
            existReservation = receive.readBoolean();
        }
        catch (Exception except) {
            catchServe(except);
        }
        return existReservation;
    }
    /**
     * Metoda, której celem jest komunikacja z serwerem w celu pobrania nazwy użytkownika z bazy danych
     * @param userID ID użytkownika
     * @return Zwraca nickname użytkownika o podanym ID
     */
    protected static String downloadNickname(@NotNull String userID){
        try {
            Klient.send.writeUTF("DownloadNickname");
            Klient.send.flush();
            Klient.send.writeUTF(userID);
            Klient.send.flush();
            nickname = receive.readUTF();
        }
        catch (Exception except) {
            catchServe(except);
        }
        return nickname;
    }
    /**
     * Metoda, której celem jest komunikacja z serwerem w celu otrzymania odpowiednich powiadomień
     * @param notificationType Typ powiadomienia
     */
    @Contract(pure = true)
    protected static void notifications(@NotNull String notificationType){
        try{
            Klient.send.writeUTF(notificationType+"Notifications");
            Klient.send.flush();
            int size = Klient.receive.readInt();
            switch(notificationType) {
                case "Admin" -> {
                    for(int i=0; i<size; i++){
                        Powiadomienia.adminReservationNotifications.add(Klient.receive.readUTF());
                    }
                    size = Klient.receive.readInt();
                    for(int j=0; j<size; j++){
                        Powiadomienia.adminDetentionDVDsNotifications.add(Klient.receive.readUTF());
                    }
                }
                case "User" -> {
                    for(int i=0; i<size; i++){
                        Powiadomienia.userDetentionDVDsNotifications.add(Klient.receive.readUTF());
                    }
                }
            }
        }
        catch (Exception except){
            catchServe(except);
        }
    }
}
