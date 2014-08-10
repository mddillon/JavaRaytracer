package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.*;

public class Light {
    protected Point position;
    protected Color color;
    protected double intensity;

    public Light(double x, double y, double z, double r, double g, double b) {
        position = new Point(x, y, z);
        color = new Color(r, g, b);
    }
    
    public Light(double x, double y, double z, Color c) {
        position = new Point(x, y, z);
        color = c;
    }
    
    public Light(Point p, float r, float g, float b) {
        position = p;
        color = new Color(r, g, b);
    }
    
    public Light(Point p, Color c) {
        position = p;
        color = c;
    }
    
    public Point getPosition() {
        return position;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String toString() {
        String out = "Pinlight\n";
        out += "Position " + position.toString();
        out += "Color " + color.toString();
        out += "Intensity = " + intensity + "\n";
        return out;
    }
    
}
