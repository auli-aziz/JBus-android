package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView loginNow = null;
    private Button registerButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        loginNow = findViewById(R.id.login_now);
        registerButton = findViewById(R.id.register_button);

        loginNow.setOnClickListener(v -> {
            moveActivity(this, LoginActivity.class);
            viewToast(this, "Moved to login from register");
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}