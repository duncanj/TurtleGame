package turtlegame.games;


import turtlegame.RenderContext;

import java.awt.*;

public class GameContext {
    private Point turtlePosition;
    private Point lastPosition;
    private RenderContext renderContext;

    public Point getTurtlePosition() {
        return turtlePosition;
    }

    public void setTurtlePosition(Point turtlePosition) {
        this.turtlePosition = turtlePosition;
    }

    public Point getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Point lastPosition) {
        this.lastPosition = lastPosition;
    }

    public RenderContext getRenderContext() {
        return renderContext;
    }

    public void setRenderContext(RenderContext renderContext) {
        this.renderContext = renderContext;
    }

    public Point convertToStepCoordinates(Point point) {
        int x = renderContext.inverseScaleX(point.x);
        int y = renderContext.inverseScaleY(point.y);
        return new Point(x,y);
    }

    public Rectangle convertToStepCoordinates(Rectangle r, RenderContext renderCtx) {
        int x = renderCtx.inverseScaleX(r.x);
        int y = renderCtx.inverseScaleY(r.y);
        int width = renderCtx.inverseScaleX(r.width);
        int height = renderCtx.inverseScaleY(r.height);
        return new Rectangle(x,y,width,height);
    }


}
