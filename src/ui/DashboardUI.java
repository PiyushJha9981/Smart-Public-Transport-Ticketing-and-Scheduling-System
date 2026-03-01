package ui;
import javax.swing.*;

public class DashboardUI extends JFrame {

    public DashboardUI(String user) {
        setTitle("Dashboard");
        setSize(400, 300);

        JButton book = new JButton("Book Ticket");
        JButton history = new JButton("Journey History");

        book.addActionListener(e -> new BookingUI(user));
        history.addActionListener(e -> new HistoryUI(user));

        add(book);
        add(history);

        setLayout(new java.awt.FlowLayout());
        setVisible(true);
    }
}