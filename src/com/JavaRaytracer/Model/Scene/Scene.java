package com.JavaRaytracer.Model.Scene;

import java.util.ArrayList;

import com.JavaRaytracer.Model.Geometry.*;

public class Scene {
    private ArrayList<Light> lights;
	private ArrayList<Surface> surfaces;
    private Point cameraPos;
    private double cameraYaw;
    private double cameraPitch;
    private Color background;
    private double focalplane;
    private int maxdepth;
    private double halfwidth;
    
    public Scene() {
        lights = new ArrayList<Light>();
        surfaces = new ArrayList<Surface>();
        background = new Color();
        focalplane = -1;
        halfwidth = Math.tan(Math.PI/6);
        maxdepth = 5;
        cameraPos = new Point();
        cameraYaw = 0;
        cameraPitch = 0;
    }
    
    public void setBackground(Color background) {
        if (background != null) {
            this.background = background;
        }
    }
    
    public void setCameraPosition(Point cameraPos) {
		if (cameraPos != null) {
            this.cameraPos = cameraPos;
        }
    }

    public void setCameraSpin(double cameraYaw) {
        this.cameraYaw = cameraYaw;
    }

    public void setCameraTilt(double cameraPitch) {
        this.cameraPitch = cameraPitch;
    }
    
    public void addPinlight(Light pinlight) {
		if (pinlight != null) {
			lights.add(pinlight);
        }
    }
    
    public void addSurface(Surface surface) {
		if (surface != null) {
			surfaces.add(surface);
		}
    }

    public ArrayList<Surface> getSurfaces() {
        return surfaces;
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public Color getBackground() {
        return background;
    }

    public Point getCameraPos() {
        return cameraPos;
    }

    public double getHalfwidth() {
        return halfwidth;
    }

    public double getFocalplane() {
        return focalplane;
    }

    public int getMaxdepth() {
        return maxdepth;
    }

    public double getCameraYaw() {
        return cameraYaw;
    }

    public double getCameraPitch() {
        return cameraPitch;
    }

    public void setHalfwidth(double halfwidth) {
        this.halfwidth = halfwidth;
    }
}
