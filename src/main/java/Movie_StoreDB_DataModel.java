import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/*Jeremy B
* This class is used to set the data models and used to add and delete rows in tables*/


//used parts of the cube project to help me create this dat model
public class Movie_StoreDB_DataModel extends AbstractTableModel {


    private int rowCount = 0;
    private int colCount = 0;
    ResultSet resultSet;

    public Movie_StoreDB_DataModel(ResultSet rs) {
        this.resultSet = rs;
        setup();
    }

    //setting up the table
    private void setup() {

        countRows();

        try {
            colCount = resultSet.getMetaData().getColumnCount();

        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }
    }

    //method to update the results
    public void updateResultSet(ResultSet newRS) {
        resultSet = newRS;
        setup();
    }
    //method to count the rows
    private void countRows() {
        rowCount = 0;
        try {
            resultSet.beforeFirst();

            while (resultSet.next()) {
                rowCount++;
            }
            resultSet.beforeFirst();
        } catch (SQLException se) {
            System.out.println("Error counting rows " + se);
        }
    }
    //getting the row count
    @Override
    public int getRowCount() {
        countRows();
        return rowCount;
    }
    //getting the column count
    @Override
    public int getColumnCount(){
        return colCount;
    }
    //getting the object values
    @Override
    public Object getValueAt(int row, int col) {
        try {
            resultSet.absolute(row + 1);
            Object objectRS = resultSet.getObject(col + 1);
            return objectRS.toString();
        } catch (SQLException se) {
            System.out.println(se);
            return se.toString();

        }
    }

    //a method to delete a row from the table
    public boolean deleteRow(int row){
        try {
            resultSet.absolute(row + 1);
            resultSet.deleteRow();
            //redraw table
            fireTableDataChanged();
            return true;
        }catch (SQLException se) {
            System.out.println("Delete row error " + se);
            return false;
        }
    }

    //a method for inserting a new customers row
    public boolean insertRowCustomers(String firstName, String lastName, String phoneNumber) {

        try {
            //inserting a new customer row with information
            resultSet.moveToInsertRow();
            resultSet.updateString(MovieDB.CUSTOMER_CODE_COLUMN, phoneNumber);
            resultSet.updateString(MovieDB.FIRST_NAME_COLUMN, firstName);
            resultSet.updateString(MovieDB.LAST_NAME_COLUMN, lastName);
            resultSet.updateDouble(MovieDB.CUSTOMER_MONEY_COLLECTED_COLUMN, 0);
            resultSet.updateDouble(MovieDB.CUSTOMER_MONEY_HOLD_COLUMN, 0);
            resultSet.updateDouble(MovieDB.CUSTOMER_MONEY_TOTAL_COLUMN, 0);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            fireTableDataChanged();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }

    //a method for inserting a new movie row
    public boolean insertRowMovies(String movieTitle, String movieYear, double moviePrice, String movieDate, String movieFormat, String phoneNumber, String movieUPC) {

        try {
            //inserting a new row with the information
            resultSet.moveToInsertRow();
            resultSet.updateString(MovieDB.CUSTOMER_CODE_COLUMN, phoneNumber);
            resultSet.updateString(MovieDB.MOVIE_TITLE_COLUMN, movieTitle);
            resultSet.updateString(MovieDB.MOVIE_YEAR_COLUMN, movieYear);
            resultSet.updateDouble(MovieDB.MOVIE_PRICE_COLUMN, moviePrice);
            resultSet.updateString(MovieDB.MOVIE_DATE_COLUMN, movieDate);
            resultSet.updateString(MovieDB.MOVIE_FORMAT_COLUMN, movieFormat);
            resultSet.updateString(MovieDB.MOVIE_UPC_COLUMN, movieUPC);
            resultSet.updateBoolean(MovieDB.MOVIE_BARGAIN_BIN_COLUMN, false);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            fireTableDataChanged();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }

    //a method for inserting a new sales row
    public boolean insertRowSales(String date, String movieTitle, Double salePrice, Double saleTotal, Double customerPay,
                                  String moviePK, String customerPhoneNumber) {

        try {
            //inserting a new row with the information
            resultSet.moveToInsertRow();
            resultSet.updateString(MovieDB.CUSTOMER_CODE_COLUMN, customerPhoneNumber);
            resultSet.updateString(MovieDB.MOVIE_TITLE_COLUMN, movieTitle);
            resultSet.updateString(MovieDB.SALES_DATE_COLUMN, date);
            resultSet.updateDouble(MovieDB.SALES_PRICE_COLUMN, salePrice);
            resultSet.updateDouble(MovieDB.SALES_TOTAL_COLUMN, saleTotal);
            resultSet.updateDouble(MovieDB.SALES_CUSTOMER_PAY_COLUMN, customerPay);
            resultSet.updateString(MovieDB.MOVIE_PK_COLUMN, moviePK);
            resultSet.updateBoolean(MovieDB.DONATION_COLUMN, false);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            fireTableDataChanged();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }
    //setting the edited values of the cells that are being edited
    //TODO make this not have to be hard coded and not look awful
    @Override
    public void setValueAt(Object newValue, int row, int col)
    {

        try {
            if (View_Movies_GUI.test){
                if (col == 1) {
                    String newString = newValue.toString();
                    resultSet.absolute(row + 1);
                    resultSet.updateString(MovieDB.MOVIE_TITLE_COLUMN, newString);
                    resultSet.updateRow();
                }
                else if (col == 2) {
                    String newString = newValue.toString();
                    resultSet.absolute(row + 1);
                    resultSet.updateString(MovieDB.MOVIE_YEAR_COLUMN, newString);
                    resultSet.updateRow();
                }

                else if (col == 3) {
                    double newDouble;
                    newDouble = Double.parseDouble(newValue.toString());
                    resultSet.absolute(row + 1);
                    resultSet.updateDouble(MovieDB.MOVIE_PRICE_COLUMN, newDouble);
                    resultSet.updateRow();
                }

                else if (col == 4) {
                    String newString = newValue.toString();
                    resultSet.absolute(row + 1);
                    resultSet.updateString(MovieDB.MOVIE_DATE_COLUMN, newString);
                    resultSet.updateRow();
                }

                else if (col == 5) {
                    String newString = newValue.toString();
                    resultSet.absolute(row + 1);
                    resultSet.updateString(MovieDB.MOVIE_FORMAT_COLUMN, newString);
                    resultSet.updateRow();
                }
                else if (col == 6) {
                    String newString = newValue.toString();
                    resultSet.absolute(row + 1);
                    resultSet.updateString(MovieDB.MOVIE_UPC_COLUMN, newString);
                    resultSet.updateRow();
                }

                fireTableCellUpdated(row, col);
            }
            if (View_Customers_GUI.customerEdit){

                if (col == 0) {
                    String newString = newValue.toString();
                    resultSet.absolute(row + 1);
                    resultSet.updateString(MovieDB.CUSTOMER_CODE_COLUMN, newString);
                    resultSet.updateRow();
                }
                else if (col == 1) {
                    String newString = newValue.toString();
                    resultSet.absolute(row + 1);
                    resultSet.updateString(MovieDB.FIRST_NAME_COLUMN, newString);
                    resultSet.updateRow();
                }
                else if (col == 2) {
                    String newString = newValue.toString();
                    resultSet.absolute(row + 1);
                    resultSet.updateString(MovieDB.LAST_NAME_COLUMN, newString);
                    resultSet.updateRow();
                }
                fireTableCellUpdated(row, col);
            }

        }catch (SQLException e){

        }

    }

    //TODO To fix: look into table column models, and generate the number columns based on the columns found in the ResultSet.
    public boolean isCellEditable(int row, int column) {
            return true;
    }

    @Override
    public String getColumnName(int col){
        //Getting from ResultSet metadata, which contains the database column names
        //TODO translate DB column names into something nicer for display, so "YEAR_RELEASED" becomes "Year Released"
        try {
            return resultSet.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }


}
