/**
 * @file DialogDodajDVD.java
 * @brief Plik zawierający kod dialog boxa pozwalajacego na edycję wybranego klienta od strony administratora wypożyczalnii
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
public class DialogEdytujKlienta extends javax.swing.JDialog {
    /**
     * Atrybut będący listą wyboru
     */
    private static final javax.swing.JComboBox<String> jComboBox1 = new javax.swing.JComboBox<>();
    /**
     * Atrybut będący polem tekstowym
     */
    private static final JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private static final JTextField jTextField2 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private static final JTextField jTextField3 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private static final JTextField jTextField4 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym
     */
    private static final JTextField jTextField5 = new javax.swing.JTextField();
    /**
     * Atrybut będący polem tekstowym do wprowadzania hasła
     */
    private static final JPasswordField jPasswordField1 = new javax.swing.JPasswordField();
    /**
     * Atrybut będący polem tekstowym do wprowadzania hasła
     */
    private static final JPasswordField jPasswordField2 = new javax.swing.JPasswordField();
    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     */
    DialogEdytujKlienta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Klient.polacz();
        Klient.otrzymujDane("ReviewClients");
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                jComboBox1.setModel(new DefaultComboBoxModel<>());
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField5.setText("");
                jPasswordField1.setText("");
                jPasswordField2.setText("");
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
        jTextField4.setText("");
        jTextField5.setText("");
        jPasswordField1.setText("");
        jPasswordField2.setText("");
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
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel9 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel10 = new javax.swing.JLabel();
        javax.swing.JButton jButton2 = new javax.swing.JButton();
        javax.swing.JButton jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Edytuj Klienta");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Edytuj Klienta");

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel2.setText("Wybierz klienta z listy");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>());
        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/5);
        jComboBox1.setPreferredSize(new java.awt.Dimension(320, 25));
        for(int i=0; i<size; i++){
            jComboBox1.addItem(EkranSerwer.panelData.get(counter)+". "+EkranSerwer.panelData.get(counter+1)+" "+EkranSerwer.panelData.get(counter+2));
            if(size>1) counter+=5;
        }
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
            if(model.isRollover()){
                jButton1.setForeground(Color.lightGray);
            }
            else{
                jButton1.setForeground(null);
            }
        });

        jLabel4.setText("Imię");

        jTextField1.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel5.setText("Nazwisko");

        jTextField2.setPreferredSize(new java.awt.Dimension(250, 25));

        jLabel6.setText("Adres e-mail");

        jTextField3.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel7.setText("Telefon");

        jTextField4.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel8.setText("Login");

        jTextField5.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel9.setText("Hasło");

        jPasswordField1.setPreferredSize(new java.awt.Dimension(90, 25));

        jLabel10.setText("Powtórz hasło");

        jPasswordField2.setMinimumSize(new java.awt.Dimension(64, 25));
        jPasswordField2.setPreferredSize(new java.awt.Dimension(90, 25));

        jButton2.setBackground(new java.awt.Color(89, 168, 105));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setText("Zatwierdź zmiany");
        jButton2.setBorder(null);
        jButton2.setPreferredSize(new java.awt.Dimension(130, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton3.setText("Anuluj");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton3.setPreferredSize(new java.awt.Dimension(130, 35));
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1))
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel10)
                                .addComponent(jLabel9)
                                .addComponent(jLabel8)
                                .addComponent(jLabel7)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(40, Short.MAX_VALUE))
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
                    .addComponent(jButton1))
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
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        pack();
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Przepisz dane
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/5);
        for(int i=0; i<size; i++){
            String userID = String.valueOf(jComboBox1.getSelectedItem()).substring(0,(String.valueOf(jComboBox1.getSelectedItem()).indexOf(".")));
            boolean isfound = userID.equals(EkranSerwer.panelData.get(counter));
            if(isfound){
                Klient.polacz();
                String nickname = Klient.downloadNickname(userID);
                jTextField1.setText(EkranSerwer.panelData.get(counter+1));
                jTextField2.setText(EkranSerwer.panelData.get(counter+2));
                jTextField3.setText(EkranSerwer.panelData.get(counter+3));
                jTextField4.setText(EkranSerwer.panelData.get(counter+4));
                jTextField5.setText(nickname);
                break;
            }
            if(size>1) counter+=5;
        }
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Edytuj
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        String userID = String.valueOf(jComboBox1.getSelectedItem()).substring(0,(String.valueOf(jComboBox1.getSelectedItem()).indexOf(".")));
        if (jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty() || jPasswordField1.getPassword().length == 0 || jPasswordField2.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Nie wprowadzono jednej z istotnych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else if (jTextField1.getText().length() < 3) {
            JOptionPane.showMessageDialog(this, "Wprowadzone imię jest za krótkie!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else if (jTextField2.getText().length() < 4) {
            JOptionPane.showMessageDialog(this, "Wprowadzone nazwisko jest za krótkie!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else if (jTextField5.getText().length() < 3) {
            JOptionPane.showMessageDialog(this, "Wprowadzony login jest za krótki!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else if (jTextField3.getText().length() < 8) {
            JOptionPane.showMessageDialog(this, "Wprowadzony email jest za krótki!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else if (jTextField4.getText().length() < 9) {
            JOptionPane.showMessageDialog(this, "Wprowadzony numer telefonu jest za krótki!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else if (jPasswordField1.getPassword().length < 9 || jPasswordField2.getPassword().length < 9) {
            JOptionPane.showMessageDialog(this, "Wprowadzone hasło jest za krótkie!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else if (!(new String(jPasswordField1.getPassword()).equals(new String(jPasswordField2.getPassword())))) {
            JOptionPane.showMessageDialog(this, "Wprowadzone hasła różnia się!", "Błąd", JOptionPane.ERROR_MESSAGE);
            jPasswordField1.setText("");
            jPasswordField2.setText("");
        } else {
            if (!EkranUtworzKonto.walidacjaHasla(new String(jPasswordField1.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Wprowadzone hasło nie spełnia wymogów bezpieczeństwa!", "Błąd", JOptionPane.ERROR_MESSAGE);
                jPasswordField1.setText("");
                jPasswordField2.setText("");
            } else if (!EkranUtworzKonto.walidacjaTelefonu(jTextField4.getText())) {
                JOptionPane.showMessageDialog(this, "Wprowadzony telefon jest nieprawidłowy!", "Błąd", JOptionPane.ERROR_MESSAGE);
                jTextField4.setText("");
            } else if (!EkranUtworzKonto.walidacjaEmail(jTextField3.getText())) {
                JOptionPane.showMessageDialog(this, "Wprowadzony adres email jest nieprawidłowy!", "Błąd", JOptionPane.ERROR_MESSAGE);
                jTextField3.setText("");
            } else {
                String imie, nazwisko, login, email, telefon, haslo;
                imie = jTextField1.getText();
                nazwisko = jTextField2.getText();
                login = jTextField5.getText();
                email = jTextField3.getText();
                telefon = jTextField4.getText();
                haslo = Klient.hashPassword(new String(jPasswordField1.getPassword()), "e5WX^6&dNg8K");
                Klient.panelData.add(imie);
                Klient.panelData.add(nazwisko);
                Klient.panelData.add(email);
                Klient.panelData.add(telefon);
                Klient.panelData.add(login);
                Klient.panelData.add(haslo);
                Klient.panelData.add(userID);
                Klient.polacz();
                Klient.wysylajDane("EditClient");
                if(EkranSerwer.message==null) EkranSerwer.message="Wystąpił nieoczekiwany błąd!";
                JOptionPane.showMessageDialog(this, EkranSerwer.message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                clearComponents();
                dispose();
            }
        }
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Anuluj
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents();
        dispose();
    }
}
