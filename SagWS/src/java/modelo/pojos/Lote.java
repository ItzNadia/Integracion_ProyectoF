package modelo.pojos;

public class Lote {

    private Integer idLote;
    private String nombre;
    private String descripcion;
    private Integer idEstatus;
    private String estatus;
    private Integer idRancho;
    private String rancho;
    private String fechaAlta;
    private Integer idUsuarioAlta;
    private String usuarioAlta;
    private String fechaEdicion;
    private Integer idUsuarioEditor;
    private String usuarioEditor;

    public Lote() {
    }

    public Lote(Integer idLote, String nombre, String descripcion, Integer idEstatus, String estatus, Integer idRancho, String rancho, String fechaAlta, Integer idUsuarioAlta, String usuarioAlta, String fechaEdicion, Integer idUsuarioEditor, String usuarioEditor) {
        this.idLote = idLote;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idEstatus = idEstatus;
        this.estatus = estatus;
        this.idRancho = idRancho;
        this.rancho = rancho;
        this.fechaAlta = fechaAlta;
        this.idUsuarioAlta = idUsuarioAlta;
        this.usuarioAlta = usuarioAlta;
        this.fechaEdicion = fechaEdicion;
        this.idUsuarioEditor = idUsuarioEditor;
        this.usuarioEditor = usuarioEditor;
    }

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
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
