package modelo.pojos;

public class Respuesta {
    private boolean error;
    private String mensaje;
    private Object respuesta;

    public Respuesta() {
    }

    public Respuesta(boolean error, String mensaje, Object respuesta) {
        this.error = error;
        this.mensaje = mensaje;
        this.respuesta = respuesta;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Object respuesta) {
        this.respuesta = respuesta;
    }
}
