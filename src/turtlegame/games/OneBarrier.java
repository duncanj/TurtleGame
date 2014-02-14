package turtlegame.games;

import turtlegame.RenderPanel;

import java.awt.*;

public class OneBarrier extends Simple {
    private Rectangle barrier = new Rectangle((RenderPanel.XSTEPS/2)-20, (RenderPanel.YSTEPS/2)-2, 40, 1);

    public String getName() {
        return "One Barrier";
    }

    public boolean testCollisions(GameContext ctx) {
        return GameHelper.isCollidingWithBarrier(ctx.getTurtlePosition(), ctx.getLastPosition(), barrier, ctx);
    }

    public void drawGameArea(GameContext ctx, Graphics2D g) {
        super.drawGameArea(ctx, g);
        GameHelper.drawBarrier(barrier, ctx, g);
    }
}
