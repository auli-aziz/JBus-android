package com.auliaAnugrahAzizJBusRD.jbus_android.array_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.BusDetailActivity;
import com.auliaAnugrahAzizJBusRD.jbus_android.BusScheduleActivity;
import com.auliaAnugrahAzizJBusRD.jbus_android.PaymentRequestsActivity;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;

import java.util.List;

public class MyBusArrayAdapter extends ArrayAdapter<Bus> {
    private Context context;
    private TextView textView1;
    private ImageView calendar, information;
    private LinearLayout myBusView;
//    private String busName;
    public MyBusArrayAdapter(@NonNull Context context, List<Bus> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.my_bus_view, parent, false);
        }

        Bus currentBusPosition = getItem(position);

        textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentBusPosition.name);

        myBusView = currentItemView.findViewById(R.id.my_bus_view);
        calendar = currentItemView.findViewById(R.id.calendar);
        information = currentItemView.findViewById(R.id.information);

        myBusView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveActivity(context, BusDetailActivity.class, currentBusPosition);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(context, BusScheduleActivity.class, currentBusPosition);
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(context, PaymentRequestsActivity.class, currentBusPosition);
            }
        });

        return currentItemView;
    }

    private void moveActivity(Context ctx, Class<?> cls, Bus currPos) {
        Intent intent = new Intent(ctx, cls);
        intent.putExtra("BUS_ID", currPos.id);
        intent.putExtra("BUS_NAME", currPos.name);
        ctx.startActivity(intent);
    }
}