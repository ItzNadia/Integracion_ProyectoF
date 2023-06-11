package modelo.pojos;

public class ConsultaMedica {

    private Integer idConsultaMedica;
    private Integer idHato;
    private String nombreVeterinario;
    private String fechaAtencion;
    private String observaciones;
    private Integer idMotivoAtencion;
    private String motivoAtencion;
    private Boolean cancelado;
    private Integer idRancho;
    private String rancho;
    private String fechaAlta;
    private Integer idUsuarioAlta;
    private String usuarioAlta;
    private String fechaEdicion;
    private Integer idUsuarioEditor;
    private String usuarioEditor;

    public ConsultaMedica() {
    }

    public ConsultaMedica(Integer idConsultaMedica, Integer idHato, String nombreVeterinario, String fechaAtencion, String observaciones, Integer idMotivoAtencion, String motivoAtencion, Boolean cancelado, Integer idRancho, String rancho, String fechaAlta, Integer idUsuarioAlta, String usuarioAlta, String fechaEdicion, Integer idUsuarioEditor, String usuarioEditor) {
        this.idConsultaMedica = idConsultaMedica;
        this.idHato = idHato;
        this.nombreVeterinario = nombreVeterinario;
        this.fechaAtencion = fechaAtencion;
        this.observaciones = observaciones;
        this.idMotivoAtencion = idMotivoAtencion;
        this.motivoAtencion = motivoAtencion;
        this.cancelado = cancelado;
        this.idRancho = idRancho;
        this.rancho = rancho;
        this.fechaAlta = fechaAlta;
        this.idUsuarioAlta = idUsuarioAlta;
        this.usuarioAlta = usuarioAlta;
        this.fechaEdicion = fechaEdicion;
        this.idUsuarioEditor = idUsuarioEditor;
        this.usuarioEditor = usuarioEditor;
    }

    public Integer getIdConsultaMedica() {
        return idConsultaMedica;
    }

    public void setIdConsultaMedica(Integer idConsultaMedica) {
        this.idConsultaMedica = idConsultaMedica;
    }

    public Integer getIdHato() {
        return idHato;
    }

    public void setIdHato(Integer idHato) {
        this.idHato = idHato;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public String getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(String fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getIdMotivoAtencion() {
        return idMotivoAtencion;
    }

    public void setIdMotivoAtencion(Integer idMotivoAtencion) {
        this.idMotivoAtencion = idMotivoAtencion;
    }

    public String getMotivoAtencion() {
        return motivoAtencion;
    }

    public void setMotivoAtencion(String motivoAtencion) {
        this.motivoAtencion = motivoAtencion;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
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
