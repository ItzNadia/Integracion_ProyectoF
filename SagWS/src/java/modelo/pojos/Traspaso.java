/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojos;

/**
 *
 * @author nait0
 */
public class Traspaso {
    private Integer idTraspaso;
    private Integer idLoteAnterior;
    private String loteAnterior;
    private Integer idLoteDestino;
    private String loteDestino;
    private Boolean cancelado;
    private String fechaCancelacion;
    private String motivoCancelacion;
    private Integer idRancho;
    private String rancho;
    private String fechaAlta;
    private Integer idUsuarioAlta;
    private String usuarioAlta;
    private String fechaEdicion;
    private Integer idUsuarioEditor;
    private String usuarioEditor;

    public Traspaso() {
    }

    public Traspaso(Integer idTraspaso, Integer idLoteAnterior, String loteAnterior, Integer idLoteDestino, String loteDestino, Boolean cancelado, String fechaCancelacion, String motivoCancelacion, Integer idRancho, String rancho, String fechaAlta, Integer idUsuarioAlta, String usuarioAlta, String fechaEdicion, Integer idUsuarioEditor, String usuarioEditor) {
        this.idTraspaso = idTraspaso;
        this.idLoteAnterior = idLoteAnterior;
        this.loteAnterior = loteAnterior;
        this.idLoteDestino = idLoteDestino;
        this.loteDestino = loteDestino;
        this.cancelado = cancelado;
        this.fechaCancelacion = fechaCancelacion;
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

    public Integer getIdTraspaso() {
        return idTraspaso;
    }

    public void setIdTraspaso(Integer idTraspaso) {
        this.idTraspaso = idTraspaso;
    }

    public Integer getIdLoteAnterior() {
        return idLoteAnterior;
    }

    public void setIdLoteAnterior(Integer idLoteAnterior) {
        this.idLoteAnterior = idLoteAnterior;
    }

    public String getLoteAnterior() {
        return loteAnterior;
    }

    public void setLoteAnterior(String loteAnterior) {
        this.loteAnterior = loteAnterior;
    }

    public Integer getIdLoteDestino() {
        return idLoteDestino;
    }

    public void setIdLoteDestino(Integer idLoteDestino) {
        this.idLoteDestino = idLoteDestino;
    }

    public String getLoteDestino() {
        return loteDestino;
    }

    public void setLoteDestino(String loteDestino) {
        this.loteDestino = loteDestino;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public String getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(String fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
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
