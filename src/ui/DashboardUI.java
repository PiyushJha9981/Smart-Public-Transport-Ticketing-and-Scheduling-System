package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardUI extends JFrame {

    private final JButton bookButton = new JButton("Book Ticket");
    private final JButton historyButton = new JButton("History Ticket");

    public DashboardUI(String user) {
        setTitle("Dashboard");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        root.add(buildHeader(user), BorderLayout.NORTH);
        root.add(buildActions(), BorderLayout.CENTER);

        setContentPane(root);

        wireActions(user);

        setVisible(true);
    }

    private JComponent buildHeader(String user) {
        JPanel header = new JPanel(new BorderLayout(8, 8));

        String displayUser = (user == null || user.isBlank()) ? "User" : user;
        JLabel title = new JLabel("Welcome, " + displayUser);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JComponent buildActions() {
        JPanel actions = new JPanel(new GridLayout(2, 1, 12, 12));

        stylePrimaryButton(bookButton, "Book bus ticket");
        stylePrimaryButton(historyButton, "View booking history");

        actions.add(wrapButton(bookButton));
        actions.add(wrapButton(historyButton));
        return actions;
    }

    private JComponent wrapButton(JButton button) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);
        return panel;
    }

    private void stylePrimaryButton(JButton button, String tooltip) {
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        button.setPreferredSize(new Dimension(0, 64));
    }

    private void wireActions(String user) {
        bookButton.addActionListener(e -> new BookingUI(user));
        historyButton.addActionListener(e -> new HistoryUI(user));
    }
}