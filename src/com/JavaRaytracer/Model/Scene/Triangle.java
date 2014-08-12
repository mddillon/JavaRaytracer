package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.*;

public class Triangle extends Surface {

    private Point a, b, c;
    private Vector normal;

    // Assumes vertices given in clockwise order
    public Triangle(Point v1, Point v2, Point v3) {
        a = v1;
        b = v2;
        c = v3;

        Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);
        normal = Vector.cross(ac, ab);
        normal = normal.getNormalized();
    }

    public Point getIntersection(Ray ray) {
        Point intersection = null;
        // Matrix multiplication using Barycentric coordinates: Mx=S
        // The following are column vectors to the matrix M.
        Vector m1 = new Vector(b, a);
        Vector m2 = new Vector(c, a);
        Vector m3 = ray;
        Vector s = new Vector(ray.getOrigin(), a);
        // x = <beta, gamma, t>
        // The following is the application of Cramer's Rule
        double detM = Vector.determinant(m1, m2, m3);
        double beta = Vector.determinant(s, m2, m3) / detM;
        if (beta < 0 || beta > 1) return intersection;
        double gamma = Vector.determinant(m1, s, m3) / detM;
        if (gamma < 0 || gamma > 1-beta) return intersection;
        double t = Vector.determinant(m1, m2, s) / detM;
        if (t < EPSILON) {
            intersection = null;
        }
        else {
            intersection = Point.getSum(ray.getOrigin(), ray.getScaled(t));
        }
        return intersection;
    }
    
    public Vector getNormal(Point p) {
        return normal;
    }
    
    public String toString() {
        String out = "Triangle\n";
        out += "A " + a.toString();
        out += "B " + b.toString();
        out += "C " + c.toString();
        out += "Normal " + normal.toString();
        out += super.toString();
        out += "\n";
        return out;
    }
    
}
