package ui;

import model.BusBooking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PrintTicketUI extends JFrame {

    public PrintTicketUI(BusBooking booking) {
        setTitle("Ticket");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel heading = new JLabel("Ticket Details");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));
        root.add(heading, BorderLayout.NORTH);

        JTextArea ticket = new JTextArea();
        ticket.setEditable(false);
        ticket.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        String text = "========== BUS TICKET ==========" + "\n"
                + "User: " + booking.getUsername() + "\n"
                + "Route: " + booking.getFromCity() + " → " + booking.getToCity() + "\n"
                + "Date: " + booking.getTravelDate() + "\n"
                + "Bus: " + booking.getBusName() + "\n"
                + "Timing: " + booking.getTiming() + "\n"
                + "Seats: " + String.join(", ", booking.getSeatNumbers()) + "\n"
                + "Total: ₹" + booking.getTotalPrice() + "\n"
                + "Booked At: " + booking.getBookedAt() + "\n"
                + "===============================" + "\n";

        ticket.setText(text);
        root.add(new JScrollPane(ticket), BorderLayout.CENTER);

        JButton print = new JButton("Print Ticket");
        print.setPreferredSize(new Dimension(160, 44));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(print);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);

        print.addActionListener(e -> {
            try {
                boolean ok = ticket.print();
                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Print cancelled.", "Print", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Print failed: " + ex.getMessage(), "Print", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
