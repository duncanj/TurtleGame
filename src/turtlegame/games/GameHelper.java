package turtlegame.games;

import turtlegame.RenderContext;

import java.awt.*;
import java.awt.geom.Line2D;

public class GameHelper {
    public static void drawGate(Rectangle gate, GameContext ctx, Graphics2D g) {
        RenderContext renderCtx = ctx.getRenderContext();
        g.setColor(Color.yellow);
        fillRect(gate.x,gate.y,gate.width,1,renderCtx,g);
        fillRect(gate.x,gate.y,1,gate.height,renderCtx,g);
        fillRect(gate.x+gate.width-1,gate.y,1,gate.height,renderCtx,g);
    }

    public static void drawBarrier(Rectangle barrier, GameContext ctx, Graphics2D g) {
        RenderContext renderCtx = ctx.getRenderContext();
        g.setColor(Color.red);
        fillRect(barrier,renderCtx,g);
    }

    public static boolean isWithinGate(Point p, Rectangle gate, GameContext ctx) {
        Point p2 = ctx.convertToStepCoordinates(p);
        return gate.contains(p2);
    }

    public static boolean isCollidingWithBarrier(Point p1, Point p2, Rectangle barrier, GameContext ctx) {
        p1 = ctx.convertToStepCoordinates(p1);
        p2 = ctx.convertToStepCoordinates(p2);
        if( barrier.contains(p1) ) {
            return true;
        }
        return barrier.intersectsLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    private static void fillRect(Rectangle r, RenderContext renderCtx, Graphics2D g) {
        fillRect(r.x, r.y, r.width, r.height, renderCtx, g);
    }

    private static void fillRect(int x, int y, int width, int height, RenderContext renderCtx, Graphics2D g) {
        x = (int)renderCtx.scaleX(x);
        y = (int)renderCtx.scaleY(y);
        width = (int)renderCtx.scaleX(width);
        height = (int)renderCtx.scaleY(height);
        g.fillRect(x,y,width,height);
    }

}
