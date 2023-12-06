package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.array_adapter.MyPaymentArrayAdapter;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Payment;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    public MyPaymentArrayAdapter myPaymentArrayAdapter;
    private List<Payment> listPayment = new ArrayList<>();
    private ListView myPaymentListView = null;
    private Button cancelPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        myPaymentArrayAdapter = new MyPaymentArrayAdapter(this, listPayment);
        myPaymentListView = findViewById(R.id.my_payment_list);
        myPaymentListView.setAdapter(myPaymentArrayAdapter);

        handleGetPayments();
    }

    protected void handleGetPayments() {
        mApiService.getPayments(LoginActivity.loggedAccount.id).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Payment> paymentList = response.body();

                if (paymentList != null && !paymentList.isEmpty()) {
                    runOnUiThread(() -> {
                        listPayment.clear();
                        listPayment.addAll(paymentList);
                        myPaymentArrayAdapter.notifyDataSetChanged();
                    });
                } else {
                    Toast.makeText(mContext, "No buses found for the given account ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}