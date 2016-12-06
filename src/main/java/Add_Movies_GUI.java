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
        movieFormat.addItem("DVD");
        movieFormat.addItem("Blu-Ray");
        movieFormat.addItem("VHS");
        while (true) {
            for(int i = 0 ; i < MovieDB.customerModel.getRowCount(); i++){

                customerCode.addItem(MovieDB.customerModel.getValueAt(i, 0));
            }
            break;

        }
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

                String movieTitle = addMovie.getText();
                //String movieDate = String.parse(dateSpinner.getName());
                Date currentValue = (Date) dateSpinner.getValue();
                String movieFormatString = movieFormat.getSelectedItem().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String movieDate = formatter.format(currentValue);
                String phoneNumber = customerCode.getSelectedItem().toString();
                //Integer phoneNumber = Integer.parseInt((holdPhone));
                String movieUPC = upcBarcode.getText();
                //checking to make sure a name has been entered
                if (movieTitle == null || movieTitle.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a movie title");
                }

                Integer movieYear;
                try {
                    movieYear = Integer.parseInt(addMovieYear.getText());

                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPane, "Movie year needs to be a number, or can't be blank");
                    return;
                }

                Double moviePrice;
                try {
                    moviePrice = Double.parseDouble(addMoviePrice.getText());

                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPane, "Movie price needs to be a number, or can't be blank");
                    return;
                }

                JOptionPane.showMessageDialog(rootPane, "Successfully added Title: " + movieTitle + ", " + "Year: " + movieYear + ", "
                      + "Format: " + movieFormat.getSelectedItem() + ", " + "Price: $" + moviePrice + " to the movie database");

                boolean insertedRow = MovieDB.movieModel.insertRowMovies(movieTitle, movieYear, moviePrice, movieDate, movieFormatString, phoneNumber, movieUPC );

                //checking to make sure the data was entered in
                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new movie");
                }

                //setting the values back to empty after
                addMoviePrice.setText("");
                addMovie.setText("");
                addMovieYear.setText("");
                upcBarcode.setText("");
            }
        });

    }

}
