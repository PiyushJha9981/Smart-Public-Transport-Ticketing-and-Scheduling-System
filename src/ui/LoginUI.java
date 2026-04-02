package ui;

import service.AppSession;
import service.PasswordPolicy;
import service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginUI extends JFrame {

    public LoginUI() {
        setTitle("Smart Transport - Login");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel heading = new JLabel("Smart Public Transport Ticketing");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));
        root.add(heading, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Login", buildLoginPanel());
        tabs.addTab("Sign Up", buildSignupPanel(tabs));
        root.add(tabs, BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }

    private JComponent buildLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username"), gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        JLabel note = new JLabel("Tip: demo / Demo@123");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(note, gbc);

        loginButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                UserService.authenticate(username, password);
                AppSession.login(username.trim());

                dispose();
                new DashboardUI(AppSession.getCurrentUsername());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JComponent buildSignupPanel(JTabbedPane tabs) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton signupButton = new JButton("Sign Up");

        JLabel passwordRule = new JLabel(PasswordPolicy.requirementText());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("New Username"), gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("New Password"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordRule, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(signupButton, gbc);

        signupButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                UserService.signUp(username, password);
                JOptionPane.showMessageDialog(this, "Signup successful. Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Switch to login tab
                tabs.setSelectedIndex(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Signup Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}