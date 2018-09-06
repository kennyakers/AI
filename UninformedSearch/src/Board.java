
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Board{// implements Game{

    int[][] board;
    int blankX, blankY;
    int dimension;

    public Board(int dimension) {
        this.dimension = dimension;
        board = new int[dimension][dimension];

        int[] arr = new int[dimension * dimension];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        shuffleArray(arr);
        
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = arr[counter];
                if(arr[counter] == 0){
                    blankX = i;
                    blankY = j;
                }
                counter++;
            }
        }
    }
    
    public Board(int[][] boardIn){
        this.dimension = boardIn.length;
        board = new int[dimension][dimension];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = boardIn[i][j];
            }
        }
        this.blankX = getXPos(0);
        this.blankY = getYPos(0);
    }

    public Board[] nextStates(){
        
        Board[] returnable = new Board[4];
        int index = 0;
        
        if(blankY - 1 >= 0){
            Board up = new Board(this.board);
            up.swap(blankX, blankY, blankX, blankY -1);
            returnable[index++] = up;
        }
        if(blankY + 1 < dimension){
            Board down = new Board(this.board);
            down.swap(blankX, blankY, blankX, blankY + 1);
            returnable[index++] = down;
        }
        if(blankX - 1 >= 0){
            Board left = new Board(this.board);
            left.swap(blankX, blankY, blankX-1, blankY);
            returnable[index++] = left;
        }
        if(blankX + 1 < dimension){
            Board right = new Board(this.board);
            right.swap(blankX, blankY, blankX+1, blankY);
            returnable[index++] = right;
        }
        
        return returnable;
    }
    
    public int get(int x, int y) {
        if (x > dimension || y > dimension || x < 0 || y < 0) {
            return -1;
        }

        return board[x][y];
    }

    public boolean set(int x, int y, int val) {
        if (x > dimension || y > dimension || x < 0 || y < 0) {
            return false;
        }

        board[x][y] = val;
        if(val == 0){
            blankX = x;
            blankY = y;
        }
        return true;
    }

    public boolean swap(int x, int y, int xtwo, int ytwo) {
        if (x > dimension || y > dimension || x < 0 || y < 0) {
            return false;
        }
        if (xtwo > dimension || ytwo > dimension || xtwo < 0 || ytwo < 0) {
            return false;
        }

        int initialVal = get(x, y);
        set(x, y, get(xtwo, ytwo));
        set(xtwo, ytwo, initialVal);
        return true;
    }

    public int getXPos(int val) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == val) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getYPos(int val) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == val) {
                    return j;
                }
            }
        }
        return -1;
    }

    public boolean isGoalState() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != counter) {
                    return false;
                }
                counter++;
            }
        }
        return true;
    }

    public void print() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            System.out.print("[");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("]");
        }
    }
    
    public String blankPos(){
        return String.valueOf(blankX) + ", " + String.valueOf(blankY);
    }

    private void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
