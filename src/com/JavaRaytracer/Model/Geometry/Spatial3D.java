package com.JavaRaytracer.Model.Geometry;

public abstract class Spatial3D {
    protected double x, y, z;
    
    /**
     * Returns the x component of the 3D element
     * @return the element's x component
     */    
    public double getX() {return x;}

    /**
     * Returns the y component of the 3D element
     * @return the element's y component
     */
    public double getY() {return y;}

    /**
     * Returns the y component of the 3D element
     * @return the element's y component
     */
    public double getZ() {return z;}
    
}
