package com.JavaRaytracer.Model.Scene;

public class Color {

    private double r,g,b;

    public Color() {
        r = 0;
        g = 0;
        b = 0;
    }

    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
        limit();
    }

    public Color clone() {
        Color result = new Color(r, g, b);
        return result;
    }

    public Color scalarMultiply(double scalarValue) {
        Color result = new Color();
        result.r = r * scalarValue;
        result.g = g * scalarValue;
        result.b = b * scalarValue;
        result.limit();
        return result;
    }
    
    public Color scalarDivide(double scalarValue) {
        Color result = new Color();
        result.r = r / scalarValue;
        result.g = g * scalarValue;
        result.b = b * scalarValue;
        result.limit();
        return result;
    }

    boolean equals(Color x) {
        if (r == x.r && g == x.g && b == x.b) return true;
        else return false;
    }

    public String toString() {
        return ("r, g, b: " + r + ", " + g + ", " + b + "\n");
    }
    
    public int toArgbInt() {
        int alpha = 0xFF000000;
        int red = (int) Math.round(255*r);
        int green = (int) Math.round(255*g);        
        int blue = (int) Math.round(255*b);
        red = red << 16;
        green = green << 8;
        return (alpha | red | green | blue);
    }
    
    public static Color addColors(Color ... colorlist) {
        Color out = new Color();
        for (Color x : colorlist) {
            out.r += x.r;
            out.g += x.g;
            out.b += x.b;
        }
        out.limit();
        return out;
    }

    public static Color multiplyColors(Color ... colorlist) {
        Color out = new Color(1.0, 1.0, 1.0);
        for (Color x : colorlist) {
            out.r *= x.r;
            out.g *= x.g;
            out.b *= x.b;
        }
        out.limit();
        return out;
    }
    
    private void limit() {
        if (r > 1.0) r = 1.0;
        if (g > 1.0) g = 1.0;
        if (b > 1.0) b = 1.0;
        if (r < 0) r = 0;
        if (g < 0) g = 0;
        if (b < 0) b = 0;
    }
}
