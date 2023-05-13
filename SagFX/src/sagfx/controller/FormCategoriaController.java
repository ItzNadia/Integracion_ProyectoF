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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Categoria;
import sagfx.utils.Alerta;
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
    private TextField txt_activo;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;

    Categoria categoria = null;
    Boolean isNew = false;

    /**
     * Initializes the controller class.
     */
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
            this.txt_activo.setText(categoria.getActivo());
            this.txt_idCategoria.setText(categoria.getIdCategoria().toString());
            this.txt_nombre.setText(categoria.getNombre());
        }
    }

    @FXML
    private void guardarCategoria(ActionEvent event) {
        if (validar()) {
            try {
                HashMap<String, Object> categoria = new HashMap<String, Object>();
                categoria.put("idCategoria", this.txt_idCategoria.getText());
                categoria.put("nombre", this.txt_nombre.getText());
                categoria.put("activo", this.txt_activo.getText());
                String respuesta;

                if (isNew) {
                    respuesta = Requests.post("/categoria/registrarCategoria", categoria);
                } else {
                    respuesta = Requests.post("/categoria/editarCategoria", categoria);
                }
                
                JSONObject dataJson = new JSONObject(respuesta);

                if((boolean)dataJson.get("error")){
                    new Alerta("Error", dataJson.get("mensaje").toString());
                }else{
                    new Alerta("Hecho", dataJson.get("mensaje").toString());
                }
            } catch (JSONException ex) {
                Logger.getLogger(FormCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
                new Alerta("Error", "Error, verifique la información en intente nuevamente");
            }
        } else {
            new Alerta("Advertencia", "Favor de ingresar datos faltantes");
        }
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    private boolean validar() {
        if (!this.txt_idCategoria.getText().isEmpty() && !this.txt_nombre.getText().isEmpty() && !this.txt_activo.getText().isEmpty()) {
            return true;
        }
        return false;
    }
}
