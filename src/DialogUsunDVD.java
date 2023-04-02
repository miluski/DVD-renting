/** @file DialogUsunDVD.java
 * @brief plik zawierający kod dialog boxa pozwalajacego na usuwanie wybranej płyty DVD od strony administratora wypożyczalnii
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import java.awt.*;

public class DialogUsunDVD extends javax.swing.JDialog {
    private static final javax.swing.JList<String> jList1 = new javax.swing.JList<>();
    DialogUsunDVD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Klient.polacz();
        Klient.otrzymujDane("ReviewDVDCollection");
        Klient.zakonczPolaczenie();
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                EkranSerwer.panelData.clear();
                Klient.panelData.clear();
                DefaultListModel<String> newListModel = new DefaultListModel<>();
                jList1.setModel(newListModel);
                dispose();
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    private static void clearComponents(){
        DefaultListModel<String> newListModel = new DefaultListModel<>();
        jList1.setModel(newListModel);
        EkranSerwer.panelData.clear();
        Klient.panelData.clear();
    }
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JButton jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Usuń DVD");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Usuń DVD");

        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/10);
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for(int i=0; i<size; i++){
            defaultListModel.addElement(EkranSerwer.panelData.get(counter)+". "+EkranSerwer.panelData.get(counter+1));
            if(size>1) counter+=10;
        }
        jList1.setModel(defaultListModel);
        jList1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1.setViewportView(jList1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setText("Wybierz z listy DVD do usunięcia");

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Usuń");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(130, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setText("Anuluj");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setPreferredSize(new java.awt.Dimension(130, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(100, 100, 100)
                            .addComponent(jLabel2))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(159, 159, 159)
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
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
        EkranSerwer.panelData.clear();
        Klient.panelData.clear();
        pack();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        Klient.polacz();
        Klient.panelData.add(jList1.getSelectedValue().substring(0,(jList1.getSelectedValue()).indexOf(".")));
        Klient.wysylajDane("DeleteDVD");
        Klient.zakonczPolaczenie();
        if(EkranSerwer.message==null) EkranSerwer.message = "Wystąpił nieoczekiwany błąd!";
        JOptionPane.showMessageDialog(this, EkranSerwer.message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
        clearComponents();
        dispose();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents();
        dispose();
    }
}