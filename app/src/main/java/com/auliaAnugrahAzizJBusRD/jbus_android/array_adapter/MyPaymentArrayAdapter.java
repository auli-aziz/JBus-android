package com.auliaAnugrahAzizJBusRD.jbus_android.array_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Payment;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPaymentArrayAdapter extends ArrayAdapter<Payment> {
    private Context context;
    private BaseApiService mApiService;

    public MyPaymentArrayAdapter(@NonNull Context context, List<Payment> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // di sini terhubung dengan my_bus_view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_payment_view, parent, false);

            viewHolder = new MyPaymentArrayAdapter.ViewHolder();
            viewHolder.busName = convertView.findViewById(R.id.bus_name);
            viewHolder.busSeats = convertView.findViewById(R.id.bus_seats);
            viewHolder.busSched = convertView.findViewById(R.id.schedule);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.cancelButton = convertView.findViewById(R.id.cancel_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyPaymentArrayAdapter.ViewHolder) convertView.getTag();
        }

        Payment currentPaymentPosition = getItem(position);
        mApiService = UtilsApi.getApiService();

        handleGetBusDetails(viewHolder, currentPaymentPosition);
        viewHolder.busSeats.setText(currentPaymentPosition.busSeat.toString());
        viewHolder.busSched.setText(currentPaymentPosition.departureDate.toString());
        viewHolder.status.setText(currentPaymentPosition.status.toString());

        viewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.cancelButton.setEnabled(false);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView busName, busSeats, busSched, status;
        Button cancelButton;
    }

    protected void handleGetBusDetails(ViewHolder viewHolder, Payment currentPaymentPosition) {
        mApiService.getMyBusDetails(currentPaymentPosition.getBusId()).enqueue(new Callback<Bus>() {
            @Override
            public void onResponse(Call<Bus> call, Response<Bus> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Bus b = response.body();
                viewHolder.busName.setText(b.name);
            }


            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(context, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected  void handleCancel() {

    }
}
