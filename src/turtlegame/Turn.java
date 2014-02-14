package turtlegame;

public class Turn implements Command {
    private int amount;

    public Turn(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(RenderContext ctx) {
        ctx.rotate(amount);
    }

    @Override
    public String toString() {
        return "Turn{" +
                "amount=" + amount +
                '}';
    }
}
