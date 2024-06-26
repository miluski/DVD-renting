package com.client;

import com.server.Logs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa zawierająca pola i metody służące do obsługi głównego okna logowania
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class EkranLogowania extends javax.swing.JFrame {
    /**
     * Atrybut będący listą ciągów znaków przechowującym dane
     */
    protected final List<String> dane = new ArrayList<>();
    /**
     * Atrybut będący polem tekstowym GUI służącym do wprowadzania hasła
     */
    private final JPasswordField jPasswordField1 = new javax.swing.JPasswordField();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private final JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Instancja klasy Klient
     */
    private final Klient klient;
    /**
     * Atrybut będący wiadomością
     */
    public String wiadomosc;
    /**
     * IP używane do połączenia z serwerem
     */
    public String IP;
    /**
     * Atrybut będący rangą użytkownika
     */
    protected String ranga;

    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     *
     * @param IP IP ustawiane do podłączenia z serwerem
     */
    public EkranLogowania(String IP) {
        this.IP = IP;
        this.klient = new Klient(this.IP);
        setVisible(true);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * Metoda pozwalająca na uruchomienie ekranu logowania
     *
     * @param args Argumenty przyjmowane podczas uruchamiania aplikacji
     */
    public static void main(String[] args) {
        new EkranLogowania("localhost");
    }

    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JPanel jPanel1 = new javax.swing.JPanel();
            JPanel jPanel3 = new javax.swing.JPanel();
            JLabel jLabel2 = new javax.swing.JLabel();
            JLabel jLabel3 = new javax.swing.JLabel();
            JLabel jLabel4 = new javax.swing.JLabel();
            JButton jButton1 = new javax.swing.JButton();
            JButton jButton2 = new javax.swing.JButton();
            JButton jButton3 = new javax.swing.JButton();
            JButton jButton4 = new javax.swing.JButton();
            JLabel jLabel1 = new javax.swing.JLabel();

            setTitle("Wypożyczalnia DVD - Zaloguj się");
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            jPanel1.setBackground(new java.awt.Color(89, 168, 105));

            jPanel3.setBackground(new java.awt.Color(255, 255, 255));

            jLabel2.setFont(new java.awt.Font("DejaVu Sans", Font.PLAIN, 24));
            jLabel2.setText("Zaloguj się");

            jLabel3.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 14));
            jLabel3.setText("Login");

            jTextField1.setPreferredSize(new java.awt.Dimension(64, 25));

            jLabel4.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 14));
            jLabel4.setText("Hasło");

            jPasswordField1.setPreferredSize(new java.awt.Dimension(90, 25));

            jButton1.setBackground(new java.awt.Color(89, 168, 105));
            jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
            jButton1.setText("Zaloguj się");
            jButton1.setBorder(null);
            jButton1.setPreferredSize(new java.awt.Dimension(130, 35));
            jButton1.addActionListener(this::jButton1ActionPerformed);

            jButton2.setBackground(new java.awt.Color(255, 255, 255));
            jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
            jButton2.setText("Załóż konto");
            jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton2.setPreferredSize(new java.awt.Dimension(130, 35));
            jButton2.addActionListener(this::jButton2ActionPerformed);

            jButton3.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton3.setText("Nie pamiętasz hasła?");
            jButton3.setBackground(null);
            jButton3.setContentAreaFilled(false);
            jButton3.setBorderPainted(false);
            jButton3.setFocusPainted(false);
            jButton3.setOpaque(true);
            jButton3.getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    jButton3.setForeground(Color.lightGray);
                } else {
                    jButton3.setForeground(null);
                }
            });
            jButton3.addActionListener(this::jButton3ActionPerformed);

            jButton4.setBackground(new java.awt.Color(89, 168, 105));
            jButton4.setIcon(new javax.swing.ImageIcon("images\\gear.png"));
            jButton4.setBorder(null);
            jButton4.setPreferredSize(new java.awt.Dimension(25, 25));
            jButton4.addActionListener(this::jButton4ActionPerformed);

            jLabel1.setIcon(new javax.swing.ImageIcon("images\\LoginBackground2.jpg"));
            jLabel1.setPreferredSize(new java.awt.Dimension(440, 550));

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(jPanel3Layout.createSequentialGroup().addGap(0, 47, Short.MAX_VALUE).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGap(47, 47, Short.MAX_VALUE).addComponent(jButton3).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel3).addComponent(jLabel4).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(53, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel2).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
            jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGap(120, 120, 120).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(25, 25, 25).addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton3).addGap(42, 42, 42).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(28, 28, 28).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(76, 76, 76)));
            jLabel1.setPreferredSize(new java.awt.Dimension(440, 550));

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(81, Short.MAX_VALUE).addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(81, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
            jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(76, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE).addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap(76, Short.MAX_VALUE)));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE));
            layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
            pack();
        } catch (Exception ex) {
            catchService(ex);
        }
    }

    /**
     * Metoda walidująca to czy któreś z obowiązkowych pól jest puste
     *
     * @return Zwraca informację o tym, czy wszystkie obowiązkowe pola tekstowe są wypełnione
     */
    private boolean validateFieldsIsNotEmpty() {
        if (jTextField1.getText().isEmpty() || jPasswordField1.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Nie wprowadzono jednej z istotnych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Metoda wysyłająca listę danych na serwer
     */
    private void sendDataList() {
        java.util.List<String> listaDanych = (java.util.List<String>) new LinkedList<>(klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
        wiadomosc = listaDanych.get(0);
        ranga = listaDanych.get(1);
        if (wiadomosc.equals("")) {
            wiadomosc = "Wystąpił nieoczekiwany błąd!";
        }
    }

    /**
     * Metoda uzupełniająca listę danych do wysył na serwer
     */
    private void fillListData() {
        dane.clear();
        dane.add("Login");
        dane.add(jTextField1.getText());
        dane.add(klient.hashPassword(new String(jPasswordField1.getPassword()), "e5WX^6&dNg8K"));
    }

    /**
     * Metoda czyszcząca zawartości komponentów graficznych dialog boxa
     */
    private void clearComponents() {
        jTextField1.setText("");
        jPasswordField1.setText("");
        wiadomosc = "";
        ranga = "";
    }

    /**
     * Metoda obsługująca zdarzenie poprawnego zalogowania się
     */
    private void successfullLogin() {
        klient.nickname = jTextField1.getText();
        if (ranga.equals("user")) {
            dispose();
            new EkranGlownyUzytkownik(klient);
        } else if (ranga.equals("admin")) {
            dispose();
            new EkranGlownyAdmin(klient);
        } else {
            JOptionPane.showMessageDialog(this, "Nie udana próba logowania!", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Zaloguj się
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (validateFieldsIsNotEmpty()) {
            try {
                fillListData();
                sendDataList();
                JOptionPane.showMessageDialog(this, wiadomosc, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                if (wiadomosc.equals("Pomyślnie zalogowano!")) {
                    successfullLogin();
                    clearComponents();
                }
            } catch (Exception ex) {
                catchService(ex);
            }
        }
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Utwórz konto
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new EkranUtworzKonto("guest", klient);
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Nie pamiętasz hasła?
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new EkranPrzywrocHaslo(klient);
    }

    /**
     * Metoda obsługująca kliknięcie przycisku zębatki
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogUstawieniaSerwera(this, true, klient);
    }

    /**
     * Metoda obsługująca powstałe wyjątki
     *
     * @param ex Otrzymany wyjątek
     */
    private void catchService(Exception ex) {
        if (ex.getMessage() == null) {
            JOptionPane.showMessageDialog(null, "Unexpected event", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        } else JOptionPane.showMessageDialog(null, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
        new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "EkranLogowania", "error");
    }
}
