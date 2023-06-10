package com.client;

import com.server.Logs;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
import javax.swing.event.DocumentEvent;

/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogPrzegladajKolekcjeDVD extends javax.swing.JDialog {
    /**
     * Atrybut będący listą ciągów znaków przechowującym dane
     */
    protected final List<String> dane = new ArrayList<>();
    /**
     * Atrybut będący polem tekstowym
     */
    private final javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
    /**
     * Atrybut będący tabelą
     */
    private final javax.swing.JTable jTable1 = new javax.swing.JTable();
    /**
     * Atrybut będący określeniem, czy kolekcje przegląda użytkownik, czy administrator
     */
    private final String operation;
    /**
     * Lista zawierająca dane kolekcji DVD
     */
    private final java.util.List<String> panelData = new LinkedList<>();
    /**
     * Instancja klasy klient
     */
    private final Klient klient;
    /**
     * Atrybut będący sorterem tabeli
     */
    private TableRowSorter<TableModel> rowSorter;
    /**
     * Atrybut będący identyfikatorem płyty DVD
     */
    private String dvdID;
    /**
     * Atrybut określający liczbę kopii płyty DVD
     */
    private String numberOfCopies;

    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     *
     * @param klient    Instancja klasy klient
     * @param modal     Określa czy okno jest modalne, czy nie
     * @param parent    Okno macierzyste
     * @param operation Parametr określający jaką operację wykonujemy przy wyświetlaniu tego okna
     */
    public DialogPrzegladajKolekcjeDVD(Frame parent, boolean modal, String operation, Klient klient) {
        super(parent, modal);
        this.klient = klient;
        this.operation = operation;
        dane.clear();
        switch (operation) {
            case "ReviewDVDCollection" -> dane.add("ReviewDVDCollection");
            case "DVDAvalaible" -> dane.add("DVDAvalaible");
        }
        panelData.addAll((Collection<? extends String>) klient.polacz(klient, dane));
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
     * Metoda czyszcząca zawartości komponentów graficznych dialog boxa
     */
    private void resetComponents() {
        jTable1.setRowSorter(null);
        rowSorter = null;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        jTable1.setModel(new DefaultTableModel());
        jTable1.getSelectionModel().removeListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable1.getSelectedRowCount() == 1) {
                int selectedRow = jTable1.getSelectedRow();
                dvdID = jTable1.getModel().getValueAt(selectedRow, 0).toString();
                numberOfCopies = jTable1.getModel().getValueAt(selectedRow, 8).toString();
            }
        });
    }

    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
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
        int size = ((panelData.size()) / 10);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nazwa filmu", "Reżyseria", "Gatunek", "Kraj produkcji", "Rok produkcji", "Język filmu", "Długość filmu", "Ilość egzemplarzy", "Opłata za dobę"}));
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
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Przegląd kolekcji DVD");

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 14));

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        if (operation.equals("ReviewDVDCollection")) jButton1.setText("Ok");
        else if (operation.equals("DVDAvalaible")) jButton1.setText("Zarezerwuj");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(30, 30, 30).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1050, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(455, 455, 455).addComponent(jLabel1).addGap(245, 245, 245).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addContainerGap(30, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGap(500, 500, 500).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(100, 100, 100)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(26, 26, 26).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(jLabel2).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(26, 26, 26).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(26, 26, 26).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(21, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        panelData.clear();
        pack();
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
        List<String> dataList = (List<String>) new LinkedList<>(klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
        return dataList.get(0);
    }

    /**
     * Metoda, której celem jest przesył danych na serwer niezbędnych do zarezerwowania płyty DVD
     *
     * @param userID ID klienta
     * @return Zwraca informację o tym, czy przesył został wykonany prawidłowo
     */
    private String reservateDVD(String userID) {
        dane.clear();
        dane.add("ReservateDVD");
        dane.add(dvdID);
        dane.add(userID);
        List<String> dataList = (List<String>) new LinkedList<>(klient.polacz(klient, dane));
        klient.zakonczPolaczenie();
        return dataList.get(0);
    }

    /**
     * Metoda, której zadaniem jest wysył do serwera żądania o zaktualizowanie ilości płyty DVD
     *
     * @param updatingItem Nowa ilość płyty DVD na stanie
     * @param updatingID   ID aktualizowanej płyty DVD
     */
    private void updateCount(String updatingItem, String updatingID) {
        dane.clear();
        dane.add("UpdateCount");
        dane.add("dvds_data");
        dane.add(updatingID);
        dane.add(updatingItem);
        klient.polacz(klient, dane);
        klient.zakonczPolaczenie();
    }

    /**
     * Metoda obsługująca kliknięcie przycisku Ok/Zarezerwuj
     *
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (operation.equals("DVDAvalaible")) {
            try {
                if (dvdID != null) {
                    String userID = getClientID();
                    int buffer = (Integer.parseInt(numberOfCopies) - 1);
                    if (buffer < 0) {
                        throw new Exception("Nie możesz zarezerwować wypożyczenia tego DVD!");
                    } else {
                        String updatingItem = Integer.toString(buffer);
                        String message = reservateDVD(userID);
                        if (message == null || message.equals("")) {
                            message = "Wystąpił nieoczekiwany błąd!";
                        }
                        JOptionPane.showMessageDialog(this, message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                        updateCount(updatingItem, dvdID);
                        resetComponents();
                        dispose();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Informacja", JOptionPane.INFORMATION_MESSAGE);
                new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "DialogPrzegladajKolekcjeDVD", "error");
            }
        } else if (operation.equals("ReviewDVDCollection")) {
            resetComponents();
            dispose();
        }
    }
}


