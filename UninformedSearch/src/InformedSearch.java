
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
        return null;
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
        int index = 0;
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
            state.setWeight(evaluationFunction(state, heuristicMethod));
            successors.add(state);
        }

        /*
        BELOW LINE DOES THE SAME AS THE BELOW FOR-EACH LOOP. 
        INTERESTING EXAMPLE OF JAVA 8.
        
        successors.stream().filter((s) -> !(s == null)).map((s) -> (Board) s).forEachOrdered((successor) -> {
            successor.setWeight(Math.max(evaluationFunction(successor, heuristicMethod), evaluationFunction(board, heuristicMethod)));
        });
         */
        for (GameState s : successors) {
            if (s == null) {
                continue;
            }
            Board successor = (Board) s;
            successor.setWeight(Math.max(evaluationFunction(successor, heuristicMethod), evaluationFunction(board, heuristicMethod)));
        }

        Collections.sort(successors);

        //Iterating best first
        for (GameState state : successors) {
            if (state == null) { // Skip null states (i.e. when there are only 2 or 3 valid moves).
                continue;
            }

            Board successor = (Board) state;

            // if best.f > f limit then return failure, best.f
            if (successor.getWeight() > fLimit) {
                return false;
            }

            // result,best.f ← RBFS(problem,best,min(f limit,alternative))
            if (RBFS(successor, Math.min(fLimit, successor.getWeight()))) {
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

    private static int maxEvaluationFunction(GameState state) {
        int max = 0;
        for (HeuristicMethods method : HeuristicMethods.values()) {
            int evaluationResult = evaluationFunction(state, method);
            if (evaluationResult > max) {
                max = evaluationResult;
            }
        }
        return max;
    }

    private static int evaluationFunction(GameState gameState, HeuristicMethods method) {
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

}
