package com.fime.osoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import maes.tech.intentanim.CustomIntent;


public class MainActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_DISPLAY_LENGTH = 1500;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // String colorActionBar = "#FF000000";
        mAuth = FirebaseAuth.getInstance();

        // Ocultar el action bar
        getSupportActionBar().hide();

        // Al terminar la animacion del splash screen se dirije hacia el main
        new Handler().postDelayed(() -> {
            FirebaseUser user = mAuth.getCurrentUser();

            // Si no existe algun usuario activo se enviará al login
            if(user == null){
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
            } else {
                startActivity(new Intent(MainActivity.this, FeedActivity.class));
                CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
            }
        }, SPLASH_SCREEN_DISPLAY_LENGTH);
    }
}