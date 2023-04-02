/** @file EkranUtworzKonto.java
 * @brief plik zawierający kod głównego okna umożliwiającego utwozenie konta
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EkranUtworzKonto extends javax.swing.JFrame {
    private static final JTextField jTextField1 = new javax.swing.JTextField();
    private static final JTextField jTextField2 = new javax.swing.JTextField();
    private static final JTextField jTextField3 = new javax.swing.JTextField();
    private static final JTextField jTextField4 = new javax.swing.JTextField();
    private static final JTextField jTextField5 = new javax.swing.JTextField();
    private static final JPasswordField jPasswordField1 = new javax.swing.JPasswordField();
    private static final JPasswordField jPasswordField2 = new javax.swing.JPasswordField();
    private static final String pass_patt = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern patt = Pattern.compile(pass_patt);
    private static final String email_patt = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern patt2 = Pattern.compile(email_patt);
    private static final String phone_number_patt = "^(?:(?:(?:\\+|00)?48)|(?:\\(\\+?48\\)))?(?:1[2-8]|2[2-69]|3[2-49]|4[1-8]|5[0-9]|6[0-35-9]|[7-8][1-9]|9[145])\\d{7}$";
    private static final Pattern patt3 = Pattern.compile(phone_number_patt);
    private static String user_information, rank;
    protected static final List<String> dane = new ArrayList<>();
    protected static String blad;
    protected static int generujKlucz(){
        Random random = new Random();
        return (random.nextInt(999999-100000)+100000);
    }
    protected static boolean walidacjaHasla(final String pass) {
        Matcher match = patt.matcher(pass);
        return match.matches();
    }
    protected static boolean walidacjaEmail(final String email) {
        Matcher match = patt2.matcher(email);
        return match.matches();
    }
    protected static boolean walidacjaTelefonu(final String phone_number) {
        Matcher match = patt3.matcher(phone_number);
        return match.matches();
    }
    private static void wyczyscPola(){
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jPasswordField1.setText("");
        jPasswordField2.setText("");
    }
    EkranUtworzKonto(String rank) {
        setVisible(true);
        initComponents();
        this.setLocationRelativeTo(null);
        user_information = rank;
        EkranUtworzKonto.rank = rank;
    }

    private void initComponents() {
        JPanel jPanel1 = new javax.swing.JPanel();
        JPanel jPanel2 = new javax.swing.JPanel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel6 = new javax.swing.JLabel();
        JLabel jLabel7 = new javax.swing.JLabel();
        JLabel jLabel8 = new javax.swing.JLabel();
        JLabel jLabel9 = new javax.swing.JLabel();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        JLabel jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Utwórz Konto");

        jPanel1.setBackground(new java.awt.Color(89, 168, 105));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, Color.BLACK, new java.awt.Color(51, 51, 51)));

        jLabel2.setFont(new java.awt.Font("Noto Sans", Font.PLAIN, 20));
        jLabel2.setText("Utwórz Konto");

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel3.setText("Imię");

        jTextField1.setMinimumSize(new java.awt.Dimension(64, 25));
        jTextField1.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel4.setText("Nazwisko");

        jTextField2.setMinimumSize(new java.awt.Dimension(64, 25));
        jTextField2.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel5.setText("Adres e-mail");

        jTextField3.setMinimumSize(new java.awt.Dimension(64, 25));
        jTextField3.setPreferredSize(new java.awt.Dimension(71, 25));

        jLabel6.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel6.setText("Telefon");

        jTextField4.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel7.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel7.setText("Login");

        jTextField5.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel8.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel8.setText("Hasło");

        jLabel9.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel9.setText("Powtórz hasło");

        jPasswordField1.setPreferredSize(new java.awt.Dimension(64, 25));

        jPasswordField2.setPreferredSize(new java.awt.Dimension(64, 25));

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Załóż konto");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setText("Anuluj");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(155, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addContainerGap(154, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel8)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel5)
                                                .addComponent(jLabel4)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel2)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon("images\\DVDBackground.jpg"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, true)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(105, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(50, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(50, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if(jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty() || jPasswordField1.getPassword().length == 0 || jPasswordField2.getPassword().length == 0){
            JOptionPane.showMessageDialog(this,"Nie wprowadzono jednej z istotnych danych!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else if(jTextField1.getText().length() < 3) {
            JOptionPane.showMessageDialog(this,"Wprowadzone imię jest za krótkie!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else if(jTextField2.getText().length() < 4){
            JOptionPane.showMessageDialog(this,"Wprowadzone nazwisko jest za krótkie!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else if(jTextField5.getText().length() < 3){
            JOptionPane.showMessageDialog(this,"Wprowadzony login jest za krótki!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else if(jTextField3.getText().length() < 8){
            JOptionPane.showMessageDialog(this,"Wprowadzony email jest za krótki!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else if(jTextField4.getText().length() < 9){
            JOptionPane.showMessageDialog(this,"Wprowadzony numer telefonu jest za krótki!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else if(jPasswordField1.getPassword().length < 9 || jPasswordField2.getPassword().length < 9){
            JOptionPane.showMessageDialog(this,"Wprowadzone hasło jest za krótkie!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else if(!(new String(jPasswordField1.getPassword()).equals(new String(jPasswordField2.getPassword())))){
            JOptionPane.showMessageDialog(this,"Wprowadzone hasła różnia się!","Błąd",JOptionPane.ERROR_MESSAGE);
            jPasswordField1.setText("");
            jPasswordField2.setText("");
        }
        else {
            if(!walidacjaHasla(new String(jPasswordField1.getPassword()))) {
                JOptionPane.showMessageDialog(this,"Wprowadzone hasło nie spełnia wymogów bezpieczeństwa!","Błąd",JOptionPane.ERROR_MESSAGE);
                jPasswordField1.setText("");
                jPasswordField2.setText("");
            }
            else if(!walidacjaTelefonu(jTextField4.getText())) {
                JOptionPane.showMessageDialog(this,"Wprowadzony telefon jest nieprawidłowy!","Błąd",JOptionPane.ERROR_MESSAGE);
                jTextField4.setText("");
            }
            else if(!walidacjaEmail(jTextField3.getText())) {
                JOptionPane.showMessageDialog(this,"Wprowadzony adres email jest nieprawidłowy!","Błąd",JOptionPane.ERROR_MESSAGE);
                jTextField3.setText("");
            }
            else {
                String imie, nazwisko, login, email, telefon, haslo;
                imie = jTextField1.getText();
                nazwisko = jTextField2.getText();
                login = jTextField5.getText();
                email = jTextField3.getText();
                telefon = jTextField4.getText();
                haslo = Klient.hashPassword(new String(jPasswordField1.getPassword()),"e5WX^6&dNg8K");
                int klucz = generujKlucz();
                dane.add(imie);
                dane.add(nazwisko);
                dane.add(login);
                dane.add(email);
                dane.add(telefon);
                dane.add(haslo);
                dane.add(Integer.toString(klucz));
                try {
                    Klient.polacz();
                    Klient.zarejestruj();
                    Klient.zakonczPolaczenie();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                }
                dane.clear();
                if(blad==null){
                    blad="Wystąpił nieoczekiwany błąd!";
                    JOptionPane.showMessageDialog(this, blad, "Błąd", JOptionPane.ERROR_MESSAGE);
                }
                else if (blad.equals("Podany uzytkownik istnieje juz w bazie danych!")) {
                    JOptionPane.showMessageDialog(this, blad, "Błąd", JOptionPane.ERROR_MESSAGE);
                } else if(blad.equals("Sukces")){
                    if(rank.equals("guest")) {
                        JOptionPane.showMessageDialog(this, "Pomyślnie zarejestrowano! \n\n" + "Twój klucz zapasowy to: " + klucz + "\nZapisz go w bezpiecznym miejscu!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                        wyczyscPola();
                        dispose();
                        new EkranLogowania();
                    }
                    else if(rank.equals("admin")){
                        JOptionPane.showMessageDialog(this, "Pomyślnie dodano nowego klienta! \n\n" + "Klucz zapasowy użytkownika to: " + klucz + "\nPrzekaż mu go przez adres email!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                        wyczyscPola();
                        dispose();
                    }
                }
                else {
                    blad="Wystąpił nieoczekiwany błąd!";
                    JOptionPane.showMessageDialog(this, blad, "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        if(user_information.equals("guest")) {
            new EkranLogowania();
        }
    }

}
