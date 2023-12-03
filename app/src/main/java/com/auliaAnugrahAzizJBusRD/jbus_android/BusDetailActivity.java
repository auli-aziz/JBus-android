package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusDetailActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private TextView busName, busType, busCapacity, busPrice, departureStat, arrivalStat, departureAdd, departureCity, arrivalAdd, arrivalCity;
    private String name;
    private int busId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        busName = findViewById(R.id.name);
        busType = findViewById(R.id.type);
        busCapacity = findViewById(R.id.capacity);
        busPrice = findViewById(R.id.price);
        departureStat = findViewById(R.id.departure_station);
        departureCity = findViewById(R.id.departure_city);
        departureAdd = findViewById(R.id.departure_address);
        arrivalStat = findViewById(R.id.arrival_station);
        arrivalCity = findViewById(R.id.arrival_city);
        arrivalAdd = findViewById(R.id.arrival_address);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null) {
            busId = Integer.parseInt(b.get("BUS_ID").toString());
            name = b.get("BUS_NAME").toString();
//            setDetail(b);
        } else {
            Toast.makeText(mContext, "Cannot retrieve bus detail", Toast.LENGTH_SHORT).show();
        }

        busName.setText(name);
        handleDetails();
    }

    protected void handleDetails() {
        mApiService.getMyBusDetails(busId).enqueue(new Callback<Bus>() {
            @Override
            public void onResponse(Call<Bus> call, Response<Bus> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Bus b = response.body();
                busType.setText(b.busType.toString());
                busCapacity.setText(b.getCapacity());
                busPrice.setText(Double.toString(b.price.price));
                departureStat.setText(b.departure.stationName);
                arrivalStat.setText(b.arrival.stationName);
                departureCity.setText(b.departure.city.toString());
                departureAdd.setText(b.departure.address);
                arrivalCity.setText(b.arrival.city.toString());
                arrivalAdd.setText(b.arrival.address);
            }


            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    protected void setDetail(Bundle b) {
//        busName.setText(b.get("BUS_NAME").toString());
//        busType.setText(b.get("BUS_TYPE").toString());
//        busCapacity.setText(b.get("BUS_CAPACITY").toString());
//        busPrice.setText(b.get("BUS_PRICE").toString());
//        departureStat.setText(b.get("BUS_DEP_STAT").toString());
//        departureCity.setText(b.get("BUS_DEP_CITY").toString());
//        departureAdd.setText(b.get("BUS_DEP_ADD").toString());
//        arrivalStat.setText(b.get("BUS_ARR_STAT").toString());
//        arrivalCity.setText(b.get("BUS_ARR_CITY").toString());
//        arrivalAdd.setText(b.get("BUS_ARR_ADD").toString());
//    }
}