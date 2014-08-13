package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.Point;
import com.JavaRaytracer.Model.Geometry.Ray;
import com.JavaRaytracer.Model.Geometry.Vector;

// F(x,y,z) = a(x-px)^2 + b(y-py)^2 + c(z-pz)^2 + d = 0

public class SimpleQuad extends Quad {

    private double a, b, c, d;
    private Point origin;

    public SimpleQuad(String[] parameters) {
        a = Double.parseDouble(parameters[1]);
        b = Double.parseDouble(parameters[2]);
        c = Double.parseDouble(parameters[3]);
        d = Double.parseDouble(parameters[4]);
        origin = new Point(
                Double.parseDouble(parameters[5]),
                Double.parseDouble(parameters[6]),
                Double.parseDouble(parameters[7])
        );
    }

    public Point getIntersection(Ray ray) {
        // Substituting ray equation into quadric equation eventually yields
        // At^2 + Bt + C
        double A, B, C, t0, t1;
        Point E = ray.getOrigin();
        Ray R = ray;
        Point intersection = null;

        A = a * R.getX() * R.getX() + b * R.getY() * R.getY() + c * R.getZ() * R.getZ();
        B = 2 * a * (E.getX() * R.getX() - R.getX() * origin.getX()) +
                2 * b * (E.getY() * R.getY() - R.getY() * origin.getY()) +
                2 * c * (E.getZ() * R.getZ() - R.getZ() * origin.getZ());
        C = a * (E.getX() * E.getX() - 2 * E.getX() * origin.getX() + origin.getX() * origin.getX()) +
                b * (E.getY() * E.getY() - 2 * E.getY() * origin.getY() + origin.getY() * origin.getY()) +
                c * (E.getZ() * E.getZ() - 2 * E.getZ() * origin.getZ() + origin.getZ() * origin.getZ())
                + d;

        double discriminant = (B*B) - (4*A*C);
        if (A < EPSILON) {
            t0 = - C / B;
            intersection = Point.getSum(ray.getOrigin(), ray.getScaled(t0));
        }
        else if (discriminant >= 0) {
            t0 = (-B - Math.sqrt(discriminant)) / (2*A);
            t1 = (-B + Math.sqrt(discriminant)) / (2*A);
            if (t0 < EPSILON && t1 < EPSILON) return null;
            else if (t0 > EPSILON) intersection = Point.getSum(ray.getOrigin(), ray.getScaled(t0));
            else if (t1 > EPSILON) intersection = Point.getSum(ray.getOrigin(), ray.getScaled(t1));
        }
        return intersection;
    }

    public Vector getNormal(Point p) {
        double xComponent = a * (p.getX() - origin.getX());
        double yComponent = b * (p.getY() - origin.getY());
        double zComponent = c * (p.getZ() - origin.getZ());
        return (new Vector(xComponent, yComponent, zComponent)).getNormalized();
    }

    public String toString() {
        String out = "Simple Quad\n";
        out += "Origin " + origin.toString();
        out += "a, b, c, d: " + a + " " + b + " " + c + " " + d + "\n";
        out += super.toString();
        return out;
    }
}