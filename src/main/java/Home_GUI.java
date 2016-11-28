import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jeremy on 11/28/16.
 */
public class Home_GUI extends JFrame {
    private JPanel rootPanel;
    private JButton addMovieButton;
    private JButton addCustomerButton;
    private JButton viewMoviesButton;
    private JButton viewCustomersButton;
    private JButton removeMovieButton;
    private JButton removeCustomerButton;
    private JButton quitButton;

    public Home_GUI() {
        super("Movie Store");
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));
        pack();
        setVisible(true);
        quit();
        addCustomer();
        addMovie();
        viewMovies();
        viewCustomers();
    }


    public void quit() {
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Home_GUI.this, "Are you sure you want to quit?", "Quit", JOptionPane.OK_CANCEL_OPTION)) {
                    System.exit(0);
                }
            }
        });

    }


    public void addCustomer(){
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Add_Customers_GUI newCustomer = new Add_Customers_GUI(Home_GUI.this);

            }
        });
    }

    public void addMovie(){
        addMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Add_Movies_GUI newMovie = new Add_Movies_GUI(Home_GUI.this);
            }
        });
    }

    public void viewMovies(){
        viewMoviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                View_Movies_GUI viewMovie = new View_Movies_GUI(Home_GUI.this);
            }
        });
    }

    public void viewCustomers(){
        viewCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                View_Customers_GUI viewCustomers = new View_Customers_GUI(Home_GUI.this);
            }
        });
    }
}
