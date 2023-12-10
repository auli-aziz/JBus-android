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
import com.auliaAnugrahAzizJBusRD.jbus_android.model.BaseResponse;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Invoice;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Payment;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentArrayAdapter extends ArrayAdapter<Payment> {

    private Context context;
    private BaseApiService mApiService;

    public PaymentArrayAdapter(@NonNull Context context, List<Payment> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.payment_view, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.busSeats = convertView.findViewById(R.id.bus_seats);
            viewHolder.departureDate = convertView.findViewById(R.id.departure_date);
            viewHolder.acceptButton = convertView.findViewById(R.id.acc_button);
            viewHolder.cancelButton = convertView.findViewById(R.id.cancel_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Payment currentPaymentPosition = getItem(position);
        mApiService = UtilsApi.getApiService();

        viewHolder.busSeats.setText(currentPaymentPosition.busSeat.toString());
        viewHolder.departureDate.setText(currentPaymentPosition.getDepartureDate());

        viewHolder.status = currentPaymentPosition.status;
        viewHolder.id = currentPaymentPosition.id;
        viewHolder.renterId = currentPaymentPosition.renterId;
        viewHolder.buyerId = currentPaymentPosition.buyerId;
        viewHolder.busId = currentPaymentPosition.getBusId();

        if(viewHolder.status == Invoice.PaymentStatus.SUCCESS) {
            viewHolder.acceptButton.setEnabled(false);
        }

        if(viewHolder.status == Invoice.PaymentStatus.FAILED) {
            viewHolder.acceptButton.setEnabled(false);
            viewHolder.cancelButton.setEnabled(false);
        }

        viewHolder.acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, "id: " +viewHolder.id, Toast.LENGTH_SHORT).show();
                viewHolder.acceptButton.setEnabled(false);
                handleAccept(viewHolder.id);
            }
        });

        viewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewHolder.acceptButton.setEnabled(false);
                viewHolder.cancelButton.setEnabled(false);
                Toast.makeText(context, "Id: " + viewHolder.id + "BuyerId: " + viewHolder.buyerId + "BusId: " + viewHolder.busId, Toast.LENGTH_LONG).show();
                handleCancel(viewHolder.id, viewHolder.buyerId, viewHolder.busId, viewHolder.renterId);
            }
        });


        return convertView;
    }

    static class ViewHolder {
        int id, buyerId, renterId, busId;
        Invoice.PaymentStatus status;
        TextView busSeats, departureDate;
        Button acceptButton, cancelButton;
    }

    protected void handleAccept(int id) {
        mApiService.acccept(id).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.body() != null) {
                    BaseResponse<Payment> res = response.body();
                } else {
                    Toast.makeText(context, "Response body is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(context, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void handleCancel(int id, int buyerId, int busId, int renterId) {
        mApiService.cancel(id, buyerId, busId, renterId).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.body() != null) {
                    BaseResponse<Payment> res = response.body();
                } else {
                    Toast.makeText(context, "Response body is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(context, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
