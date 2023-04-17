/**
 * @file EkranSerwer.java
 * @brief Plik zawierający kod głównego okna serwera
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
package com.server;
import com.client.*;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
/**
 * Klasa zawierająca pola i metody obsługujące konstrukcję GUI i techniczną serwera
 */
public class EkranSerwer extends javax.swing.JFrame {
    /**
     * Atrybut będący nazwą użytkownika
     */
    public static String nick;
    /**
     * Atrybut będący hasłem użytkownika
     */
    public static String pass;
    /**
     * Atrybut będący rangą użytkownika
     */
    public static String rank;
    /**
     * Atrybut będący identyfikatorem użytkownika
     */
    public static String userID;
    /**
     * Atrybut będący operacją, którą ma przeprowadzić serwer z bazą danych, otrzymywana od klienta
     */
    public static String operation;
    /**
     * Atrybut będący operacją, dzięki której wiadomo czy wyświetlać chcemy wszystkie wypożyczenia/zwroty, czy konkretnego użytkownika
     */
    public static String passOperation;
    /**
     * Atrybut określający, jaka operacja na konstrukcji bazy danych ma być wykonana
     */
    public static String managementOperation;
    /**
     * Atrybut będący wiadomością informacyjną
     */
    public static String message;
    /**
     * Atrybut będący buforową nazwą dla aktualizowanego w bazie itemu
     */
    public static String updatingItem;
    /**
     * Atrybut będący nazwą użytkownika zaznaczoną przez administratora w jego panelu
     */
    public static String selectedNick;
    /**
     * Atrybut będący nazwą użytkownika, określający, na którym kliencie ma zostać wykonana operacja
     */
    public static String whichClient;
    /**
     * Atrybut będący nazwą filmu
     */
    public static String filmName;
    /**
     * Atrybut będący buforowym identyfikatorem dla aktualizowanego itemu
     */
    public static int updatingID;
    /**
     * Atrybut będący identyfikatorem płyty DVD
     */
    public static int dvdID;
    /**
     * Atrybut będący nazwą tabeli
     */
    public static String tableName;
    /**
     * Atrybut określający czy serwer jest uruchomiony
     */
    public static boolean running = true;
    /**
     * Atrybut określający czy jest połączenie z bazą danych
     */
    public static boolean noDatabaseConnection = false;
    /**
     * Atrybut określający czy rezerwacja istnieje
     */
    protected static boolean existReservation = false;
    /**
     * Atrybut będący adresem IP do podłączenia się z bazą danych
     */
    public static String IP = "localhost";
    /**
     * Atrybut będący listą wszystkich wątków serwera
     */
    public static List<Thread> clients = new ArrayList<>();
    /**
     * Atrybut będący buforową listą zawierającą dane
     */
    public static final List<String> panelData = new ArrayList<>();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    public static final javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private static final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący gniazdem, z którego podłącza się klient
     */
    protected static Socket client;
    /**
     * Atrybut będący gniazdem serwera
     */
    protected static ServerSocket socket;
    /**
     * Atrybut będący strumieniem wejściowym danych
     */
    protected static DataInputStream receive;
    /**
     * Atrybut będący strumieniem wyjściowym danych
     */
    protected static DataOutputStream send;
    /**
     * Konstruktor odpowiadający za inicjalizację GUI i operacje wykonywane przez serwer
     */
    public EkranSerwer() {
        initComponents();
        this.setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    running = false;
                    if(!noDatabaseConnection) {
                        Klient.polacz();
                        Klient.wylogujWszystkich();
                        for(Thread thread: clients){
                            new Logs("[ " + new java.util.Date() + " ] " + "Thread called " + thread.getName() + " was deleted ", "EkranSerwer", "info");
                            thread.interrupt();
                        }
                        EkranSerwer.exception("try");
                        Klient.zakonczPolaczenie();
                    }
                    new Logs("[ " + new java.util.Date() + " ] " + "Server shutdown", "EkranSerwer", "info");
                    dispose();
                }
                catch (Exception ex){
                    setMessage(ex.getMessage());
                    new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "EkranSerwer", "fatal");
                    dispose();
                }
            }
        });
    }
    /**
     * Metoda odpowiadająca za zakończenie połączenia klienta z serwerem
     * @param option Parametr określający czy zamykanie połączenia następuje przez try/catch, czy normalnie
     */
    protected static void exception(String option){
        try {
            client.close();
            send.close();
            receive.close();
            if(option.equals("catch")) {
                noDatabaseConnection = true;
                new Logs("[ " + new java.util.Date() + " ] " + "Database no connection", "EkranSerwer", "fatal");
            }
        }
        catch (Exception ex){
            setMessage(ex.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "EkranSerwer", "error");
        }
    }
    /**
     * Metoda, której celem jest ustawienie adresu IP na odpowiednie pole w klasie
     * @param IP Parametr będący adresem IP
     */
    public static void setIP(String IP){
        EkranSerwer.IP = IP;
    }
    /**
     * Metoda, której celem jest dodawanie nowych wiadomości na pole tekstowe serwera
     * @param message Dodawana wiadomość
     */
    public static void setMessage(String message){
        jTextArea1.setText(jTextArea1.getText()+"\n"+message);
    }
    /**
     * Metoda zwracająca wiadomość
     * @return Zwraca wiadomość
     */
    public static String getMessage() {
        return message;
    }
    /**
     * Metoda inicjalizująca komponenty graficzne aplikacji serwera
     */
    private void initComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
            javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
            javax.swing.JButton jButton1 = new javax.swing.JButton();
            javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
            javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Serwer Wypożyczalni DVD");

            jPanel1.setBackground(new java.awt.Color(255, 255, 255));
            jPanel1.setPreferredSize(new java.awt.Dimension(400, 500));

            jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
            jLabel1.setText("Serwer Wypożyczalni DVD");

            jTextField1.setPreferredSize(new java.awt.Dimension(200, 27));

            jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
            jLabel2.setText("Ustaw adres IP: ");

            jButton1.setBackground(new java.awt.Color(89, 168, 105));
            jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
            jButton1.setText("Ustaw IP");
            jButton1.setBorder(null);
            jButton1.setPreferredSize(new java.awt.Dimension(130, 35));
            jButton1.addActionListener(this::jButton1ActionPerformed);

            jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
            jLabel3.setText("Logi serwera:");

            jTextArea1.setColumns(1);
            jTextArea1.setRows(3);
            jTextArea1.setEnabled(false);
            jTextArea1.setDisabledTextColor(Color.BLACK);
            jScrollPane1.setViewportView(jTextArea1);

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGap(96, 96, 96)
                                                    .addComponent(jLabel1))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGap(27, 27, 27)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                    .addGap(11, 11, 11)
                                                                    .addComponent(jLabel2)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                            .addGap(115, 115, 115)
                                                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGap(157, 157, 157)
                                                    .addComponent(jLabel3)))
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(jLabel1)
                                    .addGap(30, 30, 30)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2))
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(22, 22, 22))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            pack();
        }
        catch(Exception ex){
            setMessage(ex.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "EkranSerwer", "error");
        }
    }
    /**
     * Metoda obsługująca sytuację wciśnięcia przycisku Ustaw IP
     * @param evt Bufor pobierający event utworzony podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        int reply = Klient.dialogTakNie("To działanie ponownie uruchomi serwer!\nCzy kontynuować?");
        if(reply== JOptionPane.YES_OPTION) {
            if (Klient.walidacjaIP(jTextField1.getText())) {
                setIP(jTextField1.getText());
                JOptionPane.showMessageDialog(this, "Pomyślnie ustawiono nowe IP do połączenia z serwerem!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                java.awt.EventQueue.invokeLater(() -> new EkranSerwer().setVisible(true));
                jTextArea1.setText("");
                setMessage("Ustawiono nowe IP do połączenia serwera z bazą danych:\n" + IP);
                jTextField1.setText("");
                new Logs("[ " + new java.util.Date() + " ] " + "New IP: " + IP + " for connection to database was set", "EkranSerwer", "info");
            } else {
                JOptionPane.showMessageDialog(this, "Wprowadzone IP jest nieprawidłowe!", "Błąd", JOptionPane.ERROR_MESSAGE);
                new Logs("[ " + new java.util.Date() + " ] " + "Not valid IP for connection to database", "EkranSerwer", "error");
            }
        }
    }
    /**
     * Metoda, której głównym zadaniem jest akceptowanie podłączeń nowych klientów pod serwer
     */
    private static void serverOperations() {
        java.awt.EventQueue.invokeLater(() -> new EkranSerwer().setVisible(true));
        try {
            socket = new ServerSocket(1522);
            socket.setReuseAddress(true);
            while(running) {
                client = socket.accept();
                setMessage("Połączenie z IP: " + client.getInetAddress().getHostAddress());
                SwingWorker<Void, Void> worker = new BackgroundHandler();
                worker.execute();
            }
        } catch (IOException except) {
           setMessage(except.getMessage());
           new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "EkranSerwer", "fatal");
        }
    }
    /**
     * Metoda pozwalająca na uruchomienie serwera
     * @param args Argumenty przyjmowane podczas uruchamiania aplikacji
     */
    public static void main(String[] args) {
        new Logs("[ " + new java.util.Date() + " ] " + "Server running", "EkranSerwer", "info");
        serverOperations();
    }
}
/**
 * Klasa pozwalająca na działanie wątków niezależnie od GUI serwera
 */
