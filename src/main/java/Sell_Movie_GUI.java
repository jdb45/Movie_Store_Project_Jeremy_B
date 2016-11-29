import javax.swing.*;
import java.awt.*;

/**
 * Created by Jeremy on 11/28/16.
 */
public class Sell_Movie_GUI extends JFrame{
    private JPanel rootPanel;
    private JTable sellMovieTable;
    private JButton completeSaleButton;
    private JButton exitButton;


    public Sell_Movie_GUI(View_Movies_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
