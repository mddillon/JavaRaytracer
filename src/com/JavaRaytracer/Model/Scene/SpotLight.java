package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.*;

class SpotLight extends AreaLight {
    protected Vector direction;
    
    public SpotLight(Point p, Color c, Vector v, double r) {
        super(p, c, r);
        direction = v;
    }
    
    public Vector getDirection() {
        return direction;
    }
}
