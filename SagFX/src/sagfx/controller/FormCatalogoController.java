package sagfx.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import sagfx.api.requests.Requests;
import sagfx.model.Catalogo;
import sagfx.model.Categoria;
import sagfx.utils.Alerta;
import sagfx.utils.Window;

public class FormCatalogoController implements Initializable {

    @FXML
    private Label lbl_idCategoria;
    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_activo;
    @FXML
    private TextField txt_idCatalogo;
    @FXML
    private TextField txt_nombre;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private TextField txt_idCategoria;
    @FXML
    private Label lbl_idCatalogo;
    @FXML
    private CheckBox chb_activo;
   
    Categoria categoria = null;
    Catalogo catalogo = null;
    Boolean isNew = false;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(Categoria categoria, Catalogo catalogo, Boolean isNew){
        this.categoria = categoria;
        this.catalogo = catalogo;
        this.isNew = isNew;
        this.cargarCatalogo();
    }
    
    public void cargarCatalogo(){
        if(!isNew){
            this.txt_idCatalogo.setText(catalogo.getIdCatalogo().toString());
            this.txt_idCategoria.setText(catalogo.getIdCategoria().toString());
            this.txt_nombre.setText(catalogo.getNombre());
            if (!isNew) {
                if ("S".equals(catalogo.getActivo())) {
                this.chb_activo.setText("Sí");
                this.chb_activo.setSelected(true);
            } else {
                this.chb_activo.setText("No");
                this.chb_activo.setSelected(false);
            }
            }else{
                this.txt_idCategoria.setText(categoria.getIdCategoria().toString());
            }
        }   
    }


    @FXML
    private void cerrar(ActionEvent event) {
        Window.close(event);
    }

    @FXML
    private void guardarCatalogo(ActionEvent event) {
        if(validar()){
            try {
                HashMap<String,Object> catalogo = new HashMap<String, Object> ();
                catalogo.put("idCatalogo", this.txt_idCatalogo.getText());
                catalogo.put("idCategoria", this.txt_idCategoria.getText());
                catalogo.put("nombre", this.txt_nombre.getText());
                if (this.chb_activo.isSelected()) {
                    catalogo.put("activo", "S");
                } else {
                    catalogo.put("activo", "N");
                }
                String respuesta;
                
                if(isNew){
                    respuesta = Requests.post("/catalogo/registrarCatalogo", catalogo);
                }else{
                    respuesta = Requests.post("/catalogo/editarCatalogo", catalogo);
                }
                
                JSONObject dataJson = new JSONObject(respuesta);
                
                if((boolean)dataJson.get("error")){
                    Window.close(event);
                    new Alerta("Error", dataJson.get("mensaje").toString());
                }else{
                    Window.close(event);
                    new Alerta("Hecho", dataJson.get("mensaje").toString());
                }
            } catch (JSONException ex) {
                Logger.getLogger(FormCatalogoController.class.getName()).log(Level.SEVERE, null, ex);
                new Alerta("Error", "Error, verifique la información en intente nuevamente");
            }
        }else{
            new Alerta("Advertencia", "Favor de ingresar datos faltantes");
        }
    }
    
    private boolean validar(){
        if(!this.txt_idCatalogo.getText().isEmpty() && !this.txt_idCategoria.getText().isEmpty() && !this.txt_nombre.getText().isEmpty()){
            return true;
        }
        return false;
    }

    @FXML
    private void checkActivo(ActionEvent event) {
        if (this.chb_activo.isSelected()) {
            this.chb_activo.setText("Sí");
        } else {
            this.chb_activo.setText("No");
        }
    }
}
