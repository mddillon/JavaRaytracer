package com.JavaRaytracer.Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.JavaRaytracer.Model.Render.Render;
import com.JavaRaytracer.Model.Scene.Scene;
import com.JavaRaytracer.Model.Scene.SceneInterpreter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
    // FXML Loaded UI Elements
    @FXML Button LdScnBtn;
	@FXML Canvas mainCanvas;
    
    // Model Instance Data
    private Scene activeScene;
    
    // Controller's necessary instance data
    private Stage appStage;

    public MainController(Stage appStage) {
        this.appStage = appStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LdScnBtn.setOnAction(this::loadScene);
        Render.setCanvas(mainCanvas);
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
            activeScene = SceneInterpreter.interpret(sceneFile);
            System.out.println("Finished parsing.");
            if (activeScene != null) {
                Render.setScene(activeScene);
                System.out.println("Raytracing scene . . .");
                Render.trace();
                System.out.println("Finished.");
            }
            else {
                System.out.println("There was an issue generating the scene. Correct the scene file and try again.");
            }
        }
    }
}
