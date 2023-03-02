import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

public class EkranLogowania {
    static String login_uzytkownika;
    static String haslo_uzytkownika;
    public static String wiadomosc, ranga;
    EkranLogowania () {
        JLabel kontrola = new JLabel();

        //Tworzenie nowego okna logowania i rejestracji użytkownika
        JFrame okno_logowania = new JFrame("Wypożyczalnia DVD - Ekran logowania");
        JLabel powitanie = new JLabel("Witamy w aplikacji Wypożyczalnia DVD.");
        powitanie.setBounds(80, 10, 300, 20);

        //Labele do wprowadzania danych loginu
        final JTextField pole_login = new JTextField();
        pole_login.setBounds(150, 60, 150, 25);
        JLabel l1 = new JLabel("Login:");
        l1.setBounds(80, 60, 50, 25);

        //Labele do wprowadzania danych hasła
        final JPasswordField pole_haslo = new JPasswordField();
        pole_haslo.setBounds(150, 100, 150, 25);
        JLabel l2 = new JLabel("Hasło:");
        l2.setBounds(80, 100, 50, 25);

        //Przyciski Zaloguj i Utworz konto
        JButton zaloguj_przycisk = new JButton("Zaloguj");
        zaloguj_przycisk.setBounds(120, 150, 150, 30);
        JButton utworz_konto_przycisk = new JButton("Utwórz konto");
        utworz_konto_przycisk.setBounds(120, 200, 150, 30);

        //Pobieranie danych z pol tekstowych po naciśnięciu przycisku zaloguj
        zaloguj_przycisk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login_uzytkownika = pole_login.getText();
                haslo_uzytkownika = new String(pole_haslo.getPassword());
                try {
                    serwer.serving = 1;
                    serwer.receiving = 1;
                    Socket sock = new Socket("localhost", 1522);
                    klient.zaloguj(sock);
                    kontrola.setBounds(21, 330, 400, 20);
                    kontrola.setText(wiadomosc);
                    pole_login.setText("");
                    pole_haslo.setText("");
                    if(ranga.equals("user")){
                        System.out.print(ranga+"\n");
                        // tu ma przejsc do frontendu aplikacji z wylaczona opcja zarzadzania
                    }
                    else if(ranga.equals("admin")){
                        System.out.print(ranga+"\n");
                        // tu ma przejsc do frontendu aplikacji z opcjami zarzadzania
                    }
                    else{
                        System.out.print("Użytkownik nie zalogowany"+"\n");
                    }
                    sock.close();
                } catch (IOException except) {
                    System.out.println("Kod bledu: " + except);
                }
            }
        });

        //Przycisk utowrz konto przekierowuje do nowego okna
        utworz_konto_przycisk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EkranUtworzKonto();
                okno_logowania.dispose();
            }
        });

        //Dodawanie komponentów do okna i startowanie okna logowania
        okno_logowania.add(powitanie);
        okno_logowania.add(pole_login);
        okno_logowania.add(l1);
        okno_logowania.add(pole_haslo);
        okno_logowania.add(l2);
        okno_logowania.add(zaloguj_przycisk);
        okno_logowania.add(utworz_konto_przycisk);
        okno_logowania.add(kontrola);

        okno_logowania.setBackground(Color.red);
        okno_logowania.setSize(400, 400);
        okno_logowania.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno_logowania.setLayout(null);
        okno_logowania.setVisible(true);
        okno_logowania.setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        new EkranLogowania();
    }
}
