package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.Point;
import com.JavaRaytracer.Model.Geometry.Ray;
import com.JavaRaytracer.Model.Geometry.Vector;

public class Plane extends Surface {

    Vector normal;
    Point point;

    public Plane(Ray ray) {
        normal = ray.getNormalized();
        point = ray.getOrigin();
    }

    public Plane(Point a, Point b, Point c) {
        Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);
        normal = (Vector.cross(ac, ab)).getNormalized();
        point = a;
    }

    @Override
    public Point getIntersection(Ray ray) {
        double numerator = Vector.dot(Vector.getDifference(point, ray.getOrigin()), normal);
        double denominator = Vector.dot(ray, normal);
        if (denominator == 0 && numerator == 0) return ray.getOrigin();
        else if (denominator == 0) return null;
        else {
            double t = numerator / denominator;
            if (t > EPSILON) return Point.getSum(ray.getOrigin(), ray.getScaled(t));
            else return null;
        }
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    public String toString() {
        String out = "Plane\n";
        out += "Normal " + normal.toString();
        out += "Point in plane " + point.toString();
        out += super.toString();
        out += "\n";
        return out;
    }
}
