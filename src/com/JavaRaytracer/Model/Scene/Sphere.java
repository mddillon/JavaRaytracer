package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.*;

public class Sphere extends Surface {

    private Point center;
    private double radius;

    public Sphere(double radius, double x, double y, double z) {
        center = new Point(x, y, z);
        this.radius = radius;
    }
    
    public Point getIntersection(Ray ray) {
        Vector vec = new Vector(center, ray.getOrigin());
        Point intersection = null;
        // at^2 + bt + c = 0
        double a = Vector.dot(ray, ray);
        double b = 2*Vector.dot(ray, vec);
        double c = Vector.dot(vec, vec) - (radius*radius);
        double discriminant = (b*b) - (4*a*c);
        if (discriminant >= 0) {
            double t = (-b - Math.sqrt(discriminant)) / (2*a);
            if (t < EPSILON) {
                intersection = null;
            }         
            else {
                intersection = Point.getSum(ray.getOrigin(), ray.getScaled(t));
            }
        }
        return intersection;
    }
    
    public Vector getNormal(Point p) {
        Vector result = new Vector(center, p);
        result = result.getNormalized();
        return result;
    }
    
    public String toString() {
        String out = "Sphere\n";
        out += "Center " + center.toString();
        out += "Radius = " + radius;
        out += super.toString();
        out += "\n";
        return out;
    }
    
}
