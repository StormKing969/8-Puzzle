import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

// A solver based on the A* algorithm for the 8-puzzle and its generalizations.
public class Solver {
    //The Solver data type has the following
    //instance variables:
    private LinkedStack<Board> solution;
    //The LinkedStack object solution,
    private int moves;
    //and the int moves.

    // Helper search node class.
    private class SearchNode {
        //The SearchNode class has the
        //following instance variables:
        private Board board;
        //A Board object board,
        private int moves;
        //the int moves, 
        private SearchNode previous;
        //and the SearchNode object previous.

        SearchNode(Board board, int moves, SearchNode previous) {
            //In the constructor,
            this.board = board;
            //set this.board equal to board,
            this.moves = moves;
            //set this.moves equal to moves,
            this.previous = previous;
            //and set this.previous equal to previous.
        }
    }
     
    // Find a solution to the initial board (using the A* algorithm).
    public Solver(Board initial) {
        //In the constructor, 
        if (initial == null) {
            throw new NullPointerException("Initial is null"); }
        //if initial is null, throw an error.
        if (!initial.isSolvable()) {
            throw new IllegalArgumentException("Board is not solvable"); }
        //Also, if initial is not solvable,
        //throw an error.
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(new HammingOrder());
        solution = new LinkedStack<Board>();
        pq.insert(new SearchNode(initial, 0, null));
        //Otherwise, creare a new MinPQ object
        //with HammingOrder as its parameter,
        //set solution to a new LinkedStack
        //object, and insert a SearchNode
        //object with initial, 0, and null
        //as its parameters to pq.
        while (!pq.isEmpty()) {
            //While pq is not empty,
            SearchNode node = pq.delMin();
            //Set SearchNode node equal
            //to pq.delMin() and
            if (node.board.isGoal()) {
                //if node.board is the goal,
                moves = node.moves;
                //set moves equal to node.moves.
                while (node.previous != null) {
                    //While node.previous isn't null,
                    solution.push(node.board);
                    //push node.board to solution and
                    node = node.previous;
                    //set node equal to node.previous
                    //and break.
                }
                break;
            }
            //Then, set the Iterable object neighbors
            //to node.board.neighbors() and
            Iterable<Board> neighbors = node.board.neighbors();
            for (Board neighbor : neighbors) {
                //for every Board object neighbor
                //inside neighbors,
                if (node.previous != null
                && !neighbor.equals(node.previous.board)) {
                    //If node.previous is null and
                    //neighbor does not equal the
                    //previous board,
                    SearchNode next = new SearchNode(neighbor, node.moves + 1,
                    node);
                    //set SearchNode next to
                    //a new SearchNode object with
                    //neighbor, node.moves
                    //plus 1, and node as
                    //its parameters and
                    //insert it to pq.
                    pq.insert(next); }
                else if (node.previous == null) {
                    //Else if node.previous is null,
                    SearchNode next2 = new SearchNode(neighbor, node.moves + 1,
                    node);
                    //Set SearchNode next2 to
                    //a new SearchNode object with
                    //neighbor, node.moves plus 1, and
                    //node as parameters and insert it
                    //to pq.
                    pq.insert(next2);
                }
            }
        }
    }

    // The minimum number of moves to solve the initial board.
    public int moves() {
        //In the moves() method,
        return moves;
        //return moves.
    }

    // Sequence of boards in a shortest solution.
    public Iterable<Board> solution() {
        //In the solution() method,
        return solution;
        //return solution.
    }

    // Helper hamming priority function comparator.
    private static class HammingOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            //In the compare method(),
            return (a.board.hamming() + a.moves) - (b.board.hamming() 
            + b.moves);
            //return a.board.hamming()
            //plus a.moves minus b.board
            //.hamming() plus b.moves.
        }
    }
    
    // Helper manhattan priority function comparator.
    private static class ManhattanOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            //In the compare() method,
            return (a.board.manhattan() + a.moves) - (b.board.manhattan()
            + b.moves);
            //return a.board.manhattan()
            //plus a.moves minus b.board
            //.manhattan() plus b.moves.
        }
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
        else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
