import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sell_Movie_GUI extends JFrame{
    private JPanel rootPanel;
    private JButton completeSaleButton;
    private JButton exitButton;
    private JTextArea saleTextArea;
    public static String customerPhoneNumber;
    public static double currentMoneyDouble;
    public static double pickedUpMoneyDouble;
    public static double totalMoneyDouble;

    //TODO set percent customer pay!

    DefaultListModel<Movie> movieListModel = new DefaultListModel<>();
    Movie newMovie = null;

    public Sell_Movie_GUI(View_Movies_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();

        Date dateNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String todayDate = formatter.format(dateNow);

        String ID = (String) View_Movies_GUI.list.get(0);
        String movieTitle = (String) View_Movies_GUI.list.get(1);
        String movieYear = (String) View_Movies_GUI.list.get(2);
        String moviePriceHold = (String) View_Movies_GUI.list.get(3);
        String movieDate = (String) View_Movies_GUI.list.get(4);
        String movieFormat = (String) View_Movies_GUI.list.get(5);
        String upcBarcode = (String) View_Movies_GUI.list.get(6);
        customerPhoneNumber = (String) View_Movies_GUI.list.get(7);
        String bargainCheck = (String) View_Movies_GUI.list.get(8);

        MovieDB.loadSelectedCustomer();
        String currentMoneyString = (String) MovieDB.selectModel.getValueAt(0, 3).toString();
        String pickedUpMoney = (String) MovieDB.selectModel.getValueAt(0, 4).toString();
        String totalMoney = (String) MovieDB.selectModel.getValueAt(0, 5).toString();
        currentMoneyDouble = Double.parseDouble(currentMoneyString);
        pickedUpMoneyDouble = Double.parseDouble(pickedUpMoney);
        totalMoneyDouble = Double.parseDouble(totalMoney);

        double moviePrice;

        moviePrice = Double.parseDouble(moviePriceHold);

        if(bargainCheck.equalsIgnoreCase("true") && movieFormat.equalsIgnoreCase("DVD")){
            moviePrice = 2;
        }
        else if (bargainCheck.equalsIgnoreCase("true") && movieFormat.equalsIgnoreCase("Blu-Ray"))
        {
            moviePrice = 5;
        }

        else if (bargainCheck.equalsIgnoreCase("true") && movieFormat.equalsIgnoreCase("VHS")){

            moviePrice = 1;
        }


        double tax = 0.0688;
        double total = moviePrice;
        total += moviePrice * tax;
        tax = tax * moviePrice;
        tax = Math.round(tax * 100.0) / 100.0;
        total = Math.round(total * 100.0) / 100.0;
        double customerPay = moviePrice * 0.40;
        customerPay = Math.round(customerPay * 100.0) / 100.0;
        currentMoneyDouble += customerPay;
        totalMoneyDouble += customerPay;


        newMovie = new Movie(ID, movieTitle, movieYear, moviePrice, todayDate, movieFormat, upcBarcode, customerPhoneNumber, tax, total);

        saleTextArea.append(newMovie.toString());

        double finalTotal = total;
        double finalCustomerPay = customerPay;

        double finalMoviePrice = moviePrice;
        completeSaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean insertedRow = MovieDB.salesModel.insertRowSales(todayDate, movieTitle, finalMoviePrice, finalTotal, finalCustomerPay,
                       ID, customerPhoneNumber);

                //checking to make sure the data was entered in
                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error selling movie");
                }

                else{
                    JOptionPane.showMessageDialog(rootPane, "Successfully sold: " + movieTitle + ", " + "Price: $" + finalTotal);
                }

                boolean insertMoney = MovieDB.insertCustomerMoney();

                if (insertMoney) {
                    MovieDB.loadAllTables();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error updating customer money");
                }
                    boolean deleteSold = MovieDB.movieModel.deleteRow(View_Movies_GUI.selectedRowDelete);
                    if (deleteSold) {
                        MovieDB.loadAllTables();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Error deleting movie");
                    }

                Sell_Movie_GUI.this.dispose();

            }
        });

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
