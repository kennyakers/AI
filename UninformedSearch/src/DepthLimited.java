/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   Sep 5, 2018
 */
public class DepthLimited {
    
    private static int depth = 0; 
    private static int calls = 0;
    
    private static int maxDepth = 0;
    
    private static Board returnable;
    
    public static Board search(Board g, int depth){
        maxDepth = depth;
        if(depthFirstSearch(g)){
            return returnable;
        }
        return null;
        
    }
    public static boolean depthFirstSearch(Board board){
        
        calls++;
        if(calls > depth){
            depth = calls;
        }
        if(depth >= maxDepth){
            System.out.println("Depth great");
            return false;
        }
            
        if(board.isGoalState()){
            System.out.println("Goal State");
            return true;
        }
        
        Board[] states = board.nextStates();
        
        boolean prevResult = true;
        for(Board b : states){
            if(b == null)
                continue;
            if(depthFirstSearch(b)){
                returnable = b;
                return true;
            }
        }
        
        calls--;
        
        return false;
    }
}
