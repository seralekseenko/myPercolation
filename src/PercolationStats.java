import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double K = 1.96;

    private final double[] percolationThreshold;

    private final double mean;
    private final double stddev;

    /**
     * Checks the validity of the arguments. Runs a percolation test.
     * @param n - Grid size.
     * @param tests - The number of percolation tests over the grid.
     */
    public PercolationStats(int n, int tests) { // O(
        if (n <= 0) throw new IllegalArgumentException("Invalid input argument. n: " + n);
        if (tests <= 0) throw new IllegalArgumentException("Invalid input argument. tests: " + tests);

        percolationThreshold = new double[tests];

        runPercolationTests(n, tests);

        mean = StdStats.mean(percolationThreshold);
        stddev = StdStats.stddev(percolationThreshold);
    }

    public double mean() {
        return mean;
    }
    public double stddev() {
        return stddev;
    }
    public double confidenceLo() {
        return mean - (K * stddev / Math.sqrt(percolationThreshold.length));
    }
    public double confidenceHi() {
        return mean + (K * stddev / Math.sqrt(percolationThreshold.length));
    }


    /**
     * Creates a percolation object and performs percolation tests T-times.
     * @param n - Grid size.
     * @param t - The number of percolation t over the grid.
     */
    private void runPercolationTests(int n, int t) { // O(
        for (int i = 0; i < t; i++) { // t test's

            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }

            }
            percolationThreshold[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    /**
     *Takes as input the parameters for the constructor and passes them to it.
     * Creates a PercolationStats object.
     * May take arguments from the keyboard.
     * @param args [0] & [1] - include integer n(the grid size) & integer t (the number of experiments)
     */
    public static void main(String [] args) {
        System.out.println(StdRandom.uniform(1));
        System.out.println(StdRandom.uniform(1) + 1);
        int n;
        int t;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        } else {
            StdOut.print("Enter the grid size. n: ");
            n = StdIn.readInt();

            StdOut.print("Enter the number of experiments. T: ");
            t = StdIn.readInt();
        }
        PercolationStats percolationStats = new PercolationStats(n, t);

        StdOut.printf("Mean                    = %.16f " +
                        "\nStddev                  = %1.16f " +
                        "\n95%% confidence interval = [%1.16f, %1.16f]",
                percolationStats.mean(),
                percolationStats.stddev(),
                percolationStats.confidenceLo(),
                percolationStats.confidenceHi());
    }
}
