import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    private int experimentsCount;
    private Percolation pr;
    private final double[] x;

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) throw new IllegalArgumentException(); //Throws error is n <= 0 and m <= 0
        x = new double[m]; // Number of experiments
        for (int i = 0; i < m; i++) {
            Percolation pr = new Percolation(n);
            int s = 0;
            while (!pr.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int column = StdRandom.uniform(n) + 1;
                if (!pr.isOpen(row, column)) {
                    pr.open(row, column);
                    s++;
                }
            }
            x[i] = (double) s / (n * n);
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int j = 0;
        int i = 0;
        n = i;
        m = j;
        PercolationStats pr = new Percolation(i, j) {
            @Override
            public void open(int i, int j) {

            }

            @Override
            public boolean isOpen(int i, int j) {
                return false;
            }

            @Override
            public boolean isFull(int i, int j) {
                return false;
            }

            @Override
            public int numberOfOpenSites() {
                return 0;
            }

            @Override
            public boolean percolates() {
                return false;
            }
        };
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(x);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(x);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(x.length));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(x.length));
    }
}