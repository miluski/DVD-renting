/**
 * @file DialogDodajDVD.java
 * @brief Plik zawierający kod dialog boxa pozwalajacego na dodanie nowej płyty DVD
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
package com.client;
import com.server.EkranSerwer;
import com.server.Logs;
import javax.swing.*;
import java.awt.*;
/**
 * Klasa zawierająca pola i metody obsługująca komponenty graficzne dialog boxa Dodaj DVD
 */
public final class DialogDodajDVD extends javax.swing.JDialog {
    private static final JTextField jTextField1 = new javax.swing.JTextField();
    private static final JTextField jTextField2 = new javax.swing.JTextField();
    private static final JTextField jTextField3 = new javax.swing.JTextField();
    private static final JTextField jTextField4 = new javax.swing.JTextField();
    private static final JTextField jTextField5 = new javax.swing.JTextField();
    private static final JTextField jTextField6 = new javax.swing.JTextField();
    private static final JTextField jTextField7 = new javax.swing.JTextField();
    private static final JSpinner jSpinner1 = new javax.swing.JSpinner();
    private static final JSpinner jSpinner2 = new javax.swing.JSpinner();
    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     */
    DialogDodajDVD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel6 = new javax.swing.JLabel();
        JLabel jLabel7 = new javax.swing.JLabel();
        JLabel jLabel8 = new javax.swing.JLabel();
        JLabel jLabel9 = new javax.swing.JLabel();
        JLabel  jLabel10 = new javax.swing.JLabel();
        JPanel jPanel1 = new javax.swing.JPanel();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Dodawanie DVD");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 750));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Dodawanie nowego filmu DVD do kolekcji");

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel2.setText("Nazwa filmu");

        jTextField1.setPreferredSize(new java.awt.Dimension(71, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel3.setText("Reżyseria");

        jTextField2.setPreferredSize(new java.awt.Dimension(71, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel4.setText("Gatunek");

        jTextField3.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel5.setText("Kraj produkcji");

        jTextField4.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel6.setText("Rok premiery");

        jTextField5.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel7.setText("Język filmu");

        jTextField6.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel8.setText("Długość filmu");

        jTextField7.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel9.setText("Ilość egzemplarzy");

        jSpinner1.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel10.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel10.setText("Opłata za dobę");

        jSpinner2.setMinimumSize(new java.awt.Dimension(64, 25));
        jSpinner2.setPreferredSize(new java.awt.Dimension(64, 25));

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Dodaj");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setText("Anuluj");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(83, 83, 83)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
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
        jSpinner2.setValue(10);
        pack();
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Dodaj
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){
        if(jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty()  || jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty() || jTextField6.getText().isEmpty() || jTextField7.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Nie wprowadzono jednej z istotnych danych!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else {
            if(Integer.parseInt(jSpinner1.getValue().toString()) < 0){
                JOptionPane.showMessageDialog(this,"Ilość egzemplarzy nie może być mniejsza od 0!","Błąd",JOptionPane.ERROR_MESSAGE);
                jSpinner1.setValue(0);
            }
            else if(Integer.parseInt(jSpinner2.getValue().toString()) < 10){
                JOptionPane.showMessageDialog(this,"Opłata za dobę nie może być mniejsza od 10 zł!","Błąd",JOptionPane.ERROR_MESSAGE);
                jSpinner1.setValue(10);
            }
            else {
                Klient.panelData.add(jTextField1.getText());
                Klient.panelData.add(jTextField2.getText());
                Klient.panelData.add(jTextField3.getText());
                Klient.panelData.add(jTextField4.getText());
                Klient.panelData.add(jTextField5.getText());
                Klient.panelData.add(jTextField6.getText());
                Klient.panelData.add(jTextField7.getText());
                Klient.panelData.add(jSpinner1.getValue().toString());
                Klient.panelData.add(jSpinner2.getValue().toString());
                try {
                    Klient.polacz();
                    Klient.wysylajDane("AddDVD");
                    String title;
                    if (EkranSerwer.message == null || EkranSerwer.message.equals("")) {
                        EkranSerwer.message = "Wystąpił nieoczekiwany błąd!";
                        title = "Błąd";
                    } else title = "Sukces";
                    JOptionPane.showMessageDialog(this, EkranSerwer.message, title, JOptionPane.INFORMATION_MESSAGE);
                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jTextField7.setText("");
                    jSpinner1.setValue(0);
                    jSpinner1.setValue(10);
                    new Logs("[ " + new java.util.Date() + " ] " + EkranSerwer.message, "DialogDodajDVD", "info");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "DialogDodajDVD", "error");
                }
            }
        }
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Anuluj
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt){
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jSpinner1.setValue(0);
        jSpinner2.setValue(10);
        dispose();
    }
}
