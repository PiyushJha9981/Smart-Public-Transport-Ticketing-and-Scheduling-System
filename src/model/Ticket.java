package model;

import java.time.LocalDateTime;

public class Ticket {

    private int id;
    private int userId;
    private String transportType;
    private String source;
    private String destination;
    private double fare;
    private LocalDateTime issueTime;
    private String qrPath;

    public Ticket() {}

    public Ticket(int id, int userId, String transportType,
                  String source, String destination,
                  double fare, LocalDateTime issueTime, String qrPath) {
        this.id = id;
        this.userId = userId;
        this.transportType = transportType;
        this.source = source;
        this.destination = destination;
        this.fare = fare;
        this.issueTime = issueTime;
        this.qrPath = qrPath;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTransportType() { return transportType; }
    public void setTransportType(String transportType) { this.transportType = transportType; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public LocalDateTime getIssueTime() { return issueTime; }
    public void setIssueTime(LocalDateTime issueTime) { this.issueTime = issueTime; }

    public String getQrPath() { return qrPath; }
    public void setQrPath(String qrPath) { this.qrPath = qrPath; }
}