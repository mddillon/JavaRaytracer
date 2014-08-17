package com.JavaRaytracer.Model.Scene;

import com.JavaRaytracer.Model.Geometry.Point;
import com.JavaRaytracer.Model.Geometry.Ray;
import com.JavaRaytracer.Model.Geometry.Vector;
import com.JavaRaytracer.Model.Render.Render;

public class Camera {

    private Vector upAxis;
    private Vector rightAxis;
    private Vector aim;
    private Point position;
    private double halfwidth, focalLength;

    public Camera() {
        upAxis = new Vector(0, 1, 0);
        rightAxis = new Vector(1, 0, 0);
        aim = Vector.cross(upAxis, rightAxis);
        position = new Point();
        halfwidth = Math.tan(Math.PI/6);
        focalLength = -1;
    }

    public Camera(double x, double y, double z, double yaw,
                  double pitch, double halfwidth, double focalLength) {
        position = new Point(x, y, z);
        yaw = Math.toRadians(yaw);
        aim = new Vector(Math.sin(yaw), 0, -Math.cos(yaw));
        rightAxis = Vector.cross(aim, new Vector(0, 1, 0));
        this.halfwidth = halfwidth;
        this.focalLength = focalLength;

        if (pitch % 360 != 0) {
            pitch = Math.toRadians(pitch);
            aim = Vector.getSum(
                    rightAxis.getScaled(Vector.dot(aim, rightAxis) * (1 - Math.cos(pitch))),
                    aim.getScaled(Math.cos(pitch)),
                    Vector.cross(rightAxis, aim).getScaled(Math.sin(pitch))
            );
        }

        upAxis = Vector.cross(rightAxis, aim);
    }

    public Camera(double[] cameraSettings) {
        this(cameraSettings[0], cameraSettings[1], cameraSettings[2],cameraSettings[3],
                cameraSettings[4], cameraSettings[5], cameraSettings[6]);
    }

    public Ray castRayTowardsPixel(int i, int j) {
        Vector topLeft = Vector.getSum(
                position, aim, upAxis.getScaled(halfwidth),
                rightAxis.getScaled(-halfwidth)
        );

        double screenwidth = 2 * halfwidth;
        double screenheight = 2 * halfwidth;
        double hfrac = screenwidth / Render.getCanvasWidth() * (j+.5);
        double vfrac = screenheight /Render.getCanvasHeight() * (i+.5);

        Point pixel = Point.getSum(
                topLeft,
                rightAxis.getScaled(hfrac),
                upAxis.getScaled(-vfrac)
        );

        return new Ray(position, pixel);
    }

    public String toString() {
        String out = "Camera\n";
        out += "Position " + position.toString();
        out += "Right Axis, Up Axis, and Forward Axis:\n";
        out += rightAxis.toString() + upAxis.toString() + aim.toString();
        out += "Halfwidth & FocalLength: " + halfwidth + ", " + focalLength +"\n";
        return out;
    }
}
