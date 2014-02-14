package turtlegame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class RenderContext {
    private double scaleX;
    private double scaleY;
    private double scaleR;

    private int height;

    private Graphics2D g;

    private ArrayList<AffineTransform> stack = new ArrayList();

    public void init(Graphics2D g, int width, int height) {
        this.g = g;

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

    /**
     * Get the current origin position in component co-ordinates.
     * @return
     */
    public Point getComponentCoordinates() {
        int tx = (int)(g.getTransform().getTranslateX());
        int ty = (int)(g.getTransform().getTranslateY());
        return new Point(tx, ty);
    }

    public void pushTransform(AffineTransform transform) {
        stack.add(g.getTransform());
        g.setTransform(transform);
    }

    public void popTransform() {
        if( stack.isEmpty() ) {
            return;
        }
        AffineTransform transform = stack.remove(stack.size()-1);
        g.setTransform(transform);
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
}