package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardUI extends JFrame {

    private final JButton bookButton = new JButton("Book Ticket");
    private final JButton historyButton = new JButton("Journey History");
    private final JButton logoutButton = new JButton("Logout");

    private final JLabel statusLabel = new JLabel("Ready");
    private final JLabel clockLabel = new JLabel();

    public DashboardUI(String user) {
        setTitle("Dashboard");
        setSize(520, 360);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        root.add(buildHeader(user), BorderLayout.NORTH);
        root.add(buildActions(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);

        setContentPane(root);

        wireActions(user);
        startClock();

        setVisible(true);
    }

    private JComponent buildHeader(String user) {
        JPanel header = new JPanel(new BorderLayout(8, 8));

        JLabel title = new JLabel("Welcome, " + (user == null || user.isBlank() ? "User" : user));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        clockLabel.setFont(clockLabel.getFont().deriveFont(Font.PLAIN, 12f));
        right.add(clockLabel);
        right.add(logoutButton);

        header.add(title, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JComponent buildActions() {
        JPanel actions = new JPanel(new GridLayout(2, 1, 12, 12));

        stylePrimaryButton(bookButton, "Create a ticket and generate QR");
        stylePrimaryButton(historyButton, "View your recent journeys");

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

    private JComponent buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.PLAIN, 12f));
        footer.add(statusLabel, BorderLayout.WEST);
        return footer;
    }

    private void wireActions(String user) {
        bookButton.setMnemonic('B');
        historyButton.setMnemonic('H');
        logoutButton.setMnemonic('L');

        bookButton.addActionListener(e -> openChildWindow("Booking", () -> new BookingUI(user)));
        historyButton.addActionListener(e -> openChildWindow("History", () -> new HistoryUI(user)));
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginUI();
        });
    }

    private void openChildWindow(String label, ChildWindowFactory factory) {
        setActionsEnabled(false);
        setStatus("Opening " + label + "...");

        JFrame child;
        try {
            child = factory.create();
        } catch (Exception ex) {
            setActionsEnabled(true);
            setStatus("Failed to open " + label);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        child.setLocationRelativeTo(this);
        child.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setActionsEnabled(true);
                setStatus("Ready");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                setActionsEnabled(true);
                setStatus("Ready");
            }
        });

        setStatus(label + " open");
    }

    private void setActionsEnabled(boolean enabled) {
        bookButton.setEnabled(enabled);
        historyButton.setEnabled(enabled);
    }

    private void setStatus(String text) {
        statusLabel.setText(text);
    }

    private void startClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timer timer = new Timer(500, e -> clockLabel.setText(LocalTime.now().format(formatter)));
        timer.setRepeats(true);
        timer.start();
    }

    @FunctionalInterface
    private interface ChildWindowFactory {
        JFrame create();
    }
}