import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame implements ActionListener {
    JButton loginBtn, regBtn;
    JPanel newPanel;
    JLabel usernameLabel, passwordLabel;
    JTextField textField1;
    JTextField textField2;
    public static String loggedUserName;

    LoginForm() {
        this.setLayout(new BorderLayout()); // Set the border layout
        newPanel = new JPanel();
        newPanel.setLayout(null);
        this.add(newPanel);

        // Creating and positioning username label
        usernameLabel = new JLabel();
        usernameLabel.setText("User name:");
        usernameLabel.setBounds(50, 80, 100, 30);
        newPanel.add(usernameLabel);

        // Creating and positioning username text field
        textField1 = new JTextField();
        textField1.setBounds(120, 80, 200, 30);
        newPanel.add(textField1);

        // Creating and positioning password label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        newPanel.add(passwordLabel);

        // Creating and positioning password text field
        textField2 = new JTextField();
        textField2.setBounds(115, 150, 200, 30);
        newPanel.add(textField2);

        // Creating and positioning login button
        loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(80, 200, 100, 30);
        newPanel.add(loginBtn);

        // Creating and positioning register button
        regBtn = new JButton("Register");
        regBtn.setBounds(200, 200, 100, 30);
        newPanel.add(regBtn);

        // Adding action listeners to buttons
        loginBtn.addActionListener(this);
        regBtn.addActionListener(this);

        // Setting frame properties
        this.setTitle("Login Form");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String userName = textField1.getText();
            String password = textField2.getText();
            loggedUserName = userName;
            User.userDataLoad("purchaseDetailsFile");

            // Create and display the GUIHomePage
            GUIHomePage homePage = new GUIHomePage();
            homePage.homePage();

            // Close the login form
            this.dispose();
        } else if (e.getSource() == regBtn) {
            // Creating and displaying the registration form
            RegistrationForm regForm = new RegistrationForm(this); // Pass the login form as a parameter
            regForm.setSize(400, 300);
            regForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            regForm.setVisible(true);
        }
    }
    public static void main(String[] args) {
        // Creating and displaying the login form
        LoginForm loginForm = new LoginForm();
        loginForm.setSize(400, 300);
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginForm.setVisible(true);
    }
}

class RegistrationForm extends JFrame implements ActionListener {
    JLabel usernameLabel, passwordLabel;
    JTextField usernameField;
    JTextField passwordField;
    JButton registerBtn;
    LoginForm loginForm; // Reference to the login form

    RegistrationForm(LoginForm loginForm) {
        this.loginForm = loginForm; // Store the reference to the login form

        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);

        // Creating and positioning username label
        usernameLabel = new JLabel("User name:");
        usernameLabel.setBounds(50, 80, 100, 30);
        panel.add(usernameLabel);

        // Creating and positioning username text field
        usernameField = new JTextField();
        usernameField.setBounds(120, 80, 200, 30);
        panel.add(usernameField);

        // Creating and positioning password label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        panel.add(passwordLabel);

        // Creating and positioning password text field
        passwordField = new JTextField();
        passwordField.setBounds(115, 150, 200, 30);
        panel.add(passwordField);

        // Creating and positioning register button
        registerBtn = new JButton("Register");
        registerBtn.setBounds(150, 200, 100, 30);
        panel.add(registerBtn);

        // Adding action listener to the register button
        registerBtn.addActionListener(this);

        // Setting frame properties
        this.setTitle("Registration Form");
        this.setSize(400, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerBtn) {
            String name = usernameField.getText();
            String password = passwordField.getText();
            User user = new User(name,password);
            User.usersList.add(user);
            // Close the registration form
            this.dispose();

            // Show the login form again
            loginForm.setVisible(true);
        }
    }
}
