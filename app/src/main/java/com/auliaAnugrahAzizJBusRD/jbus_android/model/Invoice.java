package com.auliaAnugrahAzizJBusRD.jbus_android.model;


import java.sql.Timestamp;

public class Invoice  extends Serializable {
    private Timestamp time;
    public int buyerId;
    public int renterId;
    public PaymentStatus status;

//    public String getOrderTime() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        sdf.setTimeZone(TimeZone.getDefault());
//        return sdf.format(this.time);
//    }

    public enum PaymentStatus {
        FAILED,
        WAITING,
        SUCCESS;
    }
}
