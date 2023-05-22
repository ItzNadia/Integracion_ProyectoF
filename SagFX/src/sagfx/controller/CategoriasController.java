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
import sagfx.model.Catalogo;
import sagfx.model.Categoria;
import sagfx.utils.Window;

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

    private Categoria categoria = null;
    private Catalogo catalogo = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.cargarCategorias();
    }

    @FXML
    private void buscarCategoria(ActionEvent event) {
        this.buscarCategoria();
        /*if (this.validarNumero(this.txt_busqueda.getText())) {
            new Alerta("Debug", "Sí es un número we");
        } else {
            new Alerta("Debug", "Nmms a esto llamas número?");
        }*/
    }

    @FXML
    private void limpiarBusqueda(ActionEvent event) {
        this.txt_busqueda.setText("");
    }

    @FXML
    private void registrarCategoria(ActionEvent event) {
        this.formCategoria(true); // editar = false, nuevo = true
    }

    @FXML
    private void editarCategoria(ActionEvent event) {
        if (this.categoria != null) {
            this.formCategoria(false); //Como estoy editando es false, si fuera nuevo seria true
        } else {
            Window.alertaAdvertencia("Debe seleccionar una categoría");
        }
    }

    @FXML
    private void activarCategoria(ActionEvent event) {
        this.cambiarActivoCategoria("S");

    }

    @FXML
    private void desactivarCategoria(ActionEvent event) {
        this.cambiarActivoCategoria("N");
    }

    @FXML
    private void registrarCatalogo(ActionEvent event) {
        if (this.categoria != null) {
            this.formCatalogo(true); // editar = false, nuevo = true
        } else {
            Window.alertaAdvertencia("Debe seleccionar una categoría");
        }
    }

    @FXML
    private void editarCatalogo(ActionEvent event) {
        if (this.catalogo != null) {
            this.formCatalogo(false); // editar = false, nuevo = true
        } else {
            Window.alertaAdvertencia("Debe seleccionar un catalogo");
        }
    }

    @FXML
    private void activarCatalogo(ActionEvent event) {
        this.cambiarActivoCatalogo("S");
    }

    @FXML
    private void desactivarCatalogo(ActionEvent event) {
        this.cambiarActivoCatalogo("N");
    }

    @FXML
    private void clickTableCategorias(MouseEvent event) {
        if (tbl_categoria.getSelectionModel().getSelectedItem() != null) {
            categoria = tbl_categoria.getSelectionModel().getSelectedItem();
            this.cargarCatalogos();
        }
    }

    @FXML
    private void clickTableCatalogos(MouseEvent event) {
        if (this.tbl_catalogo.getSelectionModel().getSelectedItem() != null) {
            this.catalogo = tbl_catalogo.getSelectionModel().getSelectedItem();
        }
    }

    private void cargarCategorias() {
        String respuesta = "";
        tbl_categoria.getItems().clear();
        tbl_catalogo.getItems().clear();
        this.categoria = null;
        this.catalogo = null;

        respuesta = Requests.get("/categoria/getAllCategorias/");
        Gson gson = new Gson();

        //Definimos u  TypeToken que representa una lista de objetos Categoria
        TypeToken<List<Categoria>> token = new TypeToken<List<Categoria>>() {
        };

        List<Categoria> listCategorias = gson.fromJson(respuesta, token.getType());

        tcl_categoriaIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        tcl_categoriaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcl_categoriaActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        //El foreach nos ayuda a recorrer la lista
        listCategorias.forEach(e -> {
            //con el add agrega los elementos a la tabla
            tbl_categoria.getItems().add(e);
            //System.out.println(e);
        });
    }

    private void cargarCatalogos() {
        String respuesta = "";

        tbl_catalogo.getItems().clear();
        this.catalogo = null;
        respuesta = Requests.get("/catalogo/getCatalogosByCategoria/" + categoria.getIdCategoria());
        Gson gson = new Gson();

        //Definimos u  TypeToken que representa una lista de objetos Categoria
        TypeToken<List<Catalogo>> token = new TypeToken<List<Catalogo>>() {
        };

        //Utilizamos el método fromJson() de la clase Gson para convertir el JSON en una lista de objetos
        List<Catalogo> listCatalogo = gson.fromJson(respuesta, token.getType());

        tcl_catalogoIdCatalogo.setCellValueFactory(new PropertyValueFactory<>("idCatalogo"));
        tcl_catalogoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcl_catalogoActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        listCatalogo.forEach(e -> {
            //con el add agrega los elementos a la tabla
            tbl_catalogo.getItems().add(e);
        });
    }

    public void buscarCategoria() {
        String respuesta = "";
        this.tbl_catalogo.getItems().clear();
        this.tbl_categoria.getItems().clear();
        this.categoria = null;
        this.catalogo = null;

        HashMap<String, Object> params = new LinkedHashMap<>();
        params.put("busqueda", this.txt_busqueda.getText());

        respuesta = Requests.post("/categoria/buscarCategorias", params);
        Gson gson = new Gson();

        //Definimos u  TypeToken que representa una lista de objetos Categoria
        TypeToken<List<Categoria>> token = new TypeToken<List<Categoria>>() {
        };

        //Utilizamos el método fromJson() de la clase Gson para convertir el JSON en una lista de objetos
        List<Categoria> listCatalogo = gson.fromJson(respuesta, token.getType());

        if (listCatalogo.size() > 0) {
            tcl_catalogoIdCatalogo.setCellValueFactory(new PropertyValueFactory<>("idCatalogo"));
            tcl_catalogoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tcl_catalogoActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

            listCatalogo.forEach(e -> {
                tbl_categoria.getItems().add(e);
            });
        } else {
            Window.alertaInformacion("No se encontraron datos coincidentes...");
        }
    }

    private void formCategoria(boolean isNew) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormCategoriaFXML.fxml"));
            Parent formCategoria = loader.load();
            FormCategoriaController ctrl = loader.getController();
            ctrl.setData(this.categoria, isNew); //Como estoy editando es false, si fuera nuevo seria true
            Scene scene = new Scene(formCategoria);
            stage.setScene(scene);
            if (isNew) {
                stage.setTitle("Nueva categoría");
            } else {
                stage.setTitle("Editar categoría: '" + categoria.getNombre() + "'");
            }

            stage.showAndWait();
            this.cargarCategorias();
        } catch (IOException ex) {
            Logger.getLogger(sagfx.controller.CategoriasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void formCatalogo(boolean isNew) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sagfx/gui/view/FormCatalogoFXML.fxml"));
            Parent formCatalogo = loader.load();
            FormCatalogoController ctrl = loader.getController();
            ctrl.setData(this.categoria, this.catalogo, isNew); //Como estoy editando es false, si fuera nuevo seria true
            Scene scene = new Scene(formCatalogo);
            stage.setScene(scene);
            if (isNew) {
                stage.setTitle("Nuevo catalogo para: '" + categoria.getNombre() + "'");
            } else {
                stage.setTitle("Editar catalogo: '" + catalogo.getNombre() + "'");
            }

            stage.showAndWait();
            this.cargarCatalogos();
        } catch (IOException ex) {
            Logger.getLogger(sagfx.controller.CategoriasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cambiarActivoCategoria(String activo) {
        if (this.categoria != null) {
            if (!this.categoria.getActivo().equals(activo)) {
                String msj = "activar";
                if (activo.equals("N")) {
                    msj = "desactivar";
                }

                if (Window.alertaConfirmacion("Realmente desea " + msj + " la categoría: '" + this.categoria.getNombre() + "'")) {
                    try {
                        HashMap<String, Object> estatus = new LinkedHashMap<>();
                        estatus.put("idCategoria", categoria.getIdCategoria());
                        estatus.put("activo", activo);

                        JSONObject respuesta = new JSONObject(Requests.post("/categoria/editarEstatusCategoria", estatus));

                        if (!(Boolean) respuesta.get("error")) {
                            this.cargarCategorias();
                        }
                        Window.alertaInformacion(respuesta.getString("mensaje"));
                    } catch (JSONException ex) {
                        Logger.getLogger(CategoriasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                if (this.categoria.getActivo().equals("S")) {
                    Window.alertaAdvertencia("La categoria ya se encuentra activada");
                } else {
                    Window.alertaAdvertencia("La categoria ya se encuentra desactivada");
                }
            }
        } else {
            Window.alertaAdvertencia("Debe seleccionar una categoría");
        }
    }

    private void cambiarActivoCatalogo(String activo) {
        if (this.catalogo != null) {
            if (!this.catalogo.getActivo().equals(activo)) {
                String msj = "activar";
                if (activo.equals("N")) {
                    msj = "desactivar";
                }

                if (Window.alertaConfirmacion("Realmente desea " + msj + " el catálogo: '" + this.catalogo.getNombre() + "'")) {
                    try {
                        HashMap<String, Object> estatus = new LinkedHashMap<>();
                        estatus.put("idCatalogo", catalogo.getIdCatalogo());
                        estatus.put("activo", activo);

                        JSONObject respuesta = new JSONObject(Requests.post("/catalogo/editarEstatusCatalogo", estatus));

                        if (!(Boolean) respuesta.get("error")) {
                            this.cargarCatalogos();
                        }
                        Window.alertaInformacion(respuesta.getString("mensaje"));
                    } catch (JSONException ex) {
                        Logger.getLogger(CategoriasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                if (this.catalogo.getActivo().equals("S")) {
                    Window.alertaAdvertencia("El catálogo ya se encuentra activado");
                } else {
                    Window.alertaAdvertencia("El catálogo ya se encuentra desactivado");
                }
            }
        } else {
            Window.alertaAdvertencia("Debe seleccionar un catálogo");
        }
    }

    @FXML
    private void buscarCategorias(ActionEvent event) {
        this.buscarCategoria();
    }
}
