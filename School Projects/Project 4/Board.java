import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;


// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    // A 2d representation of the tiles in the puzzle.
    int[][] tiles;
    // The number of rows, or the size of the board.
    int n;   
    // The calculated hamming distance of all the tiles on the board.       
    int hamming;    
    // The calculated manhattan distance of all the tiles on the board.
    int manhattan;  
    // The position of the blank tile in row major order.
    int blankPos;   

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.tiles = tiles.clone();
        n = tiles.length;

        /*
        For each tile, add the hamming distance to the total hamming distance.
        */
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {

                if (tiles[i][j] == 0) { // Set location of blank and don't find hamming distance.
                    blankPos = tiles.length * i + j + 1;
                    continue;
                }

                if (tiles[i][j] != tiles.length * i + j + 1) {
                    hamming++;
                }
            }
        }

        /*
        For each tile, calculate the Manhattan distance and add to total.
         */
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {

                if (tiles[i][j] == 0) {
                    continue;
                }

                int placement = tiles[i][j];
                int rowDisplacement = Math.abs(i - ((placement - 1) / n));
                int columnDisplacement = Math.abs(j - ((placement - 1) % n));
                manhattan += columnDisplacement + rowDisplacement;

            }
        }
    }

    // Returns the size of this board.
    public int size() {
        return tiles.length;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        return hamming;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        return manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        return hamming == 0;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {

        // Create array to represent board in row major order.
        int[] rowMajorBoard = new int[n * n - 1];
        // What row the blank tile is in, will be changed to correct value.
        int zeroRow = -1;   
        /*
        Put the board in row major order and store it in rowMajorBoard.
         */
        // Current index of the row major order array you want to define.
        int count = 0;  
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {

                /*
                If current tile is the blank tile, do not include it in row major order.
                 */
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    continue;
                }

                rowMajorBoard[count] = tiles[i][j];
                count++;
            }
        }

        /*
        Find the number of inversions to the solution board, and if the size of the board is
        odd, it is solvable if the number of inversions is even. If the size is even, the number
        of inversions + number of rows must be opposite.
         */
        long inversions = Inversions.count(rowMajorBoard);
        if (n % 2 == 0) {
            return (inversions + zeroRow) % 2 != 0;
        }   else {
            return inversions % 2 == 0;
        }
    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        LinkedQueue<Board> neighbors = new LinkedQueue<>();

        /*
        Find the row and column numbers of the blank tile.
         */
        int zeroX = -1, zeroY = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }

        /*
        Find the neighboring boards and add them to the queue.
         */
        if (zeroY + 1 < n) {
            int[][] copyTiles = cloneTiles();
            copyTiles[zeroX][zeroY] = copyTiles[zeroX][zeroY + 1];
            copyTiles[zeroX][zeroY + 1] = 0;
            Board neighbor = new Board(copyTiles);
            neighbors.enqueue(neighbor);
        }

        if (zeroY - 1 >= 0) {
            int[][] copyTiles = cloneTiles();
            copyTiles[zeroX][zeroY] = copyTiles[zeroX][zeroY - 1];
            copyTiles[zeroX][zeroY - 1] = 0;
            Board neighbor = new Board(copyTiles);
            neighbors.enqueue(neighbor);
        }

        if (zeroX - 1 >= 0) {
            int[][] copyTiles = cloneTiles();
            copyTiles[zeroX][zeroY] = copyTiles[zeroX - 1][zeroY];
            copyTiles[zeroX - 1][zeroY] = 0;
            Board neighbor = new Board(copyTiles);
            neighbors.enqueue(neighbor);
        }

        if (zeroX + 1 < n) {
            int[][] copyTiles = cloneTiles();
            copyTiles[zeroX][zeroY] = copyTiles[zeroX + 1][zeroY];
            copyTiles[zeroX + 1][zeroY] = 0;
            Board neighbor = new Board(copyTiles);
            neighbors.enqueue(neighbor);
        }


        return neighbors;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return this.tiles == ((Board) other).tiles;
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
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
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}