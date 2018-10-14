
import java.util.Iterator;

public interface GameState extends Iterable<GameState> {

    public boolean isGoalState();

    @Override
    public Iterator<GameState> iterator();

    public void print();

}
