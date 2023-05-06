package com.client;
import com.server.EkranSerwer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa zawierająca pola i metody służące do obsługi dialog boxa
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class DialogZwroc extends javax.swing.JDialog {
    /**
     * Atrybut będący buforową listą zawierającą dane
     */
    private static List<String> panelData = new ArrayList<>();
    /**
     * Atrybut będący listą w postaci graficznej
     */
    private static final javax.swing.JList<String> jList1 = new javax.swing.JList<>();
    /**
     * Atrybut będący listą w postaci graficznej
     */
    private static final javax.swing.JList<String> jList2 = new javax.swing.JList<>();
    /**
     * Atrybut będący scrollbarem
     */
    private static final javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    /**
     * Atrybut będący scrollbarem
     */
    private static final javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
    /**
     * Atrybut będący scrollbarem
     */
    private static final javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
    /**
     * Atrybut będący scrollbarem
     */
    private static final javax.swing.JScrollPane jScrollPane4 = new javax.swing.JScrollPane();
    /**
     * Atrybut będący polem tekstowym
     */
    private static final javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
    /**
     * Atrybut będący polem tekstowym
     */
    private static final javax.swing.JTextArea jTextArea2 = new javax.swing.JTextArea();
    /**
     * Atrybut będący identyfikatorem użytkownika
     */
    protected static String userID;
    /**
     * Atrybut będący identyfikatorem wypożyczenia
     */
    protected static String rentID;
    /**
     * Atrybut będący datą wypożyczenia
     */
    protected static String rentDate;
    /**
     * Atrybut będący nazwą filmu
     */
    protected static String filmName;
    /**
     * Atrybut będący liczbą kopii płyty DVD
     */
    private static int numberOfCopies;
    /**
     * Atrybut będący identyfikatorem płyty DVD
     */
    private static int dvdID;
    /**
     * Konstruktor odpowiadający za inicjalizację GUI
     */
    DialogZwroc(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
                jList1.removeListSelectionListener(e2-> jList2.removeListSelectionListener(e3-> {}));
                DefaultListModel<String> defaultListModel3 = new DefaultListModel<>();
                jList1.setModel(defaultListModel3);
                jList2.setModel(defaultListModel3);
                EkranSerwer.panelData.clear();
                Klient.panelData.clear();
                panelData.clear();
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metoda czyszcząca zawartości komponentów graficznych dialog boxa
     */
    private static void clearComponents(){
        jList1.removeListSelectionListener(e2-> jList2.removeListSelectionListener(e3-> {}));
        DefaultListModel<String> defaultListModel3 = new DefaultListModel<>();
        jList1.setModel(defaultListModel3);
        jList2.setModel(defaultListModel3);
        EkranSerwer.panelData.clear();
        Klient.panelData.clear();
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
        Klient.polacz();
        Klient.otrzymujDane("ReviewClients");
        panelData = EkranSerwer.panelData;
        int size = ((panelData.size())/5);
        int counter = 0;
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for(int i=0; i<size; i++){
            defaultListModel.addElement(panelData.get(counter)+". "+panelData.get(counter+1));
            if(size>1) counter+=5;
        }
        jList1.setModel(defaultListModel);
        jList1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jScrollPane1.setViewportView(jList1);
        EkranSerwer.panelData.clear();
        Klient.panelData.clear();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypożyczalnia DVD - Zwróć");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel1.setText("Zwróć DVD");

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setText("Wybierz z listy Klienta, który DVD zwraca");

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        jLabel3.setText("Wybierz z listy DVD, które klient chce zwrócić");


        jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        jLabel4.setText("Podsumowanie");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(1);
        jList1.addListSelectionListener(e -> {
            EkranSerwer.panelData.clear();
            DefaultListModel<String> defaultListModel3 = new DefaultListModel<>();
            jList2.setModel(defaultListModel3);
            if(jList1.getSelectedValue()!=null) {
                jTextArea1.setText(jList1.getSelectedValue());
                DialogMojeWypozyczenia.userID = jList1.getSelectedValue().substring(0, (jList1.getSelectedValue()).indexOf("."));
                userID = DialogMojeWypozyczenia.userID;
                Klient.polacz();
                Klient.otrzymujDane("ReviewMyRents");
                int counter2 = 0;
                int size2 = ((EkranSerwer.panelData.size()) / 9);
                DefaultListModel<String> defaultListModel2 = new DefaultListModel<>();
                for (int i = 0; i < size2; i++) {
                    defaultListModel2.addElement(EkranSerwer.panelData.get(counter2) + ". " + EkranSerwer.panelData.get(counter2 + 1));
                    if (size2 > 1) counter2 += 9;
                }
                jList2.setModel(defaultListModel2);
                jList2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
                jScrollPane2.setViewportView(jList2);
                jTextArea2.setColumns(20);
                jTextArea2.setRows(1);
                jList2.addListSelectionListener(e2 -> {
                    if(jList2.getSelectedValue()!=null) {
                        jTextArea2.setText(jList2.getSelectedValue());
                        filmName = jList2.getSelectedValue().substring((jList2.getSelectedValue()).indexOf(".")+2);
                        rentID = jList2.getSelectedValue().substring(0,(jList2.getSelectedValue()).indexOf("."));
                        rentDate = EkranSerwer.panelData.get(8);
                    }
                });
                jTextArea2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
                jTextArea2.setDisabledTextColor(Color.BLACK);
                jTextArea2.setEnabled(false);
                jScrollPane4.setViewportView(jTextArea2);
            }
        });
        jTextArea1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jTextArea1.setDisabledTextColor(Color.BLACK);
        jTextArea1.setEnabled(false);
        jScrollPane3.setViewportView(jTextArea1);

        jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel5.setText("Klient:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel6.setText("Zwraca:");

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(455, 455, 455)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(79, 79, 79)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                            .addComponent(jLabel6)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel4)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        EkranSerwer.panelData.clear();
        Klient.panelData.clear();
        panelData.clear();
        pack();
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Zatwierdź
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        EkranSerwer.panelData.clear();
        Klient.panelData.clear();
        Klient.polacz();
        Klient.otrzymujDane("ReviewDVDCollection");
        int counter2 = 0;
        int size2 = ((EkranSerwer.panelData.size())/10);
        for(int i=0; i<size2; i++){
            boolean isFound = filmName.equals(EkranSerwer.panelData.get(counter2+1));
            if(isFound) {
                numberOfCopies = Integer.parseInt(EkranSerwer.panelData.get(counter2 + 8));
                dvdID = Integer.parseInt(EkranSerwer.panelData.get(counter2));
                break;
            }
            if(size2>1) counter2+=10;
        }
        Klient.panelData.add(userID);
        Klient.panelData.add(Integer.toString(dvdID));
        Klient.panelData.add(rentID);
        Klient.panelData.add(rentDate);
        Klient.polacz();
        Klient.wysylajDane("ReturnDVD");
        EkranSerwer.updatingID = Integer.parseInt(Integer.toString(dvdID));
        EkranSerwer.updatingItem = Integer.toString(numberOfCopies+1);
        Klient.polacz();
        Klient.zaktualizujStan();
        jList1.removeListSelectionListener(e2-> jList2.removeListSelectionListener(e3-> {}));
        if(EkranSerwer.message==null) EkranSerwer.message = "Wystąpił nieoczekiwany błąd!";
        else if (EkranSerwer.message.equals("Pomyślnie zmieniono liczbę DVD!")) EkranSerwer.message = "Pomyślnie zwrócono DVD!";
        JOptionPane.showMessageDialog(this, EkranSerwer.message, "Informacja", JOptionPane.INFORMATION_MESSAGE);
        clearComponents();
        dispose();
    }
    /**
     * Metoda obsługująca kliknięcie przycisku Anuluj
     * @param evt Przyjęty event podczas kliknięcia przycisku
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        jList1.removeListSelectionListener(e2-> jList2.removeListSelectionListener(e3-> {}));
        clearComponents();
    }
}
