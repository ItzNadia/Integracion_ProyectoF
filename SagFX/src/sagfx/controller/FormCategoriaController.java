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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Categoria;
import sagfx.utils.Window;

public class FormCategoriaController implements Initializable {

    @FXML
    private Label lbl_idCategoria;
    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_activo;
    @FXML
    private TextField txt_idCategoria;
    @FXML
    private TextField txt_nombre;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private CheckBox chb_activo;

    private Categoria categoria = null;
    private Boolean isNew = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Categoria categoria, Boolean isNew) {
        this.categoria = categoria;
        this.isNew = isNew;
        this.cargarCategoria();
    }

    public void cargarCategoria() {
        if (!isNew) {
            if ("S".equals(categoria.getActivo())) {
                this.chb_activo.setText("Sí");
                this.chb_activo.setSelected(true);
            } else {
                this.chb_activo.setText("No");
                this.chb_activo.setSelected(false);
            }
            this.txt_idCategoria.setText(categoria.getIdCategoria().toString());
            this.txt_nombre.setText(categoria.getNombre());
            this.txt_idCategoria.editableProperty().set(false);
        }
    }

    @FXML
    private void guardarCategoria(ActionEvent event) {
        if (validar()) {
            try {
                HashMap<String, Object> categoria = new HashMap<String, Object>();
                categoria.put("idCategoria", this.txt_idCategoria.getText());
                categoria.put("nombre", this.txt_nombre.getText());
                if (this.chb_activo.isSelected()) {
                    categoria.put("activo", "S");
                } else {
                    categoria.put("activo", "N");
                }
                String respuesta;

                if (isNew) {
                    respuesta = Requests.post("/categoria/registrarCategoria", categoria);
                } else {
                    respuesta = Requests.post("/categoria/editarCategoria", categoria);
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

    private boolean validar() {
        if (!this.txt_idCategoria.getText().isEmpty() && !this.txt_nombre.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    @FXML
    private void checkActivo(ActionEvent event) {
        if (this.chb_activo.isSelected()) {
            this.chb_activo.setText("Sí");
        } else {
            this.chb_activo.setText("No");
        }
    }

    @FXML
    private void restriccionNumeros(KeyEvent event) {
        if (event.getTarget() == this.txt_idCategoria) {
            if (!Character.isDigit(event.getCharacter().charAt(0))) {
                event.consume();
            }
        }
    }
}
