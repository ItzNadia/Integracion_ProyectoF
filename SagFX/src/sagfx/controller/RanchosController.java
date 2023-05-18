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
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Rancho;
import sagfx.model.Usuario;
import sagfx.utils.Window;

public class RanchosController implements Initializable {

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
    private Button btn_nuevoRancho;
    @FXML
    private Button btn_editarRancho;
    @FXML
    private TableView<Rancho> tbl_ranchos;
    @FXML
    private TableColumn<Rancho, String> tcl_ranchoNombre;
    @FXML
    private TableColumn<Rancho, String> tcl_ranchoDireccion;
    @FXML
    private TableColumn<Rancho, String> tcl_ranchoEncargado;
    @FXML
    private TableColumn<Rancho, String> tcl_ranchoFechaAlta;
    @FXML
    private TableColumn<Rancho, String> tcl_ranchoUsuarioAlta;
    @FXML
    private TableColumn<Rancho, String> tcl_ranchoFechaEdicion;
    @FXML
    private TableColumn<Rancho, String> tcl_ranchoUsuarioEdicion;
    @FXML
    private TableColumn<Rancho, Integer> tcl_idRancho;
    @FXML
    private TableColumn<Rancho, String> tbl_ranchoEstatus;
    @FXML
    private SplitPane spl_ranchos;
    @FXML
    private Pane pnl_ranchosBotones;
    @FXML
    private Button btn_desactivarRancho;
    @FXML
    private Button btn_activarRancho;

    private Rancho rancho = null;
    private HashMap<String, Object> context;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void limpiarBusqueda(ActionEvent event) {
        this.txt_busqueda.setText("");
    }

    @FXML
    private void buscarRancho(ActionEvent event) {
        this.buscarRanchos();
    }

    @FXML
    private void registrarRancho(ActionEvent event) {
        this.formRancho(true); // editar = false, nuevo = true
    }

    @FXML
    private void editarRancho(ActionEvent event) {
        if (this.rancho != null) {
            this.formRancho(false); //Como estoy editando es false, si fuera nuevo seria true
        } else {
            Window.alertaAdvertencia("Debe seleccionar un rancho");
        }
    }

    @FXML
    private void clickTableRanchos(MouseEvent event) {
        if (tbl_ranchos.getSelectionModel().getSelectedItem() != null) {
            rancho = tbl_ranchos.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void desactivarRancho(ActionEvent event) {
        this.cambiarEstatus(102);
    }

    @FXML
    private void activarRancho(ActionEvent event) {
        this.cambiarEstatus(101);
    }

    public void setData(HashMap<String, Object> context) {
        this.context = context;
        this.cargarRanchos();
    }

    private void cargarRanchos() {
        String respuesta = "";
        this.tbl_ranchos.getItems().clear();
        this.rancho = null;

        respuesta = Requests.get("/rancho/getAllRanchos/");
        Gson gson = new Gson();

        TypeToken<List<Rancho>> token = new TypeToken<List<Rancho>>() {
        };

        List<Rancho> listRanchos = gson.fromJson(respuesta, token.getType());
        tcl_idRancho.setCellValueFactory(new PropertyValueFactory<>("idRancho"));
        tcl_ranchoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcl_ranchoDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tcl_ranchoEncargado.setCellValueFactory(new PropertyValueFactory<>("nombreEncargado"));
        tbl_ranchoEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        tcl_ranchoFechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        tcl_ranchoUsuarioAlta.setCellValueFactory(new PropertyValueFactory<>("usuarioAlta"));
        tcl_ranchoFechaEdicion.setCellValueFactory(new PropertyValueFactory<>("fechaEdicion"));
        tcl_ranchoUsuarioEdicion.setCellValueFactory(new PropertyValueFactory<>("usuarioEditor"));

        listRanchos.forEach(e -> {
            tbl_ranchos.getItems().add(e);
        });
    }

    public void buscarRanchos() {
        String respuesta = "";
        this.tbl_ranchos.getItems().clear();
        this.rancho = null;

        HashMap<String, Object> params = new LinkedHashMap<>();
        params.put("busqueda", this.txt_busqueda.getText());

        respuesta = Requests.post("/rancho/buscarRanchos", params);
        Gson gson = new Gson();

        //Definimos u  TypeToken que representa una lista de objetos Categoria
        TypeToken<List<Rancho>> token = new TypeToken<List<Rancho>>() {
        };

        //Utilizamos el método fromJson() de la clase Gson para convertir el JSON en una lista de objetos
        List<Rancho> listRanchos = gson.fromJson(respuesta, token.getType());

        if (listRanchos.size() > 0) {
            tcl_idRancho.setCellValueFactory(new PropertyValueFactory<>("idRancho"));
            tcl_ranchoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tcl_ranchoDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            tcl_ranchoEncargado.setCellValueFactory(new PropertyValueFactory<>("nombreEncargado"));
            tbl_ranchoEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
            tcl_ranchoFechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
            tcl_ranchoUsuarioAlta.setCellValueFactory(new PropertyValueFactory<>("usuarioAlta"));
            tcl_ranchoFechaEdicion.setCellValueFactory(new PropertyValueFactory<>("fechaEdicion"));
            tcl_ranchoUsuarioEdicion.setCellValueFactory(new PropertyValueFactory<>("usuarioEditor"));

            listRanchos.forEach(e -> {
                tbl_ranchos.getItems().add(e);
            });
        } else {
            Window.alertaInformacion("No se encontraron datos coincidentes...");
        }
    }

    private void formRancho(boolean isNew) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormRanchoFXML.fxml"));
            Parent formRancho = loader.load();
            FormRanchoController ctrl = loader.getController();
            ctrl.setData(this.context, this.rancho, isNew);
            Scene scene = new Scene(formRancho);
            stage.setScene(scene);
            if (isNew) {
                stage.setTitle("Nuevo Rancho");
            } else {
                stage.setTitle("Editar Rancho: '" + rancho.getNombre() + "'");
            }

            stage.showAndWait();
            this.cargarRanchos();
        } catch (IOException ex) {
            Logger.getLogger(sagfx.controller.RanchosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cambiarEstatus(int activo) {
        if (this.rancho != null) {
            if (this.rancho.getIdEstatus() != activo) {
                String msj = "activar";
                if (activo == 102) {
                    msj = "desactivar";
                }

                if (Window.alertaConfirmacion("Realmente desea " + msj + " el rancho: '" + this.rancho.getNombre() + "'")) {
                    try {
                        HashMap<String, Object> estatus = new LinkedHashMap<>();
                        estatus.put("idRancho", this.rancho.getIdRancho());
                        estatus.put("idEstatus", activo);
                        estatus.put("idUsuarioEditor", ((Usuario)this.context.get("usuario")).getIdUsuario());

                        JSONObject respuesta = new JSONObject(Requests.post("/rancho/editarEstatusRancho", estatus));

                        if (!(Boolean) respuesta.get("error")) {
                            this.cargarRanchos();
                        }
                        Window.alertaInformacion(respuesta.getString("mensaje"));
                    } catch (JSONException ex) {
                        Logger.getLogger(CategoriasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                if (this.rancho.getIdEstatus() == 101) {
                    Window.alertaAdvertencia("El rancho ya se encuentra activado");
                } else {
                    Window.alertaAdvertencia("El rancho ya se encuentra desactivado");
                }
            }
        } else {
            Window.alertaAdvertencia("Debe seleccionar un rancho");
        }
    }
}
