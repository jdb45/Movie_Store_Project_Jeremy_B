import javax.swing.*;
import java.awt.*;

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
    }
}
