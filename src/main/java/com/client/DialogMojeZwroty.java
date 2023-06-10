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
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogMojeZwroty extends javax.swing.JDialog {
    /**
     * Atrybut będący polem tekstowym
     */
    private static final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący tabelą
     */
    private static final javax.swing.JTable jTable1 = new javax.swing.JTable();
    /**
     * Atrybut będący ID użytkownika
     */
    protected static String userID;
    /**
     * Atrybut będący sorterem tabeli
     */
    private static TableRowSorter<TableModel> rowSorter;
    /**
     * Atrybut będący listą ciągów znaków przechowującym dane
     */
    protected final List<String> dane = new ArrayList<>();
    /**
     * Lista pobranych danych
     */
    private final java.util.List<String> panelData = new LinkedList<>();
    /**
     * Instancja klasy klient
     */
    private final Klient klient;

    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     *
     * @param parent Rodzic okna
     * @param modal  Parametr określający czy okno ma należeć do rodzica
     * @param klient Instancja klasy klient
     */
    public DialogMojeZwroty(Frame parent, boolean modal, Klient klient) {
        super(parent, modal);
        this.klient = klient;
        userID = getClientID();
        fillPanelData();
        initComponents();
        this.setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
            }
        });
    }

    /**
     * Metoda pobierająca ID klienta od serwera
     *
     * @return Zwraca ID klienta
     */
    private String getClientID() {
        dane.clear();
        dane.add("GetClientID");
        dane.add(klient.nickname);
        java.util.List<String> dataList = (List<String>) new LinkedList<>(klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
        if(!dataList.isEmpty()) return dataList.get(0);
        return null;
    }

    /**
     * Metoda, której zadaniem jest wysłanie żądania do serwera celem odbioru danych wypożyczeń danego użytkownika
     */
    private void fillPanelData() {
        dane.clear();
        dane.add("ReviewMyReturns");
        dane.add(userID);
        panelData.addAll((Collection<? extends String>) klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
    }

    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel("Wyszukiwanie: ");
        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 14));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Moje zwroty");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Lista zwróconych przeze mnie DVD");
        int counter = 0;
        int size = ((panelData.size()) / 10);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{}, new String[]{"ID Zwrotu", "Nazwa filmu", "Reżyseria", "Gatunek", "Kraj produkcji", "Rok produkcji", "Język filmu", "Długość filmu", "Data wypożyczenia", "Data zwrotu"}));
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < size; i++) {
            model.addRow(new Object[]{panelData.get(counter), panelData.get(counter + 1), panelData.get(counter + 2), panelData.get(counter + 3), panelData.get(counter + 4), panelData.get(counter + 5), panelData.get(counter + 6), panelData.get(counter + 7), panelData.get(counter + 8), panelData.get(counter + 9)});
            if (size > 1) counter += 10;
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 10; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            jTable1.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        jScrollPane1.setViewportView(jTable1);
        jTable1.setBorder(new LineBorder(Color.BLACK));
        jTable1.setEnabled(false);
        jTextField1.setBorder(new LineBorder(Color.BLACK));
        jTextField1.setColumns(13);
        jTextField1.setDisabledTextColor(Color.lightGray);
        jTextField1.setMaximumSize(jTextField1.getPreferredSize());
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

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Ok");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(400, 400, 400).addComponent(jLabel1).addGap(155, 155, 155).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1)).addGroup(jPanel1Layout.createSequentialGroup().addGap(34, 34, 34).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(450, 450, 450).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(36, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(25, 25, 25).addComponent(jLabel1).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(25, 25, 25).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(18, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pack();
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Ok
     *
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
