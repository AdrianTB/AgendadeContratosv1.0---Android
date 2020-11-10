package com.example.agendadecontratosv10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.Contrato;

public class Conexion {

    private final Context CNT;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase BD;

    public Conexion( Context cnt){
        this.CNT = cnt;
        DBHelper = new DatabaseHelper(cnt);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static String BASE_DATOS = "BDBiblioteca.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context cnt){
            super(cnt, BASE_DATOS,null,VERSION);
        } // Constuctor

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("create table bd_contrato(codigo char(4) not null primary key," +
                        "cliente varchar(50) not null," +
                        "evento varchar(100) not null, "+
                        "descripcion varchar(180) not null," +
                        "fecha varchar(18) not null," +
                        "adelanto float not null," +
                        "pendiente float );");
            }catch (Exception e){
                e.printStackTrace(); ;
            }
        } // Creacion de BD

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }


    public void Abrir() {
        BD = DBHelper.getWritableDatabase(); // Opcion de escritura
        return;
    }

    public void Cerrar() {
        DBHelper.close();
    }

    String numero = "";
    public String generarCodigo(){

        String codigo="A001";

        Cursor reg_contrato = BD.rawQuery("select * from bd_contrato order by codigo desc", null);

        if(reg_contrato.moveToFirst()){
            codigo = reg_contrato.getString(0);
            numero = String.valueOf(Integer.parseInt(codigo.substring(2,4))+1);
            switch (numero.length()){
                case 1: codigo = codigo.substring(0,1)+"00"+numero;break;
                case 2: codigo = codigo.substring(0,1)+"0"+numero;break;
                case 3: codigo = codigo.substring(0,1)+numero;break;
            }

        }

        return codigo;
    }

    public String RegistrarContrato(Contrato obj_contrato) {

        String mensaje = "Contrato Registrado";

        long nr = 0;

        ContentValues contrato = new ContentValues();

        contrato.put("codigo", obj_contrato.getCodigo());
        contrato.put("cliente", obj_contrato.getCliente());
        contrato.put("evento", obj_contrato.getEvento());
        contrato.put("descripcion", obj_contrato.getDescripcion());
        contrato.put("fecha", obj_contrato.getFecha());
        contrato.put("adelanto", obj_contrato.getAdelanto());
        contrato.put("pendiente", obj_contrato.getPendiente());

        nr = BD.insert("bd_contrato", null, contrato); // Se inserta data ya que la opcion "getWritetableDatabase" está habilitada

        if (nr == -1 || nr == 0)
            mensaje = "Error al registrar el contrato";

        return mensaje;

    }

    public ArrayList<String> obtenerContrato() {

        Contrato obj_contrato = null;
        ArrayList<String> lista = new ArrayList<String>();


        Cursor reg_contrato = BD.rawQuery("select * from bd_contrato order by codigo", null);


        while (reg_contrato.moveToNext()) { // Recorrer cada registro de nuestra tabla

            obj_contrato = new Contrato();

            obj_contrato.setCodigo(reg_contrato.getString(0));
            obj_contrato.setCliente(reg_contrato.getString(1));
            obj_contrato.setEvento(reg_contrato.getString(2));
            obj_contrato.setFecha(reg_contrato.getString(3));
            obj_contrato.setFecha(reg_contrato.getString(4));
            obj_contrato.setPendiente(Float.parseFloat(reg_contrato.getString(6)));


            lista.add(obj_contrato.getCodigo()+" - "+obj_contrato.getCliente() + "  /  " +obj_contrato.getEvento()+"  /  "+obj_contrato.getFecha()+"   /   S/"+obj_contrato.getPendiente());

        }

        return lista;

    }

    public Contrato BuscarPorCodigo(String codigo){

        String[] parametro = {codigo};
        String[] campos = {"codigo","cliente","evento","descripcion","fecha","adelanto","pendiente"};

        Cursor reg_contrato = BD.query("bd_contrato",campos,"codigo = ?",parametro,null, null, null);

        if (reg_contrato.moveToFirst()) {

            Contrato obj_contrato= new Contrato();
            obj_contrato.setCodigo(reg_contrato.getString(0));
            obj_contrato.setCliente(reg_contrato.getString(1));
            obj_contrato.setEvento(reg_contrato.getString(2));
            obj_contrato.setDescripcion(reg_contrato.getString(3));
            obj_contrato.setFecha(reg_contrato.getString(4));
            obj_contrato.setAdelanto(reg_contrato.getFloat(5));
            obj_contrato.setPendiente(reg_contrato.getFloat(6));

            return  obj_contrato;

        }else {
            return  null;
        }

    }

    public String ActualizarContrato(Contrato obj_contrato) {


        String mensaje = "Contrato Actualizado";
        long nr = 0;

        ContentValues contrato = new ContentValues();

        contrato.put("cliente", obj_contrato.getCliente());
        contrato.put("evento", obj_contrato.getEvento());
        contrato.put("descripcion", obj_contrato.getDescripcion());
        contrato.put("fecha", obj_contrato.getFecha());
        contrato.put("adelanto", obj_contrato.getAdelanto());
        contrato.put("pendiente", obj_contrato.getPendiente());


        String[] parametro = {obj_contrato.getCodigo()};


        nr = BD.update("bd_contrato", contrato, "codigo = ?", parametro); // Se inserta data ya que la opcion "getWritetableDatabase" está habilitada

        if (nr == -1 || nr == 0)
            mensaje = "Error al actualizar datos";

        return mensaje;

    }

    public String EliminarContrato(String codigo){

        String mensaje = "Contrato Eliminado";
        long nr = 0;


        String[] parametro = {codigo};


        nr = BD.delete("bd_contrato","codigo = ?",parametro); // Se inserta data ya que la opcion "getWritetableDatabase" está habilitada

        if(nr == -1 || nr == 0)
            mensaje = "Error al Eliminar los datos";

        return mensaje;
    }


}
