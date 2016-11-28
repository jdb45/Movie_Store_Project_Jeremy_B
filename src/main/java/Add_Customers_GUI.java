import javax.swing.*;
import java.awt.*;

/**
 * Created by Jeremy on 11/28/16.
 */
public class Add_Customers_GUI extends JFrame{
    private JPanel rootPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton addCustomerButton;
    private JButton exitButton;


    public Add_Customers_GUI(Home_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
