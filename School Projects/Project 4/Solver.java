import dsa.LinkedStack;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

// A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations.
public class Solver {
    // The number of moves it takes to get to the fastest possible solution.
    int moves;  
    // Stack to store the boards of solution.
    LinkedStack<Board> solution = new LinkedStack<>();  
    // Finds a solution to the initial board using the A* algorithm.
    public Solver(Board board) {
        if (board == null) {
            throw new NullPointerException("board is null");
        }   else if (!board.isSolvable()) {
            throw new IllegalArgumentException("board is unsolvable");
        }
        // Priority queue to use for the A* algorithm.
        MinPQ<SearchNode> pq = new MinPQ<>();   
        // Insert the starting board into the queue.
        pq.insert(new SearchNode(board, 0, new SearchNode(board, 0, null)));

        /*
        While there are still possible boards, pop of the highest priority board,
        and check to see if it is the solution. If it is, then add it to the stack of
        solution boards, and in reverse order find all the boards that led to that solution,
        and add them to the stack. If the board was not the solution, find all neighboring
        boards that are not reverting to the previous position, add them to potential solutions,
        and then start the process again.
         */
        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();
            if (node.board.isGoal()) {
                this.moves = node.moves;
                solution.push(node.board);
                while (node.previous.board != board) {
                    node = node.previous;
                    solution.push(node.board);
                }
                break;
            }
            for (Board neighbor: node.board.neighbors()) {
                if (node.previous.board != neighbor) {
                    pq.insert(new SearchNode(neighbor, node.moves + 1, node));
                }
            }
        }
    }

    // Returns the minimum number of moves needed to solve the initial board.
    public int moves() {
        return moves;
    }

    // Returns a sequence of boards in the shortest solution of the initial board.
    public Iterable<Board> solution() {
        return solution;
    }

    // A data type that represents a search node in the game tree. Each node includes a
    // reference to a board, the number of moves to the node from the initial node, and a
    // reference to the previous node.
    private class SearchNode implements Comparable<SearchNode> {
        Board board;    // Board to store in the search node.
        int moves;      // Number of moves it has taken to get to this board position.
        SearchNode previous;    // The previous node.

        // Constructs a new search node.
        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        // Returns a comparison of this node and other based on the following sum:
        //   Manhattan distance of the board in the node + the # of moves to the node
        public int compareTo(SearchNode other) {
            return (board.manhattan + moves) - (other.board.manhattan + other.moves);
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}