
import java.util.Arrays;


/**
 * Kenny Akers and Aidan Chandra
 * AI
 *
 * Sliding tile solver using uninformed search (Iterative deepening depth first search)
 */
public class InformedSearch {

    public static int depth = 0;
    public static int calls = 0;
    private static int maxDepth = 0;
    private static heuristicMethods heuristicMethod;

    private static final boolean debug = false;

    private static GameState goal;

    public static GameState search(GameState board, int maxDepth, heuristicMethods desired) {
        heuristicMethod = desired;
        InformedSearch.maxDepth = maxDepth;
        if (depthFirstSearch(board)) {
            return goal;
        }
        return null;
    }

    private static boolean depthFirstSearch(GameState board) {
        calls++;

        if (calls > depth) {
            depth = calls;
        }

        if (debug) {
            System.out.println("\nCurrent depth: " + depth);
        }
        if (debug) {
            board.print();
        }

        if (board.isGoalState()) {
            goal = board;
            return true;
        }

        if (depth >= maxDepth) {
            if (debug) {
                System.out.println("Reached maximum depth " + depth);
            }
            calls--;
            return false;
        }

        if (debug) {
            System.out.println("\nPossible moves:");
        }

        BoardWeight[] states = new BoardWeight[4];
        int index = 0;
        for (GameState b : board) { // Lists possible moves from current board.
            if (b == null) {
                continue;
            }
            if (debug) {
                b.print();
            }
            if (debug) {
                System.out.println("");
            }
            states[index++] = new BoardWeight((Board) b, evaluationFunction(b, heuristicMethod));
        }
        Arrays.sort(states);
        
        //Iterating best first
        for (BoardWeight state : states) {
            if (state == null) { // Skip null states (i.e. when there are only 2 or 3 valid moves).
                continue;
            }
            if (depthFirstSearch(state.b)) {
                return true;
            }
        }
        calls--;

        return false;
    }

    private static class BoardWeight implements Comparable {

        Board b;
        int w;

        public BoardWeight(Board b, int w) {
            this.b = b;
            this.w = w;
        }

        public int compareTo(Object other) {
            BoardWeight otherBoardWeight = (BoardWeight) other;
            if (otherBoardWeight.w == w) {
                return 0;
            }
            if (otherBoardWeight.w > w) {
                return -1;
            }
            return 1;
        }
    }

    /*
        Evaluation Functions for a sliding tile board game
     */
    static enum heuristicMethods {
        NETOUTOFPLACE,
        MANHATTAN
    }

    static public int maxEvaluationFunction(GameState state) {
        int max = 0;
        for (heuristicMethods choice : heuristicMethods.values()) {
            int val = evaluationFunction(state, choice);
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    static public int evaluationFunction(GameState game, heuristicMethods choice) {
        Board state = (Board) game;

        //net tiles out of place, manhattan model (sum of distances), 
        switch (choice) {
            case NETOUTOFPLACE:
                return state.netOutOfPlace();
            case MANHATTAN:
                return state.manhattanSum();
        }
        return -1;
    }

    public static int getDepth() {
        return depth;
    }
}
