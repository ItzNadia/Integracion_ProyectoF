/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sagfx.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sagfx.api.requests.Requests;
import sagfx.model.Usuario;
import sagfx.utils.Alerta;

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
    private TableView<Usuario> tbl_usuarios;
    @FXML
    private TableColumn<Usuario, Integer> tcl_idUsuario;
    @FXML
    private TableColumn<Usuario, String> tcl_nombre;
    @FXML
    private TableColumn<Usuario, String> tcl_apellidoPaterno;
    @FXML
    private TableColumn<Usuario, String> tcl_apellidoMaterno;
    @FXML
    private TableColumn<Usuario, String> tcl_celular;
    @FXML
    private TableColumn<Usuario, String> tcl_usuario;
    @FXML
    private TableColumn<Usuario, String> tcl_rol;
    @FXML
    private TableColumn<Usuario, String> tcl_estatus;
    @FXML
    private TableColumn<Usuario, String> tcl_fechaAlta;
    @FXML
    private TableColumn<Usuario, String> tcl_usuarioAlta;
    @FXML
    private TableColumn<Usuario, String> tcl_fechaEdicion;
    @FXML
    private TableColumn<Usuario, String> tcl_usuarioEdicion;
    @FXML
    private SplitPane spl_usuarios;
    
    HashMap<String, Object> context;
    private Usuario usuario = null;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarUsuario(ActionEvent event) {
    }

    @FXML
    private void limpiarBusqueda(ActionEvent event) {
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        this.formUsuario(true);
    }

    @FXML
    private void editarUsuario(ActionEvent event) {
        if (this.usuario != null) {
            this.formUsuario(false);
        } else {
            new Alerta("Advertencia", "Debe seleccionar un usuario");
        }
    }

    @FXML
    private void activarUsuario(ActionEvent event) {
    }

    @FXML
    private void desactivarUsuario(ActionEvent event) {
    }

    @FXML
    private void clickTableUsuarios(MouseEvent event) {
        if (tbl_usuarios.getSelectionModel().getSelectedItem() != null) {
            usuario = tbl_usuarios.getSelectionModel().getSelectedItem();
        }
    }
    
    public void setData(HashMap<String, Object> context){
        this.context= context;
        this.cargarUsuarios();
    }
    
    private void formUsuario(boolean isNew) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormUsuarioFXML.fxml"));
            Parent formUsuario = loader.load();
            FormUsuarioController ctrl = loader.getController();
            ctrl.setData(context, this.usuario, isNew);
            Scene scene = new Scene(formUsuario);
            stage.setScene(scene);
            if (isNew) {
                stage.setTitle("Nuevo Usuario");
            } else {
                stage.setTitle("Editar usuario: " + usuario.getNombre() + "'");
            }

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(sagfx.controller.RanchosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarUsuarios() {
        String respuesta = "";
        tbl_usuarios.getItems().clear();

        respuesta = Requests.get("/usuario/getUsuariosByIdRancho/" + ((Usuario) this.context.get("usuario")).getIdRancho()); //REVISAAAAAR
        Gson gson = new Gson();

        TypeToken<List<Usuario>> token = new TypeToken<List<Usuario>>() {
        };
        
        List<Usuario> listUsuarios = gson.fromJson(respuesta, token.getType());
        tcl_idUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        tcl_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcl_apellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        tcl_apellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        tcl_celular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        tcl_usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        tcl_rol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        tcl_estatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        tcl_fechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        tcl_usuarioAlta.setCellValueFactory(new PropertyValueFactory<>("usuarioAlta"));
        tcl_fechaEdicion.setCellValueFactory(new PropertyValueFactory<>("fechaEdicion"));
        tcl_usuarioEdicion.setCellValueFactory(new PropertyValueFactory<>("usuarioEditor"));

        listUsuarios.forEach(e -> {
            tbl_usuarios.getItems().add(e);
        });
    }
    
    
}
