import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/*Jeremy B
* This class is used to view, delete, search, and sell movies*/

public class View_Movies_GUI extends JFrame {
    private JPanel rootPanel;
    private JTable movieTable;
    private JTextField searchByUser;
    private JButton exitButton;
    private JButton sellMovieButton;
    private JButton deleteButton;
    //setting some static variables
    public static boolean movieEdit;
    public static ArrayList<Object> list = new ArrayList<>();
    public static int selectedRowDelete;

    public View_Movies_GUI(Home_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1000, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        sellMovie();
        deleteMovie();
        movieEdit = true;
        //setting the table models
        movieTable.setGridColor(Color.BLACK);
        movieTable.setModel(MovieDB.movieModel);

        // I got some ideas about this search method from this website - http://stackoverflow.com/questions/22066387/how-to-search-an-element-in-a-jtable-java
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(movieTable.getModel());

        movieTable.setRowSorter(rowSorter);

        searchByUser.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchByUser.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchByUser.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

        });
    }

    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View_Movies_GUI.this.dispose();
                movieEdit = false;
            }
        });
    }

    public void deleteMovie() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getting the current row
                int currentRow = movieTable.getSelectedRow();

                //checking to see if a movie has been selected
                if (currentRow == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Please choose a movie to delete");

                }
                //removing a movie if one ahs been selected
                else if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(View_Movies_GUI.this, "Are you sure you want to delete the Movie?", "Delete", JOptionPane.OK_CANCEL_OPTION)) {
                    boolean deleted = MovieDB.movieModel.deleteRow(currentRow);
                    if (deleted) {
                        MovieDB.loadAllTables();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Error deleting movie");
                    }
                }
            }
        });
    }

    public void sellMovie(){
        sellMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getting the selected row
                int currentRow = movieTable.getSelectedRow();
                selectedRowDelete = currentRow;
                //initializing the list
                list = new ArrayList<Object>();

                //going through each value assigning each value to the list to be moved to the next form
                for(int row = 0; row < movieTable.getRowCount(); row++) {
                    if(row == currentRow) {
                        for (int column = 0; column < movieTable.getColumnCount(); column++) {
                            list.add(movieTable.getValueAt(row, column));
                        }
                    }
                }

                //checking to see if a movie has been selected
                if (currentRow == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Please choose a movie to sell");
                }
                else {
                    Sell_Movie_GUI newRemove = new Sell_Movie_GUI(View_Movies_GUI.this);
                }
            }
        });
    }
}
