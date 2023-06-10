package com.client;

import com.server.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.io.DataOutputStream;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Klasa zawierająca pola i metody niezbędne do komunikacji aplikacji klienta z aplikacją serwera
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class Klient implements Callable<Void>, Serializable {
    /**
     * Atrybut będący wzorcem hasła
     */
    private final String ipPatt = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    /**
     * Atrybut będący skompilowaną wersją wzorca hasła
     */
    private final Pattern patt = Pattern.compile(ipPatt);
    /**
     * Atrybut będący ciągiem znaków, w którym zapisywane jest IP, do którego użytkownik będzie chciał się podłączyć
     */
    public String IP;
    /**
     * Atrybut będący nazwą użytkownika
     */
    public String nickname = null;
    /**
     * Atrybut będący gniazdem, pod które będzie podłączał się klient
     */
    private Socket sock;
    /**
     * Atrybut będący strumieniem wyjściowych danych dla klienta
     */
    private DataOutputStream send;
    /**
     * Atrybut będący strumieniem wejściowym danych dla klienta
     */
    private DataInputStream receive;
    /**
     * Konstruktor umożliwiający tworzenie instancji klasy
     */
    public Klient() {

    }
    /**
     * Konstruktor umożliwiający ustawienie IP do połączenia z serwerem
     *
     * @param IP IP ustawiane do połączenia klienta z serwerem
     */
    public Klient(String IP) {
        this.IP = IP;
    }

    /**
     * Przesłonięcie metody konwertującej dane na typ łańcucha znaków
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Metoda, której głównym zadaniem jest ustawienie parametrów gniazda klienta
     *
     * @return Nic nie zwraca
     */
    @Override
    public Void call() {
        try {
            try {
                setParametres(new Socket(IP, 1522));
            } catch (IOException except) {
                catchServe(except);
            }
            return null;
        } catch (Exception except) {
            catchServe(except);
            return null;
        }
    }

    /**
     * Metoda obsługująca postępowanie w razie wystąpienia wyjątku
     *
     * @param except Otrzymany wyjątek
     */
    protected void catchServe(@NotNull Exception except) {
        if (except.getMessage() == null)
            JOptionPane.showMessageDialog(null, "Unexpected exception", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(null, except.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "Klient", "error");
    }

    /**
     * Metoda ustawiająca podstawowe parametry gniazda i strumieni wejścia/wyjścia danych
     *
     * @param sock Gniazdo ustawione do połączenia klienta i serwera
     */
    public void setParametres(@NotNull Socket sock) {
        try {
            this.sock = sock;
            OutputStream socketSend = sock.getOutputStream();
            InputStream socketReceive = sock.getInputStream();
            send = new DataOutputStream(socketSend);
            receive = new DataInputStream(socketReceive);
        } catch (Exception except) {
            catchServe(except);
        }
    }

    /**
     * Metoda, której głównym zadaniem jest próba podłączenia się pod serwer, w przypadku gdy po 2 sekundach połączenie nie nastąpi to próba jest przerywana
     *
     * @param klient Instancja klasy klient
     * @param dane   Lista danych wysyłana na serwer
     * @return Zwraca listę danych pobraną od serwera
     */
    public List<?> polacz(Klient klient, List<String> dane) {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Void> future = executor.submit(klient);
            future.get(2, TimeUnit.SECONDS);
            wyslijDane(dane);
            return przyjmijDane();
        } catch (Exception except) {
            catchServe(except);
        }
        return null;
    }

    /**
     * Metoda, której głównym zadaniem jest hashing otrzymanego hasła
     *
     * @param passwordToHash Hasło do zahashowania
     * @param salt           Ziarno używane do hashowania hasła
     * @return Zwraca zahashowaną wersję hasła
     */
    public String hashPassword(@NotNull String passwordToHash, @NotNull String salt) {
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
     *
     * @param IP IP do zwalidowania
     * @return Zwraca true, gdy dostarczone IP jest prawidłowe
     */
    public boolean walidacjaIP(@NotNull final String IP) {
        return patt.matcher(IP).matches();
    }

    /**
     * Metoda pobierająca nickname użytkownika
     *
     * @return Zwraca nickname zalogowanego użytkownika
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Metoda pobierająca aktualne IP
     *
     * @return Zwraca aktualne IP ustawione do podłączenia klienta do serwera
     */
    public String getKlientIP() {
        return this.IP;
    }

    /**
     * Metoda typu setter, której celem jest ustawienie IP do połączenia z serwerem
     *
     * @param IP IP do ustawienia do połączenia z serwerem
     */
    protected void setKlientIP(String IP) {
        this.IP = IP;
    }

    /**
     * Metoda, której celem jest zakończenie połączenia z serwerem danego klienta
     */
    public void zakonczPolaczenie() {
        try {
            send.close();
            receive.close();
            sock.close();
        } catch (IOException except) {
            catchServe(except);
        }
    }

    /**
     * Metoda, której celem jest zwrócenie okna dialogowego tak/nie z customową wiadomością
     *
     * @param message Wiadomość okna dialogowego
     * @return Zwraca okno dialogowe z opcjami tak/nie
     */
    public int dialogTakNie(@NotNull String message) {
        Object[] takNie = {"Tak", "Nie"};
        return JOptionPane.showOptionDialog(null, message, "Potwierdzenie", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, takNie, null);
    }

    /**
     * Metoda, której zadaniem jest wysłanie danych do serwera
     *
     * @param dataList Lista wysyłanych danych
     */
    private void wyslijDane(List<String> dataList) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(dataList);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            send.write(byteArray);
            send.flush();
        } catch (Exception e) {
            catchServe(e);
        }
    }

    /**
     * Metoda, której zadaniem jest przyjmowanie danych od serwera
     *
     * @return Zwraca listę ciągów znaków odebraną od serwera
     */
    private List<?> przyjmijDane() {
        try {
            byte[] receivedBytes = new byte[2048];
            int byteArraySize = receive.read(receivedBytes);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivedBytes, 0, byteArraySize);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object receivedObject = objectInputStream.readObject();
            if (receivedObject instanceof List<?>) {
                return (List<?>) receivedObject;
            } else return null;
        } catch (Exception e) {
            catchServe(e);
        }
        return null;
    }
}
