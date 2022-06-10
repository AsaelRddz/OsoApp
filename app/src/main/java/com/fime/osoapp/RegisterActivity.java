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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText et_nombre, et_apellido, et_correo_electronico, et_password, et_confirmar_password;
    Button btn_registrar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Instancian las views con los atributos
        iniciarViews();
        inicializarFirebase();
        btn_registrar.setOnClickListener(v -> {
            registrar_cuenta();
        });

        getSupportActionBar().hide();
    }

    private void iniciarViews(){
        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_correo_electronico = findViewById(R.id.et_correo_electronico);
        et_password = findViewById(R.id.et_password);
        et_confirmar_password = findViewById(R.id.et_confirmar_password);
        btn_registrar = findViewById(R.id.btn_registrar);

        mAuth = FirebaseAuth.getInstance();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void registrar_cuenta(){
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String email = et_correo_electronico.getText().toString();
        String password = et_password.getText().toString();
        String confirmarPassword = et_confirmar_password.getText().toString();

        if (TextUtils.isEmpty(nombre)){
            et_nombre.setError("Ingresa un nombre");
            et_nombre.requestFocus();
        } else if(TextUtils.isEmpty(apellido)){
            et_apellido.setError("Ingresa un apellido");
            et_apellido.requestFocus();
        } else if (TextUtils.isEmpty(email)){
            et_correo_electronico.setError("Ingresa un correo");
            et_correo_electronico.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            et_password.setError("Ingrese una contraseña");
            et_password.requestFocus();
        } else if (TextUtils.isEmpty(confirmarPassword)){
            et_confirmar_password.setError("Confirme contraseña");
            et_confirmar_password.requestFocus();
        } else {
            if(password.equals(confirmarPassword)){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();


                        addUserToDatabase(nombre, apellido, mAuth.getCurrentUser().getUid());
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                });
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addUserToDatabase(String nombre, String apellido, String uid){
        mDbRef = FirebaseDatabase.getInstance().getReference();
        mDbRef.child("Usuarios").child(uid).setValue(new User(nombre,apellido,uid));
    }

    private void verifyEmail(FirebaseUser user) {
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if(task.isComplete()){
                Toast.makeText(RegisterActivity.this, "Email enviado, favor de verificarlo", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(RegisterActivity.this, "Error al enviar el email, intente de nuevo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}