import java.sql.*;

public class MovieDB {

    //the connection string to the database
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/movieStore";
    //assigning user and password
    static final String USER = "jeremy";
    static final String PASSWORD = "guszilla";

    static Connection conn = null;

    static Statement statementCustomer = null;
    static Statement statementMovie = null;

    static ResultSet rsCustomer = null;
    static ResultSet rsMovie = null;


    //setting columns for the customer table
    public final static String CUSTOMER_TABLE_NAME = "customers";
    public final static String CUSTOMER_CODE_COLUMN = "phone_number";
    public final static String FIRST_NAME_COLUMN = "first_name";
    public final static String LAST_NAME_COLUMN = "last_name";

    //setting columns for the movie table
    public final static String MOVIE_TABLE_NAME = "movies";
    public final static String MOVIE_PK_COLUMN = "id";
    public final static String MOVIE_TITLE_COLUMN = "movie_title";
    public final static String MOVIE_YEAR_COLUMN = "movie_year";
    public final static String MOVIE_PRICE_COLUMN = "movie_price";
    public final static String MOVIE_DATE_COLUMN = "date_received";
    public final static String MOVIE_FORMAT_COLUMN = "movie_format";
    public final static String MOVIE_UPC_COLUMN = "upc_barcode";

    public static Movie_StoreDB_DataModel customerModel;
    public static Movie_StoreDB_DataModel movieModel;

    public static void main(String[] args) {

    //checking for errors on setup
        if (!setup()) {
        System.exit(-1);
    }

        if (!loadAllTables()) {
        System.exit(-1);
    }

    //starting the GUI if there are no errors
        Home_GUI home_gui = new Home_GUI();
}
    //loading all the customers in the database
    public static boolean loadAllTables() {

        try {

            if (rsCustomer != null) {
                rsCustomer.close();
            }

            if (rsMovie != null) {
                rsMovie.close();
            }

            String getAllCustomers = "SELECT * FROM " + CUSTOMER_TABLE_NAME;
            rsCustomer = statementCustomer.executeQuery(getAllCustomers);

            if (customerModel == null) {
                //setting the model if there isn't one
                customerModel = new Movie_StoreDB_DataModel(rsCustomer);
            } else {
                //if there is one, it will update the result set
                customerModel.updateResultSet(rsCustomer);
            }

            String getAllMovies = "SELECT * FROM " + MOVIE_TABLE_NAME;
            rsMovie = statementMovie.executeQuery(getAllMovies);

            if (movieModel == null) {
                //setting the model if there isn't one
                movieModel = new Movie_StoreDB_DataModel(rsMovie);
            } else {
                //if there is one, it will update the result set
                movieModel.updateResultSet(rsMovie);
            }
            return true;

        } catch (Exception e) {
            System.out.println("Error loading or reloading tables");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

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

            if (!customersTableExists()) {

                //Create tables in the database
                String createCustomerTableSQL = "CREATE TABLE " + CUSTOMER_TABLE_NAME + " (" + CUSTOMER_CODE_COLUMN + " INT(10), " + FIRST_NAME_COLUMN + " VARCHAR(75), " + LAST_NAME_COLUMN + " VARCHAR(75) , PRIMARY KEY(" + CUSTOMER_CODE_COLUMN + "))";
                System.out.println(createCustomerTableSQL);
                statementCustomer.executeUpdate(createCustomerTableSQL);

                System.out.println("Created Customers table");
            }
            else if (!moviesTableExists()){
                //Create tables in the database
                String createMovieTableSQL = "CREATE TABLE " + MOVIE_TABLE_NAME + " (" + MOVIE_PK_COLUMN + " INT NOT NULL AUTO_INCREMENT,  " + MOVIE_TITLE_COLUMN + " VARCHAR(75), " + MOVIE_YEAR_COLUMN + " INT(4), " +
                        MOVIE_PRICE_COLUMN + " DOUBLE , "+  MOVIE_DATE_COLUMN + " VARCHAR(11), " + MOVIE_FORMAT_COLUMN + " VARCHAR(10), " + MOVIE_UPC_COLUMN + " VARCHAR (12), " + CUSTOMER_CODE_COLUMN + " INT(10), " + " PRIMARY KEY(" + MOVIE_PK_COLUMN + "))";
                System.out.println(createMovieTableSQL);
                statementMovie.executeUpdate(createMovieTableSQL);

                System.out.println("Created Movies Table");

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
        String checkCustomerTablePresenQuery = "SHOW TABLES LIKE '" + CUSTOMER_TABLE_NAME + "'";
        ResultSet customerRS = statementCustomer.executeQuery(checkCustomerTablePresenQuery);
        if(customerRS.next()) {
            return true;
        }
        return false;
    }

    private static boolean moviesTableExists() throws SQLException {

        //showing the tables that exists
        String checkMovieTablePresenQuery = "SHOW TABLES LIKE '" + MOVIE_TABLE_NAME + "'";
        ResultSet movieRS = statementMovie.executeQuery(checkMovieTablePresenQuery);
        if(movieRS.next()) {
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
