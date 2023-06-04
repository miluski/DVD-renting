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
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class EkranSerwer extends javax.swing.JFrame {
    /**
     * Atrybut będący nazwą użytkownika
     */
    public String nick;
    /**
     * Atrybut będący hasłem użytkownika
     */
    public String pass;
    /**
     * Atrybut będący identyfikatorem użytkownika
     */
    public String userID;
    /**
     * Atrybut będący operacją, którą ma przeprowadzić serwer z bazą danych, otrzymywana od klienta
     */
    public String operation;
    /**
     * Atrybut będący operacją, dzięki której wiadomo czy wyświetlać chcemy wszystkie wypożyczenia/zwroty, czy konkretnego użytkownika
     */
    public String passOperation;
    /**
     * Atrybut określający, jaka operacja na konstrukcji bazy danych ma być wykonana
     */
    public String managementOperation;
    /**
     * Atrybut będący wiadomością informacyjną
     */
    public String message;
    /**
     * Atrybut będący buforową nazwą dla aktualizowanego w bazie itemu
     */
    public String updatingItem;
    /**
     * Atrybut będący nazwą użytkownika zaznaczoną przez administratora w jego panelu
     */
    public String selectedNick;
    /**
     * Atrybut będący nazwą filmu
     */
    public String filmName;
    /**
     * Atrybut będący buforowym identyfikatorem dla aktualizowanego itemu
     */
    public int updatingID;
    /**
     * Atrybut będący identyfikatorem płyty DVD
     */
    public int dvdID;
    /**
     * Atrybut będący nazwą tabeli
     */
    public String tableName;
    /**
     * Atrybut określający czy serwer jest uruchomiony
     */
    public boolean running = true;
    /**
     * Atrybut określający czy jest połączenie z bazą danych
     */
    public boolean noDatabaseConnection = false;
    /**
     * Nickname pobieranego uzytkownika
     */
    public String nickname;
    /**
     * Atrybut określający czy rezerwacja istnieje
     */
    protected boolean existReservation = false;
    /**
     * Atrybut będący adresem IP do podłączenia się z bazą danych
     */
    public String IP = "localhost";
    /**
     * Atrybut będący listą wszystkich wątków serwera
     */
    public List<Thread> clients = new ArrayList<>();
    /**
     * Atrybut będący buforową listą zawierającą dane
     */
    public final List<String> panelData = new ArrayList<>();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    public final javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący gniazdem, z którego podłącza się klient
     */
    protected Socket client;
    /**
     * Atrybut będący gniazdem serwera
     */
    protected ServerSocket socket;
    /**
     * Atrybut będący strumieniem wejściowym danych
     */
    protected DataInputStream receive;
    /**
     * Atrybut będący strumieniem wyjściowym danych
     */
    protected DataOutputStream send;
    /**
     * Instancja klasy Klient
     */
    private final Klient klient = new Klient();
    /**
     * Konstruktor odpowiadający za inicjalizację GUI i operacje wykonywane przez serwer
     */
    public EkranSerwer() {
        initComponents();
        this.setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    running = false;
                    if(!noDatabaseConnection) {
                        new BazaDanych(new EkranSerwer(),"LogoutAll", IP);
                        for(Thread thread: clients){
                            new Logs("[ " + new java.util.Date() + " ] " + "Thread called " + thread.getName() + " was deleted ", "EkranSerwer", "info");
                            thread.interrupt();
                        }
                        exception("try");
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
    protected void exception(String option){
        try {
            if(client!=null)
                client.close();
            if(send!=null)
                send.close();
            if(receive!=null)
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
    public void setIP(String IP){
        this.IP = IP;
    }
    /**
     * Metoda, której celem jest dodawanie nowych wiadomości na pole tekstowe serwera
     * @param message Dodawana wiadomość
     */
    public void setMessage(String message){
        jTextArea1.setText(jTextArea1.getText()+"\n"+message);
    }
    /**
     * Metoda zwracająca wiadomość
     * @return Zwraca wiadomość
     */
    public String getMessage() {
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
        int reply = klient.dialogTakNie("To działanie ponownie uruchomi serwer!\nCzy kontynuować?");
        if(reply== JOptionPane.YES_OPTION) {
            if (klient.walidacjaIP(jTextField1.getText())) {
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
     * @param ekranSerwer Instancja klasy EkranSerwer
     */
    public void serverOperations(EkranSerwer ekranSerwer) {
        try {
            ekranSerwer.socket = new ServerSocket(1522);
            ekranSerwer.socket.setReuseAddress(true);
            while(running) {
                ekranSerwer.client = ekranSerwer.socket.accept();
                ekranSerwer.setMessage("Połączenie z IP: " + ekranSerwer.client.getInetAddress().getHostAddress());
                SwingWorker<Void, Void> worker = new BackgroundHandler(ekranSerwer);
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
        EkranSerwer ekranSerwer = new EkranSerwer();
        ekranSerwer.setVisible(true);
        ekranSerwer.serverOperations(ekranSerwer);
    }

    /**
     * Metoda zwracająca operację wykonywaną przy wysyle danych do bazy
     * @return Zwraca operację wykonywaną przy wysyle danych do bazy
     */
    public String getPassOperation() {
        return passOperation;
    }

    /**
     * Metoda zwracająca operację wykonywaną przy wysyle danych do zarządzania do bazy danych
     * @return Zwraca operację wykonywaną przy wysyle danych do zarządzania do bazy danych
     */
    public String getManagementOperation() {
        return managementOperation;
    }
}
/**
 * Klasa pozwalająca na działanie wątków niezależnie od GUI serwera
 */
class BackgroundHandler extends SwingWorker<Void, Void> {
    /**
     * Instancja klasy EkranSerwer
     */
    private final EkranSerwer ekranSerwer;
    /**
     * Konstruktur umożliwiający inicjację działania wątku w tle
     * @param ekranSerwer Instancja klasy EkranSerwer
     */
    public BackgroundHandler(EkranSerwer ekranSerwer){
        this.ekranSerwer = ekranSerwer;
    }
    /**
     * Klasa implementująca interfejs Runnable tak, aby można było tworzyć nowe wątki na serwerze
     */
    private class ClientHandler implements Runnable {
        /**
         * Atrybut będący pomocniczym iteratorem pętli
         */
        protected int j = 1;
        /**
         * Atrybut będący kontenerem, dzięki któremu można ustalić to ile wątek ma czekać na wykonanie się jakiejś metody
         */
        private Future<String> future;
        /**
         * Metoda, której zadaniem jest obsłużenie wyjątków wygenerowanych z całej aplikacji przez try/catche
         * @param ex Przyjęty wyjątek
         */
        public void unexpectedEvent(@NotNull Exception ex){
            String message;
            if(ex.getMessage()==null) message = "Unexpected event";
            else message = ex.getMessage();
            ekranSerwer.setMessage(message);
            ekranSerwer.exception("catch");
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
                InputStream socketReceive = ekranSerwer.client.getInputStream();
                ekranSerwer.receive = new DataInputStream(socketReceive);
                OutputStream socketSend;
                socketSend = ekranSerwer.client.getOutputStream();
                ekranSerwer.send = new DataOutputStream(socketSend);
                ekranSerwer.operation = ekranSerwer.receive.readUTF();
                switch (ekranSerwer.operation) {
                    case "Login" -> {
                        ekranSerwer.passOperation = "Login";
                        for (int i = 0; i < 2; i++) {
                            String buffer = ekranSerwer.receive.readUTF();
                            if (j % 2 == 1) ekranSerwer.nick = buffer;
                            else ekranSerwer.pass = buffer;
                            j++;
                        }
                        try {
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            future = executor.submit(bazaDanych);
                            future.get(2, TimeUnit.SECONDS);
                            List<String> retrievedData = new ArrayList<>(bazaDanych.getPanelData());
                            ekranSerwer.send.writeUTF(retrievedData.get(0));
                            ekranSerwer.send.flush();
                            ekranSerwer.send.writeUTF(retrievedData.get(1));
                            ekranSerwer.send.flush();
                            retrievedData.clear();
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "Logout" -> {
                        ekranSerwer.passOperation = "Logout";
                        ekranSerwer.nick = ekranSerwer.receive.readUTF();
                        try {
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            future = executor.submit(bazaDanych);
                            future.get(2, TimeUnit.SECONDS);
                            List<String> retrievedData = new ArrayList<>(bazaDanych.getPanelData());
                            ekranSerwer.send.writeUTF(retrievedData.get(0));
                            ekranSerwer.send.flush();
                            ekranSerwer.running = true;
                            retrievedData.clear();
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "LogoutAll" -> {
                        ekranSerwer.passOperation = "LogoutAll";
                        try {
                            future = executor.submit(new BazaDanych());
                            future.get(2, TimeUnit.SECONDS);
                            ekranSerwer.send.flush();
                            ekranSerwer.send.writeBoolean(true);
                            ekranSerwer.send.flush();
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "Register" -> {
                        ekranSerwer.passOperation = "Register";
                        int listSize = ekranSerwer.receive.readInt();
                        ekranSerwer.panelData.clear();
                        for (int i = 0; i < listSize; i++) {
                            ekranSerwer.panelData.add(ekranSerwer.receive.readUTF());
                        }
                        try {
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            future = executor.submit(bazaDanych);
                            future.get(1, TimeUnit.SECONDS);
                            List<String> retrievedData = new ArrayList<>(bazaDanych.getPanelData());
                            ekranSerwer.send.writeUTF(retrievedData.get(0));
                            ekranSerwer.send.flush();
                            retrievedData.clear();
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "Management" -> {
                        ekranSerwer.passOperation = "Management";
                        ekranSerwer.managementOperation = ekranSerwer.receive.readUTF();
                        try {
                            future = executor.submit(new BazaDanych(ekranSerwer));
                            future.get(2, TimeUnit.SECONDS);
                            ekranSerwer.send.writeUTF(ekranSerwer.message);
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "Recovery" -> {
                        ekranSerwer.passOperation = "Recovery";
                        int listSize = ekranSerwer.receive.readInt();
                        ekranSerwer.panelData.clear();
                        for (int i = 0; i < listSize; i++) {
                            ekranSerwer.panelData.add(ekranSerwer.receive.readUTF());
                        }
                        new Logs("[ " + new java.util.Date() + " ] " + "Account recovery from: " + ekranSerwer.client.getInetAddress().getHostAddress()  + " Nickname: " + ekranSerwer.panelData.get(0), "EkranSerwer", "info");
                        try {
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            future = executor.submit(bazaDanych);
                            future.get(1, TimeUnit.SECONDS);
                            List<String> retrievedData = new ArrayList<>(bazaDanych.getPanelData());
                            ekranSerwer.send.writeUTF(retrievedData.get(0));
                            ekranSerwer.send.flush();
                            retrievedData.clear();
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "DataPass" -> {
                        ekranSerwer.passOperation = ekranSerwer.receive.readUTF();
                        int size = ekranSerwer.receive.readInt();
                        for (int i = 0; i < size; i++) {
                            ekranSerwer.panelData.add(ekranSerwer.receive.readUTF());
                        }
                        try {
                            future = executor.submit(new BazaDanych(ekranSerwer));
                            future.get(2, TimeUnit.SECONDS);
                            ekranSerwer.send.writeUTF(ekranSerwer.message);
                            ekranSerwer.send.flush();
                            ekranSerwer.message = "";
                            ekranSerwer.panelData.clear();
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "DataReceive" -> {
                        ekranSerwer.passOperation = ekranSerwer.receive.readUTF();
                        if (ekranSerwer.passOperation.equals("ReviewMyRents") || ekranSerwer.passOperation.equals("ReviewMyReturns") || ekranSerwer.passOperation.equals("ReviewRents") || ekranSerwer.passOperation.equals("ReviewReturns")) {
                            ekranSerwer.userID = ekranSerwer.receive.readUTF();
                        }
                        try {
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            future = executor.submit(bazaDanych);
                            future.get(1, TimeUnit.SECONDS);
                            List<String> retrievedData = new ArrayList<>(bazaDanych.getPanelData());
                            ekranSerwer.send.writeInt(retrievedData.size());
                            for (String buffer : retrievedData) {
                                ekranSerwer.send.writeUTF(buffer);
                                ekranSerwer.send.flush();
                            }
                            retrievedData.clear();
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "GetClientID" -> {
                        try {
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            bazaDanych.getPanelData().clear();
                            ekranSerwer.selectedNick = ekranSerwer.receive.readUTF();
                            future = executor.submit(bazaDanych);
                            future.get(2, TimeUnit.SECONDS);
                            ekranSerwer.send.writeUTF(bazaDanych.getPanelData().get(0));
                            ekranSerwer.send.flush();
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "GetDVDID" -> {
                        try {
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            bazaDanych.getPanelData().clear();
                            ekranSerwer.filmName = ekranSerwer.receive.readUTF();
                            future = executor.submit(bazaDanych);
                            future.get(2, TimeUnit.SECONDS);
                            ekranSerwer.send.writeInt(Integer.parseInt(bazaDanych.getPanelData().get(0)));
                            ekranSerwer.send.flush();
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "UpdateCount" -> {
                        ekranSerwer.passOperation = "UpdateCount";
                        ekranSerwer.tableName = ekranSerwer.receive.readUTF();
                        ekranSerwer.updatingID = ekranSerwer.receive.readInt();
                        ekranSerwer.updatingItem = ekranSerwer.receive.readUTF();
                        try {
                            future = executor.submit(new BazaDanych(ekranSerwer));
                            future.get(2, TimeUnit.SECONDS);
                            ekranSerwer.running = true;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "DeleteReservation" -> {
                        try {
                            ekranSerwer.userID = ekranSerwer.receive.readUTF();
                            ekranSerwer.dvdID = ekranSerwer.receive.readInt();
                            future = executor.submit(new BazaDanych(ekranSerwer));
                            future.get(2, TimeUnit.SECONDS);
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "CheckReservations" -> {
                        try {
                            ekranSerwer.filmName = ekranSerwer.receive.readUTF();
                            future = executor.submit(new BazaDanych(ekranSerwer));
                            future.get(2, TimeUnit.SECONDS);
                            ekranSerwer.send.writeBoolean(ekranSerwer.existReservation);
                            ekranSerwer.send.flush();
                            ekranSerwer.existReservation = false;
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "DownloadNickname" -> {
                        try {
                            ekranSerwer.userID = ekranSerwer.receive.readUTF();
                            future = executor.submit(new BazaDanych(ekranSerwer));
                            future.get(2, TimeUnit.SECONDS);
                            ekranSerwer.send.writeUTF(ekranSerwer.nickname);
                            ekranSerwer.send.flush();
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "UserNotifications" -> {
                        try {
                            ekranSerwer.nick = ekranSerwer.receive.readUTF();
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            future = executor.submit(bazaDanych);
                            future.get(1, TimeUnit.SECONDS);
                            List<String> retrievedData = new ArrayList<>(bazaDanych.getPanelData());
                            ekranSerwer.send.writeInt(retrievedData.size());
                            ekranSerwer.send.flush();
                            for(String buffer: retrievedData){
                                ekranSerwer.send.writeUTF(buffer);
                                ekranSerwer.send.flush();
                            }
                            retrievedData.clear();
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    case "AdminNotifications" -> {
                        try {
                            ekranSerwer.nick = ekranSerwer.receive.readUTF();
                            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
                            future = executor.submit(bazaDanych);
                            future.get(1, TimeUnit.SECONDS);
                            List<String> retrievedData = new ArrayList<>(bazaDanych.getPanelData());
                            List<String> retrievedData2 = new ArrayList<>(bazaDanych.getPanelData2());
                            ekranSerwer.send.writeInt(retrievedData.size());
                            ekranSerwer.send.flush();
                            for(String buffer: retrievedData){
                                ekranSerwer.send.writeUTF(buffer);
                                ekranSerwer.send.flush();
                            }
                            ekranSerwer.send.writeInt(retrievedData2.size());
                            ekranSerwer.send.flush();
                            for(String buffer: retrievedData2){
                                ekranSerwer.send.writeUTF(buffer);
                                ekranSerwer.send.flush();
                            }
                            retrievedData.clear();
                            retrievedData2.clear();
                        } catch (Exception ex) {
                            unexpectedEvent(ex);
                        }
                    }
                    default -> {
                        ekranSerwer.setMessage("Inna opcja ");
                        throw new Exception("Wystąpił nieoczekiwany błąd!");
                    }
                }
            } catch (Exception except) {
                ekranSerwer.setMessage(except.getMessage());
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
        ekranSerwer.clients.add(thread);
        thread.start();
        new Logs("[ " + new java.util.Date() + " ]" + " New thread called " + thread.getName() + " was created ", "EkranSerwer", "info");
        return null;
    }
}
