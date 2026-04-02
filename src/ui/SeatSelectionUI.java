package ui;

import model.BusOption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SeatSelectionUI extends JFrame {

    private final Set<String> selectedSeats = new LinkedHashSet<>();

    private final JLabel selectedSeatsLabel = new JLabel("Selected Seats: (none)");
    private final JLabel totalLabel = new JLabel("Total Price: ₹0");

    public SeatSelectionUI(String user, String fromCity, String toCity, LocalDate travelDate, BusOption bus) {
        setTitle("Select Seats");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel heading = new JLabel("Seat Selection");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));

        JLabel busLine = new JLabel("Bus: " + bus.getName() + " | Timing: " + bus.getTimingText() + " | Price/Seat: ₹" + bus.getPricePerSeat());

        JPanel top = new JPanel(new GridLayout(2, 1, 8, 8));
        top.add(heading);
        top.add(busLine);
        root.add(top, BorderLayout.NORTH);

        JPanel seatsPanel = buildSeatsPanel(bus);
        root.add(seatsPanel, BorderLayout.CENTER);

        JButton proceed = new JButton("Proceed to Booking");
        proceed.setPreferredSize(new Dimension(200, 44));

        JPanel bottomLeft = new JPanel(new GridLayout(2, 1, 6, 6));
        bottomLeft.add(selectedSeatsLabel);
        bottomLeft.add(totalLabel);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(bottomLeft, BorderLayout.WEST);

        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomRight.add(proceed);
        bottom.add(bottomRight, BorderLayout.EAST);

        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);

        proceed.addActionListener(e -> {
            if (selectedSeats.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one seat.", "No Seats Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<String> seats = new ArrayList<>(selectedSeats);
            double total = seats.size() * bus.getPricePerSeat();

            new BookingSummaryUI(user, fromCity, toCity, travelDate, bus, seats, total);
            dispose();
        });

        setVisible(true);
    }

    private JPanel buildSeatsPanel(BusOption bus) {
        JPanel outer = new JPanel(new BorderLayout(8, 8));

        JLabel hint = new JLabel("Click seats to select (multiple allowed)");
        outer.add(hint, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(10, 4, 8, 8));

        // 10 rows (A-J), 4 seats per row (1-4)
        for (int r = 0; r < 10; r++) {
            char rowChar = (char) ('A' + r);
            for (int c = 1; c <= 4; c++) {
                String seatNo = rowChar + String.valueOf(c);
                JToggleButton seat = new JToggleButton(seatNo);
                seat.setFocusPainted(false);
                seat.addActionListener(e -> {
                    if (seat.isSelected()) {
                        selectedSeats.add(seatNo);
                    } else {
                        selectedSeats.remove(seatNo);
                    }
                    refreshPrice(bus);
                });
                grid.add(seat);
            }
        }

        outer.add(grid, BorderLayout.CENTER);
        return outer;
    }

    private void refreshPrice(BusOption bus) {
        if (selectedSeats.isEmpty()) {
            selectedSeatsLabel.setText("Selected Seats: (none)");
            totalLabel.setText("Total Price: ₹0");
            return;
        }

        selectedSeatsLabel.setText("Selected Seats: " + String.join(", ", selectedSeats));
        double total = selectedSeats.size() * bus.getPricePerSeat();
        totalLabel.setText("Total Price: ₹" + total);
    }
}
