/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sagfx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sagfx.model.Rancho;

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
    private TextField txt_nombreEncagradoRancho;
    
    Rancho rancho = null;
    Boolean isNew = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Rancho rancho, Boolean isNew) {
        this.rancho = rancho;
        this.isNew = isNew;
        //this.cargarRancho();
    }

    @FXML
    private void guardarRancho(ActionEvent event) {
    }

    @FXML
    private void cerrar(ActionEvent event) {
    }
    
}
