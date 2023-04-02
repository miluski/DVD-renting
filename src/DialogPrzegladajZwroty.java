/** @file DialogPrzegladajZwroty.java
 * @brief plik zawierający kod dialog boxa pozwalajacego na przeglądanie zwrotów od strony administratora wypożyczalnii
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogPrzegladajZwroty extends javax.swing.JDialog {
    private static final javax.swing.JList<String> jList1 = new javax.swing.JList<>();
    private static final javax.swing.JTable jTable1 = new javax.swing.JTable();
    protected static String userID;
    private static List<String> panelData = new ArrayList<>();
    DialogPrzegladajZwroty(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Klient.polacz();
        Klient.otrzymujDane("ReviewClients");
        Klient.zakonczPolaczenie();
        panelData = EkranSerwer.panelData;
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                jTable1.setModel(new DefaultTableModel());
                DefaultListModel<String> newListModel = new DefaultListModel<>();
                jList1.setModel(newListModel);
                EkranSerwer.panelData.clear();
                Klient.panelData.clear();
                panelData.clear();
                dispose();
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    private static void clearComponents(){
        jTable1.setModel(new DefaultTableModel());
        DefaultListModel<String> newListModel = new DefaultListModel<>();
        jList1.setModel(newListModel);
        EkranSerwer.panelData.clear();
        Klient.panelData.clear();
        panelData.clear();
    }
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Przeglądaj zwroty");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Przeglądaj Zwroty");

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setText("Wybierz klienta z listy:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        jLabel3.setText("Zwroty wybranego klienta:");

        int size = ((panelData.size())/5);
        int counter = 0;

        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for(int i=0; i<size; i++){
            defaultListModel.addElement(panelData.get(counter)+". "+panelData.get(counter+1)+" "+panelData.get(counter+2));
            if(size>1) counter+=5;
        }
        jList1.setModel(defaultListModel);
        jList1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jList1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                jTable1.setModel(new DefaultTableModel());
                if(jList1.getSelectedValue()!=null) userID = jList1.getSelectedValue().substring(0,(jList1.getSelectedValue()).indexOf("."));
                EkranSerwer.panelData.clear();
                Klient.panelData.clear();
                Klient.polacz();
                Klient.otrzymujDane("ReviewReturns");
                Klient.zakonczPolaczenie();
                int counter2 = 0;
                int size2 = ((EkranSerwer.panelData.size())/11);
                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                        new Object[][]{
                        },
                        new String[]{
                                "Nazwa filmu", "Data wypożyczenia", "Data zwrotu"
                        }
                ));
                jTable1.setEnabled(false);
                jTable1.setBorder(new LineBorder(Color.BLACK));
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                for(int i=0; i<size2; i++){
                    model.addRow(new Object[]{EkranSerwer.panelData.get(counter2+2), EkranSerwer.panelData.get(counter2+9), EkranSerwer.panelData.get(counter2+10)});
                    if(size2>1) counter2+=11;
                }
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for(int i=0; i<3; i++) {
                    jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    jTable1.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
                }
            }
        });
        jList1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1.setViewportView(jList1);

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Ok");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(371, 371, 371)
                                                .addComponent(jLabel1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(55, 55, 55)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(34, 34, 34)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(400, 400, 400)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel1)
                                .addGap(36, 36, 36)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(35, 35, 35)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        clearComponents();
    }
}
