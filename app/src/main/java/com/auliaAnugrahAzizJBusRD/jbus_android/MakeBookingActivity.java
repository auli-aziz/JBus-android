package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Schedule;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeBookingActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private int busId;
    private TextView seatPrice, departure, arrival, balance;
    private Button makeBooking;
    private Locale locale = new Locale("id", "ID");
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
    private Spinner schedSpinner;
    List<Schedule> schedList = new ArrayList<>();
    private Timestamp selectedSched;

    AdapterView.OnItemSelectedListener scheduleOISL = new
            AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    selectedSched = schedList.get(position).departureSchedule;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();

        seatPrice = findViewById(R.id.seat_price);
        departure = findViewById(R.id.departure);
        arrival = findViewById(R.id.arrival);
        balance = findViewById(R.id.balance);
        schedSpinner = findViewById(R.id.schedule_dropdown);

        makeBooking = findViewById(R.id.make_booking);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null) {
            busId = Integer.parseInt(b.get("BUS_ID").toString());
        } else {
            Toast.makeText(mContext, "Cannot retrieve bus detail", Toast.LENGTH_SHORT).show();
        }

        handleDetails();
        String formattedBalance = currencyFormat.format(LoginActivity.loggedAccount.balance);
        balance.setText(formattedBalance);

        Toast.makeText(this, "Bus id: " + busId, Toast.LENGTH_SHORT).show();
    }

    protected void handleMakeBooking() {

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

                schedList = response.body().schedules;

                String formattedPrice = currencyFormat.format(b.price.price);

                seatPrice.setText("-" + formattedPrice);
                departure.setText(b.departure.stationName);
                arrival.setText(b.arrival.stationName);

                if(schedList != null) {
                    ArrayAdapter busSched = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, schedList);
                    busSched.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    schedSpinner.setAdapter(busSched);

                    schedSpinner.setOnItemSelectedListener(scheduleOISL);
                }
            }


            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}