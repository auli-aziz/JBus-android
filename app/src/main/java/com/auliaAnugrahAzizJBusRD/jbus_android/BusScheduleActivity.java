package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.BaseResponse;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusScheduleActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private TextView busTitle;
    private EditText busSchedule;
    private Button addSchedule;
    private int busId;
    private String busName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        busTitle = findViewById(R.id.bus_name);
        busSchedule = findViewById(R.id.bus_schedule);
        addSchedule = findViewById(R.id.add_schedule);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null) {
            busId = Integer.parseInt(b.get("BUS_ID").toString());
            busName = b.get("BUS_NAME").toString();
            Toast.makeText(mContext, "Bus id: " + busId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Cannot retrieve bus Id", Toast.LENGTH_SHORT).show();
        }

        busTitle.setText(busName);

        addSchedule.setOnClickListener(v -> {
            handleAddSchedule();
        });
    }

    protected void handleAddSchedule() {
        String busScheduleS = busSchedule.getText().toString();
        if(busScheduleS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.addSchedule(busId, busScheduleS).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Bus> res = response.body();
                if (res.success) {
                    Toast.makeText(mContext, "Berhasil menambahkan schedule", Toast.LENGTH_SHORT);
                    finish();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}