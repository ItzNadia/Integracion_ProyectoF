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
    private TableView<?> tbl_movimientos;
    @FXML
    private TableColumn<?, ?> tcl_idMovimiento;
    @FXML
    private TableColumn<?, ?> tcl_movimientoCantidad;
    @FXML
    private TableColumn<?, ?> tcl_movimientoTipo;
    @FXML
    private TableColumn<?, ?> tcl_movimientoConcepto;
    @FXML
    private TableColumn<?, ?> tcl_movimientoFecha;
    @FXML
    private TableColumn<?, ?> tcl_movimientoRancho;
    @FXML
    private TableColumn<?, ?> tcl_movimientoFechaAlta;
    @FXML
    private TableColumn<?, ?> tcl_movimientoUsuarioAlta;
    @FXML
    private TableColumn<?, ?> tcl_movimientoFechaEdicion;
    @FXML
    private TableColumn<?, ?> tcl_movimientoUsuarioEdicion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarRancho(ActionEvent event) {
    }

    @FXML
    private void limpiarBusqueda(ActionEvent event) {
    }

    @FXML
    private void registrarMovimiento(ActionEvent event) {
    }

    @FXML
    private void editarMovimiento(ActionEvent event) {
    }

    @FXML
    private void clickTableMovimientos(MouseEvent event) {
    }
    
}
