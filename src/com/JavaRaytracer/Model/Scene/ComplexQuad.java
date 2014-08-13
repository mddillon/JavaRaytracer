package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.Point;
import com.JavaRaytracer.Model.Geometry.Ray;
import com.JavaRaytracer.Model.Geometry.Vector;

// F(x,y,z) = ax^2 + by^2 + cz^2 + dxy + exz + fyz + gx + hy + iz + j = 0

public class ComplexQuad extends Quad {

    private double a, b, c, d, e, f, g, h, i, j;

    public ComplexQuad(String[] parameters) {
        a = Double.parseDouble(parameters[1]);
        b = Double.parseDouble(parameters[2]);
        c = Double.parseDouble(parameters[3]);
        d = Double.parseDouble(parameters[4]);
        e = Double.parseDouble(parameters[5]);
        f = Double.parseDouble(parameters[6]);
        g = Double.parseDouble(parameters[7]);
        h = Double.parseDouble(parameters[8]);
        i = Double.parseDouble(parameters[9]);
        j = Double.parseDouble(parameters[10]);
    }

    @Override
    public Point getIntersection(Ray ray) {
        //Aq = Axd2 + Byd2 + Czd2 + Dxdyd + Exdzd + Fydzd
        double A, B, C, t0, t1;
        Point E = ray.getOrigin();
        Ray R = ray;
        Point intersection = null;

        A = a * R.getX() * R.getX() + b * R.getY() * R.getY() + c * R.getZ() * R.getZ() +
                2 * (d * R.getX() * R.getY() + e * R.getX() * R.getZ() + f * R.getY() * R.getZ());
        B = 2 * (a * E.getX() * R.getX() + b * E.getY() * R.getY() + c * E.getZ() * R.getZ() +
                d * (E.getX() * R.getY() + E.getY() * R.getX()) + e * (E.getX() * R.getZ() + E.getZ() * R.getX()) +
                f * (E.getY() * R.getZ() + E.getZ() * R.getY()) + g * R.getX() + h * R.getY() + i * R.getZ());
        C = a * E.getX() * E.getX() + b * E.getY() * E.getY() + c * E.getZ() * E.getZ() +
                2 * (d * E.getX() * E.getY() + e * E.getX() * E.getZ() + f * E.getY() * E.getZ() +
                g * E.getX() + h * E.getY() + i * E.getZ()) + j;

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

    @Override
    public Vector getNormal(Point p) {
        double xComponent = 2 * a * p.getX() + d * p.getY() + g;
        double yComponent = 2 * b * p.getY() + f * p.getZ() + h;
        double zComponent = 2 * c * p.getZ() + e * p.getX() + i;
        return (new Vector(xComponent, yComponent, zComponent)).getNormalized();
    }

    public String toString() {
        String out = "Simple Quad\n";
        out += "a, b, c, d: " + a + " " + b + " " + c + " " + d + "\n";
        out += "e, f, g, h: " + e + " " + f + " " + g + " " + h + "\n";
        out += "i, j: " + i + " " + j;
        out += super.toString();
        return out;
    }
}
