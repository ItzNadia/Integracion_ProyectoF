package sagfx.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Rancho;
import sagfx.model.Usuario;
import sagfx.utils.Window;

public class FormRanchoController implements Initializable {

    @FXML
    private Label lbl_nombreRancho;
    @FXML
    private TextField txt_nombreRancho;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_direccionRancho;
    @FXML
    private TextArea txt_direccionRancho;
    @FXML
    private TextField txt_nombreEncargadoRancho;
    @FXML
    private Label lbl_nombreEncargadoRancho;
    @FXML
    private CheckBox chb_estatus;
    @FXML
    private Label lbl_estatus;

    private Rancho rancho = null;
    private Boolean isNew = false;
    private HashMap<String, Object> context;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(HashMap<String, Object> context, Rancho rancho, Boolean isNew) {
        this.context = context;
        this.rancho = rancho;
        this.isNew = isNew;
        this.cargarRancho();
    }

    @FXML
    private void guardarRancho(ActionEvent event) {
        Usuario u = (Usuario) this.context.get("usuario");
        if (validar()) {
            try {
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("idRancho", this.rancho.getIdRancho());
                param.put("nombre", this.txt_nombreRancho.getText());
                param.put("direccion", this.txt_direccionRancho.getText());
                param.put("nombreEncargado", this.txt_nombreEncargadoRancho.getText());
                if (this.chb_estatus.isSelected()) {
                    param.put("idEstatus", 101);
                } else {
                    param.put("idEstatus", 102);
                }
                String respuesta;

                if (isNew) {
                    param.put("idUsuarioAlta", u.getIdUsuario());
                    respuesta = Requests.post("/rancho/registrarRancho", param);
                } else {
                    param.put("idUsuarioEditor", u.getIdUsuario());
                    respuesta = Requests.post("/rancho/editarRancho", param);
                }

                JSONObject dataJson = new JSONObject(respuesta);

                if ((boolean) dataJson.get("error")) {
                    Window.alertaError(dataJson.get("mensaje").toString());
                } else {
                    Window.close(event);
                    Window.alertaInformacion(dataJson.get("mensaje").toString());
                }
            } catch (JSONException ex) {
                Logger.getLogger(FormCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
                Window.alertaError("Error, verifique la información en intente nuevamente");
            }
        } else {
            Window.alertaAdvertencia("Favor de ingresar datos faltantes");
        }
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    public void cargarRancho() {
        if (!isNew) {
            if (rancho.getIdEstatus() == 101) {
                this.chb_estatus.setText("Activo");
                this.chb_estatus.setSelected(true);
            } else {
                this.chb_estatus.setText("Inactivo");
                this.chb_estatus.setSelected(false);
            }
            this.txt_nombreRancho.setText(rancho.getNombre());
            this.txt_direccionRancho.setText(rancho.getDireccion());
            this.txt_nombreEncargadoRancho.setText(rancho.getNombreEncargado());
        }
    }

    private boolean validar() {
        if (!this.txt_nombreRancho.getText().isEmpty() && !this.txt_direccionRancho.getText().isEmpty() && !this.txt_nombreEncargadoRancho.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    @FXML
    private void checkEstatus(ActionEvent event) {
        if (this.chb_estatus.isSelected()) {
            this.chb_estatus.setText("Activo");
        } else {
            this.chb_estatus.setText("Inactivo");
        }
    }

}
