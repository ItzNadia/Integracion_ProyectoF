/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sarefx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author nait0
 */
public class CategoriasController implements Initializable {

    @FXML
    private Pane pnl_principal;
    @FXML
    private Pane pnl_codigo;
    @FXML
    private Label lbl_codigo;
    @FXML
    private TextField txt_busqueda;
    @FXML
    private Button btn_buscar;
    @FXML
    private Button btn_limpiar;
    @FXML
    private SplitPane spl_categoriaCatalogo;
    @FXML
    private Pane pnl_categoriaBotones;
    @FXML
    private Button btn_nuevaCategoria;
    @FXML
    private Button btn_editarCategoria;
    @FXML
    private Button btn_activarCategoria;
    @FXML
    private Button btn_desactivarCategoria;
    @FXML
    private TableView<?> tbl_categoria;
    @FXML
    private TableColumn<?, ?> tcl_categoriaIdCategoria;
    @FXML
    private TableColumn<?, ?> tcl_categoriaNombre;
    @FXML
    private TableColumn<?, ?> tcl_categoriaActivo;
    @FXML
    private Pane pnl_catalogoBotones;
    @FXML
    private Button btn_nuevoCatalogo;
    @FXML
    private Button btn_editarCatalogo;
    @FXML
    private Button btn_activarCatalogo;
    @FXML
    private Button btn_desactivarCatalogo;
    @FXML
    private TableView<?> tbl_catalogo;
    @FXML
    private TableColumn<?, ?> tcl_catalogoIdCatalogo;
    @FXML
    private TableColumn<?, ?> tcl_catalogoNombre;
    @FXML
    private TableColumn<?, ?> tcl_catalogoActivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarCategoria(ActionEvent event) {
    }

    @FXML
    private void registrarCategoria(ActionEvent event) {
    }

    @FXML
    private void editarCategoria(ActionEvent event) {
    }

    @FXML
    private void activarCategoria(ActionEvent event) {
    }

    @FXML
    private void desactivarCategoria(ActionEvent event) {
    }

    @FXML
    private void clickTable(MouseEvent event) {
    }

    @FXML
    private void registrarCatalogo(ActionEvent event) {
    }

    @FXML
    private void activarCatalogo(ActionEvent event) {
    }

    @FXML
    private void desactivarCatalogo(MouseEvent event) {
    }

    @FXML
    private void editarCatalogo(MouseEvent event) {
    }
    
}
