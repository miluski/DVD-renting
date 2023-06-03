package com.client;
import com.server.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
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
     * Konstruktor umożliwiający tworzenie instancji
     */
    public Klient(){}
    /**
     * Konstruktor umożliwiający ustawienie IP do połączenia z serwerem
     */
    public Klient(String IP){
        this.IP = IP;
    }
    /**
     * Atrybut będący listą ciągów znaków przechowuje dane do wyświetlenia dla klienta po uruchomieniu jakiegoś okna dialogowego w panelu
     */
    protected final List<String> panelData = new ArrayList<>();
    /**
     * Atrybut będący gniazdem, pod które będzie podłączał się klient
     */
    private Socket sock;
    /**
     * Atrybut będący ciągiem znaków, w którym zapisywane jest IP, do którego użytkownik będzie chciał się podłączyć
     */
    public String IP;
    /**
     * Atrybut będący nazwą użytkownika
     */
    public String nickname = null;
    /**
     * Atrybut będący wzorcem hasła
     */
    private final String ipPatt = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    /**
     * Atrybut będący skompilowaną wersją wzorca hasła
     */
    private final Pattern patt = Pattern.compile(ipPatt);
    /**
     * Atrybut będący strumieniem wyjściowych danych dla klienta
     */
    private DataOutputStream send;
    /**
     * Atrybut będący strumieniem wejściowym danych dla klienta
     */
    private DataInputStream receive;
    /**
     * Metoda, której głównym zadaniem jest ustawienie parametrów gniazda klienta
     * @return Nic nie zwraca
     */
    @Override
    public Void call() {
        try {
            try {
                setParametres(new Socket(IP, 1522));
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
    protected void catchServe(@NotNull Exception except){
        if(except.getMessage()==null) JOptionPane.showMessageDialog(null, "Unexpected exception", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "Klient", "error");
    }
    /**
     * Metoda ustawiająca podstawowe parametry gniazda i strumieni wejścia/wyjścia danych
     * @param sock Gniazdo ustawione do połączenia klienta i serwera
     */
    public void setParametres(@NotNull Socket sock){
        try {
            this.sock = sock;
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
     * @param klient Instancja klasy klient
     */
    public void polacz(Klient klient) {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Void> future = executor.submit(klient);
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
    public String hashPassword(@NotNull String passwordToHash, @NotNull String salt){
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
    public boolean walidacjaIP(@NotNull final String IP){
        Matcher match = patt.matcher(IP);
        return match.matches();
    }
    /**
     * Metoda pobierająca nickname użytkownika
     * @return Zwraca nickname zalogowanego użytkownika
     */
    public String getNickname(){
        return this.nickname;
    }
    /**
     * Metoda pobierająca aktualne IP
     * @return Zwraca aktualne IP ustawione do podłączenia klienta do serwera
     */
    protected String getKlientIP(){
        return this.IP;
    }
    /**
     * Metoda typu setter, której celem jest ustawienie IP do połączenia z serwerem
     * @param IP IP do ustawienia do połączenia z serwerem
     */
    protected void setKlientIP(String IP){
        this.IP = IP;
    }
    /**
     * Metoda, której celem jest zakończenie połączenia z serwerem danego klienta
     */
    public void zakonczPolaczenie(){
        try {
            send.close();
            receive.close();
            sock.close();
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
    public int dialogTakNie(@NotNull String message){
        Object[] takNie = {"Tak", "Nie"};
        return JOptionPane.showOptionDialog(null,message,"Potwierdzenie",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, takNie, null);
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu odzyskania hasła dla użytkownika
     * @param recoveryData Lista danych niezbednych do odzyskania konta
     * @return Zwraca odebrane dane od serwera przy odzyskiwaniu konta
     */
    protected List<String> odzyskaj(List<String> recoveryData){
        try {
            send.writeUTF("Recovery");
            send.flush();
            send.writeInt(recoveryData.size());
            send.flush();
            for(String data:recoveryData){
                send.writeUTF(data);
                send.flush();
            }
            List<String> listaDanych = new LinkedList<>();
            listaDanych.add(receive.readUTF());
            return listaDanych;
        }
        catch (Exception except) {
            catchServe(except);
        }
        return null;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu zarejestrowania nowego użytkownika
     * @param dane Lista danych uzyskanych przy rejestracji
     * @return Zwraca listę danych otrzymaną od serwera
     */
    protected List<String> zarejestruj(List<String> dane) {
        try {
            send.writeUTF("Register");
            send.flush();
            send.writeInt(dane.size());
            send.flush();
            for(String s: dane){
                send.writeUTF(s);
                send.flush();
            }
            List<String> listaDanych = new LinkedList<>();
            listaDanych.add(receive.readUTF());
            return listaDanych;
        }
        catch (Exception except) {
            catchServe(except);
        }
        return null;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu zalogowania użytkownika
     * @param loginUzytkownika Login użytkownika
     * @param hasloUzytkownika Hasło użytkownika
     * @return  Zwraca listę danych otrzymaną od serwera
     */
    protected List<String> zaloguj(String loginUzytkownika, String hasloUzytkownika) {
        try {
            send.writeUTF("Login");
            send.flush();
            send.writeUTF(loginUzytkownika);
            send.flush();
            send.writeUTF(hasloUzytkownika);
            send.flush();
            this.nickname = loginUzytkownika;
            List<String> listaDanych = new LinkedList<>();
            for(int i=0; i<2;i++) {
                listaDanych.add(receive.readUTF());
            }
            return listaDanych;
        }
        catch (Exception except) {
            catchServe(except);
        }
        return null;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu wylogowania pojedynczego użytkownika
     * @param loginUzytkownika Login użytkownika
     * @return Zwraca listę danych otrzymaną od serwera
     */
    protected List<String> wyloguj(String loginUzytkownika){
        try {
            send.writeUTF("Logout");
            send.flush();
            send.writeUTF(loginUzytkownika);
            send.flush();
            List<String> listaDanych = new LinkedList<>();
            listaDanych.add(receive.readUTF());
            return listaDanych;
        }
        catch (Exception except) {
            catchServe(except);
        }
        return null;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem, aby zarządzać konstrukcją bazy danych
     * @param managementOption Opcja zarządzania bazą danych
     * @return Zwraca wiadomość o tym, czy dany transfer danych się powiódł
     */
    protected String zarzadzaj(@NotNull String managementOption){
        try {
            send.writeUTF("Management");
            send.flush();
            send.writeUTF(managementOption);
            send.flush();
            return receive.readUTF();
        }
        catch (Exception except) {
            catchServe(except);
        }
        return null;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu wysłania danych do bazy z odpowiedniego dialogboxa
     * @param option Typ dialog boxa, z którego wysyłane są dane
     * @return Zwraca wiadomość o tym, czy dany transfer danych się powiódł
     */
    protected String wysylajDane(@NotNull String option){
        try {
            send.writeUTF("DataPass");
            send.flush();
            send.writeUTF(option);
            send.flush();
            send.writeInt(this.panelData.size());
            send.flush();
            for(String data: this.panelData){
                send.writeUTF(data);
                send.flush();
            }
            this.panelData.clear();
            return receive.readUTF();
        }
        catch (Exception except) {
            catchServe(except);
        }
        return null;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu otrzymania danych do odpowiedniego dialogboxa
     * @param option Typ dialog boxa, z którego wysyłane są dane
     * @param userID ID użytkownika
     * @return Zwraca listę danych dla danej operacji
     */
    protected List<String> otrzymujDane(@NotNull String option, @NotNull String userID){
        try{
            send.writeUTF("DataReceive");
            send.flush();
            send.writeUTF(option);
            send.flush();
            switch (option) {
                case "ReviewMyRents", "ReviewReturns", "ReviewRents", "ReviewMyReturns" -> {
                    send.writeUTF(userID);
                    send.flush();
                }
            }
            int size = receive.readInt();
            List<String> listaDanych = new LinkedList<>();
            for(int i=0; i<size; i++){
                listaDanych.add(receive.readUTF());
            }
            return listaDanych;
        } catch (Exception except) {
            catchServe(except);
        }
        return null;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu otrzymania id klienta do odpowiedniego dialogboxa
     * @param Nick Zaznaczony przez administratora nickname użytkownika
     * @return Zwraca pobrane ID użytkownika
     */
    protected String pobierzIDKlienta(@NotNull String Nick){
        try {
            send.writeUTF("GetClientID");
            send.flush();
            send.writeUTF(Nick);
            send.flush();
            return receive.readUTF();
        } catch (Exception except) {
            catchServe(except);
        }
        return "";
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu otrzymania id płyty DVD do odpowiedniego dialogboxa
     * @param dvdName Nazwa płyty DVD
     * @return Zwraca ID otrzymanej płyty DVD
     */
    protected int pobierzIDDVD(@NotNull String dvdName) {
        try {
            send.writeUTF("GetDVDID");
            send.flush();
            send.writeUTF(dvdName);
            send.flush();
            return receive.readInt();
        } catch (Exception except) {
            catchServe(except);
        }
        return -1;
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu zaktualizowania ilości płyt DVD w bazie danych
     * @param updatingID ID aktualizowanego stanu płyty DVD
     * @param updatingItem ID aktualizowanej płyty DVD
     */
    protected void zaktualizujStan(String updatingItem, int updatingID){
        try {
            send.writeUTF("UpdateCount");
            send.flush();
            send.writeUTF("dvds_data");
            send.flush();
            send.writeInt(updatingID);
            send.flush();
            send.writeUTF(updatingItem);
            send.flush();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest wymiana danych z serwerem w celu usunięcia wybranej rezerwacji z bazy danych
     * @param dvdID ID płyty DVD
     * @param userID ID Użytkownika
     */
    protected void usunRezerwacje(String userID, int dvdID){
        try{
            send.writeUTF("DeleteReservation");
            send.flush();
            send.writeUTF(userID);
            send.flush();
            send.writeInt(dvdID);
            send.flush();
        }
        catch (Exception except) {
            catchServe(except);
        }
    }
    /**
     * Metoda, której celem jest komunikacja z serwerem w celu pobrania nazwy użytkownika z bazy danych
     * @param userID ID użytkownika
     * @return Zwraca nickname użytkownika o podanym ID
     */
    protected String downloadNickname(@NotNull String userID){
        try {
            send.writeUTF("DownloadNickname");
            send.flush();
            send.writeUTF(userID);
            send.flush();
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
     * @param username Nazwa użytkownika
     */
    @Contract(pure = true)
    protected List<String> notifications(@NotNull String notificationType, @NotNull String username){
        try{
            send.writeUTF(notificationType+"Notifications");
            send.flush();
            send.writeUTF(username);
            send.flush();
            panelData.clear();
            switch(notificationType) {
                case "User" -> {
                    int size = receive.readInt();
                    for(int i=0; i<size; i++){
                        panelData.add(receive.readUTF());
                    }
                    return panelData;
                }
                case "Admin" -> {
                    int size = receive.readInt();
                    for(int i=0; i<size; i++){
                        panelData.add(receive.readUTF());
                    }
                    int size2 = receive.readInt();
                    panelData.add("SecondNotificationLine");
                    for(int i=0; i<size2; i++){
                        panelData.add(receive.readUTF());
                    }
                    return panelData;
                }
            }
        }
        catch (Exception except){
            catchServe(except);
        }
        return null;
    }
}
