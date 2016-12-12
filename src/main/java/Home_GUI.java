import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

//TODO make method with donation code
public class Home_GUI extends JFrame {
    private JPanel rootPanel;
    private JButton addMovieButton;
    private JButton addCustomerButton;
    private JButton viewMoviesButton;
    private JButton viewCustomersButton;
    private JButton salesButton;
    private JButton cashOutCustomerButton;
    private JButton quitButton;
    public static String getTitle;
    public static String getPN;
    public static String getMovieID;

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
        removeCustomers();
        sales();
        MovieDB.checkDonation();
        int yesInt = 0;
        String yes = "";

        for(int i = 0 ; i < MovieDB.donationList.size() ; i++) {
            int hey = (int) MovieDB.donationList.get(i);

            for(int x = 0 ; x < MovieDB.movieModel.getRowCount() ; x++){
                yes = (String) MovieDB.movieModel.getValueAt(x, 0);

                yesInt = Integer.parseInt(yes);
                if (yesInt == hey) {
                    java.util.Date dateNow = new java.util.Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    String todayDate = formatter.format(dateNow);
                    getTitle = (String) MovieDB.movieModel.getValueAt(x, 1);
                    getPN = (String) MovieDB.movieModel.getValueAt(x, 7);
                    getMovieID = (String) MovieDB.movieModel.getValueAt(x, 0);

                    if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(Home_GUI.this, "The movie with the ID: " +
                            MovieDB.donationList.get(i).toString() + " has been in the inventory for over a year, would you like to donate it?", "Donate", JOptionPane.YES_NO_OPTION))
                    {
                        MovieDB.salesModel.insertRowSales(todayDate, getTitle, 0.0, 0.0, 0.0, getMovieID, getPN);
                        MovieDB.movieModel.deleteRow(x);
                        MovieDB.updateDonation();
                    }
                }
            }
            MovieDB.loadAllTables();
        }

    }


    public void quit() {
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Home_GUI.this, "Are you sure you want to quit?", "Quit", JOptionPane.OK_CANCEL_OPTION)) {
                    MovieDB.shutdown();
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

    public void sales(){
        salesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Sales_GUI salesGUI = new Sales_GUI(Home_GUI.this);
            }
        });
    }


    public void removeCustomers() {
        cashOutCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Cash_Out_Customers_GUI removeCustomers = new Cash_Out_Customers_GUI(Home_GUI.this);
            }
        });
    }
}
