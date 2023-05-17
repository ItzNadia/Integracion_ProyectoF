package sagfx.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONException;
import sagfx.api.requests.Requests;
import sagfx.model.Usuario;
import sagfx.utils.JavaUtils;
import sagfx.utils.Window;
import org.json.JSONObject;

public class LoginController implements Initializable {

    @FXML
    private Pane pnl_panelLogin;
    @FXML
    private Label lbl_usuario;
    @FXML
    private Label lbl_contrasena;
    @FXML
    private TextField txt_usuario;
    @FXML
    private TextField txt_contrasena;
    @FXML
    private Button btn_iniciar;
    @FXML
    private Button btn_cancelar;
    @FXML
    private ImageView img_banner;
    @FXML
    private ImageView img_logo;
    @FXML
    private Label lbl_mensaje;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        if (this.validar()) {
            try {
                String data = "";

                HashMap<String, Object> params = new LinkedHashMap<>();
                params.put("usuario", this.txt_usuario.getText());
                params.put("contrasena", this.txt_contrasena.getText());

                //Llamamos el servicio
                data = Requests.post("/sesion/login", params);
                JSONObject dataJson = new JSONObject(data);

                if ((Boolean) dataJson.get("error") == false) {
                    Stage stage = Window.getStageByEvent(event); //Lo mando a llamar y le mando el evento de iniciar sesion
                    Gson gson = new Gson();

                    Usuario user = gson.fromJson(dataJson.get("respuesta").toString(), Usuario.class);

                    HashMap<String, Object> context = new HashMap<String, Object>();
                    context.put("mac", JavaUtils.getMAC());
                    context.put("usuario", user);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/PrincipalFXML.fxml"));
                    Parent principal = loader.load();
                    PrincipalController ctrl = loader.getController();
                    ctrl.setData(context);
                    Scene scene = new Scene(principal);
                    stage.setScene(scene);
                    stage.setTitle("SAG (Sistema de Administración de Ganado)   |   Usuario: " + user.getUsuario());
                    stage.setResizable(false);
                    stage.show();
                } else {
                    this.lbl_mensaje.setText(dataJson.getString("mensaje"));
                }
            } catch (JSONException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.lbl_mensaje.setText("El usuario y contraseña son requeridos");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Window.close(event);
    }

    //Para validar si los campos estan vacios o no
    private boolean validar() {
        boolean valido = false;
        if (!this.txt_usuario.getText().isEmpty() && !this.txt_contrasena.getText().isEmpty()) {
            valido = true;
        }
        return valido;
    }
}
