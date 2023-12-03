package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;

import java.util.List;

public class MyBusArrayAdapter extends ArrayAdapter<Bus> {
    private Context context;
    private TextView textView1;
    private ImageView calendar;
    private ImageView information;
    private String busName, selectedBusName;
    public MyBusArrayAdapter(@NonNull Context context, List<Bus> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            // di sini terhubung dengan my_bus_view
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.my_bus_view, parent, false);
        }

        Bus currentBusPosition = getItem(position);

        textView1 = currentItemView.findViewById(R.id.textView1);
        busName = currentBusPosition.name;
        textView1.setText(busName);

        calendar = currentItemView.findViewById(R.id.calendar);
        information = currentItemView.findViewById(R.id.information);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(context, BusScheduleActivity.class, currentBusPosition, 0);
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(context, BusDetailActivity.class, currentBusPosition, 1);
            }
        });

        return currentItemView;
    }

    private void moveActivity(Context ctx, Class<?> cls, Bus currPos, int forward) {
        Intent intent = new Intent(ctx, cls);
        intent.putExtra("BUS_ID", currPos.id);
        intent.putExtra("BUS_NAME", currPos.name);
//        if(forward == 1) forwardDetail(intent, currPos);
        ctx.startActivity(intent);
    }

//    private void forwardDetail(Intent intent, Bus currPos) {
//        intent.putExtra("BUS_FAC", currPos.facilities);
//        intent.putExtra("BUS_PRICE", currPos.price.price);
//        intent.putExtra("BUS_CAP", currPos.capacity);
//        intent.putExtra("BUS_TYPE", currPos.busType.toString());
//        intent.putExtra("BUS_DEP_STAT", currPos.departure.stationName);
//        intent.putExtra("BUS_DEP_CITY", currPos.departure.city);
//        intent.putExtra("BUS_DEP_ADD", currPos.departure.address);
//        intent.putExtra("BUS_ARR_STAT", currPos.arrival.stationName);
//        intent.putExtra("BUS_ARR_CITY", currPos.arrival.city);
//        intent.putExtra("BUS_ARR_ADD", currPos.arrival.address);
////        intent.putExtra("BUS_SCHED", currPos.schedules.toString());
//    }
}