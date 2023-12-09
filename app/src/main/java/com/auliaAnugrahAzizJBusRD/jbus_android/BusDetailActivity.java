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

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusDetailActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private TextView busName, busType, busCapacity, busPrice, departureStat, arrivalStat, departureAdd, departureCity, arrivalAdd, arrivalCity;
    private String name;
    private int busId;
    private Locale locale = new Locale("id", "ID");
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
    private int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();

        busName = findViewById(R.id.name);
        busType = findViewById(R.id.type);
        busCapacity = findViewById(R.id.capacity);
        busPrice = findViewById(R.id.price);
        departureStat = findViewById(R.id.departure_station);
//        departureCity = findViewById(R.id.departure_city);
        departureAdd = findViewById(R.id.departure_address);
        arrivalStat = findViewById(R.id.arrival_station);
//        arrivalCity = findViewById(R.id.arrival_city);
        arrivalAdd = findViewById(R.id.arrival_address);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null) {
            busId = Integer.parseInt(b.get("BUS_ID").toString());
            name = b.get("BUS_NAME").toString();
        } else {
            Toast.makeText(mContext, "Cannot retrieve bus detail", Toast.LENGTH_SHORT).show();
        }

        busName.setText(name);
        handleDetails();
    }

    protected void handleDetails() {
        mApiService.getBusDetails(busId).enqueue(new Callback<Bus>() {
            @Override
            public void onResponse(Call<Bus> call, Response<Bus> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Bus b = response.body();
                busType.setText(b.busType.toString());
                busCapacity.setText(b.getCapacity());
                String formattedPrice = currencyFormat.format(b.price.price);
                busPrice.setText(formattedPrice);
                departureStat.setText(b.departure.stationName);
                arrivalStat.setText(b.arrival.stationName);
//                departureCity.setText(b.getDepartureCity());
                departureAdd.setText(b.departure.address);
//                arrivalCity.setText(b.getArrivalCity());
                arrivalAdd.setText(b.arrival.address);
                accountId = b.accountId;

                Toast.makeText(mContext, "AccountId: " + accountId, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}