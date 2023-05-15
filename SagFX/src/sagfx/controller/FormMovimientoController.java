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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Movimiento;
import sagfx.utils.Alerta;
import sagfx.utils.Window;

/**
 * FXML Controller class
 *
 * @author nait0
 */
public class FormMovimientoController implements Initializable {

    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_cantidad;
    @FXML
    private TextField txt_cantidad;
    @FXML
    private Label lbl_tipo;
    @FXML
    private TextField txt_concepto;
    @FXML
    private Label lbl_celular;
    @FXML
    private TextField txt_fecha;
    @FXML
    private Label lbl_observaciones;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private CheckBox chb_movimiento;
    
    Movimiento movimiento = null;
    Boolean isNew = false;
    HashMap<String, Object> context;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(HashMap<String, Object> context, Movimiento movimiento, Boolean isNew) {
        this.context = context;
        this.movimiento = movimiento;
        this.isNew = isNew;
        //this.cargarMovimiento();
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    @FXML
    private void guardarMovimiento(ActionEvent event) {
        if(validar()){
            try {
                HashMap<String,Object> movimiento = new HashMap<String, Object> ();
                movimiento.put("cantidadVenta", this.txt_cantidad.getText());
                if (this.chb_movimiento.isSelected()) {
                    movimiento.put("tipo", "Ingreso");
                } else {
                    movimiento.put("tipo", "Egreso");
                }
                movimiento.put("concepto", this.txt_concepto.getText());
                movimiento.put("fecha", this.txt_fecha.getText());
                movimiento.put("observaciones", this.txt_observaciones.getText());
                //movimiento.put("idRancho", );
                //movimiento.put("idUsuarioAlta", );
                
                String respuesta;
                
                if(isNew){
                    respuesta = Requests.post("/movimiento/registrarMovimiento", movimiento);
                }else{
                    respuesta = Requests.post("/movimiento/editarMovimiento", movimiento);
                }
                
                JSONObject dataJson = new JSONObject(respuesta);
                
                if((boolean)dataJson.get("error")){
                    Window.close(event);
                    new Alerta("Error", dataJson.get("mensaje").toString());
                }else{
                    Window.close(event);
                    new Alerta("Hecho", dataJson.get("mensaje").toString());
                }
            } catch (JSONException ex) {
                Logger.getLogger(FormCatalogoController.class.getName()).log(Level.SEVERE, null, ex);
                new Alerta("Error", "Error, verifique la información en intente nuevamente");
            }
        }else{
            new Alerta("Advertencia", "Favor de ingresar datos faltantes");
        }
    }

    @FXML
    private void checkMovimiento(ActionEvent event) {
        if (this.chb_movimiento.isSelected()) {
            this.chb_movimiento.setText("Ingreso");
        } else {
            this.chb_movimiento.setText("Egreso");
        }
    }
    
    private boolean validar(){
        if(!this.txt_cantidad.getText().isEmpty() && !this.txt_concepto.getText().isEmpty() && !this.txt_fecha.getText().isEmpty() && !this.txt_observaciones.getText().isEmpty()){
            return true;
        }
        return false;
    }
    
}
