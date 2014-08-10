package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.*;

class AreaLight extends Light {
    protected double radius;
    
    public AreaLight(Point p, Color c, double r) {
        super(p, c);
        radius = r;
    }
    
    public AreaLight(double x, double y, double z,
                     float r, float g, float b, double radius) {
        super(new Point(x, y, z), new Color(r, g, b));
        this.radius = radius;
    }
    
    public AreaLight(Point p, float r, float g, float b, double radius) {
        super(p, new Color(r, g, b));
        this.radius = radius;
    }
    
    public AreaLight(double x, double y, double z, Color c, double radius) {
        super(new Point(x, y, z), c);
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }
}
