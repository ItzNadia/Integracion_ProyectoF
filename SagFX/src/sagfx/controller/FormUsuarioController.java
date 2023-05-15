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
import javafx.scene.control.TextField;
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
        //this.cargarMovimiento();
    }

    @FXML
    private void guardarUsuario(ActionEvent event) {
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
    
}
