import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using a 2D array.
public class ArrayPercolation implements Percolation {
    private final boolean[][] open; // Number of Open Sites
    private final int n; // Percolation Size
    private int openSites; // Number of Open Sites

    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        this.n = n;
        this.openSites = 0;
        this.open = new boolean[this.n][this.n];
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i > this.n - 1 || j < 0 || j > this.n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!this.open[i][j]) {
            this.open[i][j] = true;
            openSites++;
        }

    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i > this.n - 1 || j < 0 || j > this.n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // check if site is open, if so return true
        return this.open[i][j];

    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i > this.n - 1 || j < 0 || j > this.n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        boolean[][] full = new boolean[this.n][this.n];

        for (int col = 0; col < this.n; col++) {
            floodFill(full, 0, col);
        }
        return full[i][j];
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        for (int col = 0; col < this.n; col++) {
            if (isFull(this.n - 1, col)) {
                return true;
            }
        }
        return false;
    }

    private void floodFill(boolean[][] full, int i, int j) {
        if (i < 0 || i > this.n - 1 || j < 0 || j > this.n - 1) {
            return;
        }
        // return if site is full or site is not open
        if (full[i][j] || !isOpen(i, j)) {
            return;
        }
        // Floodfill
        full[i][j] = true;
        floodFill(full, i + 1, j);
        floodFill(full, i, j - 1);
        floodFill(full, i, j + 1);
        floodFill(full, i - 1, j);
    }
}