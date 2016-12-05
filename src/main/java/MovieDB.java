import java.sql.*;

public class MovieDB {

    //the connection string to the database
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/movieStore";
    //assigning user and password
    static final String USER = "jeremy";
    static final String PASSWORD = "guszilla";

    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;

    //setting columns for the customer table
    public final static String CUSTOMER_TABLE_NAME = "customers";
    public final static String PK_COLUMN = "phone_number";
    public final static String FIRST_NAME_COLUMN = "first_name";
    public final static String LAST_NAME_COLUMN = "last_name";

    public static Movie_StoreDB_DataModel movieModel;

    public static void main(String[] args) {

    //checking for errors on setup
        if (!setup()) {
        System.exit(-1);
    }

        if (!loadAllCustomers()) {
        System.exit(-1);
    }

    //starting thr GUI if there are no errors
        Home_GUI home_gui = new Home_GUI(movieModel);
}
    //loading all the cube solvers in the database
    public static boolean loadAllCustomers() {

        try {

            if (rs != null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM " + CUSTOMER_TABLE_NAME;
            rs = statement.executeQuery(getAllData);

            if (movieModel == null) {
                //setting the model if there isn't one
                movieModel = new Movie_StoreDB_DataModel(rs);
            } else {
                //if there is one, it will update the result set
                movieModel.updateResultSet(rs);
            }
            return true;

        } catch (Exception e) {
            System.out.println("Error loading or reloading cube solvers");
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

            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);


            if (!cubeTableExists()) {

                //Create a table in the database with 2 columns: cube solver and time taken
                String createTableSQL = "CREATE TABLE " + CUSTOMER_TABLE_NAME + " (" + PK_COLUMN + " INT, " + FIRST_NAME_COLUMN + " VARCHAR(75), " + LAST_NAME_COLUMN + " VARCHAR(75) , PRIMARY KEY(" + PK_COLUMN + "))";
                System.out.println(createTableSQL);
                statement.executeUpdate(createTableSQL);

                System.out.println("Created Customers table");

                //String addDataSQL = "INSERT INTO " + CUSTOMER_TABLE_NAME + "( " + FIRST_NAME_COLUMN + ", " + LAST_NAME_COLUMN + ")" + " VALUES ('Cubestormer II robot', 5.270)";
                //statement.executeUpdate(addDataSQL);
            }
            return true;

        } catch (SQLException se) {
            System.out.println(se);
            se.printStackTrace();
            return false;
        }
    }

    private static boolean cubeTableExists() throws SQLException {

        //showing the table that exists
        String checkTablePresenQuery = "SHOW TABLES LIKE '" + CUSTOMER_TABLE_NAME + "'";
        ResultSet tablesRS = statement.executeQuery(checkTablePresenQuery);
        if(tablesRS.next()) {
            return true;
        }
        return false;
    }


    //shutting down all connections, statements
    public static void shutdown(){
        try{
            if(rs != null) {
                rs.close();
                System.out.println("Result set closed");
            }
        }catch (SQLException se){
            se.printStackTrace();
        }

        try {
            if(statement != null) {
                statement.close();
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
