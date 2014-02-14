package turtlegame.games;

import turtlegame.RenderContext;

import java.awt.*;

public class None implements Game {
    @Override
    public String getName() {
        return "None";
    }

    @Override
    public boolean testCollisions(GameContext ctx) {
        return false;
    }

    @Override
    public boolean testWin(GameContext ctx) {
        return false;
    }

    @Override
    public void drawGameArea(GameContext ctx, Graphics2D g) {
    }
}
