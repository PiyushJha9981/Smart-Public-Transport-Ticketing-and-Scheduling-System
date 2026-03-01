package model;

import java.time.LocalDateTime;

public class JourneyHistory {

    private int id;
    private int ticketId;
    private LocalDateTime plannedTime;
    private LocalDateTime actualTime;
    private int delayMinutes;

    public JourneyHistory() {}

    public JourneyHistory(int id, int ticketId,
                          LocalDateTime plannedTime,
                          LocalDateTime actualTime,
                          int delayMinutes) {
        this.id = id;
        this.ticketId = ticketId;
        this.plannedTime = plannedTime;
        this.actualTime = actualTime;
        this.delayMinutes = delayMinutes;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public LocalDateTime getPlannedTime() { return plannedTime; }
    public void setPlannedTime(LocalDateTime plannedTime) { this.plannedTime = plannedTime; }

    public LocalDateTime getActualTime() { return actualTime; }
    public void setActualTime(LocalDateTime actualTime) { this.actualTime = actualTime; }

    public int getDelayMinutes() { return delayMinutes; }
    public void setDelayMinutes(int delayMinutes) { this.delayMinutes = delayMinutes; }
}