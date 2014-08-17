package com.JavaRaytracer.Model.Scene;

import java.io.File;
import java.util.Scanner;

import com.JavaRaytracer.Model.Geometry.Point;
import com.JavaRaytracer.Model.Geometry.Ray;
import com.JavaRaytracer.Model.Geometry.Vector;

public class SceneInterpreter {
    
    public static Scene interpret(File sceneFile) {

        int numVerts;
        double reflectance, phong;
        double cameraSettings[];
        boolean existsError, settingCamera, creatingPlane;
        Color ambient, diffuse, specular;
        Point[] vert;
        Plane plane;

        Scene scene = new Scene();
        existsError = false;
        settingCamera = false;
        creatingPlane = false;
        numVerts = 0;
        ambient = new Color(); diffuse = new Color(); specular = new Color();
        reflectance = 0; phong = 0;
        cameraSettings = new double[7]; // x, y, z, yaw, pitch, halfwidth, focalLength
        plane = null;
        vert = new Point[3];

        try {
            Scanner in = new Scanner(sceneFile);
            in.useDelimiter("\n");
			int i = 1;
            while (in.hasNext() && !existsError) {
                String line = in.next();
                String[] tokens = line.split("\\s+");
                if (tokens.length != 0) {
                    switch (tokens[0]) {
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
                            cameraSettings = new double[7];
                            cameraSettings[5] = Math.tan(Math.PI / 6);
                            cameraSettings[6] = 1;
                            break;
                        case "fov":
                            if (settingCamera) {
                                if (tokens.length != 2) {
                                    existsError = true;
                                    reportError(i);
                                }
                                else {
                                    // This conversion gives half of field of view, which is more important
                                    double angle = 0.5 * Math.toRadians(Double.parseDouble(tokens[1]));
                                    cameraSettings[5] = Math.tan(angle);
                                }
                            }
                            break;
                        case "position":
                            if (settingCamera) {
                                if (tokens.length != 4) {
                                    existsError = true;
                                    reportError(i);
                                }
                                else {
                                    cameraSettings[0] = Double.parseDouble(tokens[1]);
                                    cameraSettings[1] = Double.parseDouble(tokens[2]);
                                    cameraSettings[2] = Double.parseDouble(tokens[3]);
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
                                    cameraSettings[3] = Double.parseDouble(tokens[1]);
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
                                    cameraSettings[4] = Double.parseDouble(tokens[1]);
                                }
                            }
                            break;
                        case "endCamera":
                            scene.addCamera(new Camera(cameraSettings));
                            settingCamera = false;
                            break;
                        case "beginPlane":
                            creatingPlane = true;
                            numVerts = 0;
                            vert = new Point[3];
                            break;
                        case "ray":
                            if (creatingPlane) {
                                if (tokens.length != 7) {
                                    existsError = true;
                                    reportError(i);
                                }
                                else {
                                    plane = new Plane(
                                        new Ray(
                                                Double.parseDouble(tokens[1]),
                                                Double.parseDouble(tokens[2]),
                                                Double.parseDouble(tokens[3]),
                                                Double.parseDouble(tokens[4]),
                                                Double.parseDouble(tokens[5]),
                                                Double.parseDouble(tokens[6])
                                        )
                                    );
                                }
                                creatingPlane = false;
                            }
                            break;
                        case "point":
                            if (creatingPlane) {
                                if (tokens.length != 4) {
                                    existsError = true;
                                    reportError(i);
                                } else {
                                    vert[numVerts] = new Point(
                                            Double.parseDouble(tokens[1]),
                                            Double.parseDouble(tokens[2]),
                                            Double.parseDouble(tokens[3])
                                    );
                                    numVerts++;
                                }
                            }
                            break;
                        case "endPlane":
                            if (numVerts != 3 && plane == null) {
                                System.out.println("Improper Plane Description.");
                                existsError = true;
                                break;
                            }
                            else if (plane == null) {
                                plane = new Plane(vert[0], vert[1], vert[2]);
                            }
                            plane.setColors(ambient, diffuse, specular);
                            plane.setLightProperties(reflectance, phong);
                            scene.addSurface(plane);
                            creatingPlane = false;
                            plane = null;
                            break;
                        case "quadric":
                            if (tokens.length != 8 && tokens.length != 11) {
                                existsError = true;
                                reportError(i);
                                break;
                            }
                            else {
                                Quad quad;
                                if (tokens.length == 8) quad = Quad.getSimpleQuad(tokens);
                                else quad = Quad.getComplexQuad(tokens);
                                quad.setColors(ambient, diffuse, specular);
                                quad.setLightProperties(reflectance, phong);
                                scene.addSurface(quad);
                            }
                            break;
                        default:
                            break;
                    }
                }
				i++;
            }
            if (scene.getCameras().size() == 0) scene.addCamera(new Camera());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something else went wrong.");
        } finally {
            if (existsError) scene = null;
        }
        return scene;
    }
    
    private static void reportError(int line) {
        System.out.println("Incorrect number of arguments on line " + line + ".");
    }
}
