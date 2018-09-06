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
public class Tester {
    public static void main(String[] args){
        Board b = new Board(3);
        Board solution = DepthLimited.search(b, 3);
        solution.print();
    }
    
}
