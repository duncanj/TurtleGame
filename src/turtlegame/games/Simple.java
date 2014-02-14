package turtlegame.games;

import turtlegame.RenderPanel;

import java.awt.*;

public class Simple implements Game {
    private Rectangle gate = new Rectangle((RenderPanel.XSTEPS/2)-5, 2, 10, 10);

    @Override
    public String getName() {
        return "Simple";
    }

    @Override
    public boolean testCollisions(GameContext ctx) {
        return false;
    }

    @Override
    public boolean testWin(GameContext ctx) {
        Point p = ctx.getTurtlePosition();
        return GameHelper.isWithinGate(p, gate, ctx);
    }

    @Override
    public void drawGameArea(GameContext ctx, Graphics2D g) {
        GameHelper.drawGate(gate, ctx, g);
    }
}
