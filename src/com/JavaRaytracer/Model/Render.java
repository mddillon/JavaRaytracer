package com.JavaRaytracer.Model;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.image.*;
import javafx.scene.canvas.GraphicsContext;

import com.JavaRaytracer.Model.Scene.*;
import com.JavaRaytracer.Model.Geometry.*;

public class Render {
    private ArrayList<Light> pinlights;
	private ArrayList<Surface> surfaces;
    private Point eye;
    private Vector gaze;
    private Color background;
    private Canvas canvas;
    private int[] pixelBuffer;
    private int width;
    private int height;
    private int maxdepth;
    private double halfwidth;
    private double focalplane;
    
    public Render() {
        pinlights = new ArrayList<Light>();
        surfaces = new ArrayList<Surface>();
        eye = new Point();
        gaze = new Vector(0, 0, -1);
        background = new Color();
        halfwidth = Math.tan(Math.PI/6);
        focalplane = -1;
        maxdepth = 5;
    }
    
    public void setCanvas(Canvas canvas) {
		if (canvas != null) {
			this.canvas = canvas;
            width = (int) canvas.getWidth();
            height = (int) canvas.getHeight();
            pixelBuffer = new int[width * height];			
		}
    }
    
    public void setBackground(Color background) {
        if (background != null) {
            this.background = background;
        }
    }
    
    public void setEye(Point eye) {
		if (eye != null) {
            this.eye = eye;
        }
    }
    
    public void setGaze(Vector gaze) {
		if (background != null) {
            this.gaze = gaze;
        }
    }
    
    public void addPinlight(Light pinlight) {
		if (pinlight != null) {
			pinlights.add(pinlight);
        }

    }
    
    public void addSurface(Surface surface) {
		if (surface != null) {
			surfaces.add(surface);
		}
    }
    
    public void trace() {
        double left = eye.getX() - halfwidth; double right = eye.getX() + halfwidth;
        double top = eye.getY() + halfwidth; double bottom = eye.getY() - halfwidth;
        //for (Surface current : surfaces) {System.out.println(current.toString());}
        //for (Light current : pinlights) {System.out.println(current.toString());}
        //System.out.println("Background = " + background.toString());
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                Color shade = new Color();
                Point pixel = new Point(left + (right - left)*(j + .5)/width, top + (bottom-top)*(i + .5)/height, focalplane);
                Ray ray = new Ray(eye, pixel);
                shade = Color.addColors(shade, trace(ray, 0));
                pixelBuffer[i*width + j] = shade.toArgbInt();
            }
        }
        writePixels();
    }
    
    private Color trace(Ray ray, int depth) {
        Point intersection = null;
        Surface frontmost = null;
        Color black = new Color();
        
        for (Surface current : surfaces) {
            Point temp = current.getIntersection(ray);
            if (temp != null) {
              double distance = Vector.magnitude(new Vector(eye, temp));
              if (intersection == null || distance < Vector.magnitude(new Vector(eye, intersection))) {
                intersection = temp;
                frontmost = current;
              }   
            }
        }
                
        if (intersection == null) return background;
        else {    
            Vector normal = frontmost.getNormal(intersection);
            Vector vecE = (new Vector(intersection, ray.getOrigin())).getNormalized();
             
            Color ambientTerm = new Color();
            Color specularTerm = new Color();
            Color directionalTerm = new Color();
            Color reflectionTerm = new Color();
            
            for (Light x : pinlights) {
                Vector vec = new Vector(intersection, x.getPosition());
                Vector vecL = vec.getNormalized();
                Vector vecR = normal.getReflected(vec);
                if (!shadowTrace(new Ray(intersection, x.getPosition())) || frontmost.getReflectance() != 0) {
                    double directionalCoefficient = Math.max(Vector.dot(normal, vecL), 0);
                    directionalTerm = Color.addColors(directionalTerm, x.getColor().scalarMultiply(directionalCoefficient));
                    if (!frontmost.getSpecular().equals(black)) {
                        double specularCoefficient = Math.pow((Math.max(Vector.dot(vecE, vecR), 0)), frontmost.getPhong());
                        specularTerm = Color.addColors(specularTerm, x.getColor().scalarMultiply(specularCoefficient));
                    }
                }
            }
            
            ambientTerm = Color.multiplyColors(frontmost.getDiffuse(), frontmost.getAmbient());            
            directionalTerm = Color.multiplyColors(directionalTerm, frontmost.getDiffuse());
            specularTerm = Color.multiplyColors(specularTerm, frontmost.getSpecular());

            Color result;
            // Reflection Generating
            if (frontmost.getReflectance() != 0 && depth < maxdepth) {
                reflectionTerm = trace(new Ray(intersection, normal.getReflected(vecE)), ++depth);
                reflectionTerm = reflectionTerm.scalarMultiply(frontmost.getReflectance());
            }
            result = Color.addColors(ambientTerm, specularTerm, directionalTerm, reflectionTerm);
            return result;
        }
    }    

    // Checks to see if we hit an object in the scene along the way to the light.
    private boolean shadowTrace(Ray shadowRay) {
        Point intersection = null;
        int ndx = 0;
        while (intersection == null && ndx<surfaces.size())
            intersection = surfaces.get(ndx++).getIntersection(shadowRay);
        if (intersection == null) return false;
        else return true;
    }

    private void writePixels() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter writer = gc.getPixelWriter();
        PixelFormat<java.nio.IntBuffer> format = PixelFormat.getIntArgbInstance();       
        int offset = 0;
        writer.setPixels(0, 0, width, height, format, pixelBuffer, offset, width);        
    }

}
