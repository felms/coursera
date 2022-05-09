import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int n;
    private final int trials;
    private final double[] trialResults;
    private Percolation percolation;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        this.trialResults = new double[trials];

        for (int i = 0; i < trials; i++) {
            this.percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int column = StdRandom.uniform(1, n + 1);
                percolation.open(row, column);
            }
            double r = (double)percolation.numberOfOpenSites() / ((double) n * n);
            this.trialResults[i] = r;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(this.trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double x = this.mean();
        double s = this.stddev();
        double t = Math.sqrt(this.trials);

        return x - ((1.96 * s) / t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double x = this.mean();
        double s = this.stddev();
        double t = Math.sqrt(this.trials);

        return x + ((1.96 * s) / t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        double m = percolationStats.mean();
        double s = percolationStats.stddev();
        double sl = percolationStats.confidenceLo();
        double sh = percolationStats.confidenceHi();
        System.out.println("mean                    = " + m + "\n" +
                "stddev                  = " + s + "\n" +
                "95% confidence interval = [" + sl + ", " + sh + "]");
    }

}