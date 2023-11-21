/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pos.pro;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;




/**
 *
 * @author Liamani
 */
public class GRN extends javax.swing.JPanel {

    /**
     * Creates new form GRN
     */
    public GRN() {
        initComponents();
        data_load();
        cart_total();
    }
    
    
    public void data_load() {
        
        //load supplier

    try {
        Statement s = db.mycon().createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM suppliers");
        Vector vCustomers = new Vector();

        while (rs.next()) {
            vCustomers.add(rs.getString("supplier_lname"));
        }

        DefaultComboBoxModel comCustomers = new DefaultComboBoxModel(vCustomers);
        combo_customer.setModel(comCustomers);
    } catch (SQLException e) {
        System.out.println(e);
    }

    try {
        Statement s = db.mycon().createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM products");
        Vector vProducts = new Vector();

        while (rs.next()) {
            vProducts.add(rs.getString("product_name"));
        }

        DefaultComboBoxModel comProducts = new DefaultComboBoxModel(vProducts);
        combo_product.setModel(comProducts);
    } catch (SQLException e) {
        System.out.println(e);
    }

    try {
        Statement s = db.mycon().createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM extra WHERE exid = 1");

        if (rs.next()) {
            inid.setText(rs.getString("val"));
        }
    } catch (SQLException e) {
        System.out.println(e);
    }

    try {
        String inidText = inid.getText();
        if (!inidText.isEmpty()) {
            int i = Integer.parseInt(inidText);
            i++;
            inid.setText(String.valueOf(i));
        } else {
            // Handle the case where inidText is empty
            System.out.println("inidText is empty");
        }
    } catch (NumberFormatException e) {
        // Handle the case where inidText is not a valid integer
        System.out.println(e);
    }
}
    
        public void cart_total(){
        int numofrow = jTable1.getRowCount();
        double total = 0;
        for(int i = 0 ; i < numofrow ; i++){
            double value = Double.parseDouble(jTable1.getValueAt(i, 7).toString());
            total += value;
        }
        
        total_price.setText(Double.toString(total));  
    }

        public void discount(){
               
            try{
                double total_bill = Double.valueOf(total_price.getText());
                double promo = 0.0;
                double percent = Double.valueOf(percentage.getText());
                promo = total_bill * (percent/100);
            
                promotion.setText(String.valueOf(promo));
        }
        
            catch(Exception e){
                System.out.println(e);
        }
            totalcal();
        
        }

        
        public void totalcal(){
            double bill = Double.valueOf(total_price.getText());
            double per = Double.valueOf(promotion.getText());
            double bal = bill - per;
            balance.setText(String.valueOf(bal));
        }
        
        
        
        
        
    public void printGoodsReceivedNote() {
        try {
            AutoPrintBill autoPrintBill = new AutoPrintBill();
            StringBuilder grnContent = new StringBuilder();

            // Get today's date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Get total qty
            String Totqty = total_qty.getText();
            // Supplier information
            grnContent.append("مستلم البضائع: ").append(combo_customer.getSelectedItem().toString()).append("\n");
            grnContent.append("تاريخ الاستلام: ").append(currentDate.format(dateFormatter)).append("\n");
            grnContent.append("----------------------------------------------------------------\n");

            // Table header
            grnContent.append(" المنتج \t|\t كودبار \t|\t تاريخ إنتهاء الصلاحية |\t الكمية المستلمة|\t سعر الشراء |\t سعر البيع |\t السعر الإجمالي \n");
            grnContent.append("----------------------------------------------------------------\n");

            // Populate grnContent from jTable1 or relevant data source
            DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String productName = df.getValueAt(i, 1).toString();
                String codebar = df.getValueAt(i, 2).toString();
                String date = df.getValueAt(i, 3).toString();
                String receivedQty = df.getValueAt(i, 4).toString();
                String buy_price = df.getValueAt(i, 5).toString();
                String sell_price = df.getValueAt(i, 6).toString();
                String total_price = df.getValueAt(i, 7).toString();

                grnContent.append(String.format("%s\t|\t%s\t|\t%s|\t%s|\t%s|\t%s|\t%s\n", makeBold(productName), codebar, date,receivedQty, buy_price,sell_price,total_price));
            }

            // GRN summary
            grnContent.append("----------------------------------------------------------------\n");
            grnContent.append("مجموع الكمية المستلمة:").append("\t|\t").append(Totqty).append("\n");
            grnContent.append("====================================\n");
            grnContent.append("شكراً لتعاونكم...!\n");
            grnContent.append("----------------------------------------------------------------\n");
            grnContent.append("برمجة بواسطة ").append("ليماني المهدي").append("\n");

            //autoPrintBill.setBillContent(grnContent.toString());
            //autoPrintBill.printBill();
            saveToFile("GoodsReceivedNote.txt", grnContent.toString());
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace();
        }
    }

    // Utility method to make text bold
    private String makeBold(String text) {
        return "<b>" + text + "</b>";
    }
    
    
    
    private void saveToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("Content saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        inid = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        combo_customer = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        combo_product = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pro_qty = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        brcode = new javax.swing.JTextField();
        buyp = new javax.swing.JTextField();
        sellp = new javax.swing.JTextField();
        ssid = new javax.swing.JLabel();
        dateexp = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        total_price = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        balance = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        promotion = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        percentage = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        total_qty = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        Save = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("مذكرة رقم");

        jLabel18.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText(":");

        inid.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        inid.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        inid.setText("01");

        jLabel17.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("لقب المورد");

        jLabel20.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText(":");

        combo_customer.setFont(new java.awt.Font("Janna LT", 0, 14)); // NOI18N
        combo_customer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "اختار", " " }));
        combo_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_customerActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("سلعة");

        jLabel9.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText(":");

        combo_product.setEditable(true);
        combo_product.setFont(new java.awt.Font("Janna LT", 0, 14)); // NOI18N
        combo_product.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "اختار", "" }));
        combo_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_productActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("كمية");

        jLabel10.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText(":");

        pro_qty.setFont(new java.awt.Font("Janna LT", 0, 14)); // NOI18N
        pro_qty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pro_qty.setText("0");
        pro_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pro_qtyKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("سعر البيع");

        jLabel11.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText(":");

        jLabel5.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("سعر الشراء");

        jLabel19.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText(":");

        jLabel6.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("تاريخ إنتهاء الصلاحية");

        jLabel12.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText(":");

        jButton1.setBackground(new java.awt.Color(248, 248, 242));
        jButton1.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(40, 42, 54));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pos/pro/img/plus_1828817 (2).png"))); // NOI18N
        jButton1.setText("أضف إلى الجدول");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("رمز السلعة");

        jLabel25.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText(":");

        brcode.setFont(new java.awt.Font("Janna LT", 0, 14)); // NOI18N
        brcode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        brcode.setText("000000000000000");
        brcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brcodeActionPerformed(evt);
            }
        });

        buyp.setFont(new java.awt.Font("Janna LT", 0, 14)); // NOI18N
        buyp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        buyp.setText("0");

        sellp.setFont(new java.awt.Font("Janna LT", 0, 14)); // NOI18N
        sellp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sellp.setText("0");

        ssid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ssid.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ssid.setText("0");

        dateexp.setFont(new java.awt.Font("Janna LT", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(130, 130, 130)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(buyp, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(sellp, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(98, 98, 98)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateexp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pro_qty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(125, 125, 125)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(95, 95, 95))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ssid, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(combo_product, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_customer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(brcode, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inid, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combo_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ssid)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(combo_product, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buyp, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateexp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(brcode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(pro_qty, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sellp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addContainerGap())
        );

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GRNID", "السلعة", "Code Bar", "تاريخ", "الكمية", "سعر الشراء", "سعر البيع", "السعر الكلي"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("المجموع");

        total_price.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        total_price.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total_price.setText("00.00");
        total_price.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel13.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText(":");

        jLabel14.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("المجموع الصافي");

        jLabel15.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText(":");

        balance.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        balance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        balance.setText("00.00");
        balance.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("تخفيض");

        jLabel23.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText(":");

        promotion.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        promotion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        promotion.setText("00.00");
        promotion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel24.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("%");

        percentage.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        percentage.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        percentage.setText("0");
        percentage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                percentageActionPerformed(evt);
            }
        });
        percentage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                percentageKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(total_price, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(promotion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(balance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(percentage, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel16))
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(total_price)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(promotion)
                    .addComponent(jLabel23)
                    .addComponent(percentage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(balance)
                    .addComponent(jLabel15))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("الكمية الإجمالية");

        jLabel22.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText(":");

        total_qty.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        total_qty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total_qty.setText("00");

        jButton3.setBackground(new java.awt.Color(255, 121, 198));
        jButton3.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(40, 42, 54));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pos/pro/img/s.png"))); // NOI18N
        jButton3.setText("حذف ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(189, 147, 249));
        jButton4.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(40, 42, 54));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pos/pro/img/two_10093458 (1).png"))); // NOI18N
        jButton4.setText("حذف الكل");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        Save.setBackground(new java.awt.Color(154, 252, 179));
        Save.setFont(new java.awt.Font("Janna LT", 1, 14)); // NOI18N
        Save.setForeground(new java.awt.Color(40, 42, 54));
        Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pos/pro/img/Untitled-1.png"))); // NOI18N
        Save.setText("حفظ");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(248, 248, 242));
        jButton2.setFont(new java.awt.Font("Janna LT", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(40, 42, 54));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pos/pro/img/printer_1497542 (1).png"))); // NOI18N
        jButton2.setText("إطبع");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(total_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(total_qty))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1524, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void combo_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_customerActionPerformed
        // customer

         String name = combo_customer.getSelectedItem().toString();
        
        try{
            Statement s = db.mycon().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM suppliers WHERE supplier_lname = '"+name+"'");
            if(rs.next()){
                ssid.setText(rs.getString("sid"));        
            }
        }
        catch(Exception e){
            
        }
       

    }//GEN-LAST:event_combo_customerActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // delete 
        
        try{
            DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
            int rw = jTable1.getSelectedRow();
            
            tb.removeRow(rw);
            
        }
        
        catch(Exception e){
            System.out.println(e);
        }
        cart_total();
        discount();
        totalcal();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //delete all
        
        DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
        
        tb.setRowCount(0);
        cart_total();
        discount();
        totalcal();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
        // add to GRN
        double qty = Double.parseDouble(pro_qty.getText());
        double price = Double.parseDouble(buyp.getText());
        double total = qty * price;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String mfdate = sdf.format(dateexp.getDate());

        DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
        Vector v = new Vector();

        v.add(inid.getText());
        v.add(combo_product.getSelectedItem().toString());
        v.add(brcode.getText());
        v.add(mfdate);
        v.add(pro_qty.getText());
        v.add(buyp.getText());
        v.add(sellp.getText());
        v.add(total);

        tb.addRow(v);
        cart_total();
        discount();
        totalcal();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "أدخل رقم صحيح دون أحرف أو رموز", "خطأ", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void brcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brcodeActionPerformed

    private void pro_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pro_qtyKeyReleased
        // qty
    }//GEN-LAST:event_pro_qtyKeyReleased

    private void combo_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_productActionPerformed
        // Load product information
        
        String name = combo_product.getSelectedItem().toString();
        
        try{
            Statement s = db.mycon().createStatement();
            ResultSet rs = s.executeQuery("SELECT bar_code, buy_price, sell_price, qty FROM products WHERE product_name = '"+name+"'");
            if(rs.next()){
                sellp.setText(rs.getString("sell_price"));
                buyp.setText(rs.getString("buy_price"));
                pro_qty.setText(rs.getString("qty"));
                brcode.setText(rs.getString("bar_code"));        
            }
        }
        catch(Exception e){
            
        }
        
        
        
        
    }//GEN-LAST:event_combo_productActionPerformed

    private void percentageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_percentageActionPerformed
       
        
    }//GEN-LAST:event_percentageActionPerformed

    private void percentageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_percentageKeyReleased
       // percentage
     discount();
        
    }//GEN-LAST:event_percentageKeyReleased

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        // save
        // send to database
        
        
        try{
            DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
            int rc = tb.getRowCount();
            for(int i = 0 ; i < rc; i++){
                String inid = tb.getValueAt(i, 0).toString();
                String P_name = tb.getValueAt(i, 1).toString();
                String b_code = tb.getValueAt(i, 2).toString();
                String date = tb.getValueAt(i, 3).toString();
                String qty = tb.getValueAt(i, 4).toString();
                String buy_price = tb.getValueAt(i, 5).toString();
                String sell_price = tb.getValueAt(i, 6).toString();
                String sub_price = total_price.getText();
                String discount = promotion.getText();
                String net = balance.getText();
                String sid = ssid.getText();
                
                Statement s = db.mycon().createStatement();
                s.executeUpdate("INSERT INTO grn (grn_no, sid, barcode, prod_name, qty, buy_price, sell_price, exp_date, sub_total, discount, net_total) VALUES ('"+inid+"','"+sid+"','"+b_code+"','"+P_name+"','"+qty+"','"+buy_price+"','"+sell_price+"','"+date+"','"+sub_price+"','"+discount+"','"+net+"') ");
                
            }
            JOptionPane.showMessageDialog(null, "تم الحفظ");
            
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        
        
    }//GEN-LAST:event_SaveActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        //print
        
        printGoodsReceivedNote();
        
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Save;
    private javax.swing.JLabel balance;
    private javax.swing.JTextField brcode;
    private javax.swing.JTextField buyp;
    private javax.swing.JComboBox<String> combo_customer;
    private javax.swing.JComboBox<String> combo_product;
    private com.toedter.calendar.JDateChooser dateexp;
    private javax.swing.JLabel inid;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField percentage;
    private javax.swing.JTextField pro_qty;
    private javax.swing.JLabel promotion;
    private javax.swing.JTextField sellp;
    private javax.swing.JLabel ssid;
    private javax.swing.JLabel total_price;
    private javax.swing.JLabel total_qty;
    // End of variables declaration//GEN-END:variables
}
