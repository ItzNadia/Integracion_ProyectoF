package sagfx.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Catalogo;
import sagfx.model.Movimiento;
import sagfx.model.Usuario;
import sagfx.utils.Window;

public class FormMovimientoController implements Initializable {

    @FXML
    private Label lbl_cantidad;
    @FXML
    private TextField txt_cantidad;
    @FXML
    private Label lbl_tipo;
    @FXML
    private CheckBox chb_movimiento;
    @FXML
    private Label lbl_concepto;
    @FXML
    private Label lbl_fecha;
    @FXML
    private DatePicker dtp_fecha;
    @FXML
    private Label lbl_observaciones;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private ComboBox<Catalogo> cmb_concepto;

    private Movimiento movimiento = null;
    private Boolean isNew = false;
    private HashMap<String, Object> context;
    private List<Catalogo> listaConceptos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(HashMap<String, Object> context, Movimiento movimiento, Boolean isNew) {
        this.context = context;
        this.movimiento = movimiento;
        this.isNew = isNew;
        this.cargarConceptos();
        this.cargarMovimiento();
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    @FXML
    private void guardarMovimiento(ActionEvent event) {
        Usuario u = (Usuario) this.context.get("usuario");
        if (validar()) {
            try {
                HashMap<String, Object> movimiento = new HashMap<String, Object>();
                movimiento.put("cantidadVenta", this.txt_cantidad.getText());
                movimiento.put("idConcepto", this.cmb_concepto.getValue().getIdCatalogo());
                movimiento.put("fecha", this.dtp_fecha.getValue().toString());
                movimiento.put("observaciones", this.txt_observaciones.getText());
                movimiento.put("idRancho", u.getIdRancho());

                if (this.chb_movimiento.isSelected()) {
                    movimiento.put("tipo", "Ingreso");
                } else {
                    movimiento.put("tipo", "Egreso");
                }

                String respuesta;

                if (isNew) {
                    movimiento.put("idUsuarioAlta", u.getIdUsuario());
                    respuesta = Requests.post("/movimiento/registrarMovimiento", movimiento);
                } else {
                    movimiento.put("idMovimiento", this.movimiento.getIdMovimiento());
                    movimiento.put("idUsuarioEditor", u.getIdUsuario());
                    respuesta = Requests.post("/movimiento/editarMovimiento", movimiento);
                }

                JSONObject dataJson = new JSONObject(respuesta);

                if ((boolean) dataJson.get("error")) {

                    Window.alertaError(dataJson.get("mensaje").toString());
                } else {
                    Window.close(event);
                    Window.alertaInformacion(dataJson.get("mensaje").toString());
                }
            } catch (JSONException ex) {
                Logger.getLogger(FormMovimientoController.class.getName()).log(Level.SEVERE, null, ex);
                Window.alertaError("Error, verifique la información en intente nuevamente");
            }
        } else {
            Window.alertaAdvertencia("Favor de ingresar datos faltantes");
        }
    }

    @FXML
    private void checkMovimiento(ActionEvent event) {
        if (this.chb_movimiento.isSelected()) {
            this.chb_movimiento.setText("Ingreso");
        } else {
            this.chb_movimiento.setText("Egreso");
        }
    }

    private boolean validar() {
        if (!this.txt_cantidad.getText().isEmpty() && !this.txt_observaciones.getText().isEmpty()) {
            if (this.cmb_concepto.getValue() != null) {
                return true;
            }
        }
        return false;
    }

    public void cargarMovimiento() {
        if (!isNew) {
            if ("Ingreso".equals(movimiento.getTipo())) {
                this.chb_movimiento.setText("Ingreso");
                this.chb_movimiento.setSelected(true);
            } else {
                this.chb_movimiento.setText("Egreso");
                this.chb_movimiento.setSelected(false);
            }
            this.txt_cantidad.setText(movimiento.getCantidadVenta().toString());
            this.cargarConceptoEnEdicion();
            String fechaF = movimiento.getFecha();
            this.dtp_fecha.setValue(LocalDate.parse(fechaF.substring(6, 10) + "-" + fechaF.substring(3, 5) + "-" + fechaF.substring(0, 2)));
            this.txt_observaciones.setText(movimiento.getObservaciones());
        }
    }

    @FXML
    private void restriccionNumeros(KeyEvent event) {
        if (event.getTarget() == this.txt_cantidad) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) && event.getCharacter().charAt(0) != '.') {
                event.consume();
            }
            if (event.getCharacter().charAt(0) == '.' && this.txt_cantidad.getText().contains(".")) {
                event.consume();
            }
        }
    }

    private void cargarConceptos() {
        String respuesta = Requests.get("/catalogo/getConceptosMovimientos");
        Gson gson = new Gson();

        TypeToken<List<Catalogo>> token = new TypeToken<List<Catalogo>>() {
        };

        listaConceptos = gson.fromJson(respuesta, token.getType());

        if (this.listaConceptos.size() > 0) {
            ObservableList<Catalogo> conceptos = FXCollections.observableArrayList();

            listaConceptos.forEach(e -> {
                conceptos.add(e);
            });
            this.cmb_concepto.setItems(conceptos);
        } else {
            this.cmb_concepto.setPromptText("No hay roles activos...");
        }
    }

    private void cargarConceptoEnEdicion() {
        this.listaConceptos.forEach(e -> {
            if (Objects.equals(this.movimiento.getIdConcepto(), e.getIdCatalogo())) {
                this.cmb_concepto.setValue(e);
            }
        });
    }
}
