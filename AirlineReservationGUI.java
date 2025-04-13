import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Flight {
    String flightNumber, source, destination;
    int seatsAvailable;

    public Flight(String flightNumber, String source, String destination, int seatsAvailable) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.seatsAvailable = seatsAvailable;
    }

    public boolean bookSeat() {
        if (seatsAvailable > 0) {
            seatsAvailable--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return flightNumber + " - " + source + " to " + destination + " | Seats: " + seatsAvailable;
    }
}

class Reservation {
    String passengerName;
    Flight flight;

    public Reservation(String passengerName, Flight flight) {
        this.passengerName = passengerName;
        this.flight = flight;
    }

    @Override
    public String toString() {
        return passengerName + " - " + flight.flightNumber;
    }
}

public class AirlineReservationGUI extends JFrame {
    DefaultListModel<String> flightListModel = new DefaultListModel<>();
    DefaultListModel<String> reservationListModel = new DefaultListModel<>();
    java.util.List<Flight> flights = new ArrayList<>();
    java.util.List<Reservation> reservations = new ArrayList<>();
    JList<String> flightList;
    JTextField nameField;

    public AirlineReservationGUI() {
        setTitle("Airline Reservation System");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addSampleFlights();
        for (Flight f : flights) {
            flightListModel.addElement(f.toString());
        }

        flightList = new JList<>(flightListModel);

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(new JLabel("Available Flights:"), BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(flightList), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new FlowLayout());
        nameField = new JTextField(15);
        JButton bookBtn = new JButton("Book Flight");

        formPanel.add(new JLabel("Your Name:"));
        formPanel.add(nameField);
        formPanel.add(bookBtn);

        mainPanel.add(formPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Reservations:"), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(new JList<>(reservationListModel)), BorderLayout.CENTER);

        add(rightPanel, BorderLayout.EAST);

        bookBtn.addActionListener(e -> {
            int index = flightList.getSelectedIndex();
            String name = nameField.getText().trim();

            if (index == -1 || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name and select a flight.");
                return;
            }

            Flight selectedFlight = flights.get(index);
            if (selectedFlight.bookSeat()) {
                Reservation r = new Reservation(name, selectedFlight);
                reservations.add(r);
                reservationListModel.addElement(r.toString());
                flightListModel.set(index, selectedFlight.toString());
                JOptionPane.showMessageDialog(this, "Flight booked successfully!");
                nameField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No seats available on this flight.");
            }
        });

        setVisible(true);
    }

    void addSampleFlights() {
        flights.add(new Flight("AI101", "Chennai", "Delhi", 5));
        flights.add(new Flight("AI202", "Mumbai", "Bangalore", 3));
        flights.add(new Flight("AI303", "Kolkata", "Hyderabad", 2));
    }

    public static void main(String[] args) {
        new AirlineReservationGUI();
    }
}