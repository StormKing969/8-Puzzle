import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdOut;

// Models a board in the 8-puzzle game or its generalization.
public class Board {
    //The Board data type has the following instance variables:
    private int[][] tiles;
    //The 2D int array tiles,
    private int N;
    //The integer N,
    private int hamming;
    //The integer hamming,
    private int manhattan;
    //and the integer manhattan.

    // Construct a board from an N-by-N array of tiles, where 
    // tiles[i][j] = tile at row i and column j, and 0 represents the blank 
    // square.
    public Board(int[][] tiles) {
        //In the constructor,
        this.N = tiles[0].length;
        //set N equal to the length of tiles at index 0
        this.tiles = new int[N][N];
        //and set tiles equal to a new 2D array with dimensions N*N.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j]; }
        }
        //Then, in a double for loop, set tiles equal to tiles at i and j.
        int misplaced = 0;
        //After, create a new int misplaced that is set to 0
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    continue; }
                if (tiles[i][j] != i * N + j + 1) {
                    ++misplaced; }
            }
        }
        //and in a double for loop, if tiles at i and j equals 0,
        //continue. Otherwise, if tiles at i and j is not equal to
        //i times N plus j plus 1, then increment misplaced.
        hamming = misplaced;
        //Then, set hamming equal to misplaced.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int ideal = i * N + j + 1;
                if (tiles[i][j] != 0 && tiles[i][j] != ideal) {
                    int idealI = (tileAt(i, j) - 1) / N;
                    int idealJ = tileAt(i, j) - 1 - idealI * N;
                    manhattan += Math.abs(i - idealI) + Math.abs(j - idealJ);
                }
            }
        }
        //After, in a double for loop, set ideal equal to
        //i times N plus j plus 1, and if tiles at i and
        //j do not equal 0 or are not equal to ideal,
        //create a new int idealI set to the tile
        //at i and j minus 1 divided by N and create
        //another int idealJ equal to the tile at
        //i and j minus 1 minus idealI times N.
        //After, calculate manhattan by adding the
        //absolute value of i minus idealI and the
        //absolute value of j minus idealJ to it.
    }

    // Tile at row i and column j.
    public int tileAt(int i, int j) {
        //At the tileAt() method,
        return tiles[i][j];
        //return tiles at i and j.
    }

    // Size of this board.
    public int size() {
        //At the size() method,
        return N * N;
        //return N times N.
    }

    // Number of tiles out of place.
    public int hamming() {
        //At the hamming() method,
        return hamming;
        //return hamming.
    }

    // Sum of Manhattan distances between tiles and goal.
    public int manhattan() {
        //At the manhattan() method,
        return manhattan;
        //return hamming.
    }

    // Is this board the goal board?
    public boolean isGoal() {
        //At the isGoal() method,
        return blankPos() == N * N - 1 && inversions() == 0;
        //return if blankPos() is equal to
        //N times N minus 1
    }

    // Is this board solvable?
    public boolean isSolvable() {
        //At the isSolvable() method,
        if (N % 2 != 0) {
            if (inversions() % 2 != 0) { 
                return false; }
            return true;
            //if N is odd and inversions() are odd,
            //return false. Otherwise, return true.
        }
        else {
            int blankRow = blankPos() / N;
            int sum = blankRow + inversions();
            if (sum % 2 == 0) { 
                return false; }
            return true;
            //Else, create new ints blankRow,
            //set to blankPos() divided by N,
            //and sum, set to blankRow plus
            //inversions(). If sum is even,
            //return false. Otherwise, 
            //return true.
        }
    }

    // Does this board equal that?
    public boolean equals(Board that) {
        //At the equals() method,
        if (this.N != that.N) {
            return false; }
        //if this.N is not equal to that.N,
        //return false.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false; }
            }
        }
        //Also, return false if tiles at i
        //and j is not equal to that.tiles
        //at i and j. Otherwise, return
        //true.
        return true;
    }

    // All neighboring boards.
    public Iterable<Board> neighbors() {
        //In the neighbors() method,
        LinkedQueue<Board> lq = new LinkedQueue<Board>();
        //create a new LinkedQueue object lq,
        int[][] neighbor;
        //create a new 2D int array neighbor,
        int blankPos = blankPos();
        //create a new int blankPos, set to
        //the blankPos() method,
        int i = blankPos / N;
        //create a new int i, set to blankPos
        //divided by N,
        int j = blankPos % N;
        //create a new int j, set to blankPos
        //mod N,
        int temp;
        //and create a new int temp.
        if (i - 1 >= 0 && i - 1 < N) {
            neighbor = cloneTiles();
            temp = neighbor[i][j];
            neighbor[i][j] = neighbor[i - 1][j];
            neighbor[i - 1][j] = temp;
            Board board = new Board(neighbor);
            lq.enqueue(board);
        }
        if (j + 1 >= 0 && j + 1 < N) {
            neighbor = cloneTiles();
            temp = neighbor[i][j];
            neighbor[i][j] = neighbor[i][j + 1];
            neighbor[i][j + 1] = temp;
            Board board = new Board(neighbor);
            lq.enqueue(board);
        }
        if (i + 1 >= 0 && i + 1 < N) {
            neighbor = cloneTiles();
            temp = neighbor[i][j];
            neighbor[i][j] = neighbor[i + 1][j];
            neighbor[i + 1][j] = temp;
            Board board = new Board(neighbor);
            lq.enqueue(board);
        }
        if (j - 1 >= 0 && j - 1 < N) {
            neighbor = cloneTiles();
            temp = neighbor[i][j];
            neighbor[i][j] = neighbor[i][j - 1];
            neighbor[i][j - 1] = temp;
            Board board = new Board(neighbor);
            lq.enqueue(board);
        }
        //In these if statements (if
        //statements are for corner
        //cases), set neighbor equal
        //to cloneTiles() and temp to
        //neighbor at index i and j.
        //After, set neighbor at i and
        //j equal to i and the corner
        //case in the if statement and
        //set neighbor at i and the corner
        //case equal to temp. Finally, create
        //a new board object with neighbor as
        //its parameter and put it in the Linked
        //Queue. Finally, after all the if
        //statements are done, return the Linked
        //Queue.
        return lq;
    }

    // String representation of this board.
    public String toString() {
        //In the toString() method,
        StringBuilder s = new StringBuilder();
        //Create a new StringBuilder s and
        s.append(N + "\n");
        //append N and a new line to it.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j < N - 1) {
                    s.append(String.format("%2d ", tiles[i][j])); }
                else {
                    s.append(String.format("%2d", tiles[i][j])); }
            }
            if (i < N - 1) {
                s.append("\n"); }
        }
        //Then, in a double for loop, if
        //j is less than N minus 1, append
        //tiles at i and j with the format
        //"%2d " to it. Else, append tiles
        //at i and j with the format "%2d"
        //to it. Finally, if i is less than
        //N minus 1, append a new line to it
        //and return the toString method of
        //s.
        return s.toString();
    }

    // Helper method that returns the position (in row-major order) of the 
    // blank (zero) tile.
    private int blankPos() {
        //In the blankPos() method,
        int pos = 0;
        //Create a new int pos set to 0.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    pos = i * N + j; }
            }
        }
        //Then, in a double for loop, if
        //tiles at i and j is equal to 0,
        //set pos equal to i times N plus
        //j. Finally, return pos.
        return pos; }

    // Helper method that returns the number of inversions.
    private int inversions() {
        //At the inversions() method,
        int[] inv = new int[N * N - 1];
        //create a new 1D array inv
        //with size N by N minus 1.
        int x = 0;
        //Also, create new ints x,
        //set to 0,
        int count = 0;
        //and count, also set to 0.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != 0) {
                    inv[x] = tileAt(i, j);
                    x++;
                }
            }
            //In a double for loop, if tileAt
            //i and j is not equal to 0, set
            //inv at index x to the tile at
            //i and j and increment x.
        }
        for (int m = 0; m < inv.length; m++) {
            for (int n = m + 1; n < inv.length; n++) {
                if (inv[m] > inv[n]) {
                    count++; }
            }
        }
        //Then,in another double for loop,
        //if inv at index m is greater than
        //inv at index n, then increment count.
        return count;
        //Finally, return count.
    }

    // Helper method that clones the tiles[][] array in this board and 
    // returns it.
    private int[][] cloneTiles() {
        //At the cloneTiles() method,
        int[][] result = new int[N][N];
        //create a new 2d array result
        //with dimensions N by N, and
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = tiles[i][j];
            }
        }
        //in a double for loop, replace
        //result at i and j with tiles
        //at i and j and return result.
        return result;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }
    }
}
