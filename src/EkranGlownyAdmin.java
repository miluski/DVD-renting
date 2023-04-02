/** @file DialogGlownyAdmin.java
 * @brief plik zawierający kod głównego okna panelu administratora
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import java.awt.*;

public class EkranGlownyAdmin extends javax.swing.JFrame {
    private static JTabbedPane jTabbedPane2;
    protected static String message;
    EkranGlownyAdmin() {
        setVisible(true);
        initComponents();
        this.setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    Klient.polacz();
                    Klient.wyloguj();
                    Klient.zakonczPolaczenie();
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
            JPanel jPanel1 = new javax.swing.JPanel();
            JPanel jPanel2 = new javax.swing.JPanel();
            JLabel jLabel2 = new javax.swing.JLabel();
            JLabel jLabel3 = new javax.swing.JLabel();
            JButton jButton10 = new javax.swing.JButton();
            JButton jButton11 = new javax.swing.JButton();
            JButton jButton12 = new javax.swing.JButton();
            JButton jButton13 = new javax.swing.JButton();
            JButton jButton14 = new javax.swing.JButton();
            JButton jButton15 = new javax.swing.JButton();
            JButton jButton8 = new javax.swing.JButton();
            JButton jButton28 = new javax.swing.JButton();
            JPanel jPanel5 = new javax.swing.JPanel();
            JLabel jLabel4 = new javax.swing.JLabel();
            JLabel jLabel5 = new javax.swing.JLabel();
            JPanel jPanel3 = new javax.swing.JPanel();
            JButton jButton1 = new javax.swing.JButton();
            JButton jButton2 = new javax.swing.JButton();
            JButton jButton3 = new javax.swing.JButton();
            JButton jButton4 = new javax.swing.JButton();
            JButton jButton5 = new javax.swing.JButton();
            JButton jButton7 = new javax.swing.JButton();
            JPanel jPanel4 = new javax.swing.JPanel();
            JButton jButton6 = new javax.swing.JButton();
            JButton jButton16 = new javax.swing.JButton();
            JButton jButton17 = new javax.swing.JButton();
            JPanel jPanel6 = new javax.swing.JPanel();
            JButton jButton18 = new javax.swing.JButton();
            JButton jButton19 = new javax.swing.JButton();
            JButton jButton20 = new javax.swing.JButton();
            JButton jButton21 = new javax.swing.JButton();
            JPanel jPanel7 = new javax.swing.JPanel();
            JButton jButton27 = new javax.swing.JButton();
            JPanel jPanel9 = new javax.swing.JPanel();
            JButton jButton29 = new javax.swing.JButton();
            JButton jButton30 = new javax.swing.JButton();
            JPanel jPanel8 = new javax.swing.JPanel();
            JButton jButton22 = new javax.swing.JButton();
            JButton jButton23 = new javax.swing.JButton();
            JButton jButton24 = new javax.swing.JButton();
            JButton jButton25 = new javax.swing.JButton();
            JButton jButton26 = new javax.swing.JButton();
            JButton jButton31 = new javax.swing.JButton();
            JLabel jLabel1 = new javax.swing.JLabel();

            UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
            UIManager.put("TabbedPane.selected", Color.WHITE);
            jTabbedPane2 = new JTabbedPane();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Wypożyczalnia DVD - Zarządzanie");

            jPanel1.setBackground(new java.awt.Color(255, 255, 255));

            jPanel2.setBackground(new java.awt.Color(89, 168, 105));
            jPanel2.setPreferredSize(new java.awt.Dimension(200, 498));

            jPanel3.setBackground(new java.awt.Color(255, 255, 255));
            jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jPanel4.setBackground(new java.awt.Color(255, 255, 255));
            jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jPanel6.setBackground(new java.awt.Color(255, 255, 255));
            jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jPanel7.setBackground(new java.awt.Color(255, 255, 255));
            jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jPanel8.setBackground(new java.awt.Color(255, 255, 255));
            jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jPanel9.setBackground(new java.awt.Color(255, 255, 255));
            jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 25));
            jLabel2.setText("Wypożyczalnia");

            jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 25));
            jLabel3.setText("DVD");

            jButton10.setBackground(new java.awt.Color(255, 255, 255));
            jButton10.setBorder(null);
            jButton10.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton10.setText("Kolekcja DVD");
            jButton10.setPreferredSize(new java.awt.Dimension(100, 30));
            jButton10.addActionListener(this::jButton10ActionPerformed);

            jButton11.setBackground(new java.awt.Color(255, 255, 255));
            jButton11.setBorder(null);
            jButton11.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton11.setText("Klienci");
            jButton11.setPreferredSize(new java.awt.Dimension(72, 30));
            jButton11.addActionListener(this::jButton11ActionPerformed);

            jButton12.setBackground(new java.awt.Color(255, 255, 255));
            jButton12.setBorder(null);
            jButton12.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton12.setText("Wypożyczenia i zwroty");
            jButton12.setPreferredSize(new java.awt.Dimension(149, 30));
            jButton12.addActionListener(this::jButton12ActionPerformed);

            jButton13.setBackground(new java.awt.Color(255, 255, 255));
            jButton13.setBorder(null);
            jButton13.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton13.setText("Rezerwacje");
            jButton13.setPreferredSize(new java.awt.Dimension(88, 30));
            jButton13.addActionListener(this::jButton13ActionPerformed);

            jButton14.setBackground(new java.awt.Color(255, 255, 255));
            jButton14.setBorder(null);
            jButton14.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton14.setText("Pulpit");
            jButton14.setPreferredSize(new java.awt.Dimension(72, 30));
            jButton14.addActionListener(this::jButton14ActionPerformed);

            jButton15.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton15.setText("Wyloguj się");
            jButton15.setBackground(null);
            jButton15.setContentAreaFilled(false);
            jButton15.setBorderPainted(false);
            jButton15.setFocusPainted(false);
            jButton15.setOpaque(true);
            jButton15.getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if(model.isRollover()){
                    jButton15.setForeground(Color.lightGray);
                }
                else{
                    jButton15.setForeground(null);
                }
            });
            jButton15.addActionListener(this::jButton15ActionPerformed);

            jButton8.setBackground(new java.awt.Color(255, 255, 255));
            jButton8.setBorder(null);
            jButton8.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton8.setText("Zarządzaj bazą danych");
            jButton8.setPreferredSize(new java.awt.Dimension(75, 30));
            jButton8.addActionListener(this::jButton8ActionPerformed);

            jButton28.setBackground(new java.awt.Color(255, 255, 255));
            jButton28.setBorder(null);
            jButton28.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton28.setText("Wystawianie rachunków");
            jButton28.setPreferredSize(new java.awt.Dimension(81, 30));
            jButton28.addActionListener(this::jButton28ActionPerformed);

            jButton31.setText("Edytuj Klienta");
            jButton31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton31.setPreferredSize(new java.awt.Dimension(150, 200));
            jButton31.addActionListener(this::jButton31ActionPerformed);

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(49, 49, 49)
                                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 13, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel3)
                    .addGap(63, 63, 63)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton15)
                    .addGap(32, 32, 32))
            );

            jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
            jTabbedPane2.setPreferredSize(new java.awt.Dimension(700, 520));
            jTabbedPane2.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));
            jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jTabbedPane2.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

            jPanel5.setBackground(new java.awt.Color(255, 255, 255));
            jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 24));
            jLabel4.setText("Powiadomienia:");

            jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4))
                    .addContainerGap(480, Short.MAX_VALUE))
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jLabel4)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel5)
                    .addContainerGap(402, Short.MAX_VALUE))
            );

            jTabbedPane2.addTab("Pulpit", jPanel5);

            jPanel3.setBackground(new java.awt.Color(255, 255, 255));

            jButton1.setBackground(new java.awt.Color(255, 255, 255));
            jButton1.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton1.setText("Dodaj DVD");
            jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton1.addActionListener(this::jButton1ActionPerformed);

            jButton2.setBackground(new java.awt.Color(255, 255, 255));
            jButton2.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton2.setText("Edytuj DVD");
            jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton2.addActionListener(this::jButton2ActionPerformed);

            jButton3.setBackground(new java.awt.Color(255, 255, 255));
            jButton3.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton3.setText("Usun DVD");
            jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton3.addActionListener(this::jButton3ActionPerformed);

            jButton4.setBackground(new java.awt.Color(255, 255, 255));
            jButton4.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton4.setText("Przegladaj kolekcje");
            jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton4.addActionListener(this::jButton4ActionPerformed);

            jButton5.setBackground(new java.awt.Color(255, 255, 255));
            jButton5.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton5.setText("Zwiększ liczbę Egzemplarzy");
            jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton5.addActionListener(this::jButton5ActionPerformed);

            jButton7.setBackground(new java.awt.Color(255, 255, 255));
            jButton7.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton7.setText("Stan magazynowy");
            jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton7.addActionListener(this::jButton7ActionPerformed);

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(90, 90, 90)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(35, 35, 35))
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(27, 27, 27)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(30, Short.MAX_VALUE))
            );

            jTabbedPane2.addTab("Kolekcje", jPanel3);

            jPanel4.setBackground(new java.awt.Color(255, 255, 255));
            jPanel4.setPreferredSize(new java.awt.Dimension(700, 520));

            jButton6.setBackground(new java.awt.Color(255, 255, 255));
            jButton6.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton6.setText("Przeglądaj listę klientów");
            jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton6.addActionListener(this::jButton6ActionPerformed);

            jButton16.setBackground(new java.awt.Color(255, 255, 255));
            jButton16.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton16.setText("Usuń klienta");
            jButton16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton16.addActionListener(this::jButton16ActionPerformed);

            jButton17.setBackground(new java.awt.Color(255, 255, 255));
            jButton17.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton17.setText("Dodaj Klienta");
            jButton17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton17.addActionListener(this::jButton17ActionPerformed);

            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(35,35,35)
                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(35, 35, 35))
            );
            jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE)
                    .addContainerGap(257, Short.MAX_VALUE))
            );

            jTabbedPane2.addTab("Klienci", jPanel4);

            jPanel6.setBackground(new java.awt.Color(255, 255, 255));

            jButton18.setBackground(new java.awt.Color(255, 255, 255));
            jButton18.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton18.setText("Wypożycz");
            jButton18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton18.addActionListener(this::jButton18ActionPerformed);

            jButton19.setBackground(new java.awt.Color(255, 255, 255));
            jButton19.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton19.setText("Zwróć");
            jButton19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton19.addActionListener(this::jButton19ActionPerformed);

            jButton20.setBackground(new java.awt.Color(255, 255, 255));
            jButton20.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton20.setText("Przeglądaj wypożyczenia");
            jButton20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton20.addActionListener(this::jButton20ActionPerformed);

            jButton21.setBackground(new java.awt.Color(255, 255, 255));
            jButton21.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton21.setText("Przeglądaj zwroty");
            jButton21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton21.addActionListener(this::jButton21ActionPerformed);

            javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
            jPanel6.setLayout(jPanel6Layout);
            jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(90, 90, 90)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                    .addContainerGap(275, Short.MAX_VALUE))
            );
            jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(27, 27, 27)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(30, Short.MAX_VALUE))
            );

            jTabbedPane2.addTab("Wypożyczenia i zwroty", jPanel6);

            jPanel7.setBackground(new java.awt.Color(255, 255, 255));

            jButton27.setBackground(new java.awt.Color(255, 255, 255));
            jButton27.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton27.setText("Zobacz rezerwacje");
            jButton27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton27.addActionListener(this::jButton27ActionPerformed);

            javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
            jPanel7.setLayout(jPanel7Layout);
            jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(515, Short.MAX_VALUE))
            );
            jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(257, Short.MAX_VALUE))
            );

            jTabbedPane2.addTab("Rezerwacje", jPanel7);

            jPanel9.setBackground(new java.awt.Color(255, 255, 255));

            jButton29.setBackground(new java.awt.Color(255, 255, 255));
            jButton29.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton29.setText("Wystaw rachunek");
            jButton29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton29.addActionListener(this::jButton29ActionPerformed);

            jButton30.setBackground(new java.awt.Color(255, 255, 255));
            jButton30.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton30.setText("Przeglądaj rachunki");
            jButton30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton30.addActionListener(this::jButton30ActionPerformed);

            javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
            jPanel9.setLayout(jPanel9Layout);
            jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(90, 90, 90)
                    .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(275, Short.MAX_VALUE))
            );
            jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(jButton30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(257, Short.MAX_VALUE))
            );

            jTabbedPane2.addTab("Rachunki", jPanel9);

            jPanel8.setBackground(new java.awt.Color(255, 255, 255));

            jButton22.setBackground(new java.awt.Color(255, 255, 255));
            jButton22.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton22.setText("Utwórz tabele");
            jButton22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton22.addActionListener(this::jButton22ActionPerformed);

            jButton23.setBackground(new java.awt.Color(255, 255, 255));
            jButton23.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton23.setText("Usuń wszystkie dane bazy");
            jButton23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton23.addActionListener(this::jButton23ActionPerformed);

            jButton24.setBackground(new java.awt.Color(255, 255, 255));
            jButton24.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton24.setText("Usuń tabele");
            jButton24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton24.addActionListener(this::jButton24ActionPerformed);

            jButton25.setBackground(new java.awt.Color(255, 255, 255));
            jButton25.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton25.setText("Utwórz sekwencje");
            jButton25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton25.addActionListener(this::jButton25ActionPerformed);

            jButton26.setBackground(new java.awt.Color(255, 255, 255));
            jButton26.setFont(new java.awt.Font("DejaVu Sans Condensed", Font.PLAIN, 12));
            jButton26.setText("Usuń sekwencje");
            jButton26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jButton26.addActionListener(this::jButton26ActionPerformed);

            javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
            jPanel8.setLayout(jPanel8Layout);
            jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton25, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton26, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                    .addGap(90, 90, 90)
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(35, 35, 35))
            );
            jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(27, 27, 27)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton26, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                    .addContainerGap(30, Short.MAX_VALUE))
            );

            jTabbedPane2.addTab("Zarządzanie bazą danych", jPanel8);

            jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 24));
            jLabel1.setText("Panel Administratora");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(50, 50, 50)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(63, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(jLabel1)
                    .addGap(45, 45, 45)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            EkranSerwer.setMessage("Kod błędu: " + ex);
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogDodajDVD(this, true);
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogEdytujDVD(this, true);
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogUsunDVD(this, true);
    }
    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane2.setSelectedIndex(0);
    }
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane2.setSelectedIndex(1);
    }
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane2.setSelectedIndex(2);
    }
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane2.setSelectedIndex(3);
    }
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane2.setSelectedIndex(4);
    }
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane2.setSelectedIndex(6);
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogPrzegladajKolekcjeDVD(this, true, "ReviewDVDCollection");
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogStanMagazynowyDVD(this, true);
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogUstawLiczbeDVD(this, true);
    }
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogPrzegladajListeKlientow(this, true);
    }
    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogUsunKlienta(this, true);
    }
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {
        new EkranUtworzKonto("admin");
    }
    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogWypozycz(this, true);
    }
    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogPrzegladajWypozyczenia(this, true);
    }
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogZwroc(this, true);
    }
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogPrzegladajZwroty(this, true);
    }
    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogZobaczRezerwacje(this, true);
    }
    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogUtworzTabele(this, true);
    }
    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogUsunTabele(this, true);
    }
    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {
        int reply = Klient.dialogTakNie("Spowoduje to usunięcie wszystkich użytkowników z bazy danych\nCzy kontynuować?");
        if (reply == JOptionPane.YES_OPTION) {
            try {
                Klient.polacz();
                Klient.zarzadzaj("DeleteFromTables");
                Klient.zakonczPolaczenie();
                if(message.equals("")) message = "Wystąpił nieoczekiwany błąd!";
                JOptionPane.showMessageDialog(this, message, "Sukces", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogUtworzSekwencje(this, true);
    }
    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogUsunSekwencje(this, true);
    }
    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {
        int reply = Klient.dialogTakNie("Czy na pewno chcesz sie wylogowac?");
        if (reply == JOptionPane.YES_OPTION) {
            try {
                Klient.polacz();
                Klient.wyloguj();
                Klient.zakonczPolaczenie();
                if(message.equals("")) message = "Wystąpił nieoczekiwany błąd!";
                JOptionPane.showMessageDialog(this, message, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new EkranLogowania();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane2.setSelectedIndex(5);
    }
    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogWystawRachunek(this, true);
    }
    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogPrzegladajRachunki(this, true);
    }
    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {
        new DialogEdytujKlienta(this, true);
    }
}
