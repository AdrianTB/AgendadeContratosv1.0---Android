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

import java.util.Calendar;

import model.Contrato;

public class RegistarContratoActivity extends AppCompatActivity {


    private EditText txtcodigo;
    private EditText txtcliente;
    private EditText txtevento;
    private EditText txtdescripcion;
    private EditText txtadelanto;
    private EditText txtpendiente;
    private EditText txtfecha;


    private Button btnregistrar;

    private String codigo = "";
    private String cliente = "";
    private String evento = "";
    private String fecha = "";
    private String descripcion = "";
    private String adelanto = "";
    private String pendiente = "";

    Calendar c;
    DatePickerDialog dpd;
    private Conexion cnx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_contrato);

        txtcodigo  = (EditText) findViewById(R.id.txtCodigo);
        txtcliente  = (EditText) findViewById(R.id.txtCliente);
        txtevento  = (EditText) findViewById(R.id.txtEvento);
        txtfecha = (EditText)findViewById(R.id.txtFecha);
        txtdescripcion  = (EditText) findViewById(R.id.txtDescripcion);
        txtadelanto = (EditText) findViewById(R.id.txtAdelanto);
        txtpendiente = (EditText) findViewById(R.id.txtPendiente);

        btnregistrar = (Button)findViewById(R.id.btnRegistrar);


        cnx = new Conexion(RegistarContratoActivity.this);

        txtfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();

                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(RegistarContratoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtfecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        cnx.Abrir();
        txtcodigo.setText(cnx.generarCodigo());
        cnx.Cerrar();


        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codigo = txtcodigo.getText().toString();
                cliente = txtcliente.getText().toString();
                evento = txtevento.getText().toString();
                fecha = txtfecha.getText().toString();
                descripcion = txtdescripcion.getText().toString();
                adelanto = txtadelanto.getText().toString();
                pendiente = txtpendiente.getText().toString();


                if(txtcodigo.getText().toString().isEmpty() || txtcliente.getText().toString().isEmpty() || txtevento.getText().toString().isEmpty()|| txtdescripcion.getText().toString().isEmpty()
                        || txtadelanto.getText().toString().isEmpty() || txtpendiente.getText().toString().isEmpty()){
                    Toast.makeText(RegistarContratoActivity.this, "Complete todos los campos porfavor", Toast.LENGTH_LONG).show();
                }else {

                    Contrato obj_contrato = new Contrato(codigo,cliente,evento,fecha,descripcion,Float.parseFloat(adelanto),Float.parseFloat(pendiente));
                    String estado = "";

                    cnx.Abrir();
                    estado = cnx.RegistrarContrato(obj_contrato);
                    cnx.Cerrar();

                    Toast.makeText(RegistarContratoActivity.this, estado, Toast.LENGTH_SHORT).show();

                    Limpiar();

                    startActivity(new Intent(RegistarContratoActivity.this, PaginaPrincipalActivity.class));

                }
            }

        });


    }


    private void Limpiar() {
        txtcodigo.setText("");
        txtcliente.setText("");
        txtevento.setText("");
        txtfecha.setText("");
        txtdescripcion.setText("");
        txtadelanto.setText("");
        txtpendiente.setText("");

        txtcliente.requestFocus();
    }

}