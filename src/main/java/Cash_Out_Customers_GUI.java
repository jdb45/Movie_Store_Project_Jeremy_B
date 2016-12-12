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


    public Cash_Out_Customers_GUI(Home_GUI home_gui) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1000, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        cashOut();
        //setting the models for the table
        cashOutCustomersTable.setGridColor(Color.BLACK);
        cashOutCustomersTable.setModel(MovieDB.customerModel);

        // I got some idea about this search method from this website - http://stackoverflow.com/questions/22066387/how-to-search-an-element-in-a-jtable-java
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(cashOutCustomersTable.getModel());

        //creating a row sorter
        cashOutCustomersTable.setRowSorter(rowSorter);
        //getting the document listener for each time a value is entered or deleted
        searchField.getDocument().addDocumentListener(new DocumentListener() {

            //the override method for inserting a value
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            //the override method for removing a value
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
                //getting the selected row
                int currentRow = cashOutCustomersTable.getSelectedRow();

                //checking to see if a customer has been selected
                try {
                    //getting all the values from the customer model
                    selectedCustomerPN = (String) MovieDB.customerModel.getValueAt(currentRow, 0).toString();
                    String currentMoney = (String) MovieDB.customerModel.getValueAt(currentRow, 3).toString();
                    String pickedUpMoney = (String) MovieDB.customerModel.getValueAt(currentRow, 4).toString();
                    String totalMoney = (String) MovieDB.customerModel.getValueAt(currentRow, 5).toString();
                    //updating the money for the customer
                    double updateCurrentMoney = Double.parseDouble(currentMoney);
                    updatePickedUpMoneyDouble = Math.round(updatePickedUpMoneyDouble * 100.0) / 100.0;
                    updatePickedUpMoneyDouble = Double.parseDouble(pickedUpMoney);
                    updatePickedUpMoneyDouble += updateCurrentMoney;
                    updateTotalMoneyDouble = Double.parseDouble(totalMoney);
                    updateTotalMoneyDouble = Math.round(updateTotalMoneyDouble * 100.0) / 100.0;

                    //making sure the user can't cash out a customer if they have no money to be cashed out
                     if (currentMoney.equals("0.0")) {
                        JOptionPane.showMessageDialog(rootPane, "You can't Cash-Out a customer that does not have money owed to them");
                    }
                    //asking the user if they are sure they want to cash out the customer
                    else if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Cash_Out_Customers_GUI.this, "Are you sure you want to cash out this customer? ", "Cash-Out", JOptionPane.OK_CANCEL_OPTION)) {
                        boolean updateMoney = MovieDB.updateCustomerMoney();

                        if (updateMoney) {
                            JOptionPane.showMessageDialog(rootPane, "Cash-Out customer successful");
                            MovieDB.loadAllTables();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Error updating customer money");
                        }
                    }
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(rootPane, "Please choose a customer to cash out");
                }
            }
        });
    }

}
