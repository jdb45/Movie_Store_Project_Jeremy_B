import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jeremy on 11/28/16.
 */
public class View_Movies_GUI extends JFrame {
    private JPanel rootPanel;
    private JTable movieTable;
    private JTextField searchByUser;
    private JButton searchButton;
    private JButton exitButton;
    private JButton sellMovieButton;


    public View_Movies_GUI(Home_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        removeMovie();
    }

    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(View_Movies_GUI.this, "Are you sure you want to exit?", "Exit", JOptionPane.OK_CANCEL_OPTION)) {
                    View_Movies_GUI.this.dispose();
                }
            }
        });
    }

    public void removeMovie() {
        sellMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Sell_Movie_GUI newRemove = new Sell_Movie_GUI(View_Movies_GUI.this);
            }
        });
    }
}
