package com.fime.osoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    TextView olvido_password, registrar_cuenta;
    EditText et_email, et_password;
    Button btn_iniciar_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Instancian las views con los atributos
        iniciarViews();
        iniciarFirebase();

        // Ocultar ActionBar
        getSupportActionBar().hide();

        // On click
        olvido_password.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
            CustomIntent.customType(this, "left-to-right");
        });
        registrar_cuenta.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            CustomIntent.customType(this, "left-to-right");

        });
        btn_iniciar_sesion.setOnClickListener(v -> {
            userLogin();
        });
    }

    private void iniciarFirebase(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    private void iniciarViews(){
        olvido_password = findViewById(R.id.olvido_password);
        registrar_cuenta = findViewById(R.id.registrar_cuenta);
        btn_iniciar_sesion = findViewById(R.id.btn_iniciar_sesion);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
    }

    private void userLogin() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            et_email.setError("Ingrese el email");
            et_email.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            et_password.setError("Ingrese la contraseña");
            et_password.requestFocus();
        } else {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(user.isEmailVerified()) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, FeedActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Usuario no encontrado/verificado", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(LoginActivity.this, "Verifique su email", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    // Nos permite no ir hacia atras para evitar regresar a la splash screen
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

}