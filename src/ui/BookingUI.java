package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BookingUI extends JFrame {

    private static final String[] CITIES = {
            "New Delhi",
            "Greater Noida",
            "Noida Sector 62",
            "Connaught Place",
            "Rajiv Chowk",
            "Delhi Airport",
            "Ghaziabad",
            "Faridabad"
    };

    public BookingUI(String user) {
        setTitle("Search Buses");
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel heading = new JLabel("Search Buses");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));
        root.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> fromCity = new JComboBox<>(CITIES);
        JComboBox<String> toCity = new JComboBox<>(CITIES);

        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        JButton findBuses = new JButton("Find Buses");
        findBuses.setPreferredSize(new Dimension(180, 44));

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("From (City)"), gbc);
        gbc.gridx = 1;
        form.add(fromCity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(new JLabel("To (City)"), gbc);
        gbc.gridx = 1;
        form.add(toCity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("Date"), gbc);
        gbc.gridx = 1;
        form.add(dateSpinner, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        form.add(findBuses, gbc);

        root.add(form, BorderLayout.CENTER);
        setContentPane(root);

        findBuses.addActionListener(e -> {
            String from = String.valueOf(fromCity.getSelectedItem());
            String to = String.valueOf(toCity.getSelectedItem());

            if (from.equals(to)) {
                JOptionPane.showMessageDialog(this, "From and To cannot be the same.", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate travelDate = toLocalDate((Date) dateSpinner.getValue());
            new AvailableBusesUI(user, from, to, travelDate);
            dispose();
        });

        setVisible(true);
    }

    private static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
