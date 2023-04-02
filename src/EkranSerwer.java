/** @file EkranSerwer.java
 * @brief plik zawierający kod głównego okna serwera
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EkranSerwer extends javax.swing.JFrame {
    protected static String nick, pass, rank, userID, operation, passOperation, managementOperation, message, updatingItem, tableName, selectedNick, whichClient, filmName;
    private static int j = 1;
    protected static int updatingID, dvdID;
    private static boolean running = true, noDatabaseConnection = false;
    protected static boolean existReservation = false;
    protected static String IP = "localhost";
    protected static final List<String> panelData = new ArrayList<>();
    protected static final javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
    private static final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    private static Socket client;
    private static DataInputStream receive;
    private static DataOutputStream send;
    EkranSerwer() {
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
                        Klient.zakonczPolaczenie();
                    }
                    dispose();
                }
                catch (Exception ex){
                    setMessage("Kod błędu: " + ex);
                }
            }
        });
    }
    private static void exception(String option){
        try {
            send.close();
            receive.close();
            client.close();
            if(option.equals("catch")) noDatabaseConnection = true;
        }
        catch (Exception ex){
            setMessage(ex.getMessage());
        }
    }
    private static void setIP(String IP){
        EkranSerwer.IP = IP;
    }
    protected static void setMessage(String message){
        jTextArea1.setText(jTextArea1.getText()+"\n"+message);
    }
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
            setMessage("Kod błędu: " + ex);
        }
    }
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
            } else {
                JOptionPane.showMessageDialog(this, "Wprowadzone IP jest nieprawidłowe!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private static void serverOperations() {
        java.awt.EventQueue.invokeLater(() -> new EkranSerwer().setVisible(true));
        try {
            ServerSocket socket = new ServerSocket(1522);
            socket.setReuseAddress(true);
            while(running) {
                client = socket.accept();
                InputStream socket_receive = client.getInputStream();
                receive = new DataInputStream(socket_receive);
                OutputStream socket_send = client.getOutputStream();
                send = new DataOutputStream(socket_send);
                operation = receive.readUTF();
                setMessage("Do serwera podlaczyl sie podany klient:\n" + client);
                System.out.println("Operatin = " + operation);
                switch (operation) {
                    case "Login" -> {
                        passOperation = "Login";
                        for (int i = 0; i < 2; i++) {
                            String buffer = receive.readUTF();
                            if (j % 2 == 1) nick = buffer;
                            else pass = buffer;
                            j++;
                        }
                        try {
                            BazaDanych.databaseConnect();
                            send.writeUTF(EkranLogowania.wiadomosc);
                            send.flush();
                            send.writeUTF(rank);
                            send.flush();
                            exception("try");
                            EkranLogowania.wiadomosc = "";
                            rank = "";
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "Logout" -> {
                        passOperation = "Logout";
                        nick = receive.readUTF();
                        try {
                            BazaDanych.databaseConnect();
                            send.writeUTF(EkranGlownyAdmin.message);
                            send.flush();
                            exception("try");
                            running = true;
                            EkranGlownyAdmin.message = "";
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "LogoutAll" -> {
                        passOperation = "LogoutAll";
                        try {
                            BazaDanych.databaseConnect();
                            send.writeUTF(EkranGlownyAdmin.message);
                            send.flush();
                            send.writeBoolean(true);
                            send.flush();
                            exception("try");
                            running = true;
                            EkranGlownyAdmin.message = "";
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "Register" -> {
                        passOperation = "Register";
                        int list_size = Integer.parseInt(receive.readUTF());
                        for (int i = 0; i < list_size; i++) {
                            if (i == 0) BazaDanych.name = receive.readUTF();
                            else if (i == 1) BazaDanych.surname = receive.readUTF();
                            else if (i == 2) BazaDanych.nick = receive.readUTF();
                            else if (i == 3) BazaDanych.email = receive.readUTF();
                            else if (i == 4) BazaDanych.phone_number = receive.readUTF();
                            else if (i == 5) BazaDanych.pass = receive.readUTF();
                            else if (i == 6) BazaDanych.coverage_key = receive.readUTF();
                        }
                        try {
                            BazaDanych.databaseConnect();
                            send.writeUTF(EkranUtworzKonto.blad);
                            send.flush();
                            exception("try");
                            EkranUtworzKonto.blad = "";
                            running = true;
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "Management" -> {
                        passOperation = "Management";
                        managementOperation = receive.readUTF();
                        try {
                            BazaDanych.databaseConnect();
                            send.writeUTF(EkranGlownyAdmin.message);
                            EkranGlownyAdmin.message = "";
                            exception("try");
                            running = true;
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "Recovery" -> {
                        passOperation = "Recovery";
                        int list_size = Integer.parseInt(receive.readUTF());
                        for(int i=0; i < list_size; i++){
                            if(i == 0) BazaDanych.nick = receive.readUTF();
                            else if(i == 1) BazaDanych.coverage_key = receive.readUTF();
                            else if (i == 2) BazaDanych.pass = receive.readUTF();
                        }
                        try {
                            BazaDanych.databaseConnect();
                            send.writeUTF(EkranPrzywrocHaslo.message);
                            send.flush();
                            EkranPrzywrocHaslo.message = "";
                            exception("try");
                            running = true;
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "DataPass" -> {
                        passOperation = receive.readUTF();
                        int size = Integer.parseInt(receive.readUTF());
                        for(int i=0; i<size; i++){
                            panelData.add(receive.readUTF());
                        }
                        try {
                            BazaDanych.databaseConnect();
                            send.writeUTF(EkranSerwer.message);
                            send.flush();
                            EkranSerwer.message = "";
                            exception("try");
                            panelData.clear();
                            running = true;
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "DataReceive" -> {
                        passOperation = receive.readUTF();
                        if(passOperation.equals("ReviewMyRents") || passOperation.equals("ReviewMyReturns") || passOperation.equals("ReviewRents") || passOperation.equals("ReviewReturns")) {
                            userID = receive.readUTF();
                        }
                        try {
                            BazaDanych.databaseConnect();
                            send.writeUTF(Integer.toString(panelData.size()));
                            for(String buffer:panelData){
                                send.writeUTF(buffer);
                                send.flush();
                            }
                            exception("try");
                            panelData.clear();
                            running = true;
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "GetClientID" -> {
                        try {
                            whichClient = receive.readUTF();
                            if(whichClient.equals("selectedUser")) {
                                selectedNick = receive.readUTF();
                            }
                            BazaDanych.databaseConnect();
                            send.writeUTF(userID);
                            send.flush();
                            exception("try");
                            running = true;
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "GetDVDID" -> {
                        try {
                            filmName = receive.readUTF();
                            BazaDanych.databaseConnect();
                            send.writeInt(dvdID);
                            send.flush();
                            exception("try");
                            running = true;
                        } catch (Exception ex) {
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "UpdateCount" -> {
                        passOperation = "UpdateCount";
                        tableName = receive.readUTF();
                        updatingID = receive.readInt();
                        updatingItem = receive.readUTF();
                        try {
                            BazaDanych.databaseConnect();
                            exception("try");
                            running = true;
                        }
                        catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "DeleteReservation" -> {
                        try{
                            BazaDanych.databaseConnect();
                            exception("try");
                        }catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "CheckReservations" -> {
                        try{
                            filmName = receive.readUTF();
                            BazaDanych.databaseConnect();
                            send.writeBoolean(existReservation);
                            send.flush();
                            existReservation = false;
                            exception("try");
                        }catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    case "DownloadNickname" -> {
                        try{
                            userID = receive.readUTF();
                            BazaDanych.databaseConnect();
                            send.writeUTF(Klient.nickname);
                            send.flush();
                            exception("try");
                        }catch (Exception ex){
                            setMessage(ex.getMessage());
                            exception("catch");
                        }
                    }
                    default -> {
                        setMessage("Inna opcja ");
                        throw new Exception("Wystapił nieoczekiwany błąd!");
                    }
                }
            }
            client.close();
        } catch (IOException except) {
           setMessage("Kod błędu: " + except);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    public static void main(String[] args) {
        serverOperations();
    }
}
