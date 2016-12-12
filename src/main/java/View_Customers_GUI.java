import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View_Customers_GUI extends JFrame {
    private JPanel rootPanel;
    private JTable viewCustomerTable;
    private JTextField searchCustomerInput;
    private JButton exitButton;
    private JButton deleteButton;
    public static boolean customerEdit;

    public View_Customers_GUI(Home_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1000, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        deleteCustomer();
        customerEdit = true;

        viewCustomerTable.setGridColor(Color.BLACK);
        viewCustomerTable.setModel(MovieDB.customerModel);
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(viewCustomerTable.getModel());

        viewCustomerTable.setRowSorter(rowSorter);

        searchCustomerInput.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchCustomerInput.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchCustomerInput.getText();

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
                customerEdit = false;
                View_Customers_GUI.this.dispose();
            }
        });
    }

    public void deleteCustomer() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = viewCustomerTable.getSelectedRow();

                //checking to see if a customer has been selected
                if (currentRow == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Please choose a customer to delete");
                } else if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(View_Customers_GUI.this, "Are you sure you want to delete the customer?", "Delete", JOptionPane.OK_CANCEL_OPTION)) {
                    boolean deleted = MovieDB.customerModel.deleteRow(currentRow);
                    if (deleted) {
                        MovieDB.loadAllTables();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Error deleting customer");
                    }
                }
            }
        });
    }
}
