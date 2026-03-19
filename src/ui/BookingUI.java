package ui;

import javax.swing.*;
import service.FareCalculator;
import service.QRGenerator;
import db.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BookingUI extends JFrame {

    // Distance table
    private Map<String, Double> distanceTable = new HashMap<>();

    public BookingUI(String user) {

        setTitle("Book Ticket");
        setSize(400, 300);
        setLayout(new java.awt.FlowLayout());

        // Predefined locations
        String[] locations = {
                "New Delhi",
                "Greater Noida",
                "Noida Sector 62",
                "Connaught Place",
                "Rajiv Chowk",
                "Delhi Airport",
                "Ghaziabad",
                "Faridabad"
        };

        JComboBox<String> source = new JComboBox<>(locations);
        JComboBox<String> dest = new JComboBox<>(locations);

        String[] transports = {"Bus", "Metro", "Taxi", "Train"};
        JComboBox<String> transport = new JComboBox<>(transports);

        JButton book = new JButton("Confirm Booking");

        // Build distance table
        initializeDistances();

        // UI Layout
        add(new JLabel("Transport"));
        add(transport);

        add(new JLabel("Source"));
        add(source);

        add(new JLabel("Destination"));
        add(dest);

        add(book);

        // Booking Logic
        book.addActionListener(e -> {

            try {

                String src = source.getSelectedItem().toString();
                String dst = dest.getSelectedItem().toString();

                if(src.equals(dst)) {
                    JOptionPane.showMessageDialog(null,
                            "Source and Destination cannot be same!");
                    return;
                }

                double distance = getDistance(src, dst);

                double fare = FareCalculator.calculateFare(
                        transport.getSelectedItem().toString(),
                        distance
                );

                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO tickets(user_id,transport_type,source,destination,fare,issue_time) VALUES(?,?,?,?,?,NOW())",
                        Statement.RETURN_GENERATED_KEYS
                );

                ps.setInt(1, 1);
                ps.setString(2, transport.getSelectedItem().toString());
                ps.setString(3, src);
                ps.setString(4, dst);
                ps.setDouble(5, fare);

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int ticketId = rs.getInt(1);

                // Insert journey history
                PreparedStatement ps2 = con.prepareStatement(
                        "INSERT INTO journey_history(ticket_id, planned_time, actual_time, delay_minutes) VALUES(?, NOW(), NOW(), ?)"
                );

                int delay = (int)(Math.random() * 10);

                ps2.setInt(1, ticketId);
                ps2.setInt(2, delay);

                ps2.executeUpdate();

                String qrPath = QRGenerator.generateQR(
                        "Ticket ID: " + ticketId,
                        ticketId
                );

                JOptionPane.showMessageDialog(null,
                        "Ticket Booked!\nDistance: " + distance +
                        " km\nFare: ₹" + fare);

                // Show QR Code
                ImageIcon icon = new ImageIcon(qrPath);

                JFrame qrFrame = new JFrame("Ticket QR");
                qrFrame.setSize(300, 300);
                qrFrame.add(new JLabel(icon));
                qrFrame.setVisible(true);

            } catch (Exception ex) {

                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error: " + ex.getMessage());

            }

        });

        setVisible(true);
    }

    // Initialize distance table
    private void initializeDistances() {

        addDistance("New Delhi","Greater Noida",40);
        addDistance("New Delhi","Noida Sector 62",20);
        addDistance("New Delhi","Connaught Place",5);
        addDistance("New Delhi","Rajiv Chowk",4);
        addDistance("New Delhi","Delhi Airport",16);
        addDistance("New Delhi","Ghaziabad",25);
        addDistance("New Delhi","Faridabad",30);

        addDistance("Greater Noida","Noida Sector 62",18);
        addDistance("Greater Noida","Connaught Place",35);
        addDistance("Greater Noida","Delhi Airport",55);
    }

    private void addDistance(String a,String b,double dist) {

        distanceTable.put(a+"-"+b,dist);
        distanceTable.put(b+"-"+a,dist);

    }

    private double getDistance(String a,String b) {

        return distanceTable.getOrDefault(a+"-"+b,25.0);

    }
}