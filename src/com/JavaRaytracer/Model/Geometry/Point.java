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
     * @param a a point in world space
     * @param b a point in world space
     * @return a new point whose coordinates are the sum of the first and second
     *         point
     */
    public static Point getSum(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    
    /**
     * Computes the point representing the sum of the point and vector 
     * parameters.
     * @param a point in world space
     * @param d vector in world space
     * @return  the point along the vector separated from the first point by a
                distance of the vector's length.
     */
    public static Point getSum(Point a, Vector d) {
        return new Point(a.x + d.x, a.y + d.y, a.z + d.z);
    }
    
    /**
     * Finds the difference between two points by subtracting the second's
     * component values from the first's.
     * @param a a point in world space
     * @param b a point in world space
     * @return a new point whose coordinates is  equal to a - b
     */    
    public static Point getDifference(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    /**
     * Computes the point representing the difference of the point and vector 
     * parameters.
     * @param a a point in world space
     * @param d a vector in world space
     * @return the point along the negated vector separated from the first point
     * by a distance of the vector's length
     */      
    public static Point getDifference(Point a, Vector d) {
        return new Point(a.x - d.x, a.y - d.y, a.z - d.z);    
    }
}
