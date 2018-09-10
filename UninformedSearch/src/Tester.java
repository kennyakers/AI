
/**
 * Kenny Akers and Aidan Chandra
 * AI
 * 9/10/18
 *
 * Sliding tile solver using uninformed search (Iterative deepening depth first search)
 *
 * TODO:
 * Statistics
 * Way to enter into the command line
 * Max depth
 * Goal state depth
 */
public class Tester {

    private static boolean debug = false;

    public static void main(String[] args) {
        Board b = new Board(3);
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
            DepthLimited.depth = 0;
            DepthLimited.calls = 0;
            Board solution = DepthLimited.search(b, depth);
            if (solution == null) {
                if (debug) {
                    System.out.println("No solution found with depth " + DepthLimited.getDepth());
                }
                if (debug) {
                    System.out.println("\n");
                }
            } else {
                System.out.println("\nSolution:");
                solution.print();
                break;
            }
        }

    }

}
