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
import javafx.scene.control.TextField;
import sagfx.model.Categoria;
import sagfx.utils.Window;

/**
 * FXML Controller class
 *
 * @author nait0
 */
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
    
    public void setData(Categoria categoria, Boolean isNew){
        this.categoria = categoria;
        this.isNew = isNew;
        this.cargarCategoria();
    }
    
    public void cargarCategoria(){
        this.txt_activo.setText(categoria.getActivo());
        this.txt_idCategoria.setText(categoria.getIdCategoria().toString());
        this.txt_nombre.setText(categoria.getNombre());
    }

    @FXML
    private void guardarCategoria(ActionEvent event) {
        
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }
}
