package modelo.pojos;

public class Movimiento {
    private Integer idMovimiento;
    private Double cantidadVenta;
    private String tipo;
    private String idConcepto;
    private String concepto;
    private String fecha;
    private String observaciones;
    private String cancelado;
    private String motivoCancelacion;
    private Integer idRancho;
    private String rancho;
    private String fechaAlta;
    private Integer idUsuarioAlta;
    private String usuarioAlta;
    private String fechaEdicion;
    private Integer idUsuarioEditor;
    private String usuarioEditor;

    public Movimiento() {
    }

    public Movimiento(Integer idMovimiento, Double cantidadVenta, String tipo, String idConcepto, String concepto, String fecha, String observaciones, String cancelado, String motivoCancelacion, Integer idRancho, String rancho, String fechaAlta, Integer idUsuarioAlta, String usuarioAlta, String fechaEdicion, Integer idUsuarioEditor, String usuarioEditor) {
        this.idMovimiento = idMovimiento;
        this.cantidadVenta = cantidadVenta;
        this.tipo = tipo;
        this.idConcepto = idConcepto;
        this.concepto = concepto;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.cancelado = cancelado;
        this.motivoCancelacion = motivoCancelacion;
        this.idRancho = idRancho;
        this.rancho = rancho;
        this.fechaAlta = fechaAlta;
        this.idUsuarioAlta = idUsuarioAlta;
        this.usuarioAlta = usuarioAlta;
        this.fechaEdicion = fechaEdicion;
        this.idUsuarioEditor = idUsuarioEditor;
        this.usuarioEditor = usuarioEditor;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Double getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(Double cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(String idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCancelado() {
        return cancelado;
    }

    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public Integer getIdRancho() {
        return idRancho;
    }

    public void setIdRancho(Integer idRancho) {
        this.idRancho = idRancho;
    }

    public String getRancho() {
        return rancho;
    }

    public void setRancho(String rancho) {
        this.rancho = rancho;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Integer getIdUsuarioAlta() {
        return idUsuarioAlta;
    }

    public void setIdUsuarioAlta(Integer idUsuarioAlta) {
        this.idUsuarioAlta = idUsuarioAlta;
    }

    public String getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public String getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(String fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public Integer getIdUsuarioEditor() {
        return idUsuarioEditor;
    }

    public void setIdUsuarioEditor(Integer idUsuarioEditor) {
        this.idUsuarioEditor = idUsuarioEditor;
    }

    public String getUsuarioEditor() {
        return usuarioEditor;
    }

    public void setUsuarioEditor(String usuarioEditor) {
        this.usuarioEditor = usuarioEditor;
    }
}
