package ui;

import javax.swing.*;
import service.FareCalculator;
import service.QRGenerator;
import db.DBConnection;
import java.sql.*;
// import java.time.*;

public class BookingUI extends JFrame {

    public BookingUI(String user) {
    setTitle("Book Ticket");
    setSize(400, 400);
    setLayout(new java.awt.FlowLayout());

    String[] transports = {"Bus", "Metro", "Taxi", "Train"};
    JComboBox<String> transport = new JComboBox<>(transports);
    JTextField source = new JTextField(10);
    JTextField dest = new JTextField(10);
    JTextField distance = new JTextField(5);

    JButton book = new JButton("Confirm Booking");
    JButton mapBtn = new JButton("Open Map");

    add(new JLabel("Transport"));
    add(transport);
    add(new JLabel("Source"));
    add(source);
    add(new JLabel("Destination"));
    add(dest);
    add(new JLabel("Distance (km)"));
    add(distance);
    add(book);
    add(mapBtn);

    // MAP BUTTON
    mapBtn.addActionListener(e -> {
        try {
            java.awt.Desktop.getDesktop().browse(
                    new java.io.File("web/map.html").toURI()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    // BOOK BUTTON
    book.addActionListener(e -> {
        try {
            double dist = Double.parseDouble(distance.getText());

            double fare = FareCalculator.calculateFare(
                    transport.getSelectedItem().toString(), dist);

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO tickets(user_id,transport_type,source,destination,fare,issue_time) VALUES(?,?,?,?,?,NOW())",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, 1);
            ps.setString(2, transport.getSelectedItem().toString());
            ps.setString(3, source.getText());
            ps.setString(4, dest.getText());
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
                    "Ticket ID: " + ticketId, ticketId);

            JOptionPane.showMessageDialog(null,
                    "Ticket Booked!\nFare: " + fare);

            // Show QR
            ImageIcon icon = new ImageIcon(qrPath);
            JFrame qrFrame = new JFrame("QR Code");
            qrFrame.setSize(300, 300);
            qrFrame.add(new JLabel(icon));
            qrFrame.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    setVisible(true);
}}