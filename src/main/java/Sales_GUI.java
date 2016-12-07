import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sales_GUI extends JFrame {

    private JPanel rootPanel;
    private JTable viewSalesTable;
    private JTextField textField1;
    private JButton cashOutSaleButton;
    private JButton exitButton;

    public Sales_GUI(Home_GUI home_gui) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
    }

    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sales_GUI.this.dispose();

            }
        });
    }
}
