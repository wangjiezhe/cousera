package org.coursera.unionfind;

/**
 * Quick Find
 *
 * Worst-case time: M N,
 * for M union-find operations on a set of N objects.
 */
public class QuickFindUF {
  private int[] id;  // id[i] and i are in the same component

  public QuickFindUF(int n) {
    id = new int[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
    }
  }

  public boolean connected(int p, int q) {
    return id[p] == id[q];
  }

  public void union(int p, int q) {
    int pid = id[p];
    int qid = id[q];
    if (pid != qid) {
      for (int i = 0; i < id.length; i++) {
        if (id[i] == pid) {
          id[i] = qid;
        }
      }
    }
  }
}
