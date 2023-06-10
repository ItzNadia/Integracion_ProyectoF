package modelo.pojos;

public class Cria {

    private Integer idCria;
    private Integer idHatoMadre;
    private String sexo;
    private String fechaNacimiento;
    private Integer idRaza;
    private String raza;
    private Integer idEstatus;
    private String estatus;
    private String observaciones;
    private Integer idRancho;
    private String rancho;
    private String fechaAlta;
    private Integer idUsuarioAlta;
    private String usuarioAlta;
    private String fechaEdicion;
    private Integer idUsuarioEditor;
    private String usuarioEditor;

    public Cria() {
    }

    public Cria(Integer idCria, Integer idHatoMadre, String sexo, String fechaNacimiento, Integer idRaza, String raza, Integer idEstatus, String estatus, String observaciones, Integer idRancho, String rancho, String fechaAlta, Integer idUsuarioAlta, String usuarioAlta, String fechaEdicion, Integer idUsuarioEditor, String usuarioEditor) {
        this.idCria = idCria;
        this.idHatoMadre = idHatoMadre;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.idRaza = idRaza;
        this.raza = raza;
        this.idEstatus = idEstatus;
        this.estatus = estatus;
        this.observaciones = observaciones;
        this.idRancho = idRancho;
        this.rancho = rancho;
        this.fechaAlta = fechaAlta;
        this.idUsuarioAlta = idUsuarioAlta;
        this.usuarioAlta = usuarioAlta;
        this.fechaEdicion = fechaEdicion;
        this.idUsuarioEditor = idUsuarioEditor;
        this.usuarioEditor = usuarioEditor;
    }

    public Integer getIdCria() {
        return idCria;
    }

    public void setIdCria(Integer idCria) {
        this.idCria = idCria;
    }

    public Integer getIdHatoMadre() {
        return idHatoMadre;
    }

    public void setIdHatoMadre(Integer idHatoMadre) {
        this.idHatoMadre = idHatoMadre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Integer idRaza) {
        this.idRaza = idRaza;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
