package assignment.percolation;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int N;
  private WeightedQuickUnionUF wqu;
  private int[] openStatus;
  
  /**
   * create N-by-N grid, with all sites blocked
   * plus two virtual site: 0 (over the top) and N*N+1 (below the bottom)
   */
  public Percolation(int N) {
    if (N <= 0)
      throw new IllegalArgumentException();
    this.N = N;
    wqu = new WeightedQuickUnionUF(N*N+2);
    openStatus = new int[N*N+2];
    for (int i = 0; i < N*N+2; i++)
      openStatus[i] = 0;
  }
  
  /**
   * open site (row i, column j) if it is not open already
   */
  public void open(int i, int j) {
    if (!isOpen(i, j)) {
      if (i == 1) { // the top line
        if (openStatus[0] == 0)
          openStatus[0] = 1;
        wqu.union(0, getLoc(i, j));
      }
      if (i == N) { // the bottom line
        if (openStatus[N*N+1] == 0)
          openStatus[N*N+1] = 1;
        wqu.union(N*N+1, getLoc(i, j));
      }
      openStatus[getLoc(i, j)] = 1;
      int[][] surround = {
          {i-1, j}, {i+1, j}, {i, j-1}, {i, j+1}
      };
      for (int[] site:surround) {
        try {
          if (isOpen(site[0], site[1]))
            wqu.union(getLoc(i, j), getLoc(site[0], site[1]));
        } catch (IndexOutOfBoundsException e) {
          continue;
        }
      }
    }
  }
  
  /**
   * is site (row i, column j) open?
   */
  public boolean isOpen(int i, int j) {
    return openStatus[getLoc(i, j)] == 1;
  }
  
  /**
   * is site (row i, column j) full?
   * 
   * A full site is an open site that can be connected to an open site
   * in the top row via a chain of neighboring (left, right, up, down) open sites.
   */
  public boolean isFull(int i, int j) {
    return wqu.connected(0, getLoc(i, j));
  }
  
  /**
   * does the system percolate?
   * 
   * We say the system percolates if there is a full site in the bottom row.
   * In other words, a system percolates if we fill all open sites
   * connected to the top row and that process fills some open site
   * on the bottom row.
   */
  public boolean percolates() {
      return wqu.connected(0, N*N+1);
  }

  /**
   * test client (optional)
   */
  public static void main(String[] args) {
     for (String arg : args) {
      In in = new In(arg);
      int N = in.readInt();
      Percolation perc = new Percolation(N);
      while (!in.isEmpty()) {
        int i = in.readInt();
        int j = in.readInt();
        perc.open(i, j);
      }
      StdOut.println(perc.percolates());
    }
  }
  
  /**
   * get the location of the site (row i, column j) in the array
   * 
   */
  private int getLoc(int i, int j) {
    if (i < 1 || i > N || j < 1 || j > N)
      throw new IndexOutOfBoundsException();
    return (i-1)*N+j;
  }
}
