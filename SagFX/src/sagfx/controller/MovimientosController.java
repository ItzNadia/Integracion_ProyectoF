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
import sagfx.api.requests.Requests;
import sagfx.model.Movimiento;
import sagfx.model.Usuario;
import sagfx.utils.Window;

public class MovimientosController implements Initializable {

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
    private Pane pnl_movimientosBotones;
    @FXML
    private Button btn_nuevoMovimiento;
    @FXML
    private Button btn_editarMovimiento;
    @FXML
    private Text lbl_ingresosEgresos;
    @FXML
    private TableView<Movimiento> tbl_movimientos;
    @FXML
    private TableColumn<Movimiento, Integer> tcl_idMovimiento;
    @FXML
    private TableColumn<Movimiento, Double> tcl_movimientoCantidad;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoTipo;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoConcepto;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoFecha;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoRancho;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoFechaAlta;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoUsuarioAlta;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoFechaEdicion;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoUsuarioEdicion;
    @FXML
    private TableColumn<Movimiento, String> tcl_movimientoObservaciones;

    private Movimiento movimiento = null;
    HashMap<String, Object> context;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void limpiarBusqueda(ActionEvent event) {
        this.txt_busqueda.setText("");
    }

    @FXML
    private void registrarMovimiento(ActionEvent event) {
        this.formMovimiento(true);
    }

    @FXML
    private void editarMovimiento(ActionEvent event) {
        if (this.movimiento != null) {
            this.formMovimiento(false);
        } else {
            Window.alertaAdvertencia("Debe seleccionar un movimiento");
        }
    }

    @FXML
    private void clickTableMovimientos(MouseEvent event) {
        if (tbl_movimientos.getSelectionModel().getSelectedItem() != null) {
            movimiento = tbl_movimientos.getSelectionModel().getSelectedItem();
        }

    }

    @FXML
    private void buscarMovimientos(ActionEvent event) {
        String respuesta = "";
        this.tbl_movimientos.getItems().clear();
        this.movimiento = null;

        HashMap<String, Object> params = new LinkedHashMap<>();
        params.put("idRancho", ((Usuario) this.context.get("usuario")).getIdRancho());
        params.put("busqueda", this.txt_busqueda.getText());

        respuesta = Requests.post("/movimiento/buscarMovimientos", params);
        Gson gson = new Gson();

        TypeToken<List<Movimiento>> token = new TypeToken<List<Movimiento>>() {
        };

        List<Movimiento> listMovimiento = gson.fromJson(respuesta, token.getType());

        if (listMovimiento.size() > 0) {
            tcl_idMovimiento.setCellValueFactory(new PropertyValueFactory<>("idMovimiento"));
            tcl_movimientoCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadVenta"));
            tcl_movimientoTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            tcl_movimientoConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
            tcl_movimientoFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            tcl_movimientoObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
            tcl_movimientoRancho.setCellValueFactory(new PropertyValueFactory<>("rancho"));
            tcl_movimientoFechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
            tcl_movimientoUsuarioAlta.setCellValueFactory(new PropertyValueFactory<>("usuarioAlta"));
            tcl_movimientoFechaEdicion.setCellValueFactory(new PropertyValueFactory<>("fechaEdicion"));
            tcl_movimientoUsuarioEdicion.setCellValueFactory(new PropertyValueFactory<>("usuarioEditor"));

            listMovimiento.forEach(e -> {
                tbl_movimientos.getItems().add(e);
            });
        } else {
            Window.alertaInformacion("No se encontraron datos coincidentes...");
        }
    }

    public void setData(HashMap<String, Object> context) {
        this.context = context;
        this.cargarMovimientos();
    }

    private void formMovimiento(boolean isNew) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormMovimientoFXML.fxml"));
            Parent formMovimiento = loader.load();
            FormMovimientoController ctrl = loader.getController();
            ctrl.setData(context, this.movimiento, isNew);
            Scene scene = new Scene(formMovimiento);
            stage.setScene(scene);
            if (isNew) {
                stage.setTitle("Nuevo Movimiento");
            } else {
                stage.setTitle("Editar Movimiento");
            }

            stage.showAndWait();
            this.cargarMovimientos();
        } catch (IOException ex) {
            Logger.getLogger(sagfx.controller.RanchosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarMovimientos() {
        String respuesta = "";
        tbl_movimientos.getItems().clear();

        respuesta = Requests.get("/movimiento/getMovimientosByIdRancho/" + ((Usuario) this.context.get("usuario")).getIdRancho()); //REVISAAAAAR
        Gson gson = new Gson();

        TypeToken<List<Movimiento>> token = new TypeToken<List<Movimiento>>() {
        };

        List<Movimiento> listMovimientos = gson.fromJson(respuesta, token.getType());
        tcl_idMovimiento.setCellValueFactory(new PropertyValueFactory<>("idMovimiento"));
        tcl_movimientoCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadVenta"));
        tcl_movimientoTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tcl_movimientoConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        tcl_movimientoFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tcl_movimientoObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        tcl_movimientoRancho.setCellValueFactory(new PropertyValueFactory<>("rancho"));
        tcl_movimientoFechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        tcl_movimientoUsuarioAlta.setCellValueFactory(new PropertyValueFactory<>("usuarioAlta"));
        tcl_movimientoFechaEdicion.setCellValueFactory(new PropertyValueFactory<>("fechaEdicion"));
        tcl_movimientoUsuarioEdicion.setCellValueFactory(new PropertyValueFactory<>("usuarioEditor"));

        listMovimientos.forEach(e -> {
            tbl_movimientos.getItems().add(e);
        });
    }
}
