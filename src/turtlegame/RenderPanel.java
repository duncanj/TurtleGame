package turtlegame;

import turtlegame.games.Game;
import turtlegame.games.GameContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.util.*;

public class RenderPanel extends JPanel {
    private RenderContext ctx = new RenderContext();
    private GameContext gameCtx = new GameContext();

    public static final int XSTEPS = 100;
    public static final int YSTEPS = 100;
    public static final int RSTEPS = 16;

    private java.util.List<Command> commands = new ArrayList();

    private Screen screen;

    private Color DOTS = new Color(0,45,0);
    private Color TRAIL = DOTS;

    private Stroke defaultStroke = new BasicStroke(1.0f);
    private Stroke wideStroke = new BasicStroke(10.0f);

    private Font bigFont = new Font("Monospaced", Font.BOLD, 24);

    public RenderPanel(Screen screen) {
        this.screen = screen;
        setDoubleBuffered(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        AffineTransform initialTransform = g.getTransform();

        g.setStroke(defaultStroke);

        g.scale(1.0,-1.0);
        g.translate(0.0,-getHeight()+15.0);

//        debugGrid(g);

        ctx.init(g, getWidth(), getHeight());

        ctx.translate(XSTEPS/2,0);

        Point lastPoint = ctx.getComponentCoordinates();
        Point penultimatePoint = lastPoint;

        for( Command command : commands ) {
            g.setColor(DOTS);
            g.fillOval(-3,-3,6,6);

            command.apply(ctx);

            Point thisPoint = ctx.getComponentCoordinates();

            g.setColor(TRAIL);
            ctx.pushTransform(initialTransform);
            g.drawLine(lastPoint.x, lastPoint.y, thisPoint.x, thisPoint.y);
            ctx.popTransform();

            penultimatePoint = lastPoint;
            lastPoint = thisPoint;
        }

        drawTurtle(g);
        Point turtlePosition = ctx.getComponentCoordinates();
        gameCtx.setTurtlePosition(turtlePosition);
        gameCtx.setLastPosition(penultimatePoint);
        gameCtx.setRenderContext(ctx);

        // normal coordinates from here down.
        g.setTransform(initialTransform);

        if( screen.getCurrentGame() != null ) {

            Game game = screen.getCurrentGame();
            game.drawGameArea(gameCtx, g);

            if( game.testCollisions(gameCtx) ) {
                // crashed!
                screen.stop();

                g.setColor(Color.RED);
                g.setStroke(wideStroke);
                g.drawRect(2,2,getWidth()-4,getHeight()-4);

                g.setFont(bigFont);
                g.drawString("Try again!", 20, 42);
                return;
            } else
            if( game.testWin(gameCtx) ) {
                // win!
                screen.stop();

                g.setColor(Color.YELLOW);
                g.setStroke(wideStroke);
                g.drawRect(2,2,getWidth()-4,getHeight()-4);

                g.setFont(bigFont);
                g.drawString("You Win!", 20, 42);
                return;
            }
        }

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
//        System.out.println("Executing: "+command);
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
