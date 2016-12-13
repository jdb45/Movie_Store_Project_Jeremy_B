import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
* This class is used as the home class when the app is started  it has all the buttons to get to all the forms */

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
    public int idInt = 0;
    public String idString = "";

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
        checkIfDonated();

    }

    public void checkIfDonated(){
        //getting the values from the movies that can be donated
        for(int i = 0 ; i < MovieDB.donationList.size() ; i++) {
            int holdInt = (int) MovieDB.donationList.get(i);
            //getting the values form the datamodel
            for(int x = 0 ; x < MovieDB.movieModel.getRowCount() ; x++){
                idString = (String) MovieDB.movieModel.getValueAt(x, 0);

                idInt = Integer.parseInt(idString);
                //if theres match give the user the option to donate it or not
                if (idInt == holdInt) {
                    java.util.Date dateNow = new java.util.Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    String todayDate = formatter.format(dateNow);
                    getTitle = (String) MovieDB.movieModel.getValueAt(x, 1);
                    getPN = (String) MovieDB.movieModel.getValueAt(x, 7);
                    getMovieID = (String) MovieDB.movieModel.getValueAt(x, 0);

                    if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(Home_GUI.this, "The movie with the ID: " +
                            MovieDB.donationList.get(i).toString() + " has been in the inventory for over a year, would you like to donate it?", "Donate", JOptionPane.YES_NO_OPTION))
                    {
                        //setting the sales model and removing the movie from the inventory
                        MovieDB.salesModel.insertRowSales(todayDate, getTitle, 0.0, 0.0, 0.0, getMovieID, getPN);
                        MovieDB.movieModel.deleteRow(x);
                        MovieDB.updateDonation();
                    }
                }
            }
        }
        MovieDB.loadAllTables();
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

    //each of of these action listeners are used to open each new form
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
