package hw2;

import java.util.Map;
import java.util.Random;

public class PercolationStats {
    /**
     * perform T independent experiments on an N-by-N grid
     */
    private Random RANDOM;
    private int[] results;

    public PercolationStats (int N, int T, PercolationFactory pf) {
        this.RANDOM = new Random();
        this.results = new int[T];
        int row, col;
        for (int i = 0; i < T; i++) { // T indepedent tests
            Percolation site = pf.make(N);
            while (!site.percolates()) {
                row = RANDOM.nextInt(N);
                col = RANDOM.nextInt(N);
                site.open(row,col);
            }
            results[i] = site.numberOfOpenSites();
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        double total = 0;
        for (int i : results) {
            total += i;
        }
        return total / results.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double meanVal = mean();
        double total = 0;
        for (int i : results) {
            total += (i - meanVal) * (i - meanVal);
        }
        return Math.sqrt(total / (results.length - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev())/Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev())/Math.sqrt(results.length);
    }
}
