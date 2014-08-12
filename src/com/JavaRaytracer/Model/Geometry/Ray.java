package com.JavaRaytracer.Model.Geometry;

public class Ray extends Vector {
    private Point origin;
    
    /**
     * Constructs a unit array at the world origin.
     */
    public Ray() {
        super(1.0/3, 1.0/3, 1.0/3);
        origin = new Point();
    }
    
    /**
     * Constructs a ray given its origin coordinates and its directional
     * components.
     * @param x0 the x coordinate of the origin in world space
     * @param y0 the y coordinate of the origin in world space
     * @param z0 the z coordinate of the origin in world space
     * @param x  the directional component in the x direction
     * @param y  the directional component in the y direction
     * @param z  the directional component in the z direction
     */
    public Ray(double x0, double y0, double z0, double x, double y, double z) {
        super(x, y, z);
        origin = new Point(x0, y0, z0);
    }
    
    /**
     * Constructs a ray given a starting point and another point along the ray.
     * @param origin the starting point of the ray
     * @param next   any secondary point that exists on the ray
     */
    public Ray(Point origin, Point next) {    
        super(origin, next);
        this.origin = origin;
    }
    
    /**
     * Constructs a ray from a given starting point and direction.
     * @param origin the starting point of the ray
     * @param dir    the direction of the ray
     */
    public Ray(Point origin, Vector dir) {
        super(dir.x, dir.y, dir.z);
        this.origin = origin;
    }
    
    /**
     * Constructs a new Ray given a direction Vector. Assumes that the ray's
     * origin is located at the origin of the world, i.e. (0, 0, 0).
     * @param dir the direction of the ray
     */
    public Ray(Vector dir) {
        super(dir.x, dir.y, dir.z);
        origin = new Point();
    }
    
    /**
     * Gets a Point object denoting the origin of the ray
     * @return the origin of the ray
     */
    public Point getOrigin() {
        return origin;
    }
    
    public String toString() {
        String out = "Ray\n";
        out += "Direction: " + x + ", " + y + ", " + z + "\n";
        out += "Origin: " + origin.getX() + ", " + origin.getY() + ", " + 
                origin.getZ() + "\n";
        return out;
    }    
}
