package com.example.agendadecontratosv10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import model.Contrato;

public class PaginaPrincipalActivity extends AppCompatActivity {


    private ImageView imgagregar;
    private ImageView imgbuscar;
    private Button btncerrar;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);



        imgagregar = (ImageView) findViewById(R.id.imgAgregar);
        imgbuscar = (ImageView) findViewById(R.id.imgBuscar);
        btncerrar = (Button) findViewById(R.id.btnCerrarSesion);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        imgagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaginaPrincipalActivity.this, RegistarContratoActivity.class));
            }
        });

        imgbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaginaPrincipalActivity.this, ListarContratosActivity.class
                ));
            }
        });

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(PaginaPrincipalActivity.this, MainActivity.class));
                finish();
            }
        });


    }


}