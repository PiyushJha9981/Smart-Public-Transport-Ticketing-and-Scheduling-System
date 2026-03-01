package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import db.DBConnection;

public class HistoryUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    public HistoryUI(String user) {

        setTitle("Journey History");
        setSize(700, 400);

        String[] columns = {
                "Ticket ID",
                "Transport",
                "Source",
                "Destination",
                "Planned Time",
                "Actual Time",
                "Delay (Minutes)"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        loadHistory();

        add(new JScrollPane(table));
        setVisible(true);
    }

    private void loadHistory() {
        try {
            Connection con = DBConnection.getConnection();

            String query =
                    "SELECT t.id, t.transport_type, t.source, t.destination, " +
                    "j.planned_time, j.actual_time, j.delay_minutes " +
                    "FROM tickets t " +
                    "JOIN journey_history j ON t.id = j.ticket_id";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("transport_type"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getTimestamp("planned_time"),
                        rs.getTimestamp("actual_time"),
                        rs.getInt("delay_minutes")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}