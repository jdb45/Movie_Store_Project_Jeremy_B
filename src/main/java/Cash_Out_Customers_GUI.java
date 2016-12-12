import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cash_Out_Customers_GUI extends JFrame{
    private JPanel rootPanel;
    private JTable cashOutCustomersTable;
    private JTextField searchField;
    private JButton searchButton;
    private JButton cashOutButton;
    private JButton exitButton;
    public static String selectedCustomerPN;
    public static double updatePickedUpMoneyDouble;
    public static double updateTotalMoneyDouble;




    //TODO add metadata
    //TODO Cash out button working!
    public Cash_Out_Customers_GUI(Home_GUI home_gui) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1000, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        cashOut();

        cashOutCustomersTable.setGridColor(Color.BLACK);
        cashOutCustomersTable.setModel(MovieDB.customerModel);

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(cashOutCustomersTable.getModel());

        cashOutCustomersTable.setRowSorter(rowSorter);

        searchField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

        });

    }


    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Cash_Out_Customers_GUI.this.dispose();
            }
        });
    }

    public void cashOut(){
        cashOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int currentRow = cashOutCustomersTable.getSelectedRow();
                //checking to see if a customer has been selected

                selectedCustomerPN = (String) MovieDB.customerModel.getValueAt(currentRow, 0).toString();
                String pickedUpMoney = (String) MovieDB.customerModel.getValueAt(currentRow, 3).toString();
                String checkForMoney = (String) MovieDB.customerModel.getValueAt(currentRow, 4).toString();
                String totalMoney = (String) MovieDB.customerModel.getValueAt(currentRow, 5).toString();


                if (currentRow == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Please choose a customer to cash out");
                }

                else if (checkForMoney.equals("0.0")){
                    JOptionPane.showMessageDialog(rootPane, "You can't Cash-Out a customer that does not have money owed to them");
                }

                else if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Cash_Out_Customers_GUI.this, "Are you sure you want to cash out this customer? This means they are picking up their money", "Cash-Up", JOptionPane.OK_CANCEL_OPTION)) {
                    updatePickedUpMoneyDouble = Double.parseDouble(pickedUpMoney);
                    updateTotalMoneyDouble = Double.parseDouble(totalMoney);
                    boolean updateMoney = MovieDB.updateCustomerMoney();

                    if (updateMoney) {
                        JOptionPane.showMessageDialog(rootPane, "Cash-Out customer successful");
                        MovieDB.loadAllTables();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Error updating customer money");
                    }
                }
            }
        });
    }

}
