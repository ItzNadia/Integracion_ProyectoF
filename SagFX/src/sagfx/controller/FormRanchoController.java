/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Rancho;
import sagfx.model.Usuario;
import sagfx.utils.Alerta;
import sagfx.utils.Window;

/**
 * FXML Controller class
 *
 * @author nait0
 */
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
    private Label lbl_nombreRancho1;
    @FXML
    private TextField txt_nombreEncargadoRancho;
    
    Rancho rancho = null;
    Boolean isNew = false;
    HashMap<String, Object> context;
    
    /**
     * Initializes the controller class.
     */
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
        Usuario u = (Usuario)this.context.get("usuario");
        if (validar()) {
            try {
                HashMap<String, Object> rancho = new HashMap<String, Object>();
                rancho.put("idRancho", this.rancho.getIdRancho());
                rancho.put("nombre", this.txt_nombreRancho.getText());
                rancho.put("direccion", this.txt_direccionRancho.getText());
                rancho.put("nombreEncargado", this.txt_nombreEncargadoRancho.getText());
                String respuesta;
                System.out.println(rancho);

                if (isNew) {
                    rancho.put("idUsuarioAlta", u.getIdUsuario());
                    respuesta = Requests.post("/rancho/registrarRancho", rancho);
                } else {
                    rancho.put("idUsuarioEditor", u.getIdUsuario());
                    respuesta = Requests.post("/rancho/editarRancho", rancho);
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
        System.out.println("idUsaurio: "+u.getIdUsuario());
    }
    
    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }
    
    public void cargarRancho() {
        if (!isNew) {
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
    
}
