import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

/**
 * Created by Jeremy on 11/28/16.
 */
public class View_Movies_GUI extends JFrame {
    private JPanel rootPanel;
    private JTable movieTable;
    private JTextField searchByUser;
    private JButton exitButton;
    private JButton sellMovieButton;
    private JButton deleteButton;
    public static boolean test;
    public static ArrayList<Object> list = new ArrayList<>();

    public View_Movies_GUI(Home_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1000, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        sellMovie();
        deleteMovie();
        test = true;

        movieTable.setGridColor(Color.BLACK);
        movieTable.setModel(MovieDB.movieModel);

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

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        Object data = model.getValueAt(row, column);

    }

    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View_Movies_GUI.this.dispose();
                test = false;
            }
        });
    }


    public void deleteMovie() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = movieTable.getSelectedRow();

                //checking to see if a movie has been selected
                if (currentRow == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Please choose a movie to delete");

                }
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
    //TODO get the selected movie to carry over to the selling form
    public void sellMovie(){
        sellMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int currentRow = movieTable.getSelectedRow();

                list = new ArrayList<Object>();

                for(int row = 0; row < movieTable.getRowCount(); row++) {
                    if(row == currentRow) {
                        for (int column = 0; column < movieTable.getColumnCount(); column++) {
                            list.add(movieTable.getValueAt(row, column));
                        }
                    }
                }
                for(int i = 0 ; i < list.size() ; i++){
                    System.out.println(list.get(i));
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
