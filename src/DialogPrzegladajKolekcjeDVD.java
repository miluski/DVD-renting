/** @file DialogPrzegladajKolekcjeDVD.java
 * @brief plik zawierający kod dialog boxa pozwalajacego na przeglądanie kolekcji DVD od strony administratora i klienta wypożyczalnii
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import javax.swing.event.DocumentEvent;

public class DialogPrzegladajKolekcjeDVD extends javax.swing.JDialog {
    private static final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    private static final javax.swing.JTable jTable1 = new javax.swing.JTable();
    private static TableRowSorter<TableModel> rowSorter;
    private static String operation, dvdID, numberOfCopies, filmName;
    private static boolean reservationFound = false;
    DialogPrzegladajKolekcjeDVD(java.awt.Frame parent, boolean modal, String operation) {
        super(parent, modal);
        DialogPrzegladajKolekcjeDVD.operation = operation;
        Klient.polacz();
        switch(operation){
            case "ReviewDVDCollection" -> Klient.otrzymujDane("ReviewDVDCollection");
            case "DVDAvalaible" -> Klient.otrzymujDane("DVDAvalaible");
        }
        Klient.zakonczPolaczenie();
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                EkranSerwer.panelData.clear();
                dispose();
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    private static void resetComponents(){
        jTable1.setRowSorter(null);
        rowSorter = null;
        EkranSerwer.panelData.clear();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        jTable1.setModel(new DefaultTableModel());
        jTable1.getSelectionModel().removeListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable1.getSelectedRowCount() == 1) {
                int selectedRow = jTable1.getSelectedRow();
                dvdID = jTable1.getModel().getValueAt(selectedRow, 0).toString();
                numberOfCopies = jTable1.getModel().getValueAt(selectedRow, 8).toString();
                filmName = jTable1.getModel().getValueAt(selectedRow,1).toString();
            }
        });
    }
    private void initComponents() {
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel("Wyszukiwanie: ");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Przeglądaj Kolekcje");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setBorder(new LineBorder(Color.BLACK));
        jTextField1.setColumns(13);
        jTextField1.setDisabledTextColor(Color.lightGray);
        jTextField1.setMaximumSize(jTextField1.getPreferredSize());

        jTable1.setBorder(new LineBorder(Color.BLACK));
        jTable1.setEnabled(true);
        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/10);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "ID", "Nazwa filmu", "Reżyseria", "Gatunek", "Kraj produkcji", "Rok produkcji", "Język filmu", "Długość filmu", "Ilość egzemplarzy", "Opłata za dobę"
                }
        ));
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for(int i=0; i<size; i++){
            model.addRow(new Object[]{EkranSerwer.panelData.get(counter), EkranSerwer.panelData.get(counter+1), EkranSerwer.panelData.get(counter+2), EkranSerwer.panelData.get(counter+3), EkranSerwer.panelData.get(counter+4), EkranSerwer.panelData.get(counter+5), EkranSerwer.panelData.get(counter+6), EkranSerwer.panelData.get(counter+7), EkranSerwer.panelData.get(counter+8), EkranSerwer.panelData.get(counter+9)});
            if(size>1) counter+=10;
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<10; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            jTable1.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        jTable1.setDefaultEditor(Object.class, null);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(rowSorter);
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jTextField1.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jTextField1.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Nie obsługiwany event!");
            }
        });
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable1.getSelectedRowCount() == 1) {
                int selectedRow = jTable1.getSelectedRow();
                dvdID = jTable1.getModel().getValueAt(selectedRow, 0).toString();
                numberOfCopies = jTable1.getModel().getValueAt(selectedRow, 8).toString();
                filmName = jTable1.getModel().getValueAt(selectedRow,1).toString();
                reservationFound = false;
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Przegląd kolekcji DVD");

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 14));

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        if(operation.equals("ReviewDVDCollection")) jButton1.setText("Ok");
        else if(operation.equals("DVDAvalaible")) jButton1.setText("Zarezerwuj");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1050, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(455, 455, 455)
                        .addComponent(jLabel1)
                        .addGap(245,245,245)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(500, 500, 500)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(100,100,100))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jLabel2)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
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
        pack();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
       if (operation.equals("DVDAvalaible")) {
           Klient.filmName = filmName;
           Klient.polacz();
           reservationFound = Klient.checkReservations();
           Klient.zakonczPolaczenie();
           if(reservationFound) JOptionPane.showMessageDialog(this, "Nie możesz zarezerwować wypożyczenia tego DVD!", "Informacja", JOptionPane.INFORMATION_MESSAGE);
           if(!reservationFound) {
               try {
                   if (dvdID != null) {
                       Klient.polacz();
                       String userID = Klient.pobierzIDKlienta("loggedIn");
                       Klient.zakonczPolaczenie();
                       Klient.panelData.add(dvdID);
                       EkranSerwer.updatingID = Integer.parseInt(dvdID);
                       int buffer = (Integer.parseInt(numberOfCopies) - 1);
                       if (buffer < 0) {
                           throw new Exception("Nie możesz zarezerwować wypożyczenia tego DVD!");
                       } else {
                           EkranSerwer.updatingItem = Integer.toString(buffer);
                           Klient.panelData.add(userID);
                           Klient.polacz();
                           Klient.wysylajDane("ReservateDVD");
                           Klient.zakonczPolaczenie();
                           if (EkranSerwer.message == null || EkranSerwer.message.equals("")) {
                               EkranSerwer.message = "Wystąpił nieoczekiwany błąd!";
                           }
                           JOptionPane.showMessageDialog(this, EkranSerwer.message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                           Klient.polacz();
                           Klient.zaktualizujStan();
                           Klient.zakonczPolaczenie();
                           resetComponents();
                           dispose();
                       }
                   }
               } catch (Exception ex) {
                   JOptionPane.showMessageDialog(this, ex.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
               }
           }
        }
        else if(operation.equals("ReviewDVDCollection")){
            resetComponents();
            dispose();
       }
    }
}

