package assignment.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double[] threshold;
  
  /**
   * perform T independent experiments on an N-by-N grid
   */
  public PercolationStats(int N, int T) {
    if (N <= 0 || T <= 0)
      throw new IllegalArgumentException();
    threshold = new double[T];
    int[] percolationPoint = new int[T];
    for (int i = 0; i < T; i++) {
      percolationPoint[i] = 0;
      Percolation perc = new Percolation(N);
      while (!perc.percolates()) {
        int j = StdRandom.uniform(1, N+1);
        int k = StdRandom.uniform(1, N+1);
        if (!perc.isOpen(j, k)) {
          perc.open(j, k);
          percolationPoint[i]++;
        }
      }
      threshold[i] = percolationPoint[i] / (double) (N*N);
    }
  }
  
  /**
   * sample mean of percolation threshold
   */
  public double mean() {
    return StdStats.mean(threshold);
  }
  
  /**
   * sample standard deviation of percolation threshold
   */
  public double stddev() {
    return StdStats.stddev(threshold);
  }
  
  /**
   * low endpoint of 95% confidence interval
   */
  public double confidenceLo() {
    return mean() - 1.96 * stddev() / Math.sqrt(threshold.length);
  }
  
  /**
   * high endpoint of 95% confidence interval
   */
  public double confidenceHi() {
    return mean() + 1.96 * stddev() / Math.sqrt(threshold.length);
  }

  /**
   * test client (described below)
   */
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);
    PercolationStats percs = new PercolationStats(N, T);
    StdOut.println("mean                    = " + percs.mean());
    StdOut.println("stddev                  = " + percs.stddev());
    StdOut.println("%95 confidence interval = " +
        percs.confidenceLo() + ", " + percs.confidenceHi());
  }
}
