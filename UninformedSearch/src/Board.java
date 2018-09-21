
/**
 * Kenny Akers and Aidan Chandra
 * AI
 * 9/10/18
 *
 * Sliding tile solver using uninformed search (Iterative deepening depth first search)
 */
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board implements GameState, Iterable<GameState>, Comparable<GameState> {

    private final int[][] board;
    private int blankX;
    private int blankY;
    private final int dimension;
    private final boolean solvableCheck;
    private final boolean debug;
    private int weight;

    public Board(int dimension, boolean check, boolean debugFlag) {
        this.dimension = dimension;
        this.solvableCheck = check;
        this.debug = debugFlag;
        this.board = new int[dimension][dimension];

        int[] arr = new int[dimension * dimension];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        this.shuffleArray(arr);
        if (!this.solvableCheck) {
            while (!isValid(arr)) {
                this.shuffleArray(arr);
            }
        }

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

    public Board(int[][] boardIn, boolean check, boolean debugFlag) {
        this.solvableCheck = check;
        this.debug = debugFlag;
        this.dimension = boardIn.length;
        this.board = new int[dimension][dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.board[i][j] = boardIn[i][j];
            }
        }
        this.blankX = this.getRow(0);
        this.blankY = this.getColumn(0);
    }

    /*
        Intializes instance from a board array
     */
    public Board(int[] boardArray, boolean check, boolean debugFlag) {
        this.solvableCheck = check;
        this.debug = debugFlag;
        int[][] boardIn = new int[(int) Math.sqrt(boardArray.length)][(int) Math.sqrt(boardArray.length)];
        int index = 0;

        for (int i = 0; i < Math.sqrt(boardArray.length); i++) {
            for (int j = 0; j < Math.sqrt(boardArray.length); j++) {
                boardIn[i][j] = boardArray[index++];
            }
        }

        this.shuffleArray(boardArray);
        if (!this.solvableCheck) {
            while (!isValid(boardArray)) {
                this.shuffleArray(boardArray);
            }
        }

        this.dimension = boardIn.length;
        this.board = new int[dimension][dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.board[i][j] = boardIn[i][j];
            }
        }
        this.blankX = this.getRow(0);
        this.blankY = this.getColumn(0);

    }

    // Solvability source: https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
    private boolean isValid(int[] arr) { // Checks if the generated board is solvable
        int inversions = 0;
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            for (int j = 0; j < i; j++) {
                if (value == 0) {
                    break;
                }
                if (arr[j] > value) {
                    inversions++;
                }
            }
        }
        int zeroPosition = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                zeroPosition = i;
            }
        }
        int rowsFromTop = zeroPosition / dimension;
        int rowsFromBot = dimension - rowsFromTop;

        boolean valid = ((board.length % 2 == 1) && (inversions % 2 == 0)) || ((board.length % 2 == 0) && ((rowsFromBot % 2 == 1) == (inversions % 2 == 0)));

        if (this.debug) {
            System.out.println("\t" + Arrays.toString(arr));
            System.out.println("\tInversions: " + inversions);
            System.out.println("\tRows From Bot: " + rowsFromBot);
            System.out.println("\tValid: " + valid + "\n");
        }

        return valid;
    }

    @Override
    public stateIterator iterator() {

        Board[] returnable = new Board[4];
        int index = 0;

        if (this.blankY - 1 >= 0) {
            Board up = new Board(this.board, this.solvableCheck, this.debug);
            up.swap(blankX, blankY, blankX, blankY - 1);
            returnable[index++] = up;
        }
        if (this.blankY + 1 < this.dimension) {
            Board down = new Board(this.board, this.solvableCheck, this.debug);
            down.swap(blankX, blankY, blankX, blankY + 1);
            returnable[index++] = down;
        }
        if (this.blankX - 1 >= 0) {
            Board left = new Board(this.board, this.solvableCheck, this.debug);
            left.swap(blankX, blankY, blankX - 1, blankY);
            returnable[index++] = left;
        }
        if (this.blankX + 1 < this.dimension) {
            Board right = new Board(this.board, this.solvableCheck, this.debug);
            right.swap(blankX, blankY, blankX + 1, blankY);
            returnable[index++] = right;
        }
        this.shuffleArray(returnable);

        return new stateIterator(returnable);
    }

    public int get(int x, int y) {
        if (x > dimension || y > dimension || x < 0 || y < 0) {
            return -1;
        }

        return board[x][y];
    }

    private boolean set(int x, int y, int val) {
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

    private boolean swap(int x, int y, int xtwo, int ytwo) {
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

    public int getRow(int val) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (this.board[i][j] == val) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getColumn(int val) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == val) {
                    return j;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isGoalState() {
        int counter = 1;
        if (this.board[this.dimension - 1][this.dimension - 1] != 0) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != counter) {
                    if (i == this.dimension - 1 && j == this.dimension - 1) {
                        continue;
                    }
                    return false;
                }
                counter++;
            }
        }
        return true;
    }

    @Override
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

    public int netOutOfPlace() {
        int sum = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (!inPlace(get(i, j))) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    public int manhattanSum() {
        int sum = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sum += manhattan(get(i, j));
            }
        }
        return sum;
    }

    public boolean inPlace(int val) {
        if (val == 0) {
            return board[dimension - 1][dimension - 1] == 0;
        }

        return val - 1 == getRow(val) * dimension + getColumn(val);
    }

    public int manhattan(int val) {
        int desiredRow = (val / dimension) - 1;
        int desiredCol = (val % dimension) - 1;
        int actualRow = getRow(val);
        int actualCol = getColumn(val);
        return (Math.abs(desiredRow - actualRow) + Math.abs(desiredCol - actualCol));
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

    private void shuffleArray(Board[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Board a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getDimension() {
        return this.dimension;
    }

    public boolean isSolvableCheck() {
        return this.solvableCheck;
    }

    public boolean isDebug() {
        return this.debug;
    }

    @Override
    public int compareTo(GameState other) {
        Board otherBoard = (Board) other;
        return this.getWeight() - otherBoard.getWeight();
    }

    private class stateIterator implements Iterator<GameState> {

        private GameState[] arr;
        int index;

        public stateIterator(GameState[] in) {
            this.arr = in;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < arr.length;
        }

        @Override
        public GameState next() {
            return arr[index++];
        }
    }

}
