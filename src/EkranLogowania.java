import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class EkranLogowania extends javax.swing.JFrame {

    static String login_uzytkownika;
    static String haslo_uzytkownika;
    public static String wiadomosc, ranga;
    private static final JPasswordField jPasswordField1 = new javax.swing.JPasswordField();
    private static final JTextField jTextField1 = new javax.swing.JTextField();
    EkranLogowania() {
        setVisible(true);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {

        JPanel jPanel1 = new javax.swing.JPanel();
        JPanel jPanel3 = new javax.swing.JPanel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();
        JLabel jLabel1 = new javax.swing.JLabel();

        setTitle("Wypożyczalnia DVD - Zaloguj się");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(89, 168, 105));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 700));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 174));

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", Font.PLAIN, 24)); // NOI18N
        jLabel2.setText("Zaloguj się");

        jLabel3.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 14)); // NOI18N
        jLabel3.setText("Login");

        jTextField1.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel4.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 14)); // NOI18N
        jLabel4.setText("Hasło");

        jPasswordField1.setPreferredSize(new java.awt.Dimension(90, 25));

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton1.setText("Zaloguj się");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton2.setText("Załóż konto");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12)); // NOI18N
        jButton3.setText("Nie pamiętasz hasła?");
        jButton3.setBackground(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);
        jButton3.setOpaque(true);
        jButton3.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if(model.isRollover()){
                jButton3.setForeground(Color.lightGray);
            }
            else{
                jButton3.setForeground(null);
            }
        });
        jButton3.addActionListener(this::jButton3ActionPerformed);


        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 47, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(42, 42, 42)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon("images\\LoginBackground2.jpg")); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(440, 550));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(74, Short.MAX_VALUE))
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt){
        dispose();
        new EkranPrzywrocHaslo();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if(jTextField1.getText().isEmpty() || jPasswordField1.getPassword().length == 0){
            JOptionPane.showMessageDialog(this,"Nie wprowadzono jednej z istotnych danych!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else {
            login_uzytkownika = jTextField1.getText();
            haslo_uzytkownika = new String(jPasswordField1.getPassword());
            try {
                serwer.serving = 1;
                serwer.receiving = 1;
                Socket sock = new Socket("localhost", 1522);
                klient.zaloguj(sock);
                JOptionPane.showMessageDialog(this, wiadomosc, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                jTextField1.setText("");
                jPasswordField1.setText("");
                if (ranga.equals("user")) {
                    System.out.print(ranga + "\n");
                    //TODO: tu ma przejsc do frontendu aplikacji z wylaczona opcja zarzadzania
                } else if (ranga.equals("admin")) {
                    System.out.print(ranga + "\n");
                    //TODO: tu ma przejsc do frontendu aplikacji z opcjami zarzadzania
                } else {
                    System.out.print("Użytkownik nie zalogowany" + "\n");
                }
                sock.close();
            } catch (IOException except) {
                System.out.println("Kod bledu: " + except);
            }
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        new EkranUtworzKonto();
    }
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(EkranLogowania::new);
    }

}
