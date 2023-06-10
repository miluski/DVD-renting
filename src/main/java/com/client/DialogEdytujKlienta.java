package com.client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogEdytujKlienta extends javax.swing.JDialog {
    /**
     * Atrybut będący listą ciągów znaków przechowującym dane
     */
    protected final List<String> dane = new ArrayList<>();
    /**
     * Atrybut będący listą wyboru
     */
    private final javax.swing.JComboBox<String> jComboBox1 = new javax.swing.JComboBox<>();
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
     * Atrybut będący polem tekstowym do wprowadzania hasła
     */
    private final JPasswordField jPasswordField1 = new javax.swing.JPasswordField();
    /**
     * Atrybut będący polem tekstowym do wprowadzania hasła
     */
    private final JPasswordField jPasswordField2 = new javax.swing.JPasswordField();
    /**
     * Instancja klasy Klient
     */
    private final Klient klient;
    /**
     * Lista odebranych danych
     */
    private final java.util.List<String> panelData = new LinkedList<>();
    /**
     * Atrybut będący skompilowaną wersją wzorca hasła
     */
    private final Pattern passPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$");
    /**
     * Atrybut będący skompilowaną wersją wzorca emailu
     */
    private final Pattern emailPattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    /**
     * Atrybut będący skompilowaną wersją wzorca numeru telefonu
     */
    private final Pattern phoneNumberPattern = Pattern.compile("^(?:1[2-8]|2[2-69]|3[2-49]|4[1-8]|5[0-9]|6[0-35-9]|[7-8][1-9]|9[145])\\d{7}$");
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
     * Konstruktor odpowiadający za inicjalizację GUI
     *
     * @param klient Instancja klasy klient
     * @param modal  Określa czy okno jest modalne czy nie
     * @param parent Okno macierzyste
     */
    public DialogEdytujKlienta(Frame parent, boolean modal, Klient klient) {
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
     * Metoda czyszcząca zawartości komponentów graficznych dialog boxa
     */
    private void clearComponents(boolean canCleanComboBox) {
        if (canCleanComboBox) jComboBox1.setModel(new DefaultComboBoxModel<>());
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jPasswordField1.setText("");
        jPasswordField2.setText("");
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
        int size = ((panelData.size()) / 5);
        jComboBox1.setPreferredSize(new java.awt.Dimension(320, 25));
        for (int i = 0; i < size; i++) {
            jComboBox1.addItem(panelData.get(counter) + ". " + panelData.get(counter + 1) + " " + panelData.get(counter + 2));
            if (size > 1) counter += 5;
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
            if (model.isRollover()) {
                jButton1.setForeground(Color.lightGray);
            } else {
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
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(147, 147, 147).addComponent(jLabel1)).addGroup(jPanel1Layout.createSequentialGroup().addGap(40, 40, 40).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton1)).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGroup(jPanel1Layout.createSequentialGroup().addGap(60, 60, 60).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jLabel10).addComponent(jLabel9).addComponent(jLabel8).addComponent(jLabel7).addComponent(jLabel4).addComponent(jLabel5).addComponent(jLabel6).addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE).addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))).addContainerGap(40, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(15, 15, 15).addComponent(jLabel1).addGap(18, 18, 18).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jButton1)).addGap(18, 18, 18).addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel5).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel6).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel7).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel8).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel9).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel10).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(26, 26, 26).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(24, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE));

        pack();
    }

    /**
     * Metoda, której celem jest wysłanie żądania do serwera celem pobrania nickname użytkownika o wskazanym ID
     *
     * @param userID ID użytkownika
     * @return Zwraca pobrany nickname
     */
    private String downloadNickname(String userID){
        dane.clear();
        dane.add("DownloadNickname");
        dane.add(userID);
        List<String> dataList = (List<String>) new LinkedList<>(klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
        return dataList.get(0);
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Przepisz dane
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents(false);
        int counter = 0;
        int size = ((panelData.size()) / 5);
        for (int i = 0; i < size; i++) {
            String userID = String.valueOf(jComboBox1.getSelectedItem()).substring(0, (String.valueOf(jComboBox1.getSelectedItem()).indexOf(".")));
            String nickname = downloadNickname(userID);
            boolean isfound = userID.equals(panelData.get(counter));
            if (isfound) {
                jTextField1.setText(panelData.get(counter + 1));
                jTextField2.setText(panelData.get(counter + 2));
                jTextField3.setText(panelData.get(counter + 3));
                jTextField4.setText(panelData.get(counter + 4));
                jTextField5.setText(nickname);
                break;
            }
            if (size > 1) counter += 5;
        }
    }

    /**
     * Metoda walidująca to czy któreś z obowiązkowych pól jest puste
     *
     * @return Zwraca informację o tym, czy wszystkie obowiązkowe pola tekstowe są wypełnione
     */
    private boolean validateFieldsIsNotEmpty() {
        if (jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty() || jPasswordField1.getPassword().length == 0 || jPasswordField2.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Nie wprowadzono jednej z istotnych danych!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        } else return true;
    }

    /**
     * Metoda walidująca poprawność wprowadzonego imienia
     *
     * @return Zwraca informację o tym, czy wprowadzone imię jest poprawne
     */
    private boolean validateName() {
        if (jTextField1.getText().length() < 3) {
            JOptionPane.showMessageDialog(this, "Wprowadzone imię jest za krótkie!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Metoda walidująca poprawność wprowadzonego nazwiska
     *
     * @return Zwraca informację o tym, czy wprowadzone nazwisko jest poprawne
     */
    private boolean validateSurname() {
        if (jTextField2.getText().length() < 4) {
            JOptionPane.showMessageDialog(this, "Wprowadzone nazwisko jest za krótkie!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Metoda walidująca poprawność wprowadzonego loginu
     *
     * @return Zwraca informację o tym, czy wprowadzony login jest poprawny
     */
    private boolean validateLogin() {
        if (jTextField5.getText().length() < 3) {
            JOptionPane.showMessageDialog(this, "Wprowadzony login jest za krótki!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Metoda walidująca poprawność wprowadzonego adresu email
     *
     * @return Zwraca informację o tym, czy wprowadzone adres email jest poprawny
     */
    private boolean validateEmail() {
        if (jTextField3.getText().length() < 8) {
            JOptionPane.showMessageDialog(this, "Wprowadzony email jest za krótki!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (walidacjaEmail(jTextField3.getText())) {
            JOptionPane.showMessageDialog(this, "Wprowadzony adres email jest nieprawidłowy!", "Błąd", JOptionPane.ERROR_MESSAGE);
            jTextField3.setText("");
            return false;
        }
        return true;
    }

    /**
     * Metoda walidująca poprawność wprowadzonego numeru telefonu
     *
     * @return Zwraca informację o tym, czy wprowadzony numer telefonu jest poprawny
     */
    private boolean validatePhoneNumber() {
        if (jTextField4.getText().length() < 9) {
            JOptionPane.showMessageDialog(this, "Wprowadzony numer telefonu jest za krótki!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (walidacjaTelefonu(jTextField4.getText())) {
            JOptionPane.showMessageDialog(this, "Wprowadzony telefon jest nieprawidłowy!", "Błąd", JOptionPane.ERROR_MESSAGE);
            jTextField4.setText("");
            return false;
        }
        return true;
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
        } else if (walidacjaHasla(new String(jPasswordField1.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Wprowadzone hasło nie spełnia wymogów bezpieczeństwa!", "Błąd", JOptionPane.ERROR_MESSAGE);
            cleanPasswordFields();
            return false;
        }
        return true;
    }

    /**
     * Metoda uzupełniająca listę danych do wysył na serwer
     *
     * @param klucz Klucz zapasowy wygenerowany podczas rejestracji
     */
    private void fillDataList(int klucz) {
        dane.clear();
        dane.add("Register");
        dane.add(jTextField1.getText());
        dane.add(jTextField2.getText());
        dane.add(jTextField5.getText());
        dane.add(jTextField3.getText());
        dane.add(jTextField4.getText());
        dane.add(klient.hashPassword(new String(jPasswordField1.getPassword()), "e5WX^6&dNg8K"));
        dane.add(Integer.toString(klucz));
    }

    /**
     * Metoda służąca do walidacji wprowadzonego hasła
     *
     * @param pass Wprowadzone hasło
     * @return Zwraca true, jeśli hasło jest zgodne ze wzorcem
     */
    public boolean walidacjaHasla(final String pass) {
        return !passPattern.matcher(pass).matches();
    }

    /**
     * Metoda służąca do walidacji wprowadzonego adresu email
     *
     * @param email Wprowadzony adres email
     * @return Zwraca true, jeśli email jest zgodny ze wzorcem
     */
    public boolean walidacjaEmail(final String email) {
        return !emailPattern.matcher(email).matches();
    }

    /**
     * Metoda służąca do walidacji wprowadzonego numeru telefonu
     *
     * @param phoneNumber Wprowadzony numer telefonu
     * @return Zwraca true, jeśli numer telefonu jest zgodny ze wzorcem
     */
    public boolean walidacjaTelefonu(final String phoneNumber) {
        return !phoneNumberPattern.matcher(phoneNumber).matches();
    }

    /**
     * Metoda, której zadaniem jest uzupełnienie listy danych przed wysyłem na serwer
     */
    private void fillDataList() {
        String userID = String.valueOf(jComboBox1.getSelectedItem()).substring(0, (String.valueOf(jComboBox1.getSelectedItem()).indexOf(".")));
        dane.clear();
        dane.add("EditClient");
        dane.add(jTextField1.getText());
        dane.add(jTextField2.getText());
        dane.add(jTextField3.getText());
        dane.add(jTextField4.getText());
        dane.add(jTextField5.getText());
        dane.add(klient.hashPassword(new String(jPasswordField1.getPassword()), "e5WX^6&dNg8K"));
        dane.add(userID);
    }

    /**
     * Metoda, której zadaniem jest wysył pobranych danych na serwer
     * <p>
     * Zwraca informację o tym, czy wysył przebiegł poprawnie
     */
    private String sendDataList() {
        List<String> dataList = (List<String>) new LinkedList<>(klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
        return dataList.get(0);
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Edytuj
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (validateFieldsIsNotEmpty() && validateName() && validateSurname() && validateLogin() && validateEmail() && validatePhoneNumber() && validatePassword()) {
            fillDataList();
            String message = sendDataList();
            if (message == null) message = "Wystąpił nieoczekiwany błąd!";
            JOptionPane.showMessageDialog(this, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
            clearComponents(true);
            dispose();
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
