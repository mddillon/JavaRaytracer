package com.JavaRaytracer.Controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
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

import javax.imageio.ImageIO;

public class MainController implements Initializable {
    
    // FXML Loaded UI Elements
    @FXML Button LdScnBtn;
    @FXML Canvas mainCanvas;
    @FXML Button SaveBtn;
    
    // Model Instance Data
    private Scene activeScene;
    private String renderName;
    private File outDir;

    // Controller's necessary instance data
    private Stage appStage;

    public MainController(Stage appStage) {
        this.appStage = appStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the onAction methods for loading and saving scenes
        LdScnBtn.setOnAction(this::loadScene);
        SaveBtn.setOnAction(this::saveScene);
        // Keep the save button disabled until a scene is fully loaded
        SaveBtn.setDisable(true);
        // Attach the canvas to the renderer
        Render.setCanvas(mainCanvas);
    }

    private void saveScene(ActionEvent event) {
        // Create a new buffered image of the width and height of the canvas
        int w = (int) mainCanvas.getWidth();
        int h = (int) mainCanvas.getHeight();
        BufferedImage renderImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        // Get the pixel data from the canvas and from te buffered image
        int[] pixelArray = Render.getImageData();
        final int[] imageData = ((DataBufferInt)renderImage.getRaster().getDataBuffer()).getData();
        //renderImage.setRGB(0, 0, w, h, pixelArray, 0, (int) mainCanvas.getWidth());
        /*
        WritableRaster r = renderImage.getRaster();
        r.setPixel(0, 0, pixelArray);
        renderImage.setData(r);
        */
        // Copy the pixel data into the buffered image
        System.arraycopy(pixelArray, 0, imageData, 0, w*h);
        // Create a file based on the designated output path and text file name
        File outFile = new File(outDir, renderName);
        try {
            ImageIO.write(renderImage, "png", outFile);
            System.out.println("Finished saving file.");
        } catch (IOException e) {
            System.out.println("Error has occured.");
            e.printStackTrace();
        }
    }

    private void loadScene(ActionEvent event) {
        SaveBtn.setDisable(true); // Ensure button is disabled while loading
        // Open file Dialog
        final FileChooser fileChooser = new FileChooser();
        File sceneFile = fileChooser.showOpenDialog(appStage);
        if (sceneFile != null) {
            // Get the name and parent directory for writing
            renderName = sceneFile.getName().replace(".txt", ".png");
            outDir = sceneFile.getParentFile();

            // Parse the file and store surface and lighting information
            System.out.println("About to parse.");
            activeScene = SceneInterpreter.interpret(sceneFile); // Interpret the scene file
            System.out.println("Finished parsing.");
            if (activeScene != null) {
                Render.setScene(activeScene); // Attach the scene to the renderer
                System.out.println("Raytracing scene . . .");
                Render.trace(); // Trace the scene
                System.out.println("Finished.");
                SaveBtn.setDisable(false); // Enable saving
            }
            else {
                System.out.println("There was an issue generating the scene. Correct the scene file and try again.");
            }
        }
    }
}
