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
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nait0
 */
public class UsuariosController implements Initializable {

    @FXML
    private Pane pnl_principal;
    @FXML
    private Pane pnl_codigo;
    @FXML
    private Label lbl_busqueda;
    @FXML
    private TextField txt_busqueda;
    @FXML
    private Button btn_buscar;
    @FXML
    private Button btn_limpiar;
    @FXML
    private SplitPane spl_movimientos;
    @FXML
    private Pane pnl_usuarioBotones;
    @FXML
    private Text lbl_usuarios;
    @FXML
    private Button btn_nuevoUsuario;
    @FXML
    private Button btn_editarUsuario;
    @FXML
    private Button btn_editarUsuario1;
    @FXML
    private Button btn_desactivar;
    @FXML
    private TableView<?> tbl_usuarios;
    @FXML
    private TableColumn<?, ?> tcl_idUsuario;
    @FXML
    private TableColumn<?, ?> tcl_nombre;
    @FXML
    private TableColumn<?, ?> tcl_apellidoPaterno;
    @FXML
    private TableColumn<?, ?> tcl_apellidoMaterno;
    @FXML
    private TableColumn<?, ?> tcl_celular;
    @FXML
    private TableColumn<?, ?> tcl_usuario;
    @FXML
    private TableColumn<?, ?> tcl_rol;
    @FXML
    private TableColumn<?, ?> tcl_estatus;
    @FXML
    private TableColumn<?, ?> tcl_fechaAlta;
    @FXML
    private TableColumn<?, ?> tcl_usuarioAlta;
    
    HashMap<String, Object> context;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void limpiarBusqueda(ActionEvent event) {
    }

    @FXML
    private void buscarUsuario(ActionEvent event) {
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
    }

    @FXML
    private void editarUsuario(ActionEvent event) {
    }

    @FXML
    private void activarUsuario(ActionEvent event) {
    }

    @FXML
    private void desactivarUsuario(ActionEvent event) {
    }

    @FXML
    private void clickTableUsuarios(MouseEvent event) {
    }
    
    public void setData(HashMap<String, Object> context){
        this.context= context;;
    }
    
}
