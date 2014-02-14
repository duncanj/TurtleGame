package turtlegame.games;

import turtlegame.RenderContext;

import java.awt.*;

public interface Game {
    public String getName();
    public boolean testCollisions(GameContext ctx);
    public boolean testWin(GameContext ctx);
    public void drawGameArea(GameContext ctx, Graphics2D g);
}
