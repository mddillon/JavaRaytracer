package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.*;
import com.JavaRaytracer.Model.Scene.Color;

public abstract class Surface {
    protected Color ambient, diffuse, specular;
    protected double reflectance, phong;
    
    protected static final double EPSILON = 0.000025;

    public abstract Point getIntersection(Ray ray);
    public abstract Vector getNormal(Point p);
    
    public Color getAmbient() {return ambient;}
    public Color getDiffuse() {return diffuse;}
    public Color getSpecular() {return specular;}
    public double getReflectance() {return reflectance;}
    public double getPhong() {return phong;}
    
    public void setColors(Color amb, Color dif, Color spec) {
        ambient = amb;
        diffuse = dif;
        specular = spec;
    }
    
    public void setLightProperties(double refl, double ph) {
        reflectance = refl;
        phong = ph;
    }
    
    public String toString() {
        String out = "Light Properties:\n";
        out += "Ambient " + ambient.toString() + "Diffuse " + 
              diffuse.toString() + "Specular " + specular.toString() +
              "Reflectance = " + reflectance + ", Phong = " + phong;
        return out;
    }
}
