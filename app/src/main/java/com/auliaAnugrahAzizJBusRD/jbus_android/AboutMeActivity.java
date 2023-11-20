package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView initial = findViewById(R.id.initial);
        TextView username = findViewById(R.id.username);
        TextView email = findViewById(R.id.email);
        TextView balance = findViewById(R.id.balance);

        initial.setText("A");
        username.setText("Aulia Anugrah Aziz");
        email.setText("aulia@gmail.com");
        balance.setText("IDR " + 9999999.0);
    }
}