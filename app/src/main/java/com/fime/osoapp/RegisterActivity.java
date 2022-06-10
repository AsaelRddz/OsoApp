package com.fime.osoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fime.osoapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Verify;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class RegisterActivity extends AppCompatActivity {

    private String userID;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText et_nombre, et_apellido, et_email, et_password, et_confirmar_password;
    Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Instancian las views con los atributos
        iniciarViews();
        iniciarFirebase();

        btn_registrar.setOnClickListener(v -> {
            crearUsuario();
        });

        getSupportActionBar().hide();
    }

    private void iniciarFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void crearUsuario() {
        String nombre = et_nombre.getText().toString().trim();
        String apellido = et_apellido.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String passwordC = et_confirmar_password.getText().toString().trim();

        if(TextUtils.isEmpty(nombre)){
            et_nombre.setError("Ingresa tu nombre");
            et_nombre.requestFocus();
        } else if(TextUtils.isEmpty(apellido)){
            et_apellido.setError("Ingresa tu apellido");
        } else if(TextUtils.isEmpty(email)){
            et_email.setError("Ingresa tu correo universitario");
            et_email.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            et_password.setError("Ingresa una contraseña");
            et_password.requestFocus();
        } else if (TextUtils.isEmpty(passwordC)){
            et_confirmar_password.setError("Confirme la cotraseña");
            et_confirmar_password.requestFocus();
        } else {
            if(password.equals(passwordC)){
                if (email.contains("uanl.edu.mx")){
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                userID = mAuth.getCurrentUser().getUid();

                                Map<String, Object> user = new HashMap<>();
                                user.put("nombre", nombre);
                                user.put("apellido", apellido);
                                user.put("email", email);
                                user.put("password", password);

                                // Se creaa un hilo en la base de datos con nombre "users"
                                mDatabase.child("Users").child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            verifyEmail();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error, intente con otra cuenta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "Verique que sea un correo universitario", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void verifyEmail() {

        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registro exitoso, verifique su correo probablemente en la seccion spam", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Hubo un error, no fue posible enviar el correo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    private void iniciarViews(){
        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmar_password = findViewById(R.id.et_confirmar_password);
        btn_registrar = findViewById(R.id.btn_registrar);
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

}