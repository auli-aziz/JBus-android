package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;


public class LoginActivity extends AppCompatActivity {
//    Button loginBtn;
    private TextView registerNow = null;
    private Button loginButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        registerNow = findViewById(R.id.register_now);
        loginButton = findViewById(R.id.login_button);

        registerNow.setOnClickListener(v -> {
            moveActivity(this, RegisterActivity.class);
            viewToast(this, "Moved to register from login");
        });

        loginButton.setOnClickListener(v -> {
            moveActivity(this, MainActivity.class);
            viewToast(this, "Moved to main from login");
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