package turtlegame.games;

import turtlegame.RenderPanel;

import java.awt.*;
import java.util.ArrayList;

public class Impossible implements Game {
    private Rectangle gate = new Rectangle((RenderPanel.XSTEPS/2)-5, 2, 10, 10);

    private ArrayList<Rectangle> barriers = new ArrayList();

    public Impossible() {
        buildMap();
    }

    @Override
    public String getName() {
        return "Impossible";
    }

    @Override
    public boolean testWin(GameContext ctx) {
        Point p = ctx.getTurtlePosition();
        return GameHelper.isWithinGate(p, gate, ctx);
    }

    @Override
    public boolean testCollisions(GameContext ctx) {
        for( Rectangle b : barriers ) {
            if( GameHelper.isCollidingWithBarrier(ctx.getTurtlePosition(), ctx.getLastPosition(), b, ctx) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void drawGameArea(GameContext ctx, Graphics2D g) {
        GameHelper.drawGate(gate, ctx, g);
        for( Rectangle b : barriers ) {
            GameHelper.drawBarrier(b, ctx, g);
        }
    }

    private void buildMap() {
        for( int i=0; i<9; i++ ) {
            int x = 0;
            int y = 20 + (i * 8);
            int w = 100;
            int h = 1;
            Rectangle r = new Rectangle(x,y,w,h);
            barriers.add(r);
        }
    }

}
