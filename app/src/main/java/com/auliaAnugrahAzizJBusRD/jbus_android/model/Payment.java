package com.auliaAnugrahAzizJBusRD.jbus_android.model;

import java.sql.Timestamp;
import java.util.List;

public class Payment extends Invoice {
    private int busId;
    public Timestamp departureDate;
    public List<String> busSeat;


//    public Payment(Account buyer, Renter renter, int busId, List<String> busSeat, Timestamp departureDate) {
//        super(buyer.id, renter.id);
//
//        this.busId = busId;
//        this.departureDate = departureDate;
//        this.busSeat = busSeat;
//    }


}
