package org.coursera.unionfind;

/**
 * Quick Union
 *
 * Worst-case time: M N,
 * for M union-find operations on a set of N objects.
 */
public class QuickUnionUF {
  private int[] id;  // id[i] point to the parent of i

  public QuickUnionUF(int n) {
    id = new int[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
    }
  }

  public int root(int i) {
    while (i != id[i]) {
      i = id[i];
    }
    return i;
  }

  public boolean connected(int p, int q) {
    return root(p) == root(q);
  }

  public void union(int p, int q) {
    int i = root(p);
    int j = root(q);
    id[i] = j;
  }
}
