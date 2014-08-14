package com.JavaRaytracer.Model.Render;

import com.JavaRaytracer.Model.Geometry.Point;
import com.JavaRaytracer.Model.Geometry.Ray;
import com.JavaRaytracer.Model.Geometry.Vector;
import com.JavaRaytracer.Model.Scene.Color;
import com.JavaRaytracer.Model.Scene.Light;
import com.JavaRaytracer.Model.Scene.Scene;
import com.JavaRaytracer.Model.Scene.Surface;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

import java.nio.IntBuffer;

public class Render {
    private static Scene scene;
    private static Canvas canvas;
    private static int[] pixelBuffer;
    private static int width;
    private static int height;

    public static void setScene(Scene scene) {
        Render.scene = scene;
    }

    public static void setCanvas(Canvas canvas) {
        if (canvas != null) {
            Render.canvas = canvas;
            width = (int) canvas.getWidth();
            height = (int) canvas.getHeight();
            pixelBuffer = new int[width * height];
        }
    }

    public static int[] getImageData() {
        return pixelBuffer;
    }

    public static void trace() {
        double yaw = scene.getCameraYaw();
        double pitch = scene.getCameraPitch();
        Point eye = scene.getCameraPos();
        yaw = Math.toRadians(yaw);
        Vector aim = new Vector(Math.sin(yaw), 0, -Math.cos(yaw));
        Vector rAxis = Vector.cross(aim, new Vector(0, 1, 0));

        if (pitch % 360 != 0) {
            pitch = Math.toRadians(pitch);
            aim = Vector.getSum(
                    rAxis.getScaled(Vector.dot(aim, rAxis) * (1 - Math.cos(pitch))),
                    aim.getScaled(Math.cos(pitch)),
                    Vector.cross(rAxis, aim).getScaled(Math.sin(pitch))
            );
        }

        Vector upVec = Vector.cross(rAxis, aim);
        Vector topLeft = Vector.getSum(
                eye, aim, upVec.getScaled(scene.getHalfwidth()),
                rAxis.getScaled(-scene.getHalfwidth())
        );
        double screenwidth = 2 * scene.getHalfwidth();
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                Color shade = new Color();
                double hfrac = screenwidth/width*(j+.5);
                double vfrac = screenwidth/height*(i+.5);
                Point pixel = Point.getSum(
                        topLeft,
                        rAxis.getScaled(hfrac),
                        upVec.getScaled(-vfrac)
                );
                Ray ray = new Ray(eye, pixel);
                shade = Color.addColors(shade, trace(ray, 0));
                pixelBuffer[i*width + j] = shade.toArgbInt();
            }
        }
        writePixels();
    }

    private static Color trace(Ray ray, int depth) {
        Point intersection = null;
        Surface frontmost = null;
        Color black = new Color();

        for (Surface current : scene.getSurfaces()) {
            Point temp = current.getIntersection(ray);
            if (temp != null) {
                double distance = Vector.magnitude(new Vector(scene.getCameraPos(), temp));
                if (intersection == null || distance < Vector.magnitude(new Vector(scene.getCameraPos(), intersection))) {
                    intersection = temp;
                    frontmost = current;
                }
            }
        }

        if (intersection == null) return scene.getBackground();
        else {
            Vector normal = frontmost.getNormal(intersection);
            Vector vecE = (new Vector(intersection, ray.getOrigin())).getNormalized();

            Color ambientTerm;
            Color specularTerm = new Color();
            Color directionalTerm = new Color();
            Color reflectionTerm = new Color();

            for (Light x : scene.getLights()) {
                Vector vec = new Vector(intersection, x.getPosition());
                Vector vecL = vec.getNormalized();
                Vector vecR = normal.getReflected(vec);
                if (!shadowTrace(new Ray(intersection, x.getPosition())) || frontmost.getReflectance() != 0) {
                    double directionalCoefficient = Math.max(Vector.dot(normal, vecL), 0);
                    directionalTerm = Color.addColors(directionalTerm, x.getColor().scalarMultiply(directionalCoefficient));
                    if (!frontmost.getSpecular().equals(black)) {
                        double specularCoefficient = Math.pow((Math.max(Vector.dot(vecE, vecR), 0)), frontmost.getPhong());
                        specularTerm = Color.addColors(specularTerm, x.getColor().scalarMultiply(specularCoefficient));
                    }
                }
            }

            ambientTerm = Color.multiplyColors(frontmost.getDiffuse(), frontmost.getAmbient());
            directionalTerm = Color.multiplyColors(directionalTerm, frontmost.getDiffuse());
            specularTerm = Color.multiplyColors(specularTerm, frontmost.getSpecular());

            Color result;
            // Reflection Generating
            if (frontmost.getReflectance() != 0 && depth < scene.getMaxdepth()) {
                reflectionTerm = trace(new Ray(intersection, normal.getReflected(vecE)), ++depth);
                reflectionTerm = reflectionTerm.scalarMultiply(frontmost.getReflectance());
            }
            result = Color.addColors(ambientTerm, specularTerm, directionalTerm, reflectionTerm);
            return result;
        }
    }

    // Checks to see if we hit an object in the scene along the way to the light.
    private static boolean shadowTrace(Ray shadowRay) {
        Point intersection = null;
        int ndx = 0;
        while (intersection == null && ndx<scene.getSurfaces().size())
            intersection = scene.getSurfaces().get(ndx++).getIntersection(shadowRay);
        return (intersection != null);
    }

    private static void writePixels() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter writer = gc.getPixelWriter();
        PixelFormat<IntBuffer> format = PixelFormat.getIntArgbInstance();
        int offset = 0;
        writer.setPixels(0, 0, width, height, format, pixelBuffer, offset, width);
    }
}
