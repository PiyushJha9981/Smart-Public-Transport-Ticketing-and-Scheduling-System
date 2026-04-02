package ui;

import model.BusOption;
import service.BusSearchService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AvailableBusesUI extends JFrame {

    private final List<BusOption> busOptions;

    public AvailableBusesUI(String user, String fromCity, String toCity, LocalDate travelDate) {
        setTitle("Available Buses");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.busOptions = BusSearchService.search(fromCity, toCity);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel heading = new JLabel("Available Buses");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));

        JLabel route = new JLabel("Route: " + fromCity + " → " + toCity + " | Date: " + travelDate);

        JPanel top = new JPanel(new GridLayout(2, 1, 8, 8));
        top.add(heading);
        top.add(route);
        root.add(top, BorderLayout.NORTH);

        String[] columns = {"Bus Name", "Timing", "Price (per seat)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (BusOption b : busOptions) {
            model.addRow(new Object[]{b.getName(), b.getTimingText(), "₹" + b.getPricePerSeat()});
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton selectBus = new JButton("Select Bus");
        selectBus.setPreferredSize(new Dimension(160, 44));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(selectBus);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);

        selectBus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a bus.", "Select Bus", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BusOption selected = busOptions.get(row);
            new SeatSelectionUI(user, fromCity, toCity, travelDate, selected);
            dispose();
        });

        setVisible(true);
    }
}
