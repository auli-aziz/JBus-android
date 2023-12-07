package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.BaseResponse;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private TextView initial, username, email, balance;
    private Button topUp;
    private EditText amount;
    private Locale locale = new Locale("id", "ID");
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

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

        String formattedBalance = currencyFormat.format(LoginActivity.loggedAccount.balance);

        initial.setText(LoginActivity.loggedAccount.name.substring(0, 1));
        username.setText(LoginActivity.loggedAccount.name);
        email.setText(LoginActivity.loggedAccount.email);
        balance.setText(formattedBalance);

        topUp.setOnClickListener(v -> {
            handleTopUp();
        });

        renterSection();
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void renterSection() {
        LinearLayout ll = findViewById(R.id.renter_layout);
        TextView renterStatus = new TextView(this);

        renterStatus.setTextColor(getResources().getColor(R.color.black));
        renterStatus.setTextSize(17);

        if(LoginActivity.loggedAccount.company == null) {
            TextView registerCompany = new TextView(this);
            registerCompany.setTextColor(getResources().getColor(R.color.black));
            registerCompany.setTextSize(20);

            renterStatus.setText("You're not registered as a renter");
            registerCompany.setText("Register Here");
            registerCompany.setTypeface(null, Typeface.BOLD);

            registerCompany.setOnClickListener(v -> {
                moveActivity(this, RegisterRenterActivity.class);
            });

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            ll.addView(renterStatus, lp);
            ll.addView(registerCompany, lp);
        } else {
            Button manageBus = new Button(this);

            renterStatus.setText("You're already registered as renter");
            manageBus.setText("Manage Bus");
            manageBus.setTextSize(16);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            manageBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveActivity(mContext, ManageBusActivity.class);
                }
            });

            ll.addView(renterStatus, lp);
            ll.addView(manageBus, lp);
        }
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