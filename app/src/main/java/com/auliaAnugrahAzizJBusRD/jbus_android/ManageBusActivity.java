package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.array_adapter.MyBusArrayAdapter;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBusActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private ListView myBusListView = null;
    private List<Bus> listBus = new ArrayList<>();
    public MyBusArrayAdapter myBusArrayAdapter;
    private ImageView addBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();
        handleGetMyBus();
        myBusArrayAdapter = new MyBusArrayAdapter(this, listBus);

        myBusListView = findViewById(R.id.myBusList);
        myBusListView.setAdapter(myBusArrayAdapter);

        addBus = findViewById(R.id.add_bus);

        addBus.setOnClickListener(v -> {
            moveActivity(mContext, AddBusActivity.class);
            finish();
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleGetMyBus() {
        mApiService.getMyBus(LoginActivity.loggedAccount.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Bus> busList = response.body();

                if (busList != null && !busList.isEmpty()) {
                    runOnUiThread(() -> {
                        listBus.clear();
                        listBus.addAll(busList);
                        myBusArrayAdapter.notifyDataSetChanged();
                    });
                } else {
                    Toast.makeText(mContext, "No buses found for the given account ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}