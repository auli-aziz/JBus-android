package com.auliaAnugrahAzizJBusRD.jbus_android.array_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.BusDetailActivity;
import com.auliaAnugrahAzizJBusRD.jbus_android.MakeBookingActivity;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;

import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {
    private Context context;

    public BusArrayAdapter(@NonNull Context context, List<Bus> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }

        Bus currentBusPosition = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentBusPosition.name);

        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText(currentBusPosition.departure.stationName.substring(9) + " > " + currentBusPosition.arrival.stationName.substring(9));

        TextView textView3 = currentItemView.findViewById(R.id.textView3);
        textView3.setText("Price: " + currentBusPosition.price.price);

        LinearLayout ll = currentItemView.findViewById(R.id.bus_item);
        Button book = currentItemView.findViewById(R.id.book_button);

        ll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveActivity(context, BusDetailActivity.class, currentBusPosition);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveActivity(context, MakeBookingActivity.class, currentBusPosition);
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