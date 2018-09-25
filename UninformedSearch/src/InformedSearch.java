
import java.util.ArrayList;
import java.util.Collections;

/**
 * Kenny Akers and Aidan Chandra AI
 *
 * Sliding tile solver using informed RBFS (Recursive Best-First Search)
 */
public class InformedSearch {

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
        if (RBFS((Board) board, Integer.MAX_VALUE)) {
            return goal;
        }
        else {
            return null;
        }
    }

    private static boolean RBFS(Board board, int fLimit) {
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
            }
            if (debug) {
                System.out.println("");
            }
            state.setWeight(evaluationFunction(state, board, heuristicMethod));
            successors.add(state);
        }

        int weight = 0;
        for (GameState s : successors) {
            if (s == null) {
                continue;
            }
            Board successor = (Board) s;
            weight = Math.min(evaluationFunction(successor, board, heuristicMethod), fLimit); //used to be board.getWeight()
            System.out.print("Setting the board with h(n) of ");
            System.out.println(successor.manhattanSum());
            successor.print();
            System.out.println("to " + weight + "\n");
            successor.setWeight(weight);
        }

        Collections.sort(successors);

        //Iterating best first
        for (GameState state : successors) {

            Board successor = (Board) state;

            // if best.f > f limit then return failure, best.f
            if (successor.getWeight() > fLimit) {
                return false;
            }

            // result,best.f ← RBFS(problem,best,min(f limit,alternative))
            if (RBFS(successor, Math.max(fLimit, weight))) {
                return true;
            }
        }
        calls--;
        return false;
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
                sum = state.netOutOfPlace();
                break;
            case MANHATTAN:
                sum = state.manhattanSum();
                break;
        }
        sum += ancestor.getWeight();
        return sum;
    }

}
