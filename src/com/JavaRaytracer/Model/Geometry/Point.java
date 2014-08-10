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

    public String toString() {
        String out = "Point\n";
        out += x + ", " + y + ", " + z + "\n";
        return out;
    }

    /**
     * Adds two 3D elements by their coordinate values.
     * @param a a 3D element
     * @param b a 3D element
     * @return a new point whose coordinates are the sum of the first and second
     *         elements
     */
    public static Point getSum(Spatial3D a, Spatial3D b) {
        return new Point(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    /**
     * Finds the difference between two 3D elements by subtracting the second's
     * component values from the first's.
     * @param a a point in world space
     * @param b a point in world space
     * @return a new point whose coordinates are equal to a - b
     */    
    public static Point getDifference(Spatial3D a, Spatial3D b) {
        return new Point(a.x - b.x, a.y - b.y, a.z - b.z);
    }

}
