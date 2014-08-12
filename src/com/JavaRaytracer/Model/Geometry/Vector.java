package com.JavaRaytracer.Model.Geometry;

import java.lang.Math;
import java.util.Arrays;

public class Vector extends Spatial3D {

    /**
     * Constructs a zero vector.
     */
    public Vector() {
        x = 0; y = 0; z = 0;
    }

    /**
     * Constructs a vector from its individual components
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     */
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a vector whose direction points from point A to point B and
     * whose magnitude is the length of the line segment connecting the two 
     * points.
     * @param a a point in world space
     * @param b a point in world space
     */
    public Vector(Point a, Point b) {
        x = b.getX() - a.getX();
        y = b.getY() - a.getY();
        z = b.getZ() - a.getZ();
    }

    /**
     * Returns a scaled vector by multiplying all of its components by a given
     * coefficient.
     * @param t a coefficient by which to scale the vector
     * @return the scaled vector
     */
    public Vector getScaled(double t) {
        return new Vector(x*t, y*t, z*t);
    }

    /**
     * @return a vector equal to the normalized vector
     */
    public Vector getNormalized() {
        double norm = magnitude(x, y, z);
        return new Vector(x/norm, y/norm, z/norm);
    }

    /**
     * @return a vector equal to the negated vector
     */
    public Vector getNegated() {
        return new Vector(-x, -y, -z);
    }

    /**
     * @return a vector reflected about the input vector
     */
    public Vector getReflected(Vector refl) {
        Vector normal = getNormalized();
        refl = refl.getNormalized();
        double dotproduct = Vector.dot(normal, refl);
        Vector out = new Vector(2*normal.x*dotproduct - refl.x,
                                2*normal.y*dotproduct - refl.y,
                                2*normal.z*dotproduct - refl.z);
        return out;
    }
    
    public String toString() {
        String out = "Vector\n" + x + ", " + y + ", " + z + "\n";
        return out;
    }

    /**
     * Performs the naive dot product of two vectors.
     */
    public static double dot(Vector a, Vector b) {
        return (a.x * b.x + a.y * b.y + a.z * b.z);
    }

    /**
     * Performs the cross product of two vectors.
     */
    public static Vector cross(Vector a, Vector b) {
        double outx = a.y*b.z - a.z*b.y;
        double outy = a.z*b.x - a.x*b.z;
        double outz = a.x*b.y - a.y*b.x;
        return new Vector(outx, outy, outz);
    }

    /**
     * Computes the determinant of three vectors.
     */
    public static double determinant(Vector a, Vector b, Vector c) {
        double out = 0.0;
        out += a.x*(b.y*c.z - b.z*c.y);
        out -= b.x*(a.y*c.z - a.z*c.y);
        out += c.x*(a.y*b.z - a.z*b.y);
        return out;
    }

    /**
     * Adds multiple 3D elements by their coordinate values.
     * @param terms a list of Spatial3D terms to be added
     * @return a new vector whose coordinates are the sum of the terms' coordinates
     */
    public static Vector getSum(Spatial3D ... terms) {
        double x = 0; double y = 0; double z = 0;
        for (int i = 0; i < terms.length; i++) {
            x += terms[i].x;
            y += terms[i].y;
            z += terms[i].z;
        }
        return new Vector(x, y, z);
    }
    /**
     * Computes the component-wise difference of the given 3D elements. 
     * @return the vector difference denoted by a - b
     */
    public static Vector getDifference(Spatial3D a, Spatial3D b) {
        return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static double magnitude(double x, double y, double z) {
        double[] temp = new double[] {Math.abs(x), Math.abs(y), Math.abs(z)};
        Arrays.sort(temp);
        double smaller = temp[1]/temp[2];
        double smallest = temp[0]/temp[2];
        double result = Math.sqrt(1.0 + smaller*smaller + smallest*smallest);
        result *= temp[2];
        return result;
    }
    
    public static double magnitude(Vector v) {
        double[] temp = new double[] {Math.abs(v.x), Math.abs(v.y), Math.abs(v.z)};
        Arrays.sort(temp);
        double smaller = temp[1]/temp[2];
        double smallest = temp[0]/temp[2];
        double result = Math.sqrt(1.0 + smaller*smaller + smallest*smallest);
        result *= temp[2];
        return result;    
    }
}
