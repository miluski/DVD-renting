package com.server;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Klasa zawierająca pola i metody obsługujące konstrukcję GUI i techniczną serwera
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class EkranSerwer extends javax.swing.JFrame {
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
     * Atrybut określający czy serwer jest uruchomiony
     */
    public boolean running = true;
    /**
     * Atrybut określający czy jest połączenie z bazą danych
     */
    public boolean noDatabaseConnection = false;
    /**
     * Atrybut będący adresem IP do podłączenia się z bazą danych
     */
    public String IP = "localhost";
    /**
     * Atrybut będący listą wszystkich wątków serwera
     */
    public List<Thread> clients = new ArrayList<>();
    /**
     * Atrybut będący gniazdem, z którego podłącza się klient
     */
    protected Socket client;
    /**
     * Atrybut będący gniazdem serwera
     */
    protected ServerSocket socket;

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
                    if (!noDatabaseConnection) {
                        new BazaDanych(new EkranSerwer(), "LogoutAll", IP);
                        for (Thread thread : clients) {
                            new Logs("[ " + new java.util.Date() + " ] " + "Thread called " + thread.getName() + " was deleted ", "EkranSerwer", "info");
                            thread.interrupt();
                        }
                        exception("try");
                    }
                    new Logs("[ " + new java.util.Date() + " ] " + "Server shutdown", "EkranSerwer", "info");
                    dispose();
                } catch (Exception ex) {
                    setMessage(ex.getMessage());
                    new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "EkranSerwer", "fatal");
                    dispose();
                }
            }
        });
    }

    /**
     * Metoda pozwalająca na uruchomienie serwera
     *
     * @param args Argumenty przyjmowane podczas uruchamiania aplikacji
     */
    public static void main(String[] args) {
        new Logs("[ " + new java.util.Date() + " ] " + "Server running", "EkranSerwer", "info");
        EkranSerwer ekranSerwer = new EkranSerwer();
        ekranSerwer.setVisible(true);
        ekranSerwer.serverOperations(ekranSerwer);
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
            jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(96, 96, 96).addComponent(jLabel1)).addGroup(jPanel1Layout.createSequentialGroup().addGap(27, 27, 27).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(11, 11, 11).addComponent(jLabel2).addGap(18, 18, 18).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup().addGap(115, 115, 115).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGroup(jPanel1Layout.createSequentialGroup().addGap(157, 157, 157).addComponent(jLabel3))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
            jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(25, 25, 25).addComponent(jLabel1).addGap(30, 30, 30).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel2)).addGap(18, 18, 18).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(22, 22, 22)));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
            layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
            pack();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "EkranSerwer", "error");
        }
    }

    /**
     * Metoda, której celem jest dodawanie nowych wiadomości na pole tekstowe serwera
     *
     * @param message Dodawana wiadomość
     */
    public void setMessage(String message) {
        jTextArea1.setText(jTextArea1.getText() + "\n" + message);
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
     * Metoda obsługująca sytuację wciśnięcia przycisku Ustaw IP
     *
     * @param evt Bufor pobierający event utworzony podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        int reply = dialogTakNie("To działanie ponownie uruchomi serwer!\nCzy kontynuować?");
        if (reply == JOptionPane.YES_OPTION) {
            if (walidacjaIP(jTextField1.getText())) {
                setIP(jTextField1.getText());
                JOptionPane.showMessageDialog(this, "Pomyślnie ustawiono nowe IP do połączenia z serwerem!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
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
     *
     * @param ekranSerwer Instancja klasy EkranSerwer
     */
    public void serverOperations(EkranSerwer ekranSerwer) {
        try {
            ekranSerwer.socket = new ServerSocket(1522);
            ekranSerwer.socket.setReuseAddress(true);
            while (running) {
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
     * Metoda, której celem jest zwalidowanie adresu IP
     *
     * @param IP IP do zwalidowania
     * @return Zwraca true, gdy dostarczone IP jest prawidłowe
     */
    public boolean walidacjaIP(@NotNull final String IP) {
        return Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$").matcher(IP).matches();
    }

    /**
     * Metoda odpowiadająca za zakończenie połączenia klienta z serwerem
     *
     * @param option Parametr określający czy zamykanie połączenia następuje przez try/catch, czy normalnie
     */
    protected void exception(String option) {
        try {
            if (client != null) client.close();
            if (option.equals("catch")) {
                noDatabaseConnection = true;
                new Logs("[ " + new java.util.Date() + " ] " + "Database no connection", "EkranSerwer", "fatal");
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "EkranSerwer", "error");
        }
    }

    /**
     * Metoda, której celem jest ustawienie adresu IP na odpowiednie pole w klasie
     *
     * @param IP Parametr będący adresem IP
     */
    public void setIP(String IP) {
        this.IP = IP;
    }
}
