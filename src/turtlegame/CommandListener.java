package turtlegame;

public interface CommandListener {
    public void notifyCleared();
    public void notifyAdded();
    public void notifyChanged();
}
