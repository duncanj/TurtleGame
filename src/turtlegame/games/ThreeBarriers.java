package turtlegame.games;

import turtlegame.RenderPanel;

import java.awt.*;

public class ThreeBarriers implements Game {
    private Rectangle gate = new Rectangle((RenderPanel.XSTEPS/2)-5, 2, 10, 10);

    private Rectangle barrier1 = new Rectangle(20, (int)(0.33*RenderPanel.YSTEPS)-2, RenderPanel.XSTEPS-20, 1);
    private Rectangle barrier2 = new Rectangle(0, (RenderPanel.YSTEPS/2)-2, RenderPanel.XSTEPS-20, 1);
    private Rectangle barrier3 = new Rectangle(20, (int)(0.66*RenderPanel.YSTEPS)-2, RenderPanel.XSTEPS-20, 1);

    @Override
    public String getName() {
        return "Three Barriers";
    }

    @Override
    public boolean testWin(GameContext ctx) {
        Point p = ctx.getTurtlePosition();
        return GameHelper.isWithinGate(p, gate, ctx);
    }

    @Override
    public boolean testCollisions(GameContext ctx) {
        return (
                   GameHelper.isCollidingWithBarrier(ctx.getTurtlePosition(), ctx.getLastPosition(), barrier1, ctx)
                || GameHelper.isCollidingWithBarrier(ctx.getTurtlePosition(), ctx.getLastPosition(), barrier2, ctx)
                || GameHelper.isCollidingWithBarrier(ctx.getTurtlePosition(), ctx.getLastPosition(), barrier3, ctx)
        );
    }

    @Override
    public void drawGameArea(GameContext ctx, Graphics2D g) {
        GameHelper.drawGate(gate, ctx, g);
        GameHelper.drawBarrier(barrier1, ctx, g);
        GameHelper.drawBarrier(barrier2, ctx, g);
        GameHelper.drawBarrier(barrier3, ctx, g);
    }
}
