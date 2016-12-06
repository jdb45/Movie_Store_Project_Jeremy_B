import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        exit();

        sellMovieTable.setGridColor(Color.BLACK);
        //sellMovieTable.setModel(View_Movies_GUI.hold);
    }


    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Sell_Movie_GUI.this, "Are you sure you want to exit?", "Exit", JOptionPane.OK_CANCEL_OPTION)) {
                    Sell_Movie_GUI.this.dispose();
                }
            }
        });
    }
}
