package model;

public class Contrato {

    private String codigo;
    private String cliente;
    private String evento;
    private String descripcion;
    private String fecha;
    private Float adelanto;
    private Float pendiente;


    public Contrato() { }

    public Contrato(String codigo, String cliente,String evento,String fecha, String descripcion, Float adelanto, Float pendiente) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.evento = evento;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.adelanto = adelanto;
        this.pendiente = pendiente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Float getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(Float adelanto) {
        this.adelanto = adelanto;
    }

    public Float getPendiente() {
        return pendiente;
    }

    public void setPendiente(Float pendiente) {
        this.pendiente = pendiente;
    }
}
