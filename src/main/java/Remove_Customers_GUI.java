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
    }


    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Remove_Customers_GUI.this, "Are you sure you want to exit?", "Exit", JOptionPane.OK_CANCEL_OPTION)) {
                    Remove_Customers_GUI.this.dispose();
                }
            }
        });
    }
}
