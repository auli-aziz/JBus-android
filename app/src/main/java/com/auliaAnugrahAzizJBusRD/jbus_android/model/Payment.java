package com.auliaAnugrahAzizJBusRD.jbus_android.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Payment extends Invoice {
    private int busId;
    private Timestamp departureDate;
    public List<String> busSeat;
    public int getBusId() {
        return busId;
    }

    public String getDepartureDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(this.departureDate);
    }
}
