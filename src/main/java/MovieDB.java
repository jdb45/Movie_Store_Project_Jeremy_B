import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*Jeremy B
* This class is the main class for the database. It is used to connect and close the database.
* Create tables, add and edit values */

//used parts of the cube project to help me create this data model
public class MovieDB {

    //the connection string to the database
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/movieStore";
    //assigning user and password
    static final String USER = "jeremy";
    static final String PASSWORD = "guszilla";

    static Connection conn = null;

    static Statement statementCustomer = null;
    static Statement statementMovie = null;
    static Statement statementSales = null;

    static ResultSet rsCustomer = null;
    static ResultSet rsMovie = null;
    static ResultSet rsSales = null;
    static ResultSet rsSelect = null;


    //setting columns for the customer table
    public final static String CUSTOMER_TABLE_NAME = "customers";
    public final static String CUSTOMER_CODE_COLUMN = "phone_number";
    public final static String FIRST_NAME_COLUMN = "first_name";
    public final static String LAST_NAME_COLUMN = "last_name";
    public final static String CUSTOMER_MONEY_HOLD_COLUMN = "money_not_collected";
    public final static String CUSTOMER_MONEY_COLLECTED_COLUMN = "money_collected";
    public final static String CUSTOMER_MONEY_TOTAL_COLUMN = "total_sales";

    //setting columns for the movie table
    public final static String MOVIE_TABLE_NAME = "movies";
    public final static String MOVIE_PK_COLUMN = "movie_id";
    public final static String MOVIE_TITLE_COLUMN = "movie_title";
    public final static String MOVIE_YEAR_COLUMN = "movie_year";
    public final static String MOVIE_PRICE_COLUMN = "movie_price";
    public final static String MOVIE_DATE_COLUMN = "date_received";
    public final static String MOVIE_FORMAT_COLUMN = "movie_format";
    public final static String MOVIE_UPC_COLUMN = "upc_barcode";
    public final static String MOVIE_BARGAIN_BIN_COLUMN = "bargain_bin";

    //setting the columns for the sales table
    public final static String SALES_TABLE_NAME = "sales";
    public final static String SALES_PK_COLUMN = "sale_id";
    public final static String SALES_DATE_COLUMN = "date_sold";
    public final static String SALES_PRICE_COLUMN = "sales_price";
    public final static String SALES_TOTAL_COLUMN = "total_sales";
    public final static String SALES_CUSTOMER_PAY_COLUMN = "customer_pay";
    public final static String DONATION_COLUMN = "donated";

    //setting the models
    public static Movie_StoreDB_DataModel customerModel;
    public static Movie_StoreDB_DataModel movieModel;
    public static Movie_StoreDB_DataModel salesModel;
    public static Movie_StoreDB_DataModel selectModel;

    //some various global variables
    public static int getID;
    public static int donationID;
    public static ArrayList donationList = new ArrayList();


    public static void main(String[] args) {

    //checking for errors on setup
        if (!setup()) {
        System.exit(-1);
    }

        if (!loadAllTables()) {
        System.exit(-1);
    }
    //check to see if any movies are now in the bargain big
    checkBargain();

    //starting the GUI if there are no errors
        Home_GUI home_gui = new Home_GUI();
}
    //a method to check for movies that are in the inventory for 30 days or more
    public static void checkBargain() {

        for (int i = 0; i < movieModel.getRowCount(); i++) {
            //getting the date value of the movie
            String hold = movieModel.getValueAt(i, 4).toString();
            //getting the id value of the movie
            String IDString = movieModel.getValueAt(i, 0).toString();
            getID = Integer.parseInt(IDString);
            //creating a formatter
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            //getting todays date
            java.util.Date movieDate;
            java.util.Date today = Calendar.getInstance().getTime();
            Calendar calendarDate = Calendar.getInstance();


            try {
                //formatting the date and adding 30 days
                calendarDate.setTime(formatter.parse(hold));
                calendarDate.add(Calendar.DATE, 30);
                hold = formatter.format(calendarDate.getTime());
                movieDate = formatter.parse(hold);

                //if the movie has not been there for 30 or more days nothing happens
                if (movieDate.after(today)) {

                }
                //if it has, then update the column to true for the bargain bin
                else {

                    updateBargainBin();

                }
            } catch (ParseException y) {

            }
            loadAllTables();
        }
    }

    //a method to check if the movie is over a year old
    public static void checkDonation() {

        //pretty much the same as the bargain bin checker
        for (int i = 0; i < movieModel.getRowCount(); i++) {
            String hold = movieModel.getValueAt(i, 4).toString();
            String IDString = movieModel.getValueAt(i, 0).toString();
            donationID = Integer.parseInt(IDString);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date movieDate;
            java.util.Date today = Calendar.getInstance().getTime();
            Calendar calendarDate = Calendar.getInstance();


            try {
                //adding 365 days to check
                calendarDate.setTime(formatter.parse(hold));
                calendarDate.add(Calendar.DATE, 365);  // number of days to add
                hold = formatter.format(calendarDate.getTime());
                movieDate = formatter.parse(hold);


                if (movieDate.after(today)) {

                }
                //assigning the ids of the movies that can be donated
                else {
                    donationList.add(donationID);

                }
            } catch (ParseException y) {

            }
            loadAllTables();
        }
    }
    //loading all the tables in the database
    public static boolean loadAllTables() {

        try {

            if (rsCustomer != null) {
                rsCustomer.close();
            }

            if (rsMovie != null) {
                rsMovie.close();
            }

            if (rsSales != null) {
                rsSales.close();
            }
            //getting all the customers
            String getAllCustomers = "SELECT * FROM " + CUSTOMER_TABLE_NAME;
            rsCustomer = statementCustomer.executeQuery(getAllCustomers);

            if (customerModel == null) {
                //setting the model if there isn't one
                customerModel = new Movie_StoreDB_DataModel(rsCustomer);
            } else {
                //if there is one, it will update the result set
                customerModel.updateResultSet(rsCustomer);
            }
            //getting all the movies
            String getAllMovies = "SELECT * FROM " + MOVIE_TABLE_NAME;
            rsMovie = statementMovie.executeQuery(getAllMovies);


            if (movieModel == null) {
                //setting the model if there isn't one
                movieModel = new Movie_StoreDB_DataModel(rsMovie);
            } else {

                //if there is one, it will update the result set
                movieModel.updateResultSet(rsMovie);
            }

            //getting all the sales
            String getAllSales = "SELECT * FROM " + SALES_TABLE_NAME;
            rsSales = statementSales.executeQuery(getAllSales);

            if (salesModel == null) {
                //setting the model if there isn't one
                salesModel = new Movie_StoreDB_DataModel(rsSales);
            } else {
                //if there is one, it will update the result set
                salesModel.updateResultSet(rsSales);
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error loading or reloading tables");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    //a method to update the bargain bin
    public static boolean updateBargainBin() {
        try {
            //using the prepared statement to set the values of bargain bin to true of it matches the current ID
            String insertSQL = "UPDATE movies SET bargain_bin = (?) WHERE movie_id = (?) ";
            PreparedStatement psInsert = conn.prepareStatement(insertSQL);
            psInsert.setBoolean(1, true);
            psInsert.setInt(2, getID);
            psInsert.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error loading or reloading tables");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    // a method to update the donation
    public static boolean updateDonation() {
        try {
            //using the prepared statement to set the values of donated column to true of it matches the current ID
            String insertSQL = "UPDATE sales SET donated = (?) WHERE movie_id = (?) ";
            PreparedStatement psInsert = conn.prepareStatement(insertSQL);
            psInsert.setBoolean(1, true);
            psInsert.setString(2, Home_GUI.getMovieID);
            psInsert.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error loading or reloading tables");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    // a method to add data the customer money
    public static boolean insertCustomerMoney() {
        try {
            //updating the customer money after a movie has sold
            String insertSQL = "UPDATE customers SET money_not_collected = (?), total_sales = (?) WHERE phone_number = (?) ";
            PreparedStatement psInsert = conn.prepareStatement(insertSQL);
            psInsert.setDouble(1, Sell_Movie_GUI.currentMoneyDouble);
            psInsert.setDouble(2, Sell_Movie_GUI.totalMoneyDouble);
            psInsert.setString(3, Sell_Movie_GUI.customerPhoneNumber);
            psInsert.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error loading or reloading tables");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    //updating the customer money after the customer has cashed out and picked up their money
    public static boolean updateCustomerMoney() {
        try {
            //updating the customer after they have cashed out using a prepared statement
            String insertSQL = "UPDATE customers SET money_not_collected = (?), money_collected = (?), total_sales = (?) WHERE phone_number = (?) ";
            PreparedStatement psInsert = conn.prepareStatement(insertSQL);
            psInsert.setDouble(1, 0);
            psInsert.setDouble(2, Cash_Out_Customers_GUI.updatePickedUpMoneyDouble);
            psInsert.setDouble(3, Cash_Out_Customers_GUI.updateTotalMoneyDouble);
            psInsert.setString(4, Cash_Out_Customers_GUI.selectedCustomerPN);
            psInsert.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error loading or reloading tables");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    //TODO check if I actually need to keep this method in here
    //loading the selected customer
    public static boolean loadSelectedCustomer(){
        try {
            if (rsSelect != null) {
                rsSelect.close();
            }
            //using a prepared statement to check and get the customer that is selected
            String getSelectedCustomer = "SELECT * FROM customers WHERE phone_number = (?)";
            PreparedStatement psInsert = conn.prepareStatement(getSelectedCustomer);
            psInsert.setString(1, Sell_Movie_GUI.customerPhoneNumber);
            rsSelect = psInsert.executeQuery();
            //setting up the model for the selected customer. This is maybe not necessary to have this method.
            if (selectModel == null) {
                //setting the model if there isn't one
                selectModel = new Movie_StoreDB_DataModel(rsSelect);
            } else {
                //if there is one, it will update the result set
                selectModel.updateResultSet(rsSelect);
            }
            return true;
        }catch (Exception e) {
            System.out.println("Error loading or reloading tables");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    //setting up the database
    public static boolean setup() {

        try {

            //loading the drivers
            try {
                String Driver = "com.mysql.jdbc.Driver";
                Class.forName(Driver);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("No database drivers found. Quitting");
                return false;
            }

            //getting connection to the database
            conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);

            statementCustomer = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            statementMovie = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            statementSales = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //checking if the customer table exists
            if (!customersTableExists()) {

                //Create tables in the database
                String createCustomerTableSQL = "CREATE TABLE " + CUSTOMER_TABLE_NAME + " (" + CUSTOMER_CODE_COLUMN + " VARCHAR (10), " + FIRST_NAME_COLUMN + " VARCHAR(75), " + LAST_NAME_COLUMN + " VARCHAR(75) , "
                        + CUSTOMER_MONEY_HOLD_COLUMN + " DOUBLE , " + CUSTOMER_MONEY_COLLECTED_COLUMN + " DOUBLE , " + CUSTOMER_MONEY_TOTAL_COLUMN + " DOUBLE , " + " PRIMARY KEY(" + CUSTOMER_CODE_COLUMN + "))";
                System.out.println(createCustomerTableSQL);
                statementCustomer.executeUpdate(createCustomerTableSQL);

                System.out.println("Created Customers table");
            }
            //checking if the movies table exists
            if (!moviesTableExists()){
                //Create tables in the database
                String createMovieTableSQL = "CREATE TABLE " + MOVIE_TABLE_NAME + " (" + MOVIE_PK_COLUMN + " INT NOT NULL AUTO_INCREMENT,  " + MOVIE_TITLE_COLUMN + " VARCHAR(75), " + MOVIE_YEAR_COLUMN + " VARCHAR (4), " +
                        MOVIE_PRICE_COLUMN + " DOUBLE , " +  MOVIE_DATE_COLUMN + " VARCHAR(11), " + MOVIE_FORMAT_COLUMN + " VARCHAR(10), " + MOVIE_UPC_COLUMN + " VARCHAR (12), " + CUSTOMER_CODE_COLUMN + " VARCHAR (10), " + MOVIE_BARGAIN_BIN_COLUMN + " BIT (1), " + " PRIMARY KEY(" + MOVIE_PK_COLUMN + "))";
                System.out.println(createMovieTableSQL);
                statementMovie.executeUpdate(createMovieTableSQL);

                System.out.println("Created Movies Table");

            }
            //checking if the sales table exists
            if (!salesTableExists()){
                //Create tables in the database
                String createSalesTableSQL = "CREATE TABLE " + SALES_TABLE_NAME + " (" + SALES_PK_COLUMN + " INT NOT NULL AUTO_INCREMENT,  " + SALES_DATE_COLUMN + " VARCHAR(11), "  + MOVIE_TITLE_COLUMN + " VARCHAR(75)," + SALES_PRICE_COLUMN + " DOUBLE , " +
                        SALES_TOTAL_COLUMN + " DOUBLE , "+  SALES_CUSTOMER_PAY_COLUMN + " DOUBLE , " + CUSTOMER_CODE_COLUMN + " VARCHAR (10), " + MOVIE_PK_COLUMN + " VARCHAR (10000), " + DONATION_COLUMN + " BIT (1), " + " PRIMARY KEY(" + SALES_PK_COLUMN + "))";
                System.out.println(createSalesTableSQL);
                statementMovie.executeUpdate(createSalesTableSQL);

                System.out.println("Created Sales Table");

            }

            return true;

        } catch (SQLException se) {
            System.out.println(se);
            se.printStackTrace();
            return false;
        }
    }

    private static boolean customersTableExists() throws SQLException {

        //showing the tables that exists
        String checkCustomerTablePresentQuery = "SHOW TABLES LIKE '" + CUSTOMER_TABLE_NAME + "'";
        ResultSet customerRS = statementCustomer.executeQuery(checkCustomerTablePresentQuery);
        if(customerRS.next()) {
            return true;
        }
        return false;
    }

    private static boolean moviesTableExists() throws SQLException {

        //showing the tables that exists
        String checkMovieTablePresentQuery = "SHOW TABLES LIKE '" + MOVIE_TABLE_NAME + "'";
        ResultSet movieRS = statementMovie.executeQuery(checkMovieTablePresentQuery);
        if(movieRS.next()) {
            return true;
        }
        return false;
    }

    private static boolean salesTableExists() throws SQLException {

        //showing the tables that exists
        String checkSalesTablePresentQuery = "SHOW TABLES LIKE '" + SALES_TABLE_NAME + "'";
        ResultSet salesRS = statementSales.executeQuery(checkSalesTablePresentQuery);
        if(salesRS.next()) {
            return true;
        }
        return false;
    }


    //shutting down all connections, statements
    public static void shutdown(){
        try{
            if(rsCustomer != null) {
                rsCustomer.close();
                System.out.println("Result set closed");
            }

            if(rsMovie != null) {
                rsMovie.close();
                System.out.println("Result set closed");
            }

            if(rsSales != null) {
                rsSales.close();
                System.out.println("Result set closed");
            }
            if(rsSelect != null) {
                rsSelect.close();
                System.out.println("Result set closed");
            }
        }catch (SQLException se){
            se.printStackTrace();
        }

        try {
            if(statementMovie != null) {
                statementMovie.close();
                System.out.println("Statement closed");
            }

            if(statementCustomer != null) {
                statementCustomer.close();
                System.out.println("Statement closed");
            }

            if(statementSales != null) {
                statementSales.close();
                System.out.println("Statement closed");
            }
        }catch (SQLException se){
            se.printStackTrace();
        }

        try{
            if(conn != null){
                conn.close();
                System.out.println("Database connection closed");
            }
        }
        catch (SQLException se){
            se.printStackTrace();
        }
    }
}
