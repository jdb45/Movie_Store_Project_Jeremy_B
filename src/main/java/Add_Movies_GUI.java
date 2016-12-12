import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Jeremy on 11/28/16.
 */
public class Add_Movies_GUI extends JFrame{
    private JPanel rootPanel;
    private JTextField addMovie;
    private JTextField addMovieYear;
    private JTextField addMoviePrice;
    private JComboBox movieFormat;
    private JComboBox customerCode;
    private JTextField upcBarcode;
    private JButton addMovieButton;
    private JButton exitButton;
    private JSpinner dateSpinner;

    public Add_Movies_GUI(Home_GUI home_gui) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //assigning the values of the combo box
        movieFormat.addItem("DVD");
        movieFormat.addItem("Blu-Ray");
        movieFormat.addItem("VHS");
        //setting the barcode to null
        upcBarcode.setText("Null");
        //a while loop to add all the customer codes to the customer combo box
        while (true) {
            for(int i = 0 ; i < MovieDB.customerModel.getRowCount(); i++){

                customerCode.addItem(MovieDB.customerModel.getValueAt(i, 0));
            }
            break;

        }
        //setting the model for the date spinner
        dateSpinner.setModel(new SpinnerDateModel());

        exit();
        addMovie();
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

    public void addMovie(){
        addMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //getting all the values from the text and combo fields
                String movieTitle = addMovie.getText();
                Date currentValue = (Date) dateSpinner.getValue();
                String movieFormatString = movieFormat.getSelectedItem().toString();
                //creating a date formatter
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String movieDate = formatter.format(currentValue);
                String phoneNumber = customerCode.getSelectedItem().toString();
                String movieYear = addMovieYear.getText();
                String movieUPC = upcBarcode.getText();

                //checking to make sure a name has been entered
                if (movieTitle == null || movieTitle.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a movie title");
                }
                //checking to make sure a year has been entered
                if (movieYear == null || movieTitle.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a movie year");
                }

                Double moviePrice;
                //parsing the movie price and making sure its a double
                try {
                    moviePrice = Double.parseDouble(addMoviePrice.getText());

                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPane, "Movie price needs to be a number, or can't be blank");
                    return;
                }

                boolean insertedRow = MovieDB.movieModel.insertRowMovies(movieTitle, movieYear, moviePrice, movieDate, movieFormatString, phoneNumber, movieUPC );

                //checking to make sure the data was entered in
                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new movie");
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "Successfully added Title: " + movieTitle + ", " + "Year: " + movieYear + ", "
                            + "Format: " + movieFormat.getSelectedItem() + ", " + "Price: $" + moviePrice + " to the movie database");
                }
                MovieDB.checkBargain();

                //setting the values back to empty after
                addMoviePrice.setText("");
                addMovie.setText("");
                addMovieYear.setText("");
                upcBarcode.setText("Null");
            }
        });

    }

}
