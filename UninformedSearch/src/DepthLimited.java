
public class DepthLimited {

    public static int depth = 0;
    public static int calls = 0;

    private static int maxDepth = 0;

    private static Board goal;

    public static Board search(Board board, int depth) {
        maxDepth = depth;
        if (depthFirstSearch(board)) {
            return goal;
        }
        return null;
    }

    public static boolean depthFirstSearch(Board board) {
        calls++;

        if (calls > depth) {
            depth = calls;
        }

        System.out.println("Current depth: " + depth);
        board.print();

        if (board.isGoalState()) {
            goal = board;
            return true;
        }

        if (depth >= maxDepth) {
            System.out.println("Reached maximum depth " + depth + ":");
            return false;
        }

        Board[] states = board.nextStates();
        System.out.println("Possible moves:");
        for (Board b : states) {
            if (b == null) {
                continue;
            }
            b.print();
            System.out.println("");
        }

        for (Board state : states) {
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
