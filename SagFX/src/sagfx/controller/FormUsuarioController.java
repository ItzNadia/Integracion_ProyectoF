package sagfx.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Catalogo;
import sagfx.model.Usuario;
import sagfx.utils.Window;

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
    private ComboBox<Catalogo> cmb_rol;
    @FXML
    private TextField txt_nombre;
    @FXML
    private Label lbl_contrasena;
    @FXML
    private PasswordField txt_contrasena;
    @FXML
    private CheckBox chb_estatus;

    private Usuario usuario = null;
    private Boolean isNew = false;
    private HashMap<String, Object> context;
    private List<Catalogo> listaRoles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(HashMap<String, Object> context, Usuario usuario, Boolean isNew) {
        this.context = context;
        this.usuario = usuario;
        this.isNew = isNew;
        this.cargarConceptos();
        this.cargarUsuario();
    }

    @FXML
    private void guardarUsuario(ActionEvent event) {
        Usuario u = (Usuario) this.context.get("usuario");
        if (validar()) {
            try {
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("nombre", this.txt_nombre.getText());
                param.put("apellidoPaterno", this.txt_apellidoPaterno.getText());
                param.put("apellidoMaterno", this.txt_apellidoMaterno.getText());
                param.put("celular", this.txt_celular.getText());
                param.put("usuario", this.txt_usuario.getText());
                param.put("idRol", this.cmb_rol.getValue().getIdCatalogo());

                if (this.chb_estatus.isSelected()) {
                    param.put("idEstatus", 101);
                } else {
                    param.put("idEstatus", 102);
                }
                param.put("idRancho", u.getIdRancho());
                param.put("idUsuarioAlta", u.getIdUsuario());

                String respuesta;
                System.out.println("Es nuevo?: " + isNew);
                if (isNew) {
                    param.put("contrasena", this.txt_contrasena.getText());
                    param.put("idUsuarioAlta", u.getIdUsuario());
                    respuesta = Requests.post("/usuario/registrarUsuario", param);
                } else {
                    param.put("idUsuario", this.usuario.getIdUsuario());
                    param.put("idUsuarioEditor", u.getIdUsuario());
                    respuesta = Requests.post("/usuario/editarUsuario", param);
                }

                JSONObject dataJson = new JSONObject(respuesta);

                if ((boolean) dataJson.get("error")) {
                    Window.alertaError(dataJson.get("mensaje").toString());
                } else {
                    Window.close(event);
                    Window.alertaInformacion(dataJson.get("mensaje").toString());
                }
            } catch (JSONException ex) {
                Logger.getLogger(FormUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Window.alertaAdvertencia("Favor de ingresar datos faltantes");
        }
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
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
        if (event.getTarget() == this.txt_celular) {
            if (!Character.isDigit(event.getCharacter().charAt(0))) {
                event.consume();
            }
            if (!(this.txt_celular.lengthProperty().getValue() < 10)) {
                event.consume();
            }
        }
    }

    public void cargarUsuario() {
        if (!isNew) {
            this.txt_contrasena.setDisable(true);
            this.txt_contrasena.setOpacity(0);
            this.lbl_contrasena.setOpacity(0);

            this.txt_nombre.setText(usuario.getNombre());
            this.txt_apellidoPaterno.setText(usuario.getApellidoPaterno());
            this.txt_apellidoMaterno.setText(usuario.getApellidoMaterno());
            this.txt_celular.setText(usuario.getCelular());
            this.txt_usuario.setText(usuario.getUsuario());

            this.cargarConceptoEnEdicion();

            if (usuario.getIdEstatus() == 101) {
                this.chb_estatus.setText("Activo");
                this.chb_estatus.setSelected(true);
            } else {
                this.chb_estatus.setText("Inactivo");
                this.chb_estatus.setSelected(false);
            }
        }
    }

    private boolean validar() {
        if (!this.txt_nombre.getText().isEmpty() && !this.txt_apellidoPaterno.getText().isEmpty() && !this.txt_apellidoMaterno.getText().isEmpty()
                && !this.txt_celular.getText().isEmpty() && !this.txt_usuario.getText().isEmpty()) {
            if (this.cmb_rol.getValue() != null) {
                if (this.isNew) {
                    if (!this.txt_contrasena.getText().isEmpty()) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private void cargarConceptos() {
        String respuesta = Requests.get("/catalogo/getRolesUsuarios");
        Gson gson = new Gson();

        TypeToken<List<Catalogo>> token = new TypeToken<List<Catalogo>>() {
        };

        this.listaRoles = gson.fromJson(respuesta, token.getType());

        if (this.listaRoles.size() > 0) {
            ObservableList<Catalogo> roles = FXCollections.observableArrayList();

            this.listaRoles.forEach(e -> {
                roles.add(e);
            });
            this.cmb_rol.setItems(roles);
        } else {
            this.cmb_rol.setPromptText("No hay roles activos...");
        }
    }

    private void cargarConceptoEnEdicion() {
        this.listaRoles.forEach(e -> {
            if (Objects.equals(this.usuario.getIdRol(), e.getIdCatalogo())) {
                this.cmb_rol.setValue(e);
            }
        });
    }
}
