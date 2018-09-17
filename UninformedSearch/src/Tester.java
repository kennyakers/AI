
import java.io.Console;

/**
 * Kenny Akers and Aidan Chandra AI 9/10/18
 *
 * Sliding tile solver using uninformed search (Iterative deepening depth first
 * search)
 *
 * TODO: Statistics Way to enter into the command line Max depth Goal state
 * depth
 */
public class Tester {

    private static boolean debug = false;
    private static String startState = "";

    /*
    Required Arguments:
        -dimension <n> Generates a random board with dimension nxn
            or
        -state [<state>]     Generates a state based off of the provided string

    Optional Arguments:
        -override          Will attempt to solve from an unsolvable start state
        -verbose           Toggles on verbose output
     */
    public static void main(String[] args) {

        Console console = System.console();
        String option = "";
        int dimension = 0;
        Boolean override = false;

        if (console == null) {
            System.err.println("No console");
            return;
        }

        for (String arg : args) {
            switch (arg) {
                case "-dimension":
                    option = arg;
                    continue;
                case "-state":
                    option = arg;
                    continue;
                case "-override":
                    override = true;
                    continue;
                case "-verbose":
                    debug = true;
                    continue;
                    
            }

            switch (option) {
                case "-dimension":
                    try {
                        dimension = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid dimension: " + arg);
                    }
                    break;
                case "-state":
                    startState += arg + ",";
                    break;
            }
        }
        
        if(debug){
            System.out.println("S: " + startState);
            System.out.println("D: " + dimension);
            System.out.println("O: " + override);
        }
        if(startState.equals("")){
            System.out.println("Generating new state with dimension " + dimension + ". Override: " + override);
            Board b = new Board(dimension, override);
        }
        else{
            
        }
        

 /*
        Board b = new Board(3);
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
            DepthLimited.depth = 0;
            DepthLimited.calls = 0;
            GameState solution = DepthLimited.search(b, depth);
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
         */
    }

}
