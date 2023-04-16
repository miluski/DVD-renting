/**
 * @file DialogUstawLiczbeDVD.java
 * @brief Plik zawierający kod dialog boxa pozwalającego na zwiększenie liczby płyt DVD od strony administratora wypożyczalni
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
package com.client;
import com.server.EkranSerwer;

import javax.swing.*;
import java.awt.*;
/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 */
public class DialogUstawLiczbeDVD extends javax.swing.JDialog {
    /**
     * Atrybut będący listą wyboru
     */
    private static final javax.swing.JComboBox<String> jComboBox1 = new javax.swing.JComboBox<>();
    /**
     * Atrybut będący polem liczbowym
     */
    private static final javax.swing.JSpinner jSpinner1 = new javax.swing.JSpinner();
    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     */
    DialogUstawLiczbeDVD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Klient.polacz();
        Klient.otrzymujDane("ReviewDVDCollection");
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                EkranSerwer.panelData.clear();
                Klient.panelData.clear();
                jComboBox1.setModel(new DefaultComboBoxModel<>());
                dispose();
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metoda czyszcząca zawartości komponentów graficznych dialog boxa
     */
    private static void clearComponents(){
        jComboBox1.setModel(new DefaultComboBoxModel<>());
        Klient.panelData.clear();
        EkranSerwer.panelData.clear();
    }
    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JButton jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Ustaw liczbę egzemplarzy DVD");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Ustaw liczbę egzemplarzy DVD");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>());
        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/10);
        jComboBox1.setPreferredSize(new java.awt.Dimension(320, 25));
        for(int i=0; i<size; i++){
            jComboBox1.addItem(EkranSerwer.panelData.get(counter)+". "+EkranSerwer.panelData.get(counter+1));
            if(size>1) counter+=10;
        }
        jComboBox1.setPreferredSize(new java.awt.Dimension(72, 25));
        jComboBox1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel2.setText("Wybierz DVD z listy:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel3.setText("Ustaw liczbę egzemplarzy:");

        jSpinner1.setPreferredSize(new java.awt.Dimension(64, 25));
        jSpinner1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Zatwierdź zmiany");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setText("Anuluj");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, 0, 300, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
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
        EkranSerwer.panelData.clear();
        Klient.panelData.clear();
        pack();
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Zatwierdź zmiany
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if((Integer)jSpinner1.getValue()<0){
            JOptionPane.showMessageDialog(this, "Liczba egzemplarzy nie może być mniejsza od 0!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        else{
            int liczba = (Integer)jSpinner1.getValue();
            Klient.panelData.add(Integer.toString(liczba));
            Klient.panelData.add(String.valueOf(jComboBox1.getSelectedItem()).substring(0,(String.valueOf(jComboBox1.getSelectedItem()).indexOf("."))));
            Klient.polacz();
            Klient.wysylajDane("AddSameDVDs");
            if(EkranSerwer.message==null) EkranSerwer.message="Wystąpił nieoczekiwany błąd!";
            JOptionPane.showMessageDialog(this, EkranSerwer.message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
            clearComponents();
            dispose();
        }
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Anuluj
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents();
        dispose();
    }
}
