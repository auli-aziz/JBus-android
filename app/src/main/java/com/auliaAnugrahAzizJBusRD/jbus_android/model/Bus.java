package com.auliaAnugrahAzizJBusRD.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bus extends Serializable {
    public int accountId;
    public String name;
    public List<Facility> facilities;
    public Price price;
    public int capacity;
    public BusType busType;
    public Station departure;
    public Station arrival;
    public List<Schedule> schedules;

    public static List<Bus> sampleBusList(int size) {
        List<Bus> busList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Bus bus = new Bus();
            Random rand = new Random();

            bus.name = "Bus " + i;
            bus.capacity = rand.nextInt(50-5) + 5;
            bus.busType = BusType.REGULER;
            busList.add(bus);
        }

        return busList;
    }

//    @NonNull
//    @Override
//    public String toString() {
//        return name;
//    }

    @NonNull
    public String getName() {
        return this.name;
    }

    @NonNull
    public String getType() {
        return this.busType.toString();
    }

    public String getCapacity() {
        return "Capacity: " + Integer.toString(this.capacity);
    }
}
