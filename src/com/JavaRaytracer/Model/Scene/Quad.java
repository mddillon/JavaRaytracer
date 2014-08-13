package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.*;

public abstract class Quad extends Surface {

    public abstract Point getIntersection(Ray ray);
    public abstract Vector getNormal(Point p);

    public static Quad getSimpleQuad(String[] parameters) {
        return new SimpleQuad(parameters);
    }

    public static Quad getComplexQuad(String[] parameters) {
        return new ComplexQuad(parameters);
    }
    
}
