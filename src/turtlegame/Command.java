package turtlegame;

import java.awt.*;

public interface Command {
    public void apply(RenderContext ctx);
}
