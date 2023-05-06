package com.client;
import com.server.EkranSerwer;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogZobaczRezerwacje extends javax.swing.JDialog {
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private static final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący tabelą
     */
    private static final javax.swing.JTable jTable1 = new javax.swing.JTable();
    /**
     * Atrybut będący sorterem tabeli
     */
    private static TableRowSorter<TableModel> rowSorter;
    /**
     * Atrybut będący nazwą użytkownika
     */
    private static String userName;
    /**
     * Atrybut będący nazwą płyty DVD
     */
    private static String dvdName;
    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     */
    DialogZobaczRezerwacje(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        jTable1.setRowSorter(null);
        rowSorter = null;
        Klient.polacz();
        Klient.otrzymujDane("ReviewReservations");
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
                Klient.panelData.clear();
                EkranSerwer.panelData.clear();
                jTable1.setModel(new DefaultTableModel());
                jTable1.getSelectionModel().removeListSelectionListener(e2 -> {
                    if (!e2.getValueIsAdjusting() && jTable1.getSelectedRowCount() == 1) {
                        int selectedRow = jTable1.getSelectedRow();
                        userName = jTable1.getModel().getValueAt(selectedRow, 0).toString();
                        dvdName = jTable1.getModel().getValueAt(selectedRow, 1).toString();
                    }
                });
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel("Wyszukiwanie: ");

        setTitle("Wypożyczalnia DVD - Rezerwacje");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18));
        jLabel1.setText("Rezerwacje");

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Zatwierdź rezerwację");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(200, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jTextField1.setBorder(new LineBorder(Color.BLACK));
        jTextField1.setColumns(29);
        jTextField1.setDisabledTextColor(Color.lightGray);
        jTextField1.setMaximumSize(jTextField1.getPreferredSize());

        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/2);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Klient", "Rezerwuje"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for(int i=0; i<size; i++){
            model.addRow(new Object[]{EkranSerwer.panelData.get(counter), EkranSerwer.panelData.get(counter+1)});
            if(size>1) counter+=2;
        }
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
        rowSorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setDefaultEditor(Object.class, null);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setRowSorter(rowSorter);
        jTable1.setBorder(new LineBorder(Color.BLACK));
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable1.getSelectedRowCount() == 1) {
                int selectedRow = jTable1.getSelectedRow();
                userName = jTable1.getModel().getValueAt(selectedRow, 0).toString();
                dvdName = jTable1.getModel().getValueAt(selectedRow, 1).toString();
            }
        });
        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<2; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            jTable1.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jLabel1)
                    )
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField1)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(15,15,15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
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
        Klient.panelData.clear();
        EkranSerwer.panelData.clear();
        jTable1.setRowSorter(null);
        rowSorter = null;
        pack();
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Zatwierdź rezerwację
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        jTable1.setRowSorter(null);
        rowSorter = null;
        if(userName!=null) {
            EkranSerwer.selectedNick = userName;
            EkranSerwer.filmName = dvdName;
            Klient.polacz();
            Klient.panelData.add(Klient.pobierzIDKlienta("selectedUser"));
            Klient.polacz();
            Klient.panelData.add(Integer.toString(Klient.pobierzIDDVD()));
            Klient.polacz();
            Klient.wysylajDane("RentDVD");
            Klient.polacz();
            Klient.usunRezerwacje();
            Klient.panelData.clear();
            EkranSerwer.panelData.clear();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            model.setColumnCount(0);
            jTable1.setModel(new DefaultTableModel());
            dispose();
        }
    }
}
