package com.JavaRaytracer.Model.Scene;

import java.util.ArrayList;
import java.util.LinkedList;

import com.JavaRaytracer.Model.Geometry.*;

public class Scene {
    private ArrayList<Light> lights;
	private ArrayList<Surface> surfaces;
    private LinkedList<Camera> cameras;
    private Color background;
    private int maxdepth;
    
    public Scene() {
        lights = new ArrayList<Light>();
        surfaces = new ArrayList<Surface>();
        cameras = new LinkedList<>();
        background = new Color();
        maxdepth = 5;
    }
    
    public void setBackground(Color background) {
        if (background != null) {
            this.background = background;
        }
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

    public int getMaxdepth() {
        return maxdepth;
    }

    public void addCamera(Camera camera) {
        cameras.add(camera);
    }

    public LinkedList<Camera> getCameras() {
        return cameras;
    }
}
