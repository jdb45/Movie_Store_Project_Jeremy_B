import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Movie {

    private String ID;
    private String movieTitle;
    private String movieYear;
    private double moviePrice;
    private String movieDate;
    private String movieFormat;
    private String upcBarcode;
    private String customerPhoneNumber;
    private double tax;
    private double total;
    //formatting the date
    Date dateNow = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    String todayDate = formatter.format(dateNow);


    public Movie(String ID, String movieTitle, String movieYear, Double moviePrice, String movieDate, String movieFormat, String upcBarcode,
                 String customerPhoneNumber, double tax, double total){
        this.ID = ID;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.moviePrice = moviePrice;
        this.movieDate = movieDate;
        this.movieFormat = movieFormat;
        this.upcBarcode = upcBarcode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.tax = tax;
        this.total = total;
    }
    @Override
    public String toString(){

        return "Movie title: " + movieTitle + "\n" +
               "Movie price: $"  + moviePrice + "\n" +
               "Movie format: " + movieFormat + "\n" +
                "Date sold: " + todayDate + "\n"+
                "Customer code: " + customerPhoneNumber + "\n"+ "\n"+
                "Tax: $" + tax + "\n"+
                "Total: $"+ total;
    }
}
