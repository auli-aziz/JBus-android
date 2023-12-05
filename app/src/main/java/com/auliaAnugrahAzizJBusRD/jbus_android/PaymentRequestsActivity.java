package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.array_adapter.PaymentArrayAdapter;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Payment;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRequestsActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private ListView paymentListView = null;
    private List<Payment> listPayment = new ArrayList<>();
    public PaymentArrayAdapter paymentArrayAdapter;
    private TextView busName;
    private int busId;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_requests);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();
        paymentArrayAdapter = new PaymentArrayAdapter(this, listPayment);

        busName = findViewById(R.id.bus_name);

        paymentListView = findViewById(R.id.payment_list);
        paymentListView.setAdapter(paymentArrayAdapter);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b != null) {
            busId = Integer.parseInt(b.get("BUS_ID").toString());
            name = b.get("BUS_NAME").toString();
        } else {
            Toast.makeText(mContext, "Cannot retrieve bus detail", Toast.LENGTH_SHORT).show();
        }

        busName.setText(name);
        handleGetPaymentRequests();
    }

    protected void handleGetPaymentRequests() {
        mApiService.getPaymentRequests(busId).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.body() != null) {
                    List<Payment> paymentList = response.body();
                    runOnUiThread(() -> {
                        listPayment.clear();
                        listPayment.addAll(paymentList);
                        paymentArrayAdapter.notifyDataSetChanged();
                    });
                    if (paymentList == null && paymentList.isEmpty()) {
                        Toast.makeText(mContext, "No payments found for the given bus ID", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Response body is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Log.e("PaymentRequestsActivity", "Error in API call", t);
                Toast.makeText(mContext, "Problem with the server*", Toast.LENGTH_SHORT).show();
            }
        });
    }
}