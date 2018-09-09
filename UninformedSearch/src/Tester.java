
/**
 * Kenny Akers and Aidan Chandra
 * AI
 * 9/10/18
 *
 * Sliding tile solver using uninformed search (Iterative deepening depth first search)
 */

public class Tester {

    public static void main(String[] args) {
        Board b = new Board(2);
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
            DepthLimited.depth = 0;
            DepthLimited.calls = 0;
            Board solution = DepthLimited.search(b, depth);
            if (solution == null) {
                System.out.println("No solution found with depth " + DepthLimited.getDepth());
                System.out.println("\n");
            } else {
                System.out.println("\nSolution:");
                solution.print();
                break;
            }
        }

    }

}
