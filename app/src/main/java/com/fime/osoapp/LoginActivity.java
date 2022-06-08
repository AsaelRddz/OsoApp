package com.fime.osoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView olvido_contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        olvido_contrasena = findViewById(R.id.olvido_contrasena);

        olvido_contrasena.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });
    }

    // Nos permite no ir hacia atras para evitar regresar a la splash screen
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}