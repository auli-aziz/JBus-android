package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Account;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.BaseResponse;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Payment;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Schedule;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    private EditText busSeat;
    private Button makeBooking;
    private Locale locale = new Locale("id", "ID");
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
    private Spinner schedSpinner;
    List<Schedule> schedList = new ArrayList<>();
    private Timestamp selectedSched;
    private int renterId, accountId;

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
        busSeat = findViewById(R.id.seat_booking);

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
//        handleGetRenterId();


        makeBooking.setOnClickListener(v -> {
            handleMakeBooking();
        });
    }

//    protected void handleGetRenterId() {
//        mApiService.getById(accountId).enqueue(new Callback<Account>() {
//            @Override
//            public void onResponse(Call<Account> call, Response<Account> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                renterId = response.body().company.id;
//                Toast.makeText(mContext, "renter: " + renterId, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<Account> call, Throwable t) {
//                Toast.makeText(mContext, "Problem with the server 1", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    // TODO: bisa beli bus sendiri
    protected void handleMakeBooking() {
        String busSeatS = busSeat.getText().toString();
        String[] stringArray = busSeatS.split(",");
        List<String> seatList = Arrays.asList(stringArray);

        if(busSeatS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.makeBooking(LoginActivity.loggedAccount.id, renterId, busId, seatList, selectedSched.toString()).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Payment> res = response.body();

                if (res.success) {
                    Toast.makeText(mContext, "Berhasil menambahkan bus", Toast.LENGTH_SHORT);
                    finish();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server 2", Toast.LENGTH_SHORT).show();
            }
        });
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

                String formattedPrice = currencyFormat.format(b.price.price);

                seatPrice.setText("- " + formattedPrice);
                departure.setText(b.departure.stationName);
                arrival.setText(b.arrival.stationName);

                accountId = b.accountId;

                mApiService.getById(accountId).enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        renterId = response.body().company.id;

                        Toast.makeText(mContext, "RenterId: " + renterId, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        Toast.makeText(mContext, "Problem with the server 1", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(mContext, "AccountId: " + accountId, Toast.LENGTH_SHORT).show();

                schedList = response.body().schedules;

                if(schedList != null) {
                    ArrayAdapter busSched = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, schedList);
                    busSched.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    schedSpinner.setAdapter(busSched);

                    schedSpinner.setOnItemSelectedListener(scheduleOISL);
                } else {
                    Toast.makeText(mContext, "No Schedules Available", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server 3", Toast.LENGTH_SHORT).show();
            }
        });
    }
}