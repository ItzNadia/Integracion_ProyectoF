/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sagfx.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sagfx.model.Usuario;
import sagfx.utils.Window;

/**
 * FXML Controller class
 *
 * @author nait0
 */
public class FormUsuarioController implements Initializable {

    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_apellidoPaterno;
    @FXML
    private TextField txt_apellidoPaterno;
    @FXML
    private Label lbl_apellidoMaterno;
    @FXML
    private TextField txt_apellidoMaterno;
    @FXML
    private Label lbl_celular;
    @FXML
    private TextField txt_celular;
    @FXML
    private Label lbl_usuario;
    @FXML
    private TextField txt_usuario;
    @FXML
    private Label lbl_rol;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_rol1;
    @FXML
    private TextField txt_nombre;
    @FXML
    private Label lbl_contrasena;
    @FXML
    private PasswordField txt_contrasena;
    @FXML
    private CheckBox chb_estatus;
    @FXML
    private CheckBox chb_rol;
    
    Usuario usuario = null;
    Boolean isNew = false;
    HashMap<String, Object> context;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(HashMap<String, Object> context, Usuario usuario, Boolean isNew) {
        this.context = context;
        this.usuario = usuario;
        this.isNew = isNew;
        this.cargarUsuario();
    }

    @FXML
    private void guardarUsuario(ActionEvent event) {
/*        Usuario u = (Usuario)this.context.get("usuario");
        if(validar()){
            try {
                
                HashMap<String,Object> param = new HashMap<String, Object> ();
                param.put("idUsuario", this.usuario.getIdUsuario());
                param.put("nombre", this.txt_nombre.getText());
                param.put("apellidoPaterno", this.txt_apellidoPaterno.getText());
                param.put("apellidoMaterno", this.txt_apellidoMaterno.getText());
                param.put("celular", this.txt_celular.getText());
                param.put("usuario", this.txt_usuario.getText());
                param.put("idRol", );
                param.put("idEstatus", );
                param.put("idUsuarioAlta", u.getIdUsuario());
                
                if (this.chb_movimiento.isSelected()) {
                    movimiento.put("tipo", "Ingreso");
                } else {
                    movimiento.put("tipo", "Egreso");
                }
                
                String respuesta;
                
                if(isNew){
                    movimiento.put("idUsuarioAlta", u.getIdUsuario());
                    respuesta = Requests.post("/movimiento/registrarMovimiento", movimiento);
                }else{
                    System.out.println(movimiento);
                    movimiento.put("idUsuarioEditor", u.getIdUsuario());
                    respuesta = Requests.post("/movimiento/editarMovimiento", movimiento);
                }
                
                JSONObject dataJson = new JSONObject(respuesta);
                
                if((boolean)dataJson.get("error")){
                    
                    Window.alertaError(dataJson.get("mensaje").toString());
                }else{
                    Window.close(event);
                    Window.alertaInformacion(dataJson.get("mensaje").toString());
                }
            } catch (JSONException ex) {
                Logger.getLogger(FormMovimientoController.class.getName()).log(Level.SEVERE, null, ex);
                Window.alertaError("Error, verifique la información en intente nuevamente");
            }
        }else{
            Window.alertaAdvertencia("Favor de ingresar datos faltantes");
        }*/
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    @FXML
    private void checkRol(ActionEvent event) {
        if (this.chb_rol.isSelected()) {
            this.chb_rol.setText("Administrador");
        } else {
            this.chb_rol.setText("Vaquero");
        }
    }

    @FXML
    private void checkEstatus(ActionEvent event) {
        if (this.chb_estatus.isSelected()) {
            this.chb_estatus.setText("Activo");
        } else {
            this.chb_estatus.setText("Inactivo");
        }
    }

    @FXML
    private void restriccionNumerosyTamano(KeyEvent event) {
        // && Integer.parseInt(.toString())>9
        if(event.getTarget() == this.txt_celular){
            if(!Character.isDigit(event.getCharacter().charAt(0))){
                event.consume();
            }
            if(!(this.txt_celular.lengthProperty().getValue()<10)){
                event.consume();
            }
        }
    }
    
    public void cargarUsuario() {
        if (!isNew) {
            if (usuario.getIdRol() == 201) {
                this.chb_rol.setText("Administrador");
                this.chb_rol.setSelected(true);
            } else {
                this.chb_rol.setText("Vaquero");
                this.chb_rol.setSelected(false);
            }
            if (usuario.getIdEstatus() == 101) {
                this.chb_rol.setText("Activo");
                this.chb_rol.setSelected(true);
            } else {
                this.chb_rol.setText("Inactivo");
                this.chb_rol.setSelected(false);
            }
            this.txt_nombre.setText(usuario.getNombre());
            this.txt_apellidoPaterno.setText(usuario.getApellidoPaterno());
            this.txt_apellidoMaterno.setText(usuario.getApellidoMaterno());
            this.txt_celular.setText(usuario.getCelular());
            this.txt_usuario.setText(usuario.getUsuario());
            
            this.txt_contrasena.setDisable(true);
            this.txt_contrasena.setOpacity(0);
            this.lbl_contrasena.setOpacity(0);
        }
    }
    
    private boolean validar(){
        if(!this.txt_nombre.getText().isEmpty() && !this.txt_apellidoPaterno.getText().isEmpty() && !this.txt_apellidoMaterno.getText().isEmpty() 
                && !this.txt_celular.getText().isEmpty() && !this.txt_usuario.getText().isEmpty() && !this.txt_contrasena.getText().isEmpty()){
            return true;
        }
        return false;
    }
}
