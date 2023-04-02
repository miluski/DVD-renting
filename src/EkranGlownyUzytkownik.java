/** @file EkranGlownyUzytkownik.java
 * @brief plik zawierający kod głównego okna panelu użytkownika
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class EkranGlownyUzytkownik extends javax.swing.JFrame {
    private final javax.swing.JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();
    protected static String message;
    EkranGlownyUzytkownik() {
        initComponents();
        this.setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    Klient.polacz();
                    Klient.wyloguj();
                    Klient.zakonczPolaczenie();
                    dispose();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
    private void initComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
            javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
            javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
            javax.swing.JButton jButton5 = new javax.swing.JButton();
            javax.swing.JButton jButton6 = new javax.swing.JButton();
            javax.swing.JButton jButton7 = new javax.swing.JButton();
            javax.swing.JButton jButton8 = new javax.swing.JButton();
            javax.swing.JButton jButton9 = new javax.swing.JButton();
            javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
            javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
            javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
            javax.swing.JPanel jPanel4 = new javax.swing.JPanel();
            javax.swing.JButton jButton1 = new javax.swing.JButton();
            javax.swing.JPanel jPanel5 = new javax.swing.JPanel();
            javax.swing.JButton jButton2 = new javax.swing.JButton();
            javax.swing.JButton jButton3 = new javax.swing.JButton();
            javax.swing.JPanel jPanel6 = new javax.swing.JPanel();
            javax.swing.JButton jButton4 = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Wypożyczalnia DVD - Ekran główny użytkownika");

            jPanel1.setBackground(new java.awt.Color(255, 255, 255));

            jPanel2.setBackground(new java.awt.Color(89, 168, 105));

            jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 24));
            jLabel1.setText("Wypożyczalnia");

            jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 24));
            jLabel2.setText("DVD");

            jButton5.setBackground(new java.awt.Color(255, 255, 255));
            jButton5.setBorder(null);
            jButton5.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton5.setText("Pulpit");
            jButton5.setPreferredSize(new java.awt.Dimension(75, 30));
            jButton5.addActionListener(this::jButton5ActionPerformed);

            jButton6.setBackground(new java.awt.Color(255, 255, 255));
            jButton6.setBorder(null);
            jButton6.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton6.setText("Kolekcje");
            jButton6.setPreferredSize(new java.awt.Dimension(100, 30));
            jButton6.addActionListener(this::jButton6ActionPerformed);

            jButton7.setBackground(new java.awt.Color(255, 255, 255));
            jButton7.setBorder(null);
            jButton7.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton7.setText("Wypożyczenia i zwroty");
            jButton7.setPreferredSize(new java.awt.Dimension(149, 30));
            jButton7.addActionListener(this::jButton7ActionPerformed);

            jButton8.setBackground(new java.awt.Color(255, 255, 255));
            jButton8.setBorder(null);
            jButton8.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton8.setText("Rezerwacje");
            jButton8.setPreferredSize(new java.awt.Dimension(88, 30));
            jButton8.addActionListener(this::jButton8ActionPerformed);

            jButton9.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton9.setText("Wyloguj się");
            jButton9.setBackground(null);
            jButton9.setContentAreaFilled(false);
            jButton9.setBorderPainted(false);
            jButton9.setFocusPainted(false);
            jButton9.setOpaque(true);
            jButton9.getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if(model.isRollover()){
                    jButton9.setForeground(Color.lightGray);
                }
                else{
                    jButton9.setForeground(null);
                }
            });
            jButton9.addActionListener(this::jButton9ActionPerformed);

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                    .addGap(18, 18, 18)
                                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addComponent(jLabel1)
                                                                            .addComponent(jLabel2)))
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                    .addGap(55, 55, 55)
                                                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGap(0, 19, Short.MAX_VALUE)))
                                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel2)
                                    .addGap(78, 78, 78)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(31, 31, 31))
            );

            jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 24));
            jLabel3.setText("Panel Klienta");

            jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
            jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

            jPanel3.setBackground(new java.awt.Color(255, 255, 255));
            jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jLabel4.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24));
            jLabel4.setText("Powiadomienia:");

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(35, 35, 35)
                                    .addComponent(jLabel4)
                                    .addContainerGap(480, Short.MAX_VALUE))
            );
            jPanel3Layout.setVerticalGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(35, 35, 35)
                                    .addComponent(jLabel4)
                                    .addContainerGap(420, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Pulpit", jPanel3);

            jPanel4.setBackground(new java.awt.Color(255, 255, 255));
            jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jButton1.setText("Przeglądaj kolekcję");
            jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton1.addActionListener(this::jButton1ActionPerformed);

            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(35, 35, 35)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(513, Short.MAX_VALUE))
            );
            jPanel4Layout.setVerticalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(255, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Kolekcje", jPanel4);

            jPanel5.setBackground(new java.awt.Color(255, 255, 255));
            jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jButton2.setText("Moje wypożyczenia");
            jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton2.addActionListener(this::jButton2ActionPerformed);

            jButton3.setText("Moje zwroty");
            jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton3.addActionListener(this::jButton3ActionPerformed);

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(35, 35, 35)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(90, 90, 90)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(273, Short.MAX_VALUE))
            );
            jPanel5Layout.setVerticalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(255, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Wypożyczenia i zwroty", jPanel5);

            jPanel6.setBackground(new java.awt.Color(255, 255, 255));
            jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jButton4.setText("Zarezerwuj DVD");
            jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton4.addActionListener(this::jButton4ActionPerformed);

            javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
            jPanel6.setLayout(jPanel6Layout);
            jPanel6Layout.setHorizontalGroup(
                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGap(35, 35, 35)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(513, Short.MAX_VALUE))
            );
            jPanel6Layout.setVerticalGroup(
                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(255, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Rezerwacje", jPanel6);

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(50, 50, 50)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 61, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(45, 45, 45)
                                    .addComponent(jLabel3)
                                    .addGap(45, 45, 45)
                                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(58, Short.MAX_VALUE))
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

            pack();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
        int reply = Klient.dialogTakNie("Czy na pewno chcesz sie wylogowac?");
        if (reply == JOptionPane.YES_OPTION) {
            try {
                Klient.polacz();
                Klient.wyloguj();
                Klient.zakonczPolaczenie();
                if(message==null || message.equals("")) message = "Wystąpił nieoczekiwany błąd!\n";
                JOptionPane.showMessageDialog(this, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new EkranLogowania();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane1.setSelectedIndex(0);
    }
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane1.setSelectedIndex(1);
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane1.setSelectedIndex(2);
    }
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane1.setSelectedIndex(3);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogPrzegladajKolekcjeDVD(this, true, "ReviewDVDCollection");
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogMojeWypozyczenia(this, true);
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogMojeZwroty(this, true);
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogPrzegladajKolekcjeDVD(this, true, "DVDAvalaible");
    }
}