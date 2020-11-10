package com.example.agendadecontratosv10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {


    private EditText txtnombre;
    private EditText txtcorreo;
    private EditText txtpassword;

    private Button btnregistrar;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String name = "";
    private String correo = "";
    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        txtnombre = (EditText) findViewById(R.id.txtNombre);
        txtcorreo = (EditText) findViewById(R.id.txtCorreo);
        txtpassword = (EditText) findViewById(R.id.txtPassword);

        btnregistrar = (Button) findViewById(R.id.btnRegistro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtnombre.getText().toString();
                correo = txtcorreo.getText().toString();
                password = txtpassword.getText().toString();

                if (!name.isEmpty() && !correo.isEmpty() && !password.isEmpty()){

                    if(password.length()>=6){
                        registerUser();
                    }else {
                        Toast.makeText(RegistrarseActivity.this, "La contraseña debe tener al menos 6 carácteres ", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(RegistrarseActivity.this, "Complete Campos", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void registerUser() {

        mAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){ // Si se registro satisfactoriamente

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("correo",correo);
                    map.put("password",password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(RegistrarseActivity.this, "Usuario Registrado", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegistrarseActivity.this,PaginaPrincipalActivity.class));
                                finish();
                            }else {
                                Toast.makeText(RegistrarseActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(RegistrarseActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}