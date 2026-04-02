package ui;

import model.BusBooking;
import model.BusOption;
import service.BookingHistoryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingSummaryUI extends JFrame {

    public BookingSummaryUI(
            String user,
            String fromCity,
            String toCity,
            LocalDate travelDate,
            BusOption bus,
            List<String> seats,
            double totalPrice
    ) {
        setTitle("Booking Summary");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel heading = new JLabel("Booking Summary");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));
        root.add(heading, BorderLayout.NORTH);

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        String text = "User Name: " + user + "\n"
                + "Route: " + fromCity + " → " + toCity + "\n"
                + "Date: " + travelDate + "\n"
                + "Bus Name: " + bus.getName() + "\n"
                + "Timing: " + bus.getTimingText() + "\n"
                + "Seats No: " + String.join(", ", seats) + "\n"
                + "Total Price: ₹" + totalPrice + "\n";

        details.setText(text);
        root.add(new JScrollPane(details), BorderLayout.CENTER);

        JButton confirm = new JButton("Confirm Booking");
        confirm.setPreferredSize(new Dimension(180, 44));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(confirm);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);

        confirm.addActionListener(e -> {
            BusBooking booking = new BusBooking(
                    user,
                    fromCity,
                    toCity,
                    travelDate,
                    bus.getName(),
                    bus.getTimingText(),
                    seats,
                    totalPrice,
                    LocalDateTime.now()
            );

            BookingHistoryService.add(booking);

            new PrintTicketUI(booking);
            dispose();
        });

        setVisible(true);
    }
}
