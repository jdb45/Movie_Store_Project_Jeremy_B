import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sales_GUI extends JFrame {

    private JPanel rootPanel;
    private JTable viewSalesTable;
    private JTextField searchTextField;
    private JButton exitButton;

    public Sales_GUI(Home_GUI home_gui) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1000, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();
        //setting the table models
        viewSalesTable.setGridColor(Color.BLACK);
        viewSalesTable.setModel(MovieDB.salesModel);


        // I got some ideas about this search method from this website - http://stackoverflow.com/questions/22066387/how-to-search-an-element-in-a-jtable-java
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(viewSalesTable.getModel());

        viewSalesTable.setRowSorter(rowSorter);

        searchTextField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchTextField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchTextField.getText();

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
                Sales_GUI.this.dispose();

            }
        });
    }
}
