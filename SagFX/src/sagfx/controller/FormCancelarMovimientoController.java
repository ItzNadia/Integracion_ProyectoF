package sagfx.controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Movimiento;
import sagfx.model.Usuario;
import sagfx.utils.Window;

public class FormCancelarMovimientoController implements Initializable {

    @FXML
    private Label lbl_motivoCancelacion;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private TextArea txt_motivoCancelacion;

    private HashMap<String, Object> context;
    private Movimiento movimiento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void guardarCancelacion(ActionEvent event) {
        if (!this.validar()) {
            try {
                String data = "";
                HashMap<String, Object> params = new LinkedHashMap<>();
                params.put("idMovimiento", this.movimiento.getIdMovimiento());
                params.put("motivoCancelacion", this.txt_motivoCancelacion.getText());
                params.put("idUsuarioEditor", ((Usuario) this.context.get("usuario")).getIdUsuario());

                data = Requests.post("/movimiento/cancelarMovimiento", params);
                JSONObject dataJson = new JSONObject(data);

                if (!(boolean) dataJson.get("error")) {
                    Window.close(event);
                    Window.alertaInformacion(dataJson.getString("mensaje"));
                } else {
                    Window.alertaError(dataJson.getString("mensaje"));
                }
            } catch (JSONException ex) {
                Logger.getLogger(FormCancelarMovimientoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Window.alertaAdvertencia("Favor de ingresar un motivo de cancelación...");
        }
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    public void setData(HashMap<String, Object> context, Movimiento movimiento) {
        this.context = context;
        this.movimiento = movimiento;
    }

    private boolean validar() {
        if (this.txt_motivoCancelacion.getText().isEmpty()) {
            return true;
        }
        return false;
    }
}
