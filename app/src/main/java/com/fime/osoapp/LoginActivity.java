package com.fime.osoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView olvido_password, registrar_cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        olvido_password = findViewById(R.id.olvido_password);
        registrar_cuenta = findViewById(R.id.registrar_cuenta);

        getSupportActionBar().hide();

        // On click
        olvido_password.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });
        registrar_cuenta.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    // Nos permite no ir hacia atras para evitar regresar a la splash screen
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}