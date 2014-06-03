package com.JavaRaytracer.Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public class MainController implements Initializable {
    
    @FXML Button LdScnBtn;

    private Stage appStage;

    public MainController(Stage appStage) {
        this.appStage = appStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LdScnBtn.setOnAction(this::loadScene);
    }
    
    /**
     * Opens file dialog for selecting scene file
     */
    private void loadScene(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        File sceneFile = fileChooser.showOpenDialog(appStage);
        if (sceneFile != null) {
            // Parse the file and store surface and lighting information
        }
    }
}
