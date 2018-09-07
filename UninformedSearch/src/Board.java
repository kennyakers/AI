
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    private int[][] board;
    private int blankX;
    private int blankY;
    private int dimension;

    public Board(int dimension) {
        this.dimension = dimension;
        this.board = new int[dimension][dimension];

        int[] arr = new int[dimension * dimension];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        this.shuffleArray(arr);

        int counter = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.board[i][j] = arr[counter];
                if (arr[counter] == 0) {
                    this.blankX = i;
                    this.blankY = j;
                }
                counter++;
            }
        }
    }

    public Board(int[][] boardIn) {
        this.dimension = boardIn.length;
        this.board = new int[dimension][dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.board[i][j] = boardIn[i][j];
            }
        }
        this.blankX = this.getXPos(0);
        this.blankY = this.getYPos(0);
    }

    public Board[] nextStates() {

        Board[] returnable = new Board[4];
        int index = 0;

        if (this.blankY - 1 >= 0) {
            Board up = new Board(this.board);
            up.swap(blankX, blankY, blankX, blankY - 1);
            returnable[index++] = up;
        }
        if (this.blankY + 1 < this.dimension) {
            Board down = new Board(this.board);
            down.swap(blankX, blankY, blankX, blankY + 1);
            returnable[index++] = down;
        }
        if (this.blankX - 1 >= 0) {
            Board left = new Board(this.board);
            left.swap(blankX, blankY, blankX - 1, blankY);
            returnable[index++] = left;
        }
        if (this.blankX + 1 < this.dimension) {
            Board right = new Board(this.board);
            right.swap(blankX, blankY, blankX + 1, blankY);
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
        if (val == 0) {
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
        this.set(x, y, get(xtwo, ytwo));
        this.set(xtwo, ytwo, initialVal);
        return true;
    }

    public int getXPos(int val) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (this.board[i][j] == val) {
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
        int counter = 1;
        if (this.board[this.dimension - 1][this.dimension - 1] != 0) { // If last tile is not blank, it's not a goal
            //System.out.println("Last tile is not 0");
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != counter) {
                    if (i == this.dimension - 1 && j == this.dimension - 1) {
                        continue;
                    }
                    //System.out.println(board[i][j] + " is not equal to " + counter);
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

    public String blankPos() {
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
