package com.client;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.LinkedList;

/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogPrzegladajListeKlientow extends javax.swing.JDialog {
    /**
     * Atrybut będący polem tekstowym
     */
    private final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący tabelą
     */
    private final javax.swing.JTable jTable1 = new javax.swing.JTable();
    /**
     * Atrybut będący sorterem tabeli
     */
    private TableRowSorter<TableModel> rowSorter;
    /**
     * Lista odebranych danych
     */
    private final java.util.List<String> panelData = new LinkedList<>();
    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     * @param klient Instancja klasy klient
     * @param modal Określa czy okno jest modalne, czy nie
     * @param parent Okno macierzyste
     */
    public DialogPrzegladajListeKlientow(Frame parent, boolean modal, Klient klient) {
        super(parent, modal);
        klient.polacz(klient);
        panelData.addAll(klient.otrzymujDane("ReviewClients",""));
        klient.zakonczPolaczenie();
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
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
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel("Wyszukiwanie: ");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Lista klientów");
        setPreferredSize(new java.awt.Dimension(950, 600));
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel2.setText("Lista Klientów");

        jTextField1.setBorder(new LineBorder(Color.BLACK));
        jTextField1.setColumns(13);
        jTextField1.setDisabledTextColor(Color.lightGray);
        jTextField1.setMaximumSize(jTextField1.getPreferredSize());
        int counter = 0;
        int size = ((panelData.size())/5);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID","Imię", "Nazwisko", "Adres e-mail", "Telefon"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for(int i=0; i<size; i++){
            model.addRow(new Object[]{panelData.get(counter), panelData.get(counter+1), panelData.get(counter+2), panelData.get(counter+3), panelData.get(counter+4)});
            if(size>1) counter+=5;
        }
        jTable1.setEnabled(false);
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

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<5; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            jTable1.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 14));

        jScrollPane1.setViewportView(jTable1);
        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Ok");
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
                        .addGap(420, 420, 420)
                        .addComponent(jLabel2)
                        .addGap(190,190,190)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(410, 410, 410)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jLabel3)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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
    /**
     * Metoda obsługująca kliknięcie przycisku Ok
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        jTable1.setRowSorter(null);
        rowSorter = null;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        jTable1.setModel(new DefaultTableModel());
    }
}
