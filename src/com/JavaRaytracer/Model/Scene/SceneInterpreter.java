package com.JavaRaytracer.Model.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.JavaRaytracer.Model.Geometry.Point;

public class SceneInterpreter {
    
    private static Color ambient, diffuse, specular;
    private static double reflectance, phong, halfwidth;
    private static boolean existsError;
    private static boolean settingCamera;
    private static Point[] vert;
    private static int numVerts;
    
    public static Scene interpret(File sceneFile) {
        Scene scene = new Scene();
        existsError = false;
        settingCamera = false;
        numVerts = 0;
        ambient = new Color(); diffuse = new Color(); specular = new Color();
        reflectance = 0; phong = 0; halfwidth = Math.tan(Math.PI/6);
        try {
            Scanner in = new Scanner(sceneFile);
            in.useDelimiter("\n");
			int i = 1;
            while (in.hasNext() && !existsError) {
                String line = in.next();
                String[] tokens = line.split("\\s+");
                if (tokens.length != 0) {
                    switch (tokens[0]) {
                        case "fov":
							if (tokens.length != 2) {
								existsError = true;
								reportError(i);
							}
							else {
                                // This conversion gives half of field of view, which is more important
                                double angle = Math.toRadians(Double.parseDouble(tokens[1]));
                                halfwidth = Math.tan(angle); 
							}
                            break;
                        case "background":
							if (tokens.length != 4) {
								existsError = true;
								reportError(i);
							}
							else {
								scene.setBackground(new Color(Double.parseDouble(tokens[1]),
                                                               Double.parseDouble(tokens[2]),
                                                               Double.parseDouble(tokens[3])));
							}						
                            break;
                        case "light":
							if (tokens.length != 7) {
								existsError = true;
								reportError(i);
							}
							else {
                                scene.addPinlight(new Light(Double.parseDouble(tokens[1]),
                                                             Double.parseDouble(tokens[2]),
                                                             Double.parseDouble(tokens[3]),
                                                             Double.parseDouble(tokens[4]),
                                                             Double.parseDouble(tokens[5]),
                                                             Double.parseDouble(tokens[6])));
							}	
                            break;
                        case "ambient":
							if (tokens.length != 4) {
								existsError = true;
								reportError(i);
							}
							else {
								ambient = new Color(Double.parseDouble(tokens[1]),
                                                    Double.parseDouble(tokens[2]),
                                                    Double.parseDouble(tokens[3]));
							}	
                            break;
                        case "diffuse":
							if (tokens.length != 4) {
								existsError = true;
								reportError(i);
							}
							else {
								diffuse = new Color(Double.parseDouble(tokens[1]),
                                                    Double.parseDouble(tokens[2]),
                                                    Double.parseDouble(tokens[3]));	
							}	
                            break;
                        case "specular":
							if (tokens.length != 5) {
								existsError = true;
								reportError(i);
							}
							else {
								specular = new Color(Double.parseDouble(tokens[1]),
                                                    Double.parseDouble(tokens[2]),
                                                    Double.parseDouble(tokens[3]));
                                phong = Double.parseDouble(tokens[4]);
							}	
                            break;
                        case "reflective":
							if (tokens.length != 2) {
								existsError = true;
								reportError(i);
							}
							else {
								reflectance = Double.parseDouble(tokens[1]);
							}	
                            break;
                        case "sphere":
							if (tokens.length != 5) {
								existsError = true;
								reportError(i);
							}
							else {
								Sphere sphere = new Sphere(Double.parseDouble(tokens[1]),
                                                           Double.parseDouble(tokens[2]),
                                                           Double.parseDouble(tokens[3]),
                                                           Double.parseDouble(tokens[4]));
                                sphere.setColors(ambient, diffuse, specular);
                                sphere.setLightProperties(reflectance, phong);
                                scene.addSurface(sphere);
							}	
                            break;
                        case "begin":
							if (tokens.length != 1) {
								existsError = true;
								reportError(i);
							}
							else {
                                numVerts = 0;
                                vert = new Point[3];								
							}	
                            break;
                        case "vertex":
							if (tokens.length != 4) {
								existsError = true;
								reportError(i);
							}
							else {
                                vert[numVerts] = new Point(Double.parseDouble(tokens[1]),
                                                           Double.parseDouble(tokens[2]),
                                                           Double.parseDouble(tokens[3]));
								numVerts++;
							}	
                            break;
                        case "end":
							if (tokens.length != 1) {
								existsError = true;
								reportError(i);
							}
							else {
                                if (numVerts == 3) {
                                    Triangle triangle = new Triangle(vert[0], vert[1], vert[2]);
                                    triangle.setColors(ambient, diffuse, specular);
                                    triangle.setLightProperties(reflectance, phong);
                                    scene.addSurface(triangle);
                                }
                                else {
                                    System.out.println("Incomplete polygon before line " + i);
                                }
							}	
                            break;
                        case "beginCamera":
                            settingCamera = true;
                            break;
                        case "position":
                            if (settingCamera) {
                                if (tokens.length != 4) {
                                    existsError = true;
                                    reportError(i);
                                }
                                else {
                                    scene.setCameraPosition(
                                            new Point(
                                                Double.parseDouble(tokens[1]),
                                                Double.parseDouble(tokens[2]),
                                                Double.parseDouble(tokens[3])
                                            )
                                    );
                                }
                            }
                            break;
                        case "spin":
                            if (settingCamera) {
                                if (tokens.length != 2) {
                                    existsError = true;
                                    reportError(i);
                                }
                                else {
                                    scene.setCameraSpin(Double.parseDouble(tokens[1]));
                                }
                            }
                            break;
                        case "tilt":
                            if (settingCamera) {
                                if (tokens.length != 2) {
                                    existsError = true;
                                    reportError(i);
                                }
                                else {
                                    scene.setCameraTilt(Double.parseDouble(tokens[1]));
                                }
                            }
                            break;
                        case "endCamera":
                            settingCamera = false;
                            break;
                        default:
                            break;
                    }
                }
				i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something else went wrong.");
        } finally {
            if (existsError) scene = null;
            return scene;
        }
    }
    
    private static void reportError(int line) {
        System.out.println("Incorrect number of arguments on line " + line + ".");
    }
}
