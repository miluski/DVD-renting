package com.client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogUsunKlienta extends javax.swing.JDialog {
    /**
     * Atrybut będący listą ciągów znaków przechowującym dane
     */
    protected final List<String> dane = new ArrayList<>();
    /**
     * Atrybut będący listą w postaci graficznej
     */
    private final javax.swing.JList<String> jList1 = new javax.swing.JList<>();
    /**
     * Instancja klasy Klient
     */
    private final Klient klient;
    /**
     * Lista odebranych danych
     */
    private final java.util.List<String> panelData = new LinkedList<>();

    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     *
     * @param klient Instancja klasy klient
     * @param modal  Określa czy okno jest modalne, czy nie
     * @param parent Okno macierzyste
     */
    public DialogUsunKlienta(Frame parent, boolean modal, Klient klient) {
        super(parent, modal);
        this.klient = klient;
        getDataList();
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                DefaultListModel<String> newListModel = new DefaultListModel<>();
                jList1.setModel(newListModel);
                dispose();
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Metoda, której zadaniem jest pobranie listy klientów z bazy danych
     */
    private void getDataList() {
        dane.clear();
        dane.add("ReviewClients");
        panelData.addAll((Collection<? extends String>) klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
    }

    /**
     * Metoda czyszcząca zawartości komponentów graficznych dialog boxa
     */
    private void clearComponents() {
        DefaultListModel<String> newListModel = new DefaultListModel<>();
        jList1.setModel(newListModel);
    }

    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JButton jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Usuń klienta");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Usuń Klienta");

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setText("Wybierz klienta z listy do usunięcia");

        int size = ((panelData.size()) / 5);
        int counter = 0;

        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for (int i = 0; i < size; i++) {
            defaultListModel.addElement(panelData.get(counter) + ". " + panelData.get(counter + 1) + " " + panelData.get(counter + 2));
            if (size > 1) counter += 5;
        }
        jList1.setModel(defaultListModel);

        jList1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1.setViewportView(jList1);

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton1.setText("Usuń");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(130, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton2.setText("Anuluj");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setPreferredSize(new java.awt.Dimension(130, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(175, 175, 175).addComponent(jLabel1)).addGroup(jPanel1Layout.createSequentialGroup().addGap(95, 95, 95).addComponent(jLabel2)).addGroup(jPanel1Layout.createSequentialGroup().addGap(75, 75, 75).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, 30).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))).addContainerGap(50, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(25, 25, 25).addComponent(jLabel1).addGap(32, 32, 32).addComponent(jLabel2).addGap(30, 30, 30).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(45, 45, 45).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(48, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pack();
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Usuń
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        dane.clear();
        dane.add("DeleteClient");
        dane.add(jList1.getSelectedValue().substring(0, (jList1.getSelectedValue()).indexOf(".")));
        List<String> dataList = (List<String>) new LinkedList<>(klient.polacz(klient, dane));
        String message = dataList.get(0);
        klient.zakonczPolaczenie();
        if (message == null) message = "Wystąpił nieoczekiwany błąd!";
        JOptionPane.showMessageDialog(this, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
        clearComponents();
        dispose();
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Anuluj
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents();
        dispose();
    }
}
