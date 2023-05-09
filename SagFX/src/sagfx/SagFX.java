/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sagfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author nait0
 */
public class SagFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Principal login
        Parent login = FXMLLoader.load(getClass().getResource("/sagfx/gui/view/LoginFXML.fxml"));
        Scene scene = new Scene(login);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
