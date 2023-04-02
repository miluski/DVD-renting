/** @file EkranPrzywrocHaslo.java
 * @brief plik zawierający kod głównego okna odzyskiwania hasła
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EkranPrzywrocHaslo extends javax.swing.JFrame {

    private static final JTextField jTextField1 = new javax.swing.JTextField();
    private static final JTextField jTextField2 = new javax.swing.JTextField();
    private static final JPasswordField jPasswordField1 = new javax.swing.JPasswordField();
    private static final JPasswordField jPasswordField2 = new javax.swing.JPasswordField();
    protected static String message;
    protected static final List<String> recovery_data = new ArrayList<>();
    private static final String pass_patt = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern patt = Pattern.compile(pass_patt);
    EkranPrzywrocHaslo() {
        setVisible(true);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private static boolean walidacja_hasla(final String pass) {
        Matcher match = patt.matcher(pass);
        return match.matches();
    }

    private static boolean walidacja_kodu(final String key){
        boolean match = true;
        try{
            Integer.parseInt(key);
        }
        catch(NumberFormatException exception){
            match = false;
        }
        return match;
    }
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
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 65, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(280, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(280, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if(jPasswordField1.getPassword().length == 0 || jPasswordField2.getPassword().length == 0 || jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Nie wprowadzono jednej z istotnych danych!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else {
            if(jPasswordField1.getPassword().length < 9 || jPasswordField2.getPassword().length < 9){
                JOptionPane.showMessageDialog(this,"Wprowadzone hasło jest za krótkie!","Błąd",JOptionPane.ERROR_MESSAGE);
            }
            else if (!(new String(jPasswordField1.getPassword()).equals(new String(jPasswordField2.getPassword())))) {
                JOptionPane.showMessageDialog(this, "Wprowadzone hasła różnia się!", "Błąd", JOptionPane.ERROR_MESSAGE);
                jPasswordField1.setText("");
                jPasswordField2.setText("");
            } else {
                if(jTextField2.getText().length() < 6 || !walidacja_kodu(jTextField2.getText()) || jTextField2.getText().length() > 6){
                    JOptionPane.showMessageDialog(this, "Wprowadzony klucz zapasowy jest nieprawidłowy!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    jTextField2.setText("");
                }
                else if(!walidacja_hasla(new String(jPasswordField1.getPassword()))) {
                    JOptionPane.showMessageDialog(this,"Wprowadzone hasło nie spełnia wymogów bezpieczeństwa!","Błąd",JOptionPane.ERROR_MESSAGE);
                    jPasswordField1.setText("");
                    jPasswordField2.setText("");
                }
                else {
                    recovery_data.add(jTextField1.getText());
                    recovery_data.add(jTextField2.getText());
                    recovery_data.add(Klient.hashPassword(new String(jPasswordField1.getPassword()),"e5WX^6&dNg8K"));
                    try {
                        Klient.polacz();
                        Klient.odzyskaj();
                        Klient.zakonczPolaczenie();
                    }
                    catch(Exception ex){
                        JOptionPane.showMessageDialog(this, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if(message==null) {
                        message = "Wystąpił nieoczekiwany błąd!";
                        JOptionPane.showMessageDialog(this, message, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(message.equals("Wystąpił błąd przy próbie zmiany hasła!")){
                        JOptionPane.showMessageDialog(this,message,"Błąd",JOptionPane.ERROR_MESSAGE);
                    }
                    else if(message.contains("Pomyślnie zmieniono hasło!\n\nTwój nowy klucz zapasowy to: ")){
                        JOptionPane.showMessageDialog(this,message,"Sukces",JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new EkranLogowania();
                    }
                    else{
                        message = "Wystąpił nieoczekiwany błąd!";
                        JOptionPane.showMessageDialog(this, message, "Błąd", JOptionPane.WARNING_MESSAGE);
                    }
                    recovery_data.clear();
                    jTextField1.setText("");
                    jTextField2.setText("");
                    jPasswordField1.setText("");
                    jPasswordField2.setText("");
                }
            }
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new EkranLogowania();
    }

}
