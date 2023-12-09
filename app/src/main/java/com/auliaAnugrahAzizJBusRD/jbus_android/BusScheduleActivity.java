package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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
    private TextView busTitle, showDate, showTime;
    private Button addSchedule, pickDate, pickTime;
    private int busId;
    private String busName, year, month, dateOfMonth, hourOfDay, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        busTitle = findViewById(R.id.bus_name);
        addSchedule = findViewById(R.id.add_schedule);

        showDate = findViewById(R.id.show_date);
        showTime = findViewById(R.id.show_time);

        pickDate = findViewById(R.id.date_picker);
        pickTime = findViewById(R.id.time_picker);

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

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog();
            }
        });

        addSchedule.setOnClickListener(v -> {
            handleAddSchedule();
        });
    }

    private void openDateDialog() {
        DatePickerDialog dateDialog = new DatePickerDialog(mContext, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                showDate.setText(String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth));
            }
        },
                2024, 0, 1);
        dateDialog.show();
    }

    private void openTimeDialog() {
        TimePickerDialog timeDialog = new TimePickerDialog(mContext, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                showTime.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00");
            }
        }, 00, 00, true);
        timeDialog.show();
    }

    protected void handleAddSchedule() {
        String busScheduleS = showDate.getText().toString() + " " + showTime.getText().toString();
        if(busScheduleS.equals(" ")) {
            Toast.makeText(mContext, "Please set your schedule", Toast.LENGTH_SHORT).show();
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