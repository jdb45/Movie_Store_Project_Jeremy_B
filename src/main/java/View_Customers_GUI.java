import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jeremy on 11/28/16.
 */
public class View_Customers_GUI extends JFrame {
    private JPanel rootPanel;
    private JTable viewCustomerTable;
    private JTextField searchCustomerInput;
    private JButton searchButton;
    private JButton exitButton;
    public static boolean customerEdit;
    //TODO WHERE for search


    public View_Customers_GUI(Home_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        customerEdit = true;

        viewCustomerTable.setGridColor(Color.BLACK);
        viewCustomerTable.setModel(MovieDB.customerModel);
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
}
