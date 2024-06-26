package com.client;

import com.server.Logs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasa zawierająca pola i metody służące do obsługi głównego okna logowania
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class EkranPrzywrocHaslo extends javax.swing.JFrame {
    /**
     * Atrybut będący listą danych uzyskanych przy odzyskiwaniu konta
     */
    protected final List<String> recoveryData = new ArrayList<>();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private final JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private final JTextField jTextField2 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym GUI służącym do wprowadzania hasła
     */
    private final JPasswordField jPasswordField1 = new javax.swing.JPasswordField();
    /**
     * Atrybut będący polem tekstowym GUI służącym do wprowadzania hasła
     */
    private final JPasswordField jPasswordField2 = new javax.swing.JPasswordField();
    /**
     * Atrybut będący wzorcem hasła
     */
    private final String passPatt = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    /**
     * Atrybut będący skompilowaną wersją wzorca hasła
     */
    private final Pattern patt = Pattern.compile(passPatt);
    /**
     * Instancja klasy Klient
     */
    private final Klient klient;
    /**
     * Atrybut będący wiadomością
     */
    public String message;

    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     *
     * @param klient Instancja klasy klient
     */
    public EkranPrzywrocHaslo(Klient klient) {
        this.klient = klient;
        setVisible(true);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * Metoda służąca do walidacji wprowadzonego hasła
     *
     * @param pass Wprowadzone hasło
     * @return Zwraca true, jeśli hasło jest zgodne ze wzorcem
     */
    public boolean walidacjaHasla(final String pass) {
        Matcher match = patt.matcher(pass);
        return match.matches();
    }

    /**
     * Metoda służąca do walidacji wprowadzonego klucza do odzyskiwania
     *
     * @param key Wprowadzony klucz
     * @return Zwraca true, jeśli klucz jest zgodny ze wzorcem
     */
    public boolean walidacjaKodu(final String key) {
        boolean match = true;
        try {
            Integer.parseInt(key);
        } catch (NumberFormatException ex) {
            match = false;
            new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "EkranPrzywrocHaslo", "error");
        }
        return match;
    }

    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {

        JPanel jPanel1 = new javax.swing.JPanel();
        JPanel jPanel2 = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel6 = new javax.swing.JLabel();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();

        setTitle("Wypożyczalnia DVD - Odzyskiwanie konta");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel1.setBackground(new java.awt.Color(89, 168, 105));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 562));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setPreferredSize(new java.awt.Dimension(440, 600));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans Light", Font.PLAIN, 24)); // NOI18N
        jLabel1.setText("Przywracanie hasła");

        jLabel2.setIcon(new javax.swing.ImageIcon("images\\RecoverIcon.png")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jLabel3.setText("Login");

        jTextField1.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jLabel4.setText("Klucz zapasowy");

        jTextField2.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jLabel5.setText("Nowe hasło");

        jPasswordField1.setPreferredSize(new java.awt.Dimension(90, 25));

        jLabel6.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jLabel6.setText("Powtórz nowe hasło");

        jPasswordField2.setPreferredSize(new java.awt.Dimension(90, 25));

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton1.setText("Przywróć hasło");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton2.setText("Anuluj");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(0, 65, Short.MAX_VALUE).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel6).addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel5).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel4).addComponent(jLabel3).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(71, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel1).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel2).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel1).addGap(26, 26, 26).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel5).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel6).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(53, 53, 53).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(54, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(280, Short.MAX_VALUE).addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(280, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE));

        pack();
    }

    /**
     * Metoda walidująca to czy któreś z obowiązkowych pól jest puste
     *
     * @return Zwraca informację o tym, czy wszystkie obowiązkowe pola tekstowe są wypełnione
     */
    private boolean validateFieldsIsNotEmpty() {
        if (jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty() || jPasswordField1.getPassword().length == 0 || jPasswordField2.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Nie wprowadzono jednej z istotnych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        } else return true;
    }

    /**
     * Metoda czyszcząca pola tekstowe zawierające wprowadzone hasła
     */
    private void cleanPasswordFields() {
        jPasswordField1.setText("");
        jPasswordField2.setText("");
    }

    /**
     * Metoda walidująca poprawność wprowadzonego hasła
     *
     * @return Zwraca informację o tym, czy wprowadzone hasło jest poprawne
     */
    private boolean validatePassword() {
        if (jPasswordField1.getPassword().length < 9 || jPasswordField2.getPassword().length < 9) {
            JOptionPane.showMessageDialog(this, "Wprowadzone hasło jest za krótkie!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!(new String(jPasswordField1.getPassword()).equals(new String(jPasswordField2.getPassword())))) {
            JOptionPane.showMessageDialog(this, "Wprowadzone hasła różnia się!", "Błąd", JOptionPane.ERROR_MESSAGE);
            cleanPasswordFields();
            return false;
        } else if (!walidacjaHasla(new String(jPasswordField1.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Wprowadzone hasło nie spełnia wymogów bezpieczeństwa!", "Błąd", JOptionPane.ERROR_MESSAGE);
            cleanPasswordFields();
            return false;
        }
        return true;
    }

    /**
     * Metoda walidująca wprowadzony klucz odzyskujący
     *
     * @return Zwraca informację o tym, czy wprowadzony klucz jest poprawny
     */
    private boolean validateCoverageKey() {
        if (jTextField2.getText().length() < 6 || !walidacjaKodu(jTextField2.getText()) || jTextField2.getText().length() > 6) {
            JOptionPane.showMessageDialog(this, "Wprowadzony klucz zapasowy jest nieprawidłowy!", "Błąd", JOptionPane.ERROR_MESSAGE);
            jTextField2.setText("");
            return false;
        }
        return true;
    }

    /**
     * Metoda czyszcząca komponenty graficzne dialog boxa
     */
    private void clearComponents() {
        recoveryData.clear();
        jTextField1.setText("");
        jTextField2.setText("");
        jPasswordField1.setText("");
        jPasswordField2.setText("");
    }

    /**
     * Metoda uzupełniająca listę danych do wysył na serwer
     */
    private void fillListData() {
        recoveryData.clear();
        recoveryData.add("Recovery");
        recoveryData.add(jTextField1.getText());
        recoveryData.add(jTextField2.getText());
        recoveryData.add(klient.hashPassword(new String(jPasswordField1.getPassword()), "e5WX^6&dNg8K"));
    }

    /**
     * Metoda wysyłająca listę danych na serwer
     */
    private void sendListData() {
        java.util.List<String> listaDanych = (List<String>) new LinkedList<>(klient.polacz(klient, recoveryData));
        klient.zakonczPolaczenie();
        message = listaDanych.get(0);
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Przywróć hasło
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (validateFieldsIsNotEmpty() && validateCoverageKey() && validatePassword()) {
            fillListData();
            sendListData();
            if (message.equals("Wystąpił błąd przy próbie zmiany hasła!")) {
                JOptionPane.showMessageDialog(null, message, "Błąd", JOptionPane.ERROR_MESSAGE);
            } else if (message.contains("Pomyślnie zmieniono hasło!\n\nTwój nowy klucz zapasowy to: ")) {
                JOptionPane.showMessageDialog(null, message, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new EkranLogowania(klient.getKlientIP());
            } else {
                message = "Wystąpił nieoczekiwany błąd!";
                JOptionPane.showMessageDialog(this, message, "Błąd", JOptionPane.WARNING_MESSAGE);
            }
            clearComponents();
        }
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Anuluj
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new EkranLogowania(klient.getKlientIP());
    }
}
