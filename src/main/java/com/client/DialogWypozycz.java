package com.client;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogWypozycz extends javax.swing.JDialog {
    /**
     * Lista zawierająca dane
     */
    private final java.util.List<String> panelData = new LinkedList<>();
    /**
     * Lista zawierająca dane
     */
    private final java.util.List<String> panelData2 = new LinkedList<>();
    /**
     * Instancja klasy klient
     */
    private final Klient klient;
    /**
     * Atrybut będący listą w postaci graficznej
     */
    private final javax.swing.JList<String> jList1 = new javax.swing.JList<>();
    /**
     * Atrybut będący listą w postaci graficznej
     */
    private final javax.swing.JList<String> jList2 = new javax.swing.JList<>();
    /**
     * Atrybut będący scrollbarem
     */
    private final javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    /**
     * Atrybut będący scrollbarem
     */
    private final javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
    /**
     * Atrybut będący scrollbarem
     */
    private final javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
    /**
     * Atrybut będący scrollbarem
     */
    private final javax.swing.JScrollPane jScrollPane4 = new javax.swing.JScrollPane();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private final javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
    /**
     * Atrybut będący polem tekstowym GUI
     */
    private final javax.swing.JTextArea jTextArea2 = new javax.swing.JTextArea();
    /**
     * Atrybut będący identyfikatorem użytkownika
     */
    protected String userID;
    /**
     * Atrybut będący identyfikatorem płyty DVD
     */
    protected String dvdID;
    /**
     * Atrybut będący liczbą kopii danej płyty DVD
     */
    protected String numberOfCopies;
    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     * @param klient Instancja klasy klient
     * @param modal Określa czy okno jest modalne, czy nie
     * @param parent Okno macierzyste
     */
    DialogWypozycz(Frame parent, boolean modal, Klient klient) {
        super(parent, modal);
        this.klient = klient;
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                DefaultListModel<String> defaultListModel3 = new DefaultListModel<>();
                jList1.setModel(defaultListModel3);
                jList2.setModel(defaultListModel3);
                panelData.clear();
                dispose();
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metoda czyszcząca zawartości komponentów graficznych dialog boxa
     */
    private void clearComponents(){
        DefaultListModel<String> defaultListModel3 = new DefaultListModel<>();
        jList1.setModel(defaultListModel3);
        jList2.setModel(defaultListModel3);
        panelData.clear();
    }
    /**
     * Metoda inicjalizująca komponenty graficzne dialog boxa
     */
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JButton jButton2 = new javax.swing.JButton();

        klient.polacz(klient);
        panelData.addAll(klient.otrzymujDane("ReviewClients",""));
        klient.zakonczPolaczenie();
        int size = ((panelData.size())/5);
        int counter = 0;
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for(int i=0; i<size; i++){
            defaultListModel.addElement(panelData.get(counter)+". "+panelData.get(counter+1));
            if(size>1) counter+=5;
        }
        jList1.setModel(defaultListModel);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Wypożycz");
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Wypożycz DVD");

        jList1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1.setViewportView(jList1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setText("Wybierz z listy klienta, który DVD wypożycza");
        klient.polacz(klient);
        panelData2.addAll(klient.otrzymujDane("ReviewDVDCollection", ""));
        klient.zakonczPolaczenie();
        int counter2 = 0;
        int size2 = ((panelData2.size())/10);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel<>();
        for(int i=0; i<size2; i++){
            defaultListModel2.addElement(panelData2.get(counter2)+". "+panelData2.get(counter2+1));
            if(size>1) counter2+=10;
        }
        jList2.setModel(defaultListModel2);
        jList2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane2.setViewportView(jList2);

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        jLabel3.setText("Wybierz z listy DVD, które klient chce wypożyczyć");

        jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel4.setText("Podsumowanie");

        jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel5.setText("Klient:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel6.setText("Wypożycza:");

        jScrollPane3.setPreferredSize(new java.awt.Dimension(234, 30));
        jList1.addListSelectionListener(e -> {
            if(jList1.getSelectedValue()!=null) {
                userID = jList1.getSelectedValue().substring(0, (jList1.getSelectedValue()).indexOf("."));
                jTextArea1.setColumns(20);
                jTextArea1.setRows(1);
                jTextArea1.setText(jList1.getSelectedValue());
                jTextArea1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
                jTextArea1.setDisabledTextColor(Color.BLACK);
                jTextArea1.setEnabled(false);
                jScrollPane3.setViewportView(jTextArea1);
            }
        });

        jList2.addListSelectionListener(e -> {
            if(jList2.getSelectedValue()!=null) {
                dvdID = jList2.getSelectedValue().substring(0, (jList2.getSelectedValue()).indexOf("."));
                jTextArea2.setColumns(20);
                jTextArea2.setRows(1);
                jTextArea2.setText(jList2.getSelectedValue());
                jTextArea2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
                jTextArea2.setDisabledTextColor(Color.BLACK);
                jTextArea2.setEnabled(false);
                jScrollPane4.setViewportView(jTextArea2);
                int counter3 = 0;
                int size3 = ((panelData2.size())/10);
                for(int i=0; i<size3; i++){
                    boolean isFound = dvdID.equals(panelData2.get(counter3));
                    if(isFound){
                        numberOfCopies = panelData2.get(counter3+8);
                        break;
                    }
                    if(size3>1) counter3+=10;
                }
            }
        });

        jButton1.setBackground(new java.awt.Color(89, 168, 105));
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Zatwierdź");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setText("Anuluj");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(71, 71, 71))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                                .addContainerGap(52, Short.MAX_VALUE))))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(441, 441, 441)
                .addComponent(jLabel1)
                .addGap(11, 11, 11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Zatwierdź
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        int buffer = (Integer.parseInt(numberOfCopies) - 1);
        if (buffer <= 0) {
            JOptionPane.showMessageDialog(this, "Nie możesz wypożyczyć tego DVD!", "Błąd", JOptionPane.WARNING_MESSAGE);
        } else {
            klient.panelData.clear();
            klient.panelData.add(userID);
            klient.panelData.add(dvdID);
            klient.polacz(klient);
            String message = klient.wysylajDane("RentDVD");
            klient.zakonczPolaczenie();
            int updatingID = Integer.parseInt(dvdID);
            String updatingItem = Integer.toString(buffer);
            klient.polacz(klient);
            klient.zaktualizujStan(updatingItem,updatingID);
            klient.zakonczPolaczenie();
            if (message == null) JOptionPane.showMessageDialog(this, "Wystąpil nieoczekiwany błąd!", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Sukces", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            clearComponents();
            dispose();
        }
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Anuluj
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents();
        dispose();
    }
}
