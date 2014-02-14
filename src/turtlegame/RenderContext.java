package turtlegame;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RenderContext {
    public double scaleX;
    public double scaleY;
    public double scaleR;

    public int height;

    public Graphics2D g;

    private AffineTransform originalTransform;

    public void init(Graphics2D g, int width, int height) {
        this.g = g;

        originalTransform = g.getTransform();

        this.height = height;

        scaleX = width / RenderPanel.XSTEPS;
        scaleY = height / RenderPanel.YSTEPS;

        scaleR = (2.0 * Math.PI) / RenderPanel.RSTEPS;
    }

    public void translate(int stepsX, int stepsY) {
        g.translate(scaleX(stepsX), scaleY(stepsY));
    }

    public void rotate(int steps) {
        g.rotate(scaleR(steps));
    }

    private int scaleX(int x) {
        return (int)(((double)x) * scaleX);
    }

    private int scaleY(int y) {
        return (int)(((double)y) * scaleY);
    }

    private double scaleR(int r) {
        return ((double)r) * scaleR;
    }

    public void reset() {
        g.setTransform(originalTransform);
    }
}