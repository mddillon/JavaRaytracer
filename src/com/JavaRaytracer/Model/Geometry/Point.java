package com.JavaRaytracer.Model.Geometry;

public class Point extends Spatial3D {

    /**
     * Constructs a point at the world origin.
     */
    public Point() {
        x = 0; y = 0; z = 0;
    }

    /**
     * Constructs a point at the given coordinates.
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     */
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return a String representation of the Point
     */
    public String toString() {
        String out = "Point: ";
        out += x + ", " + y + ", " + z + "\n";
        return out;
    }

    /**
     * Adds multiple 3D elements by their coordinate values.
     * @param terms an array of Spatial3D terms to be added
     * @return a new point whose coordinates are the sum of the terms' coordinates
     */

    public static Point getSum(Spatial3D ... terms) {
        double x = 0; double y = 0; double z = 0;
        for (int i = 0; i < terms.length; i++) {
            x += terms[i].x;
            y += terms[i].y;
            z += terms[i].z;
        }
        return new Point(x, y, z);
    }

    /**
     * Finds the difference between two 3D elements by subtracting the second's
     * component values from the first's.
     * @param a an element in 3D space
     * @param b an element in 3D space
     * @return a new point whose coordinates are equal to the component-wise a - b
     */    
    public static Point getDifference(Spatial3D a, Spatial3D b) {
        return new Point(a.x - b.x, a.y - b.y, a.z - b.z);
    }

}
