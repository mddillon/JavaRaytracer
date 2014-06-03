package com.JavaRaytracer.Model.Geometry;

public class Point {
    private double x, y, z;

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
     * Returns the x coordinate of the point.
     * @return the point's x coordinate
     */
    public double getX() {return x;}
    
    /**
     * Returns the y coordinate of the point.
     * @return the point's y coordinate
     */
    public double getY() {return y;}
    
    /**
     * Returns the z coordinate of the point.
     * @return the point's z coordinate
     */
    public double getZ() {return z;}

    /**
     * Adds two points by their coordinate values.
     * @param  a point in world space
     * @param  a point in world space
     * @return a new point whose coordinates are the sum of the inputted points'
     *         coordinates
     */
    public static Point add(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y, a.z + b.z);
    }
}
