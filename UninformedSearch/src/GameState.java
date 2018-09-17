/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aidanchandra
 */
import java.util.Iterator;

public interface GameState extends Iterable<GameState> {

    public boolean isGoalState();

    public Iterator<GameState> iterator();

    public void print();
}
