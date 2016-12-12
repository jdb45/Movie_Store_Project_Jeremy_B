import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cash_Out_Customers_GUI extends JFrame{
    private JPanel rootPanel;
    private JTable cashOutCustomersTable;
    private JTextField searchField;
    private JButton searchButton;
    private JButton cashOutButton;
    private JButton exitButton;


    //TODO add metadata
    //TODO Cash out button working!
    public Cash_Out_Customers_GUI(Home_GUI home_gui) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        cashOut();

        cashOutCustomersTable.setGridColor(Color.BLACK);
        //cashOutCustomersTable.setModel(MovieDB.customerModel);
        cashOutCustomersTable.setModel(MovieDB.selectModel);

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(cashOutCustomersTable.getModel());

        cashOutCustomersTable.setRowSorter(rowSorter);

        searchField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchField.getText();

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

                Cash_Out_Customers_GUI.this.dispose();
            }
        });
    }

    public void cashOut(){
        cashOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }

}
