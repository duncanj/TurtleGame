package turtlegame.games;

import turtlegame.RenderPanel;
import turtlegame.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Maze implements Game {
    private Rectangle gate = new Rectangle((RenderPanel.XSTEPS/2)-5, 2, 10, 10);

    private ArrayList<Rectangle> barriers = new ArrayList();

    public Maze() {
        buildMaze();
    }

    @Override
    public String getName() {
        return "Maze";
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

    private void buildMaze() {
        addV(0,3,7);
        addH(2,3,8);
        addV(2,3,5);
        addH(0,9,3);
        addH(2,5,2);
        addH(9,4,6);
        addV(14,4,2);
        addH(14,6,6);
        addV(17,4,2);
        addH(17,4,5);
        addH(21,3,5);
        addV(21,5,2);
        addV(7,4,3);
        addH(5,5,3);
        addH(7,6,6);
        addH(4,8,5);
        addV(5,6,3);
        addH(10,8,13);
        addV(4,8,5);
        addV(10,8,3);
        addH(8,10,4);
        addV(6,10,6);
        addV(2,11,9);
        addH(2,12,3);
        addH(1,14,4);
        addH(0,12,1);
        addH(4,15,4);
        addV(11,10,11);
        addH(6,12,4);
        addH(9,14,3);

        addH(2,23,9);
        addH(0,25,13);
        addV(0,16,10);
        addH(0,21,3);
        addV(12,20,6);
        addH(8,20,5);

        addH(4,21,3);
        addV(5,22,1);
        addV(8,20,2);
        addV(7,15,4);
        addV(4,18,3);
        addH(6,18,1);
        addV(6,20,1);
        addV(5,15,2);
        addV(9,16,3);
        addV(10,22,1);

        addV(13,8,6);
        addH(13,10,4);
        addH(14,26,13);

        addV(16,22,2);
        addH(12,22,5);
        addH(18,22,7);
        addH(14,24,7);
        addH(22,24,3);
        addH(11,15,5);
        addV(15,12,8);

        addH(13,17,3);
        addH(14,20,6);
        addV(20,20,2);
        addV(13,17,2);
        addH(2,18,3);

        addH(16,18,5);
        addV(18,8,3);
        addV(24,18,6);

        addH(23,5,2);
        addV(23,5,5);
        addH(23,9,2);
        addH(25,7,2);
        addH(22,16,4);
        addV(22,16,5);

        addH(23,11,3);
        addV(23,11,3);
        addH(23,13,1);

        addH(17,16,4);
        addH(17,12,4);
        addH(18,10,4);
        addV(21,10,3);

        addV(17,12,2);
        addV(17,15,1);
        addH(23,13,2);

        addV(20,14,2);
        addH(20,14,4);

        addH(-h,0,h);
        addH(-h,25,h);
        addV(-1,0,26);

        addV(26,0,26);
        addH(26,0,5);
        addH(26,25,5);

        addV(-3,2,21);
        addV(28,2,22);
        addV(12,25,2);
    }

    int h = 10;
    int v = 5;
    int sh = 3;
    int sv = 3;
    public void addH(int x, int y, int len) {
        barriers.add(new Rectangle(h+x*sh, v+y*sv, len*sh, sv));
    }

    public void addV(int x, int y, int len) {
        barriers.add(new Rectangle(h+x*sh, v+y*sv, sh, len*sv));
    }

    public static void main(String[] args) {
        Maze m = new Maze();
        Screen s = new Screen();
        RenderPanel p = new RenderPanel(s);
        JFrame f = new JFrame();
        f.getContentPane().add(p);

    }

}
