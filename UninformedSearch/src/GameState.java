
import java.util.Iterator;

public interface GameState extends Iterable<GameState> {

    public boolean isGoalState(int goalStateDepth);

    public Iterator<GameState> iterator();

    public void print();
}
