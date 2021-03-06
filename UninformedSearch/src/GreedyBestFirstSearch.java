
import java.util.ArrayList;
import java.util.Collections;

public class GreedyBestFirstSearch {

    private static int depth;
    private static int calls;
    private static boolean debug;

    public static int maxDepth;
    public static int goalStateDepth;

    private static HeuristicMethods heuristicMethod;
    private static GameState goal;

    public static GameState bestFirstSearch(GameState board, HeuristicMethods method, boolean debugFlag) {
        depth = 0;
        calls = 0;
        maxDepth = 0;
        heuristicMethod = method;
        debug = debugFlag;
        if (GBFS((Board) board)) {
            return goal;
        } else {
            return null;
        }
    }

    private static boolean GBFS(Board board) {
        calls++;
        if (calls > depth) {
            depth = calls;
        }
         if (depth > maxDepth) {
            maxDepth = depth;
        }
         if (debug) {
            System.out.println("\nCurrent depth: " + depth);
            board.print();
        }
         if (board.isGoalState()) {
            goalStateDepth = depth;
            goal = board;
            return true;
        }
         if (debug) {
            System.out.println("\nPossible moves:");
        }
         ArrayList<Board> successors = new ArrayList<>();
        for (GameState b : board) { // Lists possible moves from current board.
            if (b == null) {
                continue;
            }
             Board state = (Board) b;
             if (debug) {
                state.print();
                System.out.println("");
            }
            successors.add(state);
        }
         int weight = 0;
        for (GameState s : successors) {
            if (s == null) {
                continue;
            }
            Board successor = (Board) s;
            weight = evaluationFunction(successor, board, heuristicMethod);
            if (debug) {
                System.out.print("Setting the board with h(n) of ");
                System.out.println(successor.manhattanSum());
                successor.print();
                System.out.println("to " + weight + "\n");
            }
            successor.setWeight(weight);
        }
         Collections.sort(successors);
        
        return GBFS(successors.get(0));
    }

    /*
        Evaluation Functions for a sliding tile board game
     */
    public static enum HeuristicMethods {
        NETOUTOFPLACE,
        MANHATTAN
    }

    private static int evaluationFunction(GameState gameState, GameState parent, HeuristicMethods method) {
        Board state = (Board) gameState;
        Board ancestor = (Board) parent;
        int sum = 0;
        /**
         * Heuristic functions:
         *
         * Net tiles out of place
         *
         * Manhattan distance
         */
        switch (method) {
            case NETOUTOFPLACE:
                sum = state.netOutOfPlace(); // h(n)
                break;
            case MANHATTAN:
                sum = state.manhattanSum(); // h(n)
                break;
        }
        sum += ancestor.getWeight(); // g(n)
        return sum;
    }

}
