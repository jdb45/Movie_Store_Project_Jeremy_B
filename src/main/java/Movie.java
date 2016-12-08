/**
 * Created by Jeremy on 12/7/16.
 */
public class Movie {

    private String ID;
    private String movieTitle;
    private String movieYear;
    //private double moviePrice;
    private String moviePrice;
    private String movieDate;
    private String movieFormat;
    private String upcBarcode;
    private String customerPhoneNumber;


    public Movie(String ID, String movieTitle, String movieYear, String moviePrice, String movieDate, String movieFormat, String upcBarcode,
                 String customerPhoneNumber){
        this.ID = ID;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.moviePrice = moviePrice;
        this.movieDate = movieDate;
        this.movieFormat = movieFormat;
        this.upcBarcode = upcBarcode;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    @Override
    public String toString(){

        return "Movie title: " + movieTitle + "\n" +
               "Movie price: "  + moviePrice + "\n" +
               "Movie format: " + movieFormat;
    }
}
