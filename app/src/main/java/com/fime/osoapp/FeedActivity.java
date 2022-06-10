package com.fime.osoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedActivity extends AppCompatActivity {

    Button btn_cerrar_sesion;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        iniciarFirebase();
        btn_cerrar_sesion = findViewById(R.id.btn_cerrar_sesion);

        btn_cerrar_sesion.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(FeedActivity.this, LoginActivity.class));
        });
    }

    private void iniciarFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}