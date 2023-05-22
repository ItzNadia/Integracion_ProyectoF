package sagfx.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Usuario;
import sagfx.utils.Window;

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
    private Button btn_cambiarContrasena;
    @FXML
    private Button btn_editarUsuario1;
    @FXML
    private Button btn_desactivar;
    @FXML
    private TableView<Usuario> tbl_usuarios;
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
    
    private HashMap<String, Object> context;
    private Usuario usuario = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void buscarUsuario(ActionEvent event) {
        this.buscarUsuarios();
    }
    
    @FXML
    private void limpiarBusqueda(ActionEvent event) {
        this.txt_busqueda.setText("");
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
            Window.alertaAdvertencia("Debe seleccionar un usuario");
        }
    }
    
    @FXML
    private void activarUsuario(ActionEvent event) {
        this.cambiarActivoUsuario(101);
    }
    
    @FXML
    private void desactivarUsuario(ActionEvent event) {
        this.cambiarActivoUsuario(102);
    }
    
    @FXML
    private void clickTableUsuarios(MouseEvent event) {
        if (tbl_usuarios.getSelectionModel().getSelectedItem() != null) {
            this.usuario = tbl_usuarios.getSelectionModel().getSelectedItem();
        }
    }
    
    public void setData(HashMap<String, Object> context) {
        this.context = context;
        this.cargarUsuarios();
    }
    
    private void formUsuario(boolean isNew) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormUsuarioFXML.fxml"));
            Parent formUsuario = loader.load();
            FormUsuarioController ctrl = loader.getController();
            ctrl.setData(this.context, this.usuario, isNew);
            Scene scene = new Scene(formUsuario);
            stage.setScene(scene);
            if (isNew) {
                stage.setTitle("Nuevo Usuario");
            } else {
                stage.setTitle("Editar usuario: '" + usuario.getNombre() + "'");
            }
            
            stage.showAndWait();
            this.cargarUsuarios();
        } catch (IOException ex) {
            Logger.getLogger(sagfx.controller.RanchosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarUsuarios() {
        String respuesta = "";
        tbl_usuarios.getItems().clear();
        this.usuario = null;
        
        respuesta = Requests.get("/usuario/getUsuariosByIdRancho/" + ((Usuario) this.context.get("usuario")).getIdRancho()); //REVISAAAAAR
        Gson gson = new Gson();
        
        TypeToken<List<Usuario>> token = new TypeToken<List<Usuario>>() {
        };
        
        List<Usuario> listUsuarios = gson.fromJson(respuesta, token.getType());
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
    
    private void cambiarActivoUsuario(int idEstatus) {
        if (this.usuario != null) {
            if (this.usuario.getIdEstatus() != idEstatus) {
                String msj = "activo";
                if (idEstatus == 102) {
                    msj = "inactivo";
                }
                
                if (Window.alertaConfirmacion("¿Realmente desea establecer como " + msj + " el usuario: '" + this.usuario.getUsuario() + "'?")) {
                    try {
                        System.out.println("IdEstatus: " + idEstatus);
                        HashMap<String, Object> estatus = new LinkedHashMap<>();
                        estatus.put("idUsuario", this.usuario.getIdUsuario());
                        estatus.put("idEstatus", idEstatus);
                        estatus.put("idUsuarioEditor", ((Usuario) this.context.get("usuario")).getIdUsuario());
                        
                        JSONObject respuesta = new JSONObject(Requests.post("/usuario/editarEstatusUsuario", estatus));
                        
                        if (!(Boolean) respuesta.get("error")) {
                            this.cargarUsuarios();
                        }
                        Window.alertaInformacion(respuesta.getString("mensaje"));
                    } catch (JSONException ex) {
                        Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                Window.alertaAdvertencia("El usuario ya se encuentra " + this.usuario.getEstatus() + "...");
            }
        } else {
            Window.alertaAdvertencia("Debe seleccionar un usuario");
        }
    }
    
    public void buscarUsuarios() {
        String respuesta = "";
        tbl_usuarios.getItems().clear();
        this.usuario = null;
        
        HashMap<String, Object> params = new LinkedHashMap<>();
        params.put("idRancho", ((Usuario) this.context.get("usuario")).getIdRancho());
        params.put("busqueda", this.txt_busqueda.getText());
        
        respuesta = Requests.post("/usuario/buscarUsuarios", params);
        Gson gson = new Gson();
        
        TypeToken<List<Usuario>> token = new TypeToken<List<Usuario>>() {
        };
        
        List<Usuario> listUsuarios = gson.fromJson(respuesta, token.getType());
        
        if (!listUsuarios.isEmpty()) {
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
        } else {
            Window.alertaInformacion("No se enontraron usuarios con esos parametros...");
        }
    }
    
    @FXML
    private void cambiarContrasena(ActionEvent event) {
        if (this.usuario != null) {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormCambiarContrasenaUsuarioFXML.fxml"));
                Parent formUsuario = loader.load();
                FormCambiarContrasenaUsuarioController ctrl = loader.getController();
                ctrl.setData(this.context, this.usuario);
                Scene scene = new Scene(formUsuario);
                stage.setScene(scene);
                stage.setTitle("Cambiando contraseña para el usuario: '" + this.usuario.getUsuario() + "'");
                stage.showAndWait();
                this.cargarUsuarios();
            } catch (IOException ex) {
                Logger.getLogger(sagfx.controller.RanchosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Window.alertaAdvertencia("Debe seleccionar un usuario");
        }
    }
}
