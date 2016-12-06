import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Remove_Customers_GUI extends JFrame{
    private JPanel rootPanel;
    private JTable removeCustomersTable;
    private JTextField searchField;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton exitButton;


    public Remove_Customers_GUI(Home_GUI home_gui) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        deleteCustomer();

        removeCustomersTable.setGridColor(Color.BLACK);
        removeCustomersTable.setModel(MovieDB.customerModel);
    }


    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Remove_Customers_GUI.this.dispose();
            }
        });
    }

    public void deleteCustomer(){
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = removeCustomersTable.getSelectedRow();

                //checking to see if a cube solver has been selected
                if (currentRow == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Please choose a customer to delete");
                }

                else if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Remove_Customers_GUI.this, "Are you sure you want to delete the customer?", "Delete", JOptionPane.OK_CANCEL_OPTION)) {
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
