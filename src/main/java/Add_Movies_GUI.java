import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jeremy on 11/28/16.
 */
public class Add_Movies_GUI extends JFrame{
    private JPanel rootPanel;
    private JTextField addMovie;
    private JTextField addMovieYear;
    private JTextField addMoviePrice;
    private JTextField addDate;
    private JComboBox movieFormat;
    private JComboBox customerCode;
    private JTextField upcBarcode;
    private JButton addMovieButton;
    private JButton exitButton;

    public Add_Movies_GUI(Home_GUI home_gui) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieFormat.addItem("DVD");
        movieFormat.addItem("Blu-Ray");
        movieFormat.addItem("VHS");

        exit();
    }

    public void exit() {
        exitButton.addActionListener(new ActionListener() {
         @Override
          public void actionPerformed(ActionEvent e) {
             if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Add_Movies_GUI.this, "Are you sure you want to exit? Any un-added data will be removed", "Exit", JOptionPane.OK_CANCEL_OPTION)) {
                 Add_Movies_GUI.this.dispose();
             }
         }
        });
    }



}
