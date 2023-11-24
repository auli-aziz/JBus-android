package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.BaseResponse;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    private TextView initial, username, email, balance;
    private Button topUp;
    private Context mContext;
    private BaseApiService mApiService;
    private EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        initial = findViewById(R.id.initial);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        balance = findViewById(R.id.balance);
        amount = findViewById(R.id.topUp);
        topUp = findViewById(R.id.topUp_button);

        initial.setText(LoginActivity.loggedAccount.name.substring(0, 1).toUpperCase());
        username.setText(LoginActivity.loggedAccount.name);
        email.setText(LoginActivity.loggedAccount.email);
        balance.setText("IDR " + LoginActivity.loggedAccount.balance);

        topUp.setOnClickListener(v -> {
            handleTopUp();
        });
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    protected void handleTopUp() {
        String amountS = amount.getText().toString();
        if(amountS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.topUp(LoginActivity.loggedAccount.id, Double.parseDouble(amountS)).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call,
                                   Response<BaseResponse<Double>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Double> res = response.body();
                if (res.success) {
                    LoginActivity.loggedAccount.balance += Double.parseDouble(amountS);
                    finish();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}