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
import javafx.scene.control.Alert;
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
import sagfx.api.requests.Requests;
import sagfx.model.Catalogo;
import sagfx.model.Categoria;

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
    private TableView<Categoria> tbl_categoria;
    @FXML
    private TableColumn<Categoria, Integer> tcl_categoriaIdCategoria;
    @FXML
    private TableColumn<Categoria, String> tcl_categoriaNombre;
    @FXML
    private TableColumn<Categoria, String> tcl_categoriaActivo;
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
    private TableView<Catalogo> tbl_catalogo;
    @FXML
    private TableColumn<Catalogo, Integer> tcl_catalogoIdCatalogo;
    @FXML
    private TableColumn<Catalogo, String> tcl_catalogoNombre;
    @FXML
    private TableColumn<Catalogo, String> tcl_catalogoActivo;
    
    Categoria categoria = null;
    Catalogo catalogo = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void buscarCategoria(ActionEvent event) {
        this.cargarCategorias();
    }
    
    public void cargarCategorias(){
        String respuesta = "";
        tbl_categoria.getItems().clear();
        
        respuesta = Requests.get("/categoria/getAllCategorias/");
        Gson gson = new Gson();
        
        //Definimos u  TypeToken que representa una lista de objetos Categoria
        TypeToken<List<Categoria>> token = new TypeToken<List<Categoria>>(){
        };
        
        List<Categoria> listCategorias = gson.fromJson(respuesta, token.getType());
        
        List<Catalogo> listCatalogo = gson.fromJson(respuesta, token.getType());

        tcl_categoriaIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        tcl_categoriaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcl_categoriaActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
          
        //El foreach nos ayuda a recorrer la lista
        listCategorias.forEach(e ->{
            //con el add agrega los elementos a la tabla
            tbl_categoria.getItems().add(e);
            //System.out.println(e);
        });
    }

    @FXML
    private void registrarCategoria(ActionEvent event) {
        
    }

    @FXML
    private void editarCategoria(ActionEvent event) {
        if(this.categoria!=null){
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormCategoriaFXML.fxml"));
                Parent formCategoria = loader.load();
                FormCategoriaController ctrl = loader.getController();
                ctrl.setData(this.categoria, false); //Como estoy editando es false, si fuera nuevo seria true
                Scene scene = new Scene(formCategoria);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(sarefx.controller.CategoriasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar una categoria.");
            alert.showAndWait();
        }
    }

    @FXML
    private void activarCategoria(ActionEvent event) {
    }

    @FXML
    private void desactivarCategoria(ActionEvent event) {
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
    private void clickTable(MouseEvent event) {
        String respuesta = "";
        
        if(tbl_categoria.getSelectionModel().getSelectedItem() !=null){
            categoria = tbl_categoria.getSelectionModel().getSelectedItem();
            tbl_catalogo.getItems().clear();
            respuesta = Requests.get("/catalogo/getCatalogosByCategoria/" + categoria.getIdCategoria());
            Gson gson = new Gson();
            
            //Definimos u  TypeToken que representa una lista de objetos Categoria
            TypeToken<List<Catalogo>> token = new TypeToken<List<Catalogo>>(){
            };
        
            //Utilizamos el método fromJson() de la clase Gson para convertir el JSON en una lista de objetos
            List<Catalogo> listCatalogo = gson.fromJson(respuesta, token.getType());
        
            tcl_catalogoIdCatalogo.setCellValueFactory(new PropertyValueFactory<>("idCatalogo"));
            tcl_catalogoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tcl_catalogoActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
           
            listCatalogo.forEach(e ->{
                //con el add agrega los elementos a la tabla
                tbl_catalogo.getItems().add(e);
            });
            //System.out.println(listCatalogo.size());
            
        }
    }

    @FXML
    private void editarCatalogo(ActionEvent event) {
        catalogo = tbl_catalogo.getSelectionModel().getSelectedItem();
        if(this.catalogo!=null){
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormCatalogoFXML.fxml"));
                Parent formCatalogo = loader.load();
                FormCatalogoController ctrl = loader.getController();
                ctrl.setData(this.catalogo, false); //Como estoy editando es false, si fuera nuevo seria true
                Scene scene = new Scene(formCatalogo);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(sagfx.controller.CategoriasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar una catalogo.");
            alert.showAndWait();
        }
    }
  
}
