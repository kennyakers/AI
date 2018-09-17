
/**
 * Kenny Akers and Aidan Chandra
 * AI
 *
 * Sliding tile solver using uninformed search (Iterative deepening depth first search)
 */
public class DepthLimited {

    public static int depth = 0;
    public static int calls = 0;
    private static int maxDepth = 0;

    private static final boolean debug = false;

    private static GameState goal;

    public static GameState search(GameState board, int maxDepth) {
        DepthLimited.maxDepth = maxDepth;
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
        }

        for (GameState state : board) {
            if (state == null) { // Skip null states (i.e. when there are only 2 or 3 valid moves).
                continue;
            }
            if (depthFirstSearch(state)) {
                return true;
            }
        }
        calls--;

        return false;
    }

    public static int getDepth() {
        return depth;
    }
}
