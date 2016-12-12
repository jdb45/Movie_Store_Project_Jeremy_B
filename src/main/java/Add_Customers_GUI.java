import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Add_Customers_GUI extends JFrame {
    private JPanel rootPanel;
    private JTextField enterFirstName;
    private JTextField enterLastName;
    private JTextField enterPhoneNumber;
    private JButton addCustomerButton;
    private JButton exitButton;

    public Add_Customers_GUI(Home_GUI homeForm) {

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exit();

        //a button to add customers
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //getting the text from the text fields
                String firstName = enterFirstName.getText();
                String lastName = enterLastName.getText();
                String phoneNumber = enterPhoneNumber.getText();

                //checking to make sure a first name, last name and phone have been entered
                if (firstName == null || firstName.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a first name");
                }
                if (lastName == null || lastName.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a last name");
                }
                if (phoneNumber == null || phoneNumber.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a phone number");
                }

                //splitting the phone number and putting the length into a list to get a count of how many numbers are entered
                String[] stringArray = phoneNumber.split("");

                    if (stringArray.length != 10) {
                        JOptionPane.showMessageDialog(rootPane, "Phone number needs to be 10 digits");
                        return;
                    }


                //setting the values back to empty after
                enterFirstName.setText("");
                enterLastName.setText("");
                enterPhoneNumber.setText("");

                boolean insertedRow = MovieDB.customerModel.insertRowCustomers(firstName, lastName, phoneNumber);

                //checking to make sure the data was entered in
                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new customer");
                }
                else if(insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Successfully added " + firstName + " " + lastName + ", " + "Phone number " + phoneNumber
                            + " to the customer database");
                }

            }
        });
    }


    public void exit() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(Add_Customers_GUI.this, "Are you sure you want to exit? Any un-added data will be removed", "Exit", JOptionPane.OK_CANCEL_OPTION)) {
                    Add_Customers_GUI.this.dispose();
                }
            }
        });
    }

}