class BackgroundHandler extends SwingWorker<Void, Void> {
    /**
     * Klasa implementująca interfejs Runnable tak, aby można było tworzyć nowe wątki na serwerze
     */
    private static class ClientHandler implements Runnable {
        /**
         * Atrybut będący pomocniczym iteratorem pętli
         */
        protected static int j = 1;
        /**
         * Atrybut będący kontenerem, dzięki któremu można ustalić to ile wątek ma czekać na wykonanie się jakiejś metody
         */
        private static Future<String> future;
        /**
         * Metoda, której zadaniem jest obsłużenie wyjątków wygenerowanych z całej aplikacji przez try/catche
         * @param ex Przyjęty wyjątek
         */
        public static void unexpectedEvent(@NotNull Exception ex){
            String message;
            if(ex.getMessage()==null) message = "Unexpected event";
            else message = ex.getMessage();
            EkranSerwer.setMessage(message);
            EkranSerwer.exception("catch");
            future.cancel(true);
            new Logs("[ " + new java.util.Date() + " ] " + message, "EkranSerwer", "error");
        }
        /**
         * Przesłonięcie metody run, obsługującej uruchomiony wątek
         */
        @Override
        public void run() {
            try {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                InputStream socket_receive = EkranSerwer.client.getInputStream();
                EkranSerwer.receive = new DataInputStream(socket_receive);
                OutputStream socket_send;
                socket_send = EkranSerwer.client.getOutputStream();
                EkranSerwer.send = new DataOutputStream(socket_send);
                EkranSerwer.operation = EkranSerwer.receive.readUTF();
                switch (EkranSerwer.operation) {
                    case "Login" -> {
                        EkranSerwer.passOperation = "Login";
                        for (int i = 0; i < 2; i++) {
                            String buffer = EkranSerwer.receive.readUTF();
                            if (j % 2 == 1) EkranSerwer.nick = buffer;
                            else EkranSerwer.pass = buffer;
                            j++;
                        }
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(EkranLogowania.wiadomosc);
                            EkranSerwer.send.flush();
                            EkranSerwer.send.writeUTF(EkranSerwer.rank);
                            EkranSerwer.send.flush();
                            EkranLogowania.wiadomosc = "";
                            EkranSerwer.rank = "";
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "Logout" -> {
                        EkranSerwer.passOperation = "Logout";
                        EkranSerwer.nick = EkranSerwer.receive.readUTF();
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(EkranGlownyAdmin.message);
                            EkranSerwer.send.flush();
                            EkranSerwer.running = true;
                            EkranGlownyAdmin.message = "";
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "LogoutAll" -> {
                        EkranSerwer.passOperation = "LogoutAll";
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(EkranGlownyAdmin.message);
                            EkranSerwer.send.flush();
                            EkranSerwer.send.writeBoolean(true);
                            EkranSerwer.send.flush();
                            EkranSerwer.running = true;
                            EkranGlownyAdmin.message = "";
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "Register" -> {
                        EkranSerwer.passOperation = "Register";
                        int list_size = Integer.parseInt(EkranSerwer.receive.readUTF());
                        for (int i = 0; i < list_size; i++) {
                            if (i == 0) BazaDanych.name = EkranSerwer.receive.readUTF();
                            else if (i == 1) BazaDanych.surname = EkranSerwer.receive.readUTF();
                            else if (i == 2) BazaDanych.nick = EkranSerwer.receive.readUTF();
                            else if (i == 3) BazaDanych.email = EkranSerwer.receive.readUTF();
                            else if (i == 4) BazaDanych.phone_number = EkranSerwer.receive.readUTF();
                            else if (i == 5) BazaDanych.pass = EkranSerwer.receive.readUTF();
                            else if (i == 6) BazaDanych.coverage_key = EkranSerwer.receive.readUTF();
                        }
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(1, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(EkranUtworzKonto.blad);
                            EkranSerwer.send.flush();
                            EkranUtworzKonto.blad = "";
                            EkranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "Management" -> {
                        EkranSerwer.passOperation = "Management";
                        EkranSerwer.managementOperation = EkranSerwer.receive.readUTF();
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(EkranGlownyAdmin.message);
                            EkranGlownyAdmin.message = "";
                            EkranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "Recovery" -> {
                        new Logs("[ " + new java.util.Date() + " ] " + "Account recovery from: " + EkranSerwer.client.getInetAddress().getHostAddress()  + " Nickname: " + EkranSerwer.nick, "EkranSerwer", "info");
                        EkranSerwer.passOperation = "Recovery";
                        int list_size = Integer.parseInt(EkranSerwer.receive.readUTF());
                        for (int i = 0; i < list_size; i++) {
                            if (i == 0) BazaDanych.nick = EkranSerwer.receive.readUTF();
                            else if (i == 1) BazaDanych.coverage_key = EkranSerwer.receive.readUTF();
                            else if (i == 2) BazaDanych.pass = EkranSerwer.receive.readUTF();
                        }
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(EkranPrzywrocHaslo.message);
                            EkranSerwer.send.flush();
                            EkranPrzywrocHaslo.message = "";
                            EkranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "DataPass" -> {
                        EkranSerwer.passOperation = EkranSerwer.receive.readUTF();
                        int size = Integer.parseInt(EkranSerwer.receive.readUTF());
                        for (int i = 0; i < size; i++) {
                            EkranSerwer.panelData.add(EkranSerwer.receive.readUTF());
                        }
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(EkranSerwer.message);
                            EkranSerwer.send.flush();
                            EkranSerwer.message = "";
                            EkranSerwer.panelData.clear();
                            EkranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "DataReceive" -> {
                        EkranSerwer.passOperation = EkranSerwer.receive.readUTF();
                        if (EkranSerwer.passOperation.equals("ReviewMyRents") || EkranSerwer.passOperation.equals("ReviewMyReturns") || EkranSerwer.passOperation.equals("ReviewRents") || EkranSerwer.passOperation.equals("ReviewReturns")) {
                            EkranSerwer.userID = EkranSerwer.receive.readUTF();
                        }
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(Integer.toString(EkranSerwer.panelData.size()));
                            for (String buffer : EkranSerwer.panelData) {
                                EkranSerwer.send.writeUTF(buffer);
                                EkranSerwer.send.flush();
                            }
                            EkranSerwer.panelData.clear();
                            EkranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "GetClientID" -> {
                        try {
                            EkranSerwer.whichClient = EkranSerwer.receive.readUTF();
                            if (EkranSerwer.whichClient.equals("selectedUser")) {
                                EkranSerwer.selectedNick = EkranSerwer.receive.readUTF();
                            }
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(EkranSerwer.userID);
                            EkranSerwer.send.flush();
                            EkranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "GetDVDID" -> {
                        try {
                            EkranSerwer.filmName = EkranSerwer.receive.readUTF();
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeInt(EkranSerwer.dvdID);
                            EkranSerwer.send.flush();
                            EkranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "UpdateCount" -> {
                        EkranSerwer.passOperation = "UpdateCount";
                        EkranSerwer.tableName = EkranSerwer.receive.readUTF();
                        EkranSerwer.updatingID = EkranSerwer.receive.readInt();
                        EkranSerwer.updatingItem = EkranSerwer.receive.readUTF();
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "DeleteReservation" -> {
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "CheckReservations" -> {
                        try {
                            EkranSerwer.filmName = EkranSerwer.receive.readUTF();
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeBoolean(EkranSerwer.existReservation);
                            EkranSerwer.send.flush();
                            EkranSerwer.existReservation = false;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "DownloadNickname" -> {
                        try {
                            EkranSerwer.userID = EkranSerwer.receive.readUTF();
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeUTF(Klient.nickname);
                            EkranSerwer.send.flush();
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "AdminNotifications" -> {
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeInt(Powiadomienia.adminReservationNotifications.size());
                            EkranSerwer.send.flush();
                            for(String buffer: Powiadomienia.adminReservationNotifications){
                                EkranSerwer.send.writeUTF(buffer);
                                EkranSerwer.send.flush();
                            }
                            EkranSerwer.send.writeInt(Powiadomienia.adminDetentionDVDsNotifications.size());
                            EkranSerwer.send.flush();
                            for(String buffer: Powiadomienia.adminDetentionDVDsNotifications){
                                EkranSerwer.send.writeUTF(buffer);
                                EkranSerwer.send.flush();
                            }
                            Powiadomienia.adminReservationNotifications.clear();
                            Powiadomienia.adminDetentionDVDsNotifications.clear();
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "UserNotifications" -> {
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            EkranSerwer.send.writeInt(Powiadomienia.userDetentionDVDsNotifications.size());
                            EkranSerwer.send.flush();
                            for(String buffer: Powiadomienia.userDetentionDVDsNotifications){
                                EkranSerwer.send.writeUTF(buffer);
                                EkranSerwer.send.flush();
                            }
                            Powiadomienia.userDetentionDVDsNotifications.clear();
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    default -> {
                        EkranSerwer.setMessage("Inna opcja ");
                        throw new Exception("Wystąpił nieoczekiwany błąd!");
                    }
                }
            } catch (Exception except) {
                EkranSerwer.setMessage(except.getMessage());
                new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "EkranSerwer", "fatal");
            }
        }
    }
    /**
     * Przesłonięcie metody doInBackground, dzięki czemu wątki mogą działać w tle nie wpływając na GUI serwera
     * @return Nie zwraca nic
     */
    @Override
    protected Void doInBackground() {
        Thread thread = new Thread(new ClientHandler());
        EkranSerwer.clients.add(thread);
        thread.start();
        new Logs("[ " + new java.util.Date() + " ]" + " New thread called " + thread.getName() + " was created ", "EkranSerwer", "info");
        return null;
    }
}
