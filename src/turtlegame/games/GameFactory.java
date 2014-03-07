package turtlegame.games;

import java.util.ArrayList;
import java.util.List;

public class GameFactory {
    private List<Game> games = new ArrayList<Game>();

    public GameFactory() {
        games.add(new None());
        games.add(new Simple());
        games.add(new OneBarrier());
        games.add(new ThreeBarriers());
        games.add(new Maze());
        games.add(new Impossible());
    }

    public List<Game> getGames() {
        return games;
    }
}
