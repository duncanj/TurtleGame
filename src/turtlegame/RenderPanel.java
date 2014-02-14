package turtlegame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

public class RenderPanel extends JPanel {
    private RenderContext ctx = new RenderContext();

    public static final int XSTEPS = 50;
    public static final int YSTEPS = 50;
    public static final int RSTEPS = 16;

    private java.util.List<Command> commands = new ArrayList();

    private java.util.List<Point> points = new ArrayList<Point>();

    private Color DOTS = new Color(0,45,0);
    private Color TRAIL = DOTS;

    public RenderPanel() {
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        AffineTransform initialTransform = g.getTransform();

        g.scale(1,-1);
        g.translate(0,-getHeight()+30);

//        debugGrid(g);

        ctx.init(g, getWidth(), getHeight());

        ctx.translate(25,0);

        Point lastPoint = ctx.getComponentCoordinates();

        for( Command command : commands ) {
            g.setColor(DOTS);
            g.fillOval(-2,-2,4,4);

            command.apply(ctx);

            Point thisPoint = ctx.getComponentCoordinates();

            g.setColor(TRAIL);
            ctx.pushTransform(initialTransform);
            g.drawLine(lastPoint.x, lastPoint.y, thisPoint.x, thisPoint.y);
            ctx.popTransform();

//            g.drawLine(0,0,previousPoint.x,previousPoint.y);
//
            lastPoint = thisPoint;
        }

        drawTurtle(g);

        g.setTransform(initialTransform);
    }

    private void debugGrid(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(getFont().deriveFont(10.0f));
        int d = 100;
        for( int x=-1000; x<1000; x+=d ) {
            for( int y=-1000; y<1000; y+=d ) {
                g.drawLine(x-3,y,x+3,y);
                g.drawLine(x,y-3,x,y+3);
                g.drawString(""+x+","+y,x,y);
            }
        }
    }

//    public void setTurtle()

    public void executeCommand(Command command) {
        commands.add(command);
        repaint();
    }

    public void clear() {
        commands.clear();
        repaint();
    }

    private void drawTurtle(Graphics2D g) {
        g.setColor(Color.YELLOW);
        Polygon p = new Polygon(new int[]{-16,0,16,0}, new int[]{-10,-5,-10,30}, 4);
        g.fillPolygon(p);
    }
}
