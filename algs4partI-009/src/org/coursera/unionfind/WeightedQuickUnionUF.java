package org.coursera.unionfind;

/**
 * Weighted Quick Union with Path Compression (WQUPC)
 *
 * Worst-case time: N + M lg* N,
 * where `lg*` (usually read "log star")
 * indicates the binary iterated logarithm,
 * for M union-find operations on a set of N objects.
 */
public class WeightedQuickUnionUF {
  private int[] id;  // id[i] point to the parent of i
  private int[] sz;  // Size of the Tree

  public WeightedQuickUnionUF(int n) {
    id = new int[n];
    sz = new int[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }

  public int root(int i) {
    while (i != id[i]) {
      id[i] = id[id[i]];
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
    if (i == j) {
      return;
    }
    // Link the root of the smaller tree to the root of the larger tree
    // and update sz[]
    if (sz[i] >= sz[j]) {
      id[j] = i;
      sz[i] += sz[j];
    } else {
      id[i] = j;
      sz[j] += sz[i];
    }
  }
}
