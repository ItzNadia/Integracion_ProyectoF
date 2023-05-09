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
import sagfx.model.Catalogo;
import sagfx.model.Categoria;
import sagfx.utils.Window;

/**
 * FXML Controller class
 *
 * @author nait0
 */
public class FormCatalogoController implements Initializable {

    @FXML
    private Label lbl_idCategoria;
    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_activo;
    @FXML
    private TextField txt_idCatalogo;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_activo;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;
    
    Catalogo catalogo = null;
    Boolean isNew = false;
   


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(Catalogo catalogo, Boolean isNew){
        this.catalogo = catalogo;
        this.isNew = isNew;
        this.cargarCatalogo();
    }
    
    public void cargarCatalogo(){
        this.txt_activo.setText(catalogo.getActivo());
        this.txt_idCatalogo.setText(catalogo.getIdCatalogo().toString());
        this.txt_nombre.setText(catalogo.getNombre());
    }


    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    @FXML
    private void guardarCatalogo(ActionEvent event) {
    }
}
