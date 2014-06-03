package com.JavaRaytracer.Application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.JavaRaytracer.Controller.MainController;

public class JavaRaytracer extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {		
        try {
            // Load the root layout from the fxml file
            final String loc = "/com/JavaRaytracer/Resources/JavaRaytracer.fxml";
            FXMLLoader loader = new FXMLLoader(JavaRaytracer.class.getResource(loc));
            MainController mainController = new MainController(primaryStage);
            loader.setController(mainController);
            Parent rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

}
