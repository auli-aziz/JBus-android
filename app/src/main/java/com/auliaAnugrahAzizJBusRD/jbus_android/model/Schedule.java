package com.auliaAnugrahAzizJBusRD.jbus_android.model;

import java.sql.Timestamp;
import java.util.Map;

public class Schedule {
    public Timestamp departureSchedule;
    public Map<String, Boolean> seatAvailability;

    @Override
    public String toString() {
        return departureSchedule.toString();
    }
}
