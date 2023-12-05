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
            viewHolder.orderDate = convertView.findViewById(R.id.order_date);
            viewHolder.acceptButton = convertView.findViewById(R.id.acc_button);
            viewHolder.cancelButton = convertView.findViewById(R.id.cancel_button);
            viewHolder.isAccepted = false;
            viewHolder.isCanceled = false;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Payment currentPaymentPosition = getItem(position);
        mApiService = UtilsApi.getApiService();

        viewHolder.busSeats.setText(currentPaymentPosition.busSeat.toString());
        viewHolder.departureDate.setText(currentPaymentPosition.departureDate.toString());
        viewHolder.orderDate.setText(currentPaymentPosition.time.toString());
        viewHolder.id = currentPaymentPosition.id;
        viewHolder.status = currentPaymentPosition.status;

        if(viewHolder.status == Invoice.PaymentStatus.SUCCESS) {
            viewHolder.acceptButton.setEnabled(false);
        }

        if(viewHolder.status == Invoice.PaymentStatus.FAILED) {
            viewHolder.acceptButton.setEnabled(false);
            viewHolder.cancelButton.setEnabled(false);
        }

        viewHolder.acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewHolder.acceptButton.setEnabled(false);
                viewHolder.isAccepted = true;
//                handleAccept(viewHolder.id);
            }
        });

        viewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewHolder.acceptButton.setEnabled(false);
                viewHolder.cancelButton.setEnabled(false);
                // handleAccept();
            }
        });


        return convertView;
    }

    static class ViewHolder {
        int id;
        Invoice.PaymentStatus status;
        TextView busSeats, departureDate, orderDate;
        Button acceptButton, cancelButton;
        boolean isAccepted, isCanceled;
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

//    protected void handleCancel(int id, int buyerId, int busId) {
//        mApiService.cancel(id, buyerId, busId).enqueue(new Callback<BaseResponse<Payment>>() {
//            @Override
//            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(context, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (response.body() != null) {
//                    BaseResponse<Payment> res = response.body();
//                } else {
//                    Toast.makeText(context, "Response body is null", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
//                Toast.makeText(context, "Problem with the server", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
