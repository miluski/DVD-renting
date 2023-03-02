import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EkranUtworzKonto extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField textField4;
    private JTextField textField5;
    private JButton utworzKontoButton;
    private JButton anulujButton;
    private static final String pass_patt = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern patt = Pattern.compile(pass_patt);
    private static final String email_patt = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern patt2 = Pattern.compile(email_patt);
    private static final String phone_number_patt = "^(?:(?:(?:\\+|00)?48)|(?:\\(\\+?48\\)))?(?:1[2-8]|2[2-69]|3[2-49]|4[1-8]|5[0-9]|6[0-35-9]|[7-8][1-9]|9[145])\\d{7}$";
    private static final Pattern patt3 = Pattern.compile(phone_number_patt);
    static List<String> dane = new ArrayList<String>();
    public static String blad;
    private static boolean walidacja_hasla(final String pass) {
        Matcher match = patt.matcher(pass);
        return match.matches();
    }
    private static boolean walidacja_email(final String email) {
        Matcher match = patt2.matcher(email);
        return match.matches();
    }
    private static boolean walidacja_telefonu(final String phone_number) {
        Matcher match = patt3.matcher(phone_number);
        return match.matches();
    }
    EkranUtworzKonto() {
        JFrame utworzKonto = new JFrame();
        utworzKonto.add(panel1);
        utworzKonto.setTitle("Wypożyczalnia DVD - Utworz konto");
        utworzKonto.setSize(450,500);
        utworzKonto.setLocationRelativeTo(null);
        utworzKonto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        utworzKonto.setVisible(true);

        anulujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utworzKonto.dispose();
                new EkranLogowania();
            }
        });
        
        utworzKontoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Po kliknieciu orzycisku Utworz konto //
                if(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty() || textField4.getText().isEmpty() || textField5.getText().isEmpty() || passwordField1.getPassword().length == 0 || passwordField2.getPassword().length == 0){
                    JOptionPane.showMessageDialog(utworzKonto,"Nie wprowadzono jednej z istotnych danych!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else if(textField1.getText().length() < 3) {
                    JOptionPane.showMessageDialog(utworzKonto,"Wprowadzone imię jest za krótkie!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else if(textField2.getText().length() < 4){
                    JOptionPane.showMessageDialog(utworzKonto,"Wprowadzone nazwisko jest za krótkie!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else if(textField3.getText().length() < 3){
                    JOptionPane.showMessageDialog(utworzKonto,"Wprowadzony login jest za krótki!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else if(textField4.getText().length() < 8){
                    JOptionPane.showMessageDialog(utworzKonto,"Wprowadzony email jest za krótki!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else if(textField5.getText().length() < 9){
                    JOptionPane.showMessageDialog(utworzKonto,"Wprowadzony numer telefonu jest za krótki!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else if(passwordField1.getPassword().length < 9 || passwordField2.getPassword().length < 9){
                    JOptionPane.showMessageDialog(utworzKonto,"Wprowadzone hasło jest za krótkie!","Błąd",JOptionPane.ERROR_MESSAGE);
                }
                else if(!(new String(passwordField1.getPassword()).equals(new String(passwordField2.getPassword())))){
                    JOptionPane.showMessageDialog(utworzKonto,"Wprowadzone hasła różnia się!","Błąd",JOptionPane.ERROR_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                }
                else {
                    if(!walidacja_hasla(new String(passwordField1.getPassword()))) {
                        JOptionPane.showMessageDialog(utworzKonto,"Wprowadzone hasło nie spełnia wymogów bezpieczeństwa!","Błąd",JOptionPane.ERROR_MESSAGE);
                        passwordField1.setText("");
                        passwordField2.setText("");
                    }
                    else if(!walidacja_telefonu(textField5.getText())) {
                        JOptionPane.showMessageDialog(utworzKonto,"Wprowadzony telefon jest nieprawidłowy!","Błąd",JOptionPane.ERROR_MESSAGE);
                        textField5.setText("");
                    }
                    else if(!walidacja_email(textField4.getText())) {
                        JOptionPane.showMessageDialog(utworzKonto,"Wprowadzony adres email jest nieprawidłowy!","Błąd",JOptionPane.ERROR_MESSAGE);
                        textField4.setText("");
                    }
                    else {
                        String imie, nazwisko, login, email, telefon, haslo;
                        imie = textField1.getText();
                        nazwisko = textField2.getText();
                        login = textField3.getText();
                        email = textField4.getText();
                        telefon = textField5.getText();
                        haslo = new String(passwordField1.getPassword());
                        dane.add(imie);
                        dane.add(nazwisko);
                        dane.add(login);
                        dane.add(email);
                        dane.add(telefon);
                        dane.add(haslo);
                        serwer.serving = 2;
                        serwer.receiving = 2;
                        try {
                            Socket sock = new Socket("192.168.137.1", 1522);
                            klient.zarejestruj(sock);
                            dane.clear();
                            if (blad.equals("Podany uzytkownik istnieje juz w bazie danych!")) {
                                JOptionPane.showMessageDialog(utworzKonto, blad, "Błąd", JOptionPane.ERROR_MESSAGE);
                                serwer.receiving = 2;
                                serwer.serving = 2;
                            } else {
                                serwer.serving = 1;
                                serwer.receiving = 1;
                                JOptionPane.showMessageDialog(utworzKonto, "Pomyslnie utworzono konto!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                                utworzKonto.dispose();
                                new EkranLogowania();
                            }
                            sock.close();
                        } catch (IOException except) {
                            System.out.println("Kod bledu: " + except);
                        }
                    }
                }
            }
        });
    }
}
