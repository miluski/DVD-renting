package com.client;

import com.server.Logs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa zawierająca pola i metody obsługująca komponenty graficzne dialog boxa Edytuj DVD
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogEdytujDVD extends javax.swing.JDialog {
    /**
     * Atrybut będący listą ciągów znaków przechowującym dane
     */
    protected final List<String> dane = new ArrayList<>();
    /**
     * Atrybut będący listą wyboru
     */
    private final JComboBox<Object> jComboBox1 = new javax.swing.JComboBox<>();
    /**
     * Atrybut będący polem tekstowym
     */
    private final JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private final JTextField jTextField2 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private final JTextField jTextField3 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private final JTextField jTextField4 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private final JTextField jTextField5 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private final JTextField jTextField6 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private final JTextField jTextField7 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem liczbowym
     */
    private final JSpinner jSpinner1 = new javax.swing.JSpinner();
    /**
     * Atrybut będący polem liczbowym
     */
    private final JSpinner jSpinner2 = new javax.swing.JSpinner();
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
     * @param modal  Określa czy okno jest modalne czy nie
     * @param parent Okno macierzyste
     */
    public DialogEdytujDVD(Frame parent, boolean modal, Klient klient) {
        super(parent, modal);
        this.klient = klient;
        getDataList();
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                clearComponents(true);
                dispose();
            }

        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Metoda, której zadaniem jest wysłanie żądania do serwera celem otrzymania listy płyt DVD
     */
    private void getDataList() {
        dane.clear();
        dane.add("ReviewDVDCollection");
        panelData.addAll((Collection<? extends String>) klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
    }

    /**
     * Metoda czyszcząca zawartości komponentów graficznych dialog boxa
     */
    private void clearComponents(boolean canClearComboBox) {
        if (canClearComboBox) jComboBox1.setModel(new DefaultComboBoxModel<>());
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
    }

    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {
        JPanel jPanel1 = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JButton jButton1 = new javax.swing.JButton();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel6 = new javax.swing.JLabel();
        JLabel jLabel7 = new javax.swing.JLabel();
        JLabel jLabel8 = new javax.swing.JLabel();
        JLabel jLabel9 = new javax.swing.JLabel();
        JLabel jLabel10 = new javax.swing.JLabel();
        JLabel jLabel11 = new javax.swing.JLabel();
        JLabel jLabel12 = new javax.swing.JLabel();
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setTitle("Wypożyczalnia DVD - Edytuj DVD");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 800));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel1.setText("Edytuj DVD");

        jComboBox1.setPreferredSize(new java.awt.Dimension(250, 25));
        jComboBox1.setEditable(false);
        jComboBox1.setBackground(Color.WHITE);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>());
        int counter = 0;
        int size = ((panelData.size()) / 10);
        for (int i = 0; i < size; i++) {
            jComboBox1.addItem(panelData.get(counter) + ". " + panelData.get(counter + 1));
            if (size > 1) counter += 10;
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel2.setText("Wybierz DVD z listy");

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel3.setText("Edytuj poszczególne dane");

        jButton1.setBackground(null);
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Przepisz dane");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 25));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jButton1.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if (model.isRollover()) {
                jButton1.setForeground(Color.lightGray);
            } else {
                jButton1.setForeground(null);
            }
        });

        jTextField1.setMinimumSize(new java.awt.Dimension(64, 25));
        jTextField1.setPreferredSize(new java.awt.Dimension(71, 25));

        jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel4.setText("Nazwa filmu");

        jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel5.setText("Reżyseria");

        jTextField2.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel6.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel6.setText("Gatunek");

        jTextField3.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel7.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel7.setText("Kraj produkcji");

        jTextField4.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel8.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel8.setText("Rok premiery");

        jTextField5.setPreferredSize(new java.awt.Dimension(71, 25));

        jLabel9.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel9.setText("Język filmu");

        jTextField6.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel10.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel10.setText("Długość filmu");

        jTextField7.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel11.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel11.setText("Ilość egzemplarzy");

        jSpinner1.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel12.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel12.setText("Opłata za dobę");

        jSpinner2.setPreferredSize(new java.awt.Dimension(64, 25));

        jButton2.setBackground(new java.awt.Color(89, 168, 105));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton2.setText("Zatwierdź zmiany");
        jButton2.setBorder(null);
        jButton2.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton3.setText("Anuluj");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton3.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(31, 199, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel5)
                                                .addGap(252, 252, 252)))
                                .addGap(40, 40, 40))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jLabel11))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel12)
                                                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(155, 155, 155))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Przepisz dane
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents(false);
        int counter = 0;
        int size = ((panelData.size()) / 10);
        for (int i = 0; i < size; i++) {
            String dvdID = String.valueOf(jComboBox1.getSelectedItem()).substring(0, (String.valueOf(jComboBox1.getSelectedItem()).indexOf(".")));
            boolean isfound = dvdID.equals(panelData.get(counter));
            if (isfound) {
                jTextField1.setText(panelData.get(counter + 1));
                jTextField2.setText(panelData.get(counter + 2));
                jTextField3.setText(panelData.get(counter + 3));
                jTextField4.setText(panelData.get(counter + 4));
                jTextField5.setText(panelData.get(counter + 5));
                jTextField6.setText(panelData.get(counter + 6));
                jTextField7.setText(panelData.get(counter + 7));
                jSpinner1.setValue(Integer.parseInt(panelData.get(counter + 8)));
                jSpinner2.setValue(Integer.parseInt(panelData.get(counter + 9)));
                break;
            }
            if (size > 1) counter += 10;
        }
    }

    /**
     * Metoda uzupełniająca listę danych do wysył na serwer
     */
    private void fillListData() {
        dane.clear();
        dane.add("EditDVD");
        dane.add(jTextField1.getText());
        dane.add(jTextField2.getText());
        dane.add(jTextField3.getText());
        dane.add(jTextField4.getText());
        dane.add(jTextField5.getText());
        dane.add(jTextField6.getText());
        dane.add(jTextField7.getText());
        dane.add(jSpinner1.getValue().toString());
        dane.add(jSpinner2.getValue().toString());
        dane.add(String.valueOf(jComboBox1.getSelectedItem()).substring(0, (String.valueOf(jComboBox1.getSelectedItem()).indexOf("."))));
    }

    /**
     * Metoda wysyłająca listę danych na serwer
     */
    private void sendListData() {
        java.util.List<String> listaDanych = (List<String>) new LinkedList<>(klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
        String title;
        String message = listaDanych.get(0);
        if (message == null || message.equals("")) {
            message = "Wystąpił nieoczekiwany błąd!";
            title = "Błąd";
        } else title = "Sukces";
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
        new Logs("[ " + new java.util.Date() + " ] " + message, "DialogDodajDVD", "info");
    }

    /**
     * Metoda walidująca poprawność wprowadzonych danych
     *
     * @return Zwraca true jeśli dane są poprawne
     */
    private boolean validateData() {
        if (jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty() || jTextField6.getText().isEmpty() || jTextField7.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nie wprowadzono jednej z istotnych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else {
            if (Integer.parseInt(jSpinner1.getValue().toString()) < 0) {
                JOptionPane.showMessageDialog(this, "Ilość egzemplarzy nie może być mniejsza od 0!", "Błąd", JOptionPane.ERROR_MESSAGE);
                jSpinner1.setValue(0);
            } else if (Integer.parseInt(jSpinner2.getValue().toString()) < 10) {
                JOptionPane.showMessageDialog(this, "Opłata za dobę nie może być mniejsza od 10 zł!", "Błąd", JOptionPane.ERROR_MESSAGE);
                jSpinner1.setValue(10);
            } else return true;
        }
        return false;
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Edytuj
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (validateData()) {
            try {
                fillListData();
                sendListData();
                clearComponents(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "DialogEdytujDVD", "error");
            }
        }
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Anuluj
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents(true);
        dispose();
    }
}
