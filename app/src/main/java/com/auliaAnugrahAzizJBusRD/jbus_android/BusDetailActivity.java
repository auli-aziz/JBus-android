package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.transition.AutoTransition;
import android.transition.TransitionManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Facility;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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
    private CheckBox acCheckBox, wifiCheckBox, toiletCheckBox, lcdCheckBox;
    private CheckBox coolboxCheckBox, lunchCheckBox, baggageCheckBox, electricCheckBox;
    List<Facility> facList = new ArrayList<>();
    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;

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

        cardView = findViewById(R.id.facility_card);
        arrow = findViewById(R.id.arrow_button);
        hiddenView = findViewById(R.id.hidden_view);

        arrow.setOnClickListener(view -> {
            if (hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.baseline_expand_more_24);
            }

            else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.baseline_expand_less_24);
            }
        });

        busName = findViewById(R.id.name);
        busType = findViewById(R.id.type);
        busCapacity = findViewById(R.id.capacity);
        busPrice = findViewById(R.id.price);
        departureStat = findViewById(R.id.departure_station);
        departureAdd = findViewById(R.id.departure_address);
        arrivalStat = findViewById(R.id.arrival_station);
        arrivalAdd = findViewById(R.id.arrival_address);

        // Facility checkbox id assignment
        acCheckBox = findViewById(R.id.ac);
        wifiCheckBox = findViewById(R.id.wifi);
        toiletCheckBox = findViewById(R.id.toilet);
        lcdCheckBox = findViewById(R.id.lcd_tv);
        coolboxCheckBox = findViewById(R.id.coolbox);
        lunchCheckBox = findViewById(R.id.lunch);
        baggageCheckBox = findViewById(R.id.baggage);
        electricCheckBox= findViewById(R.id.electric_socket);

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
                departureAdd.setText(b.departure.address);
                arrivalAdd.setText(b.arrival.address);
                accountId = b.accountId;

                List<Facility> listFac = response.body().facility;

                if(listFac != null && !listFac.isEmpty()) {
                    runOnUiThread(() -> {
                        facList.clear();
                        facList.addAll(listFac);
                    });
                    handleCheckFac(facList);
                } else {
                    Toast.makeText(mContext, "No facilities found", Toast.LENGTH_SHORT).show();
                }

//                Toast.makeText(mContext, "AccountId: " + accountId, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void handleCheckFac(List<Facility> facility) {
        for(Facility f : facility) {
            if(f.equals(Facility.AC)) {
                acCheckBox.setChecked(true);
            }
            if(f.equals(Facility.WIFI)) {
                wifiCheckBox.setChecked(true);
            }
            if(f.equals(Facility.TOILET)) {
                toiletCheckBox.setChecked(true);
            }
            if(f.equals(Facility.LCD_TV)) {
                lcdCheckBox.setChecked(true);
            }
            if(f.equals(Facility.COOL_BOX)) {
                coolboxCheckBox.setChecked(true);
            }
            if(f.equals(Facility.LUNCH)) {
                lunchCheckBox.setChecked(true);
            }
            if(f.equals(Facility.LARGE_BAGGAGE)) {
                baggageCheckBox.setChecked(true);
            }
            if(f.equals(Facility.ELECTRIC_SOCKET)) {
                electricCheckBox.setChecked(true);
            }
        }
    }
}