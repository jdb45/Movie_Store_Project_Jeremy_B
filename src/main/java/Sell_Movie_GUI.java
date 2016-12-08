import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jeremy on 11/28/16.
 */
public class Sell_Movie_GUI extends JFrame{
    private JPanel rootPanel;
    private JButton completeSaleButton;
    private JButton exitButton;
    private JList<Movie> saleList;

    DefaultListModel<Movie> movieListModel = new DefaultListModel<>();
    Movie newMovie = null;

    public Sell_Movie_GUI(View_Movies_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        
        saleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        saleList.setModel(movieListModel);

        String ID = (String) View_Movies_GUI.list.get(0);
        String movieTitle = (String) View_Movies_GUI.list.get(1);
        String movieYear = (String) View_Movies_GUI.list.get(2);
        //double moviePrice = (double) View_Movies_GUI.list.get(3);
        String moviePrice = (String) View_Movies_GUI.list.get(3);
        String movieDate = (String) View_Movies_GUI.list.get(4);
        String movieFormat = (String) View_Movies_GUI.list.get(5);
        String upcBarcode = (String) View_Movies_GUI.list.get(6);
        String customerPhoneNumber = (String) View_Movies_GUI.list.get(7);

        newMovie = new Movie(ID, movieTitle, movieYear, moviePrice, movieDate, movieFormat, upcBarcode, customerPhoneNumber);
        movieListModel.addElement(newMovie);


    }


    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Sell_Movie_GUI.this, "Are you sure you want to exit? If you have not completed the sale the sale will be lost", "Exit", JOptionPane.OK_CANCEL_OPTION)) {
                    Sell_Movie_GUI.this.dispose();


                }
            }
        });
    }
}
