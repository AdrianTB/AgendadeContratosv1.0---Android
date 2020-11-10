package com.example.agendadecontratosv10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ListarContratosActivity extends AppCompatActivity {

    private EditText txtbuscar;
    private ListView lvcontratos;

    private  Conexion cnx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contratos);

        txtbuscar = (EditText) findViewById(R.id.txtBuscar);
        lvcontratos = (ListView) findViewById(R.id.lvContratos);

        cnx = new Conexion(this);

        cnx.Abrir();
        ArrayAdapter adp = new ArrayAdapter(this,android.R.layout.simple_list_item_1,cnx.obtenerContrato());
        cnx.Cerrar();

        lvcontratos.setAdapter(adp);

        lvcontratos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String codigo = lvcontratos.getItemAtPosition(position).toString().substring(0,4);

                Intent ac_contrato = new Intent(ListarContratosActivity.this, DatosContratoActivity.class);
                ac_contrato.putExtra("codigo", codigo);
                startActivity(ac_contrato);
            }
        });

    }
}