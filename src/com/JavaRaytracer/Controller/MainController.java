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
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.JavaRaytracer.Model.*;
 
public class MainController implements Initializable {
    
    // FXML Loaded UI Elements
    @FXML Button LdScnBtn;
	@FXML Canvas mainCanvas;
    
    // Model Instance Data
    private Render activeRender;
    
    // Controller's necessary instance data
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
            System.out.println("About to parse.");
            activeRender = SceneInterpreter.interpret(sceneFile);
            System.out.println("Finished parsing.");
            if (activeRender != null) {
                activeRender.setCanvas(mainCanvas);
                activeRender.trace();
            }
            else {
                System.out.println("There was an issue generating the scene. Correct the scene file and try again.");
            }
        }
    }
}
