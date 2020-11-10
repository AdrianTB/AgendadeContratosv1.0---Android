package com.example.agendadecontratosv10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import model.Contrato;

public class DatosContratoActivity extends AppCompatActivity {

    private EditText txtcodigo;
    private EditText txtcliente;
    private EditText txtevento;
    private EditText txtfecha;
    private EditText txtdescripcion;
    private EditText txtadelanto;
    private EditText txtpendiente;

    private Button btnactualizar;
    private Button btnborrar;

    private Contrato obj_contrato;
    private Conexion cnx;

    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_contrato);

        txtcodigo = (EditText) findViewById(R.id.txtCodigo);
        txtcliente= (EditText) findViewById(R.id.txtCliente);
        txtevento = (EditText) findViewById(R.id.txtEvento);
        txtfecha = (EditText) findViewById(R.id.txtFecha);
        txtdescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtadelanto = (EditText) findViewById(R.id.txtAdelanto);
        txtpendiente = (EditText) findViewById(R.id.txtPendiente);

        btnactualizar = (Button) findViewById(R.id.btnActualizar);
        btnborrar = (Button) findViewById(R.id.btnBorrar);

        txtfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();

                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(DatosContratoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtfecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        cnx = new Conexion(this);

        String codigo = getIntent().getStringExtra("codigo"); // recibe parametro de ConsultaActivity


        cnx.Abrir();
        obj_contrato = cnx.BuscarPorCodigo(codigo);
        cnx.Cerrar();

        txtcodigo.setText(codigo);
        txtcliente.setText(obj_contrato.getCliente());
        txtevento.setText(obj_contrato.getEvento());
        txtdescripcion.setText(obj_contrato.getDescripcion());
        txtfecha.setText(obj_contrato.getFecha());
        txtadelanto.setText(Float.toString(obj_contrato.getAdelanto()));
        txtpendiente.setText(Float.toString(obj_contrato.getPendiente()));


        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obj_contrato.setCliente(txtcliente.getText().toString());
                obj_contrato.setEvento(txtevento.getText().toString());
                obj_contrato.setDescripcion(txtdescripcion.getText().toString());
                obj_contrato.setFecha(txtfecha.getText().toString());
                obj_contrato.setAdelanto(Float.parseFloat(txtadelanto.getText().toString()));
                obj_contrato.setPendiente(Float.parseFloat(txtpendiente.getText().toString()));


                String estado = "";

                cnx.Abrir();
                estado = cnx.ActualizarContrato(obj_contrato);
                cnx.Cerrar();

                Toast.makeText(DatosContratoActivity.this,estado,Toast.LENGTH_SHORT).show();

                Intent ac_listar = new Intent(DatosContratoActivity.this, ListarContratosActivity.class);
                startActivity(ac_listar);
            }
        });

        btnborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder confirmar = new AlertDialog.Builder(DatosContratoActivity.this);
                confirmar.setTitle("Eliminar Contrato");
                confirmar.setMessage("¿Está seguro que quiere eliminar el Contrato elegido ?");
                confirmar.setCancelable(false);

                confirmar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String estado = "";
                        cnx.Abrir();
                        estado= cnx.EliminarContrato(txtcodigo.getText().toString());
                        cnx.Cerrar();

                        Toast.makeText(DatosContratoActivity.this,estado,Toast.LENGTH_SHORT).show();

                        Intent ac_listar= new Intent(DatosContratoActivity.this, ListarContratosActivity.class);
                        startActivity(ac_listar);
                    }
                });
                confirmar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                confirmar.show();

            }
        });


    }
}