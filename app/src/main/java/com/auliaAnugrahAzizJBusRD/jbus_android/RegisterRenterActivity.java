package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.BaseResponse;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Renter;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private Button registerRenter;
    private EditText companyName, address, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        companyName = findViewById(R.id.company_name);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phone_number);
        registerRenter = findViewById(R.id.register_renter);

        registerRenter.setOnClickListener(v -> {
            handleRegisterRenter();
        });
    }

    protected void handleRegisterRenter() {
        String comapnyNameS = companyName.getText().toString();
        String addressS = address.getText().toString();
        String phoneNumberS = phoneNumber.getText().toString();
        if(comapnyNameS.isEmpty() || addressS.isEmpty() || phoneNumberS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.registerRenter(LoginActivity.loggedAccount.id, comapnyNameS, addressS, phoneNumberS).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Renter> res = response.body();
                if (res.success) {
                    Toast.makeText(mContext, "Berhasil mendaftar renter", Toast.LENGTH_SHORT);
                    LoginActivity.loggedAccount.company = res.payload;
                    finish();
                }
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}