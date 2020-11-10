package com.example.agendadecontratosv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText txtcorreo;
    private EditText txtpassword;

    private FirebaseAuth mAuth;

    private Button btnlogin;
    private Button btnregistro;


    private String correo;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        txtcorreo = (EditText) findViewById(R.id.txtCorreo);
        txtpassword = (EditText) findViewById(R.id.txtPassword);

        btnlogin = (Button) findViewById(R.id.btnLogin);
        btnregistro = (Button) findViewById(R.id.btnRegistro);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = txtcorreo.getText().toString();
                password = txtpassword.getText().toString();

                if(!correo.isEmpty() && !password.isEmpty()){
                    if (!validarEmail(correo)){
                        txtcorreo.setError("Email no válido");
                    }else {
                        loginUser();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Complete campos", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrarseActivity.class
                ));
            }
        });

    }

    private void loginUser() {

        mAuth.signInWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { // Funcion de logeo
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, PaginaPrincipalActivity.class));
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "Verifique sus datos porfavor", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }




}