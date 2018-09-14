
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

    /*
    Required Arguments:
        - generate <number> Generates a random state to begin from
            or
        - state [<state>]     Generates a state based off of the provided string
    
    Optional Arguments:
        - override          Will attempt to solve from an unsolvable start state
        - verbose           Toggles on verbose output
        
    
    */
    public static void main(String[] args) {
        
        Board b;
        
        for(int i = 0; i < args.length; i++){
            String arg = args[i];
            
            if(arg.equals("generate")){
                int size = 0;
                if(i + 1 < args.length){   
                    try{
                        size = Integer.valueOf(args[i + 1]);
                        i +=1;
                    }
                    catch (NumberFormatException e){
                        System.out.println("No number supplied following \"generate\"");
                        return;
                    }
                }
                else{
                        System.out.println("No number supplied following \"generate\"");
                        return;
                }
                //b = new Board(size);
                System.out.println("Generate with size: " + size);
            }
            
            else if(arg.equals("state")){
                if(i + 1 < args.length){
                    if(!args[i+1].equals("[")){
                        System.out.println("Misformated given state");
                    }
                }
                int index = i;
                int count = 0;
                while(index < args.length && !args[index].equals("]")){
                    count++;
                    index++;
                }
                count -=2;
                System.out.println("Count: " + count);
                int[] arr = new int[count];
                
                index = 0;
                int arrIndex = 0;
                
                while(index < args.length && !args[index].equals("]") && arrIndex < arr.length){
                    try{
                        int value = Integer.valueOf(args[index]);
                        arr[arrIndex] = value;
                        arrIndex++;
                    }
                    catch (NumberFormatException E){
                        //System.out.println("Incorrect number specified within array");
                    }
                    index++;
                    
                }
                System.out.print("Provided state with: ");
                for(int a : arr){
                    System.out.print(a + " ");
                }
                System.out.println("");
            }
            
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
