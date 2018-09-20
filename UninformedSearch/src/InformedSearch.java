
import java.util.Arrays;

/**
 * Kenny Akers and Aidan Chandra AI
 *
 * Sliding tile solver using informed search (Recursive Best-First Search)
 */
public class InformedSearch {

    public static int depth = 0;
    public static int calls = 0;
    private static int maxDepth = 0;
    private static HeuristicMethods heuristicMethod;

    private static boolean debug;

    private static GameState goal;

    public static GameState search(GameState board, int max, HeuristicMethods method, boolean debugFlag) {
        heuristicMethod = method;
        maxDepth = max;
        debug = debugFlag;
        if (bestFirstSearch(board)) {
            return goal;
        }
        return null;
    }

    private static boolean bestFirstSearch(GameState board) {
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

        if (board.isGoalState(depth)) {
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

        BoardAndWeight[] states = new BoardAndWeight[4];
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
            states[index++] = new BoardAndWeight((Board) b, evaluationFunction(b, heuristicMethod));
        }
        Arrays.sort(states);

        //Iterating best first
        for (BoardAndWeight state : states) {
            if (state == null) { // Skip null states (i.e. when there are only 2 or 3 valid moves).
                continue;
            }
            if (bestFirstSearch(state.board)) {
                return true;
            }
        }
        calls--;

        return false;
    }

    private static class BoardAndWeight implements Comparable {

        private final Board board;
        private final int weight;

        public BoardAndWeight(Board b, int w) {
            this.board = b;
            this.weight = w;
        }

        @Override
        public int compareTo(Object other) {
            BoardAndWeight otherBoard = (BoardAndWeight) other;
            return this.weight - otherBoard.weight;

        }
    }

    /*
        Evaluation Functions for a sliding tile board game
     */
    public static enum HeuristicMethods {
        NETOUTOFPLACE,
        MANHATTAN
    }

    public static int maxEvaluationFunction(GameState state) {
        int max = 0;
        for (HeuristicMethods method : HeuristicMethods.values()) {
            int evaluationResult = evaluationFunction(state, method);
            if (evaluationResult > max) {
                max = evaluationResult;
            }
        }
        return max;
    }

    public static int evaluationFunction(GameState gameState, HeuristicMethods method) {
        Board state = (Board) gameState;

        /**
         * Heuristic functions:
         *
         * Net tiles out of place
         *
         * Manhattan distance
         */
        switch (method) {
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
