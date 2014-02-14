package turtlegame;

public class Forward implements Command {
    private int steps;

    public Forward(int steps) {
        this.steps = steps;
    }

    @Override
    public void apply(RenderContext ctx) {
        ctx.translate(0,steps);
    }
}
