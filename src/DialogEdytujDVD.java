/** @file DialogEdytujDVD.java
 * @brief plik zawierający kod dialog boxa pozwalajacego na edycję wybranej DVD
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.5.0
 */
import javax.swing.*;
import java.awt.*;

public class DialogEdytujDVD extends javax.swing.JDialog {
    private static final JComboBox<Object> jComboBox1 = new javax.swing.JComboBox<>();
    private static final JTextField jTextField1 = new javax.swing.JTextField();
    private static final JTextField jTextField2 = new javax.swing.JTextField();
    private static final JTextField jTextField3 = new javax.swing.JTextField();
    private static final JTextField jTextField4 = new javax.swing.JTextField();
    private static final JTextField jTextField5 = new javax.swing.JTextField();
    private static final JTextField jTextField6 = new javax.swing.JTextField();
    private static final JTextField jTextField7 = new javax.swing.JTextField();
    private static final JSpinner jSpinner1 = new javax.swing.JSpinner();
    private static final JSpinner jSpinner2 = new javax.swing.JSpinner();

    DialogEdytujDVD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Klient.polacz();
        Klient.otrzymujDane("ReviewDVDCollection");
        Klient.zakonczPolaczenie();
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                jComboBox1.setModel(new DefaultComboBoxModel<>());
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField5.setText("");
                jTextField6.setText("");
                jTextField7.setText("");
                Klient.panelData.clear();
                EkranSerwer.panelData.clear();
                dispose();
            }

        });
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
    private static void clearComponents(){
        jComboBox1.setModel(new DefaultComboBoxModel<>());
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        Klient.panelData.clear();
        EkranSerwer.panelData.clear();
    }
    private void initComponents() {
        JPanel jPanel1 = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JButton jButton1 = new javax.swing.JButton();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel6 = new javax.swing.JLabel();
        JLabel jLabel7 = new javax.swing.JLabel();
        JLabel jLabel8 = new javax.swing.JLabel();
        JLabel jLabel9 = new javax.swing.JLabel();
        JLabel jLabel10 = new javax.swing.JLabel();
        JLabel jLabel11 = new javax.swing.JLabel();
        JLabel jLabel12 = new javax.swing.JLabel();
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setTitle("Wypożyczalnia DVD - Edytuj DVD");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 800));

        jLabel1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel1.setText("Edytuj DVD");

        jComboBox1.setPreferredSize(new java.awt.Dimension(250, 25));
        jComboBox1.setEditable(false);
        jComboBox1.setBackground(Color.WHITE);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>());
        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/10);
        for(int i=0; i<size; i++){
            jComboBox1.addItem(EkranSerwer.panelData.get(counter)+". "+EkranSerwer.panelData.get(counter+1));
            if(size>1) counter+=10;
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel2.setText("Wybierz DVD z listy");

        jLabel3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel3.setText("Edytuj poszczególne dane");

        jButton1.setBackground(null);
        jButton1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setText("Przepisz dane");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(100, 25));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jButton1.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if(model.isRollover()){
                jButton1.setForeground(Color.lightGray);
            }
            else{
                jButton1.setForeground(null);
            }
        });

        jTextField1.setMinimumSize(new java.awt.Dimension(64, 25));
        jTextField1.setPreferredSize(new java.awt.Dimension(71, 25));

        jLabel4.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel4.setText("Nazwa filmu");

        jLabel5.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel5.setText("Reżyseria");

        jTextField2.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel6.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel6.setText("Gatunek");

        jTextField3.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel7.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel7.setText("Kraj produkcji");

        jTextField4.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel8.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel8.setText("Rok premiery");

        jTextField5.setPreferredSize(new java.awt.Dimension(71, 25));

        jLabel9.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel9.setText("Język filmu");

        jTextField6.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel10.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel10.setText("Długość filmu");

        jTextField7.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel11.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel11.setText("Ilość egzemplarzy");

        jSpinner1.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel12.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        jLabel12.setText("Opłata za dobę");

        jSpinner2.setPreferredSize(new java.awt.Dimension(64, 25));

        jButton2.setBackground(new java.awt.Color(89, 168, 105));
        jButton2.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton2.setText("Zatwierdź zmiany");
        jButton2.setBorder(null);
        jButton2.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        jButton3.setText("Anuluj");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton3.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(31, 199, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(252, 252, 252)))
                .addGap(40, 40, 40))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(155, 155, 155))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        int counter = 0;
        int size = ((EkranSerwer.panelData.size())/10);
        for(int i=0; i<size; i++){
            String dvdID = String.valueOf(jComboBox1.getSelectedItem()).substring(0,(String.valueOf(jComboBox1.getSelectedItem()).indexOf(".")));
            boolean isfound = dvdID.equals(EkranSerwer.panelData.get(counter));
            if(isfound){
                jTextField1.setText(EkranSerwer.panelData.get(counter+1));
                jTextField2.setText(EkranSerwer.panelData.get(counter+2));
                jTextField3.setText(EkranSerwer.panelData.get(counter+3));
                jTextField4.setText(EkranSerwer.panelData.get(counter+4));
                jTextField5.setText(EkranSerwer.panelData.get(counter+5));
                jTextField6.setText(EkranSerwer.panelData.get(counter+6));
                jTextField7.setText(EkranSerwer.panelData.get(counter+7));
                jSpinner1.setValue(Integer.parseInt(EkranSerwer.panelData.get(counter+8)));
                jSpinner2.setValue(Integer.parseInt(EkranSerwer.panelData.get(counter+9)));
                break;
            }
            if(size>1) counter+=10;
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if(jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty()  || jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty() || jTextField6.getText().isEmpty() || jTextField7.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Nie wprowadzono jednej z istotnych danych!","Błąd",JOptionPane.ERROR_MESSAGE);
        }
        else {
            if (Integer.parseInt(jSpinner1.getValue().toString()) < 0) {
                JOptionPane.showMessageDialog(this, "Ilość egzemplarzy nie może być mniejsza od 0!", "Błąd", JOptionPane.ERROR_MESSAGE);
                jSpinner1.setValue(0);
            } else if (Integer.parseInt(jSpinner2.getValue().toString()) < 10) {
                JOptionPane.showMessageDialog(this, "Opłata za dobę nie może być mniejsza od 10 zł!", "Błąd", JOptionPane.ERROR_MESSAGE);
                jSpinner1.setValue(10);
            } else {
                String dvdID = String.valueOf(jComboBox1.getSelectedItem()).substring(0,(String.valueOf(jComboBox1.getSelectedItem()).indexOf(".")));
                Klient.panelData.add(jTextField1.getText());
                Klient.panelData.add(jTextField2.getText());
                Klient.panelData.add(jTextField3.getText());
                Klient.panelData.add(jTextField4.getText());
                Klient.panelData.add(jTextField5.getText());
                Klient.panelData.add(jTextField6.getText());
                Klient.panelData.add(jTextField7.getText());
                Klient.panelData.add(jSpinner1.getValue().toString());
                Klient.panelData.add(jSpinner2.getValue().toString());
                Klient.panelData.add(dvdID);
                try {
                    Klient.polacz();
                    Klient.wysylajDane("EditDVD");
                    Klient.zakonczPolaczenie();
                    String title;
                    if (EkranSerwer.message == null || EkranSerwer.message.equals("")) {
                        EkranSerwer.message = "Wystąpił nieoczekiwany błąd!";
                        title = "Błąd";
                    } else title = "Sukces";
                    JOptionPane.showMessageDialog(this, EkranSerwer.message, title, JOptionPane.INFORMATION_MESSAGE);
                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jTextField7.setText("");
                    jSpinner1.setValue(0);
                    jSpinner1.setValue(10);
                    clearComponents();
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex, "Informacja", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        clearComponents();
        dispose();
    }
}
