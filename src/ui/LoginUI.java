package ui;
import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    public LoginUI() {
        setTitle("Smart Transport Login");
        setSize(400, 300);
        setLayout(new FlowLayout());

        JTextField username = new JTextField(15);
        JPasswordField password = new JPasswordField(15);
        JButton login = new JButton("Login");

        add(new JLabel("Username"));
        add(username);
        add(new JLabel("Password"));
        add(password);
        add(login);

        login.addActionListener(e -> {
            dispose();
            new DashboardUI(username.getText());
        });

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}