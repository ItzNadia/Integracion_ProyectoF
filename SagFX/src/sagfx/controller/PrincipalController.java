package sagfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import sagfx.model.Usuario;

public class PrincipalController implements Initializable {

    @FXML
    private BorderPane pnl_principal;
    @FXML
    private MenuItem mi_ingresosEgresos;
    @FXML
    private MenuItem mi_usuarios;
    @FXML
    private MenuItem mi_categoria;
    @FXML
    private MenuItem mi_rancho;

    HashMap<String, Object> context;
    @FXML
    private Menu m_movimientos;
    @FXML
    private Menu m_administracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(HashMap<String, Object> context) {
        this.context = context;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/MovimientosFXML.fxml"));
            Parent movimientos = loader.load();
            MovimientosController ctrl = loader.getController();
            ctrl.setData(context);
            pnl_principal.setCenter(movimientos);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (((Usuario) this.context.get("usuario")).getIdRol() == 202) {
            this.m_administracion.setDisable(true);
        }
    }

    @FXML
    private void abrirCategorias(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/CategoriasFXML.fxml"));
            Parent categorias = loader.load();
            pnl_principal.setCenter(categorias);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void abrirRanchos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/RanchosFXML.fxml"));
            Parent ranchos = loader.load();
            RanchosController ctrl = loader.getController();
            ctrl.setData(context);
            pnl_principal.setCenter(ranchos);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirMovimientos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/MovimientosFXML.fxml"));
            Parent movimientos = loader.load();
            MovimientosController ctrl = loader.getController();
            ctrl.setData(context);
            pnl_principal.setCenter(movimientos);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirUsuarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/UsuariosFXML.fxml"));
            Parent usuarios = loader.load();
            UsuariosController ctrl = loader.getController();
            ctrl.setData(context);
            pnl_principal.setCenter(usuarios);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
