package ui;

import model.BusBooking;
import service.BookingHistoryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryUI extends JFrame {

    public HistoryUI(String user) {
        setTitle("History Ticket");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel heading = new JLabel("History Ticket");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));
        root.add(heading, BorderLayout.NORTH);

        String[] columns = {
                "User",
                "From",
                "To",
                "Date",
                "Bus",
                "Timing",
                "Seats",
                "Total Price",
                "Booked At"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<BusBooking> bookings = BookingHistoryService.forUser(user);
        for (BusBooking b : bookings) {
            model.addRow(new Object[]{
                    b.getUsername(),
                    b.getFromCity(),
                    b.getToCity(),
                    b.getTravelDate(),
                    b.getBusName(),
                    b.getTiming(),
                    String.join(", ", b.getSeatNumbers()),
                    "₹" + b.getTotalPrice(),
                    b.getBookedAt()
            });
        }

        JTable table = new JTable(model);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }
}
