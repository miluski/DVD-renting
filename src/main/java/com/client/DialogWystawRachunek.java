/**
 * @file DialogWystawRachunek.java
 * @brief plik zawierający kod dialog boxa pozwalającego na wystawienie rachunku dla klienta od strony administratora wypożyczalni
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
public class DialogWystawRachunek extends javax.swing.JDialog {
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private static final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private static final javax.swing.JTextField jTextField2 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private static final javax.swing.JTextField jTextField3 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem liczbowym
     */
    private static final javax.swing.JSpinner jSpinner1 = new javax.swing.JSpinner();
    /**
     * Atrybut będący listą wyboru
     */
    private static final javax.swing.JComboBox<String> jComboBox1 = new javax.swing.JComboBox<>();
    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     */
    DialogWystawRachunek(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Klient.polacz();
        Klient.otrzymujDane("ReviewClients");
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Klient.panelData.clear();
                EkranSerwer.panelData.clear();
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
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jSpinner1.setValue(1);
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
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JButton jButton2 = new javax.swing.JButton();

        setTitle("Wypożyczalnia DVD - Wystaw rachunek");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Wystaw rachunek");

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 15));
        jLabel2.setText("Wybierz klienta");

        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/5);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>());
        jComboBox1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jComboBox1.setPreferredSize(new java.awt.Dimension(72, 25));
        for(int i=0; i<size; i++){
            jComboBox1.addItem(EkranSerwer.panelData.get(counter)+". "+EkranSerwer.panelData.get(counter+1)+" "+EkranSerwer.panelData.get(counter+2));
            if(size>1) counter+=5;
        }
        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 15));
        jLabel3.setText("Uzupełnij dane");

        jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel4.setText("NIP");

        jTextField1.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel5.setText("REGON");

        jTextField2.setMinimumSize(new java.awt.Dimension(64, 25));
        jTextField2.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel6.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel6.setText("PESEL");

        jTextField3.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel7.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel7.setText("Kwota rachunku");

        jSpinner1.setMinimumSize(new java.awt.Dimension(64, 25));
        jSpinner1.setPreferredSize(new java.awt.Dimension(64, 25));
        jSpinner1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jSpinner1.setValue(1);
        JComponent editor = jSpinner1.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Zatwierdź");
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
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(100, 100, 100)
                    .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(80, 80, 80))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(jLabel4)))
                        .addGap(63, 63, 63))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(20, 20, 20)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addGap(13, 13, 13)
                .addComponent(jLabel4)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
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
        Klient.panelData.clear();
        EkranSerwer.panelData.clear();
        pack();
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Zatwierdź
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if(jTextField1.getText().length()<10 || jTextField1.getText().length()>10){
            JOptionPane.showMessageDialog(this,"Wprowadzony NIP jest nieprawidłowy!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else {
            if (jTextField2.getText().length() < 9 || jTextField2.getText().length() > 9) {
                JOptionPane.showMessageDialog(this,"Wprowadzony REGON jest nieprawidłowy!","Błąd",JOptionPane.ERROR_MESSAGE);
            } else{
                if (jTextField3.getText().length() < 11 || jTextField3.getText().length() > 11) {
                    JOptionPane.showMessageDialog(this,"Wprowadzony PESEL jest nieprawidłowy!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if (((Integer)jSpinner1.getValue()) < 0) {
                        JOptionPane.showMessageDialog(this,"Wprowadzona kwota jest nieprawidłowa!","Błąd",JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        String userID = (String.valueOf(jComboBox1.getSelectedItem())).substring(0,(String.valueOf(jComboBox1.getSelectedItem()).indexOf(".")));
                        Klient.panelData.add(userID);
                        Klient.panelData.add(jTextField1.getText());
                        Klient.panelData.add(jTextField2.getText());
                        Klient.panelData.add(jTextField3.getText());
                        int cost = (Integer)jSpinner1.getValue();
                        Klient.panelData.add(Integer.toString(cost));
                        Klient.polacz();
                        Klient.wysylajDane("NewBill");
                        if(EkranSerwer.message==null) EkranSerwer.message="Wystąpił nieoczekiwany błąd!";
                        JOptionPane.showMessageDialog(this, EkranSerwer.message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        clearComponents();
                        dispose();
                    }
                }
            }
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
