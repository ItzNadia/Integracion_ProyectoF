package sagfx.controller;

import com.google.gson.Gson;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Usuario;
import sagfx.utils.Window;

public class FormCambiarContrasenaUsuarioController implements Initializable {

    @FXML
    private PasswordField txt_contrasena;
    @FXML
    private PasswordField txt_nuevaContrasena;
    @FXML
    private PasswordField txt_repetirNuevaContrasena;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;

    private HashMap<String, Object> context;
    private Usuario usuario = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(HashMap<String, Object> context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
    }

    @FXML
    private void cambiarContrasena(ActionEvent event) {
        if (this.validar()) {
            if (this.txt_nuevaContrasena.getText().equals(this.txt_repetirNuevaContrasena.getText())) {
                if (!this.txt_contrasena.getText().equals(this.txt_nuevaContrasena.getText())) {
                    try {
                        String data = "";
                        HashMap<String, Object> usuarioABuscar = new LinkedHashMap<>();
                        usuarioABuscar.put("usuario", this.usuario.getUsuario());
                        usuarioABuscar.put("contrasena", this.txt_contrasena.getText());

                        data = Requests.post("/sesion/login", usuarioABuscar);
                        JSONObject dataJson = new JSONObject(data);

                        if (!(boolean) dataJson.get("error")) {
                            Gson gson = new Gson();
                            Usuario user = gson.fromJson(dataJson.get("respuesta").toString(), Usuario.class);

                            if (user.getContrasena().equals(this.usuario.getContrasena())) {
                                HashMap<String, Object> param = new LinkedHashMap<>();
                                param.put("idUsuario", this.usuario.getIdUsuario());
                                param.put("contrasena", this.txt_nuevaContrasena.getText());
                                param.put("idUsuarioEditor", ((Usuario) this.context.get("usuario")).getIdUsuario());

                                data = Requests.post("/usuario/cambiarContrasenaUsuario", param);
                                dataJson = new JSONObject(data);

                                if (!dataJson.getBoolean("error")) {
                                    Window.close(event);
                                    Window.alertaInformacion(dataJson.getString("mensaje"));
                                } else {
                                    Window.alertaError(dataJson.getString("mensaje"));
                                }
                            }
                        } else {
                            Window.alertaError("Contraseña del usuario '" + this.usuario.getUsuario() + "' incorrecta");
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(FormCambiarContrasenaUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Window.alertaError("La contraseña nueva no puede ser igual a la anterior");
                }
            } else {
                Window.alertaError("Error en la verificación de la nueva contraseña, intenta nuevamente");
            }
        } else {
            Window.alertaAdvertencia("Favor de llenar campos faltantes");
        }
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    private boolean validar() {
        if (this.txt_contrasena.getText().isEmpty() || this.txt_nuevaContrasena.getText().isEmpty() || this.txt_repetirNuevaContrasena.getText().isEmpty()) {
            return false;
        }
        return true;
    }
}
