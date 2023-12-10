package com.auliaAnugrahAzizJBusRD.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Bus extends Serializable {
    public int accountId;
    public String name;
    public List<Facility> facility;
    public Price price;
    public int capacity;
    public BusType busType;
    public Station departure;
    public Station arrival;
    public List<Schedule> schedules;

    @NonNull
    public int getBusId() {
        return this.id;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    @NonNull
    public String getType() {
        return this.busType.toString();
    }

    @NonNull
    public String getCapacity() {
        return Integer.toString(this.capacity);
    }
}
