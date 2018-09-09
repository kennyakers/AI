public class Tester {

    public static void main(String[] args) {
        Board b = new Board(2);
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
            DepthLimited.depth = 0;
            DepthLimited.calls = 0;
            Board solution = DepthLimited.search(b, depth);
            if (solution == null) {
                System.out.println("No solution found with depth " + DepthLimited.getDepth());
            } else {
                System.out.println("\nSolution:");
                solution.print();
                break;
            }
        }

    }

}
