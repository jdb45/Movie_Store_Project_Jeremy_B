import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

   // public void addCustomers() {
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String firstName = enterFirstName.getText();
                String lastName = enterLastName.getText();

                //checking to make sure a name has been entered
                if (firstName == null || firstName.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a first name");
                }
                if (lastName == null || lastName.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a last name");
                }

                Integer phoneNumber;
                int count = 0;

                //splitting the phone number and putting the length into a list to get a count of how many numbers are entered
                String numberStr = enterPhoneNumber.getText();
                int[] numHold = new int[numberStr.length()];
                for (int i = 0; i < numberStr.length(); i++) {
                    numHold[i] = Character.getNumericValue(numberStr.charAt(i));
                }


                try {
                    phoneNumber = Integer.parseInt(enterPhoneNumber.getText());

                    //checking to make sure the phone number is 10 digits
                    for (int i = 0; i < numHold.length; i++) {
                        count += 1;
                    }
                    if (count != 10) {
                        JOptionPane.showMessageDialog(rootPane, "Phone number needs to be 10 digits");
                        return;
                    }

                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPane, "Phone number needs to be a number, or can't be blank");
                    return;
                }
                JOptionPane.showMessageDialog(rootPane, "Successfully added " + firstName + " " + lastName + ", " + "Phone number " + phoneNumber
                        + " to the customer database");

                //setting the values back to empty after
                enterFirstName.setText("");
                enterLastName.setText("");
                enterPhoneNumber.setText("");

                System.out.println("Adding " + firstName + " " + lastName);
                boolean insertedRow = MovieDB.movieModel.insertRow(firstName, lastName, phoneNumber);

                //checking to make sure the data was entered in
                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new cube solver");
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
