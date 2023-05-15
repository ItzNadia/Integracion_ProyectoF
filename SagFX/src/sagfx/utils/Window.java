package sagfx.utils;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * Metodos que ayudaran a controlar mis escenas (ventanas)
 */
public class Window {
    public static void close(ActionEvent event){
       //Me devuelve el elemento al que hice click
        Node source = (Node) event.getSource();
        //Me devuelve la ventana donde se encuentra el elemento
        Stage stage = (Stage) source.getScene().getWindow();
        
        stage.close();
    }
    public static Stage getStageByEvent(ActionEvent event){
        Node source = (Node) event.getSource();
        return(Stage) source.getScene().getWindow();
    }
    public static Stage getStageByNode(Node node){
        return(Stage) node.getScene().getWindow();
    }

    public static void alertaInformacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informe");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void alertaAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("¡Advertencia!");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void alertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error...");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static Boolean alertaConfirmacion(String mensaje) {
        Boolean aceptar=null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¡Confirmación requerida!");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent()) {
            if (resultado.get() == ButtonType.OK) {
                aceptar=true;
            } else {   
                aceptar=false;
            }
        }
        return aceptar;
    }
}
