package quiz;

import edu.princeton.cs.algs4.StdOut;
import unionfind.QuickFindUF;
import unionfind.WeightedQuickUnionUF;

public class UnionFind {
  public class WQU extends WeightedQuickUnionUF {
    
    public WQU(int N) {
      super(N);
    }

    public WQU(int N, int[][] unionList) {
      super(N);
      for (int[] is : unionList) {
        this.union(is[0], is[1]);
      }
    }

    @Override
    public String toString() {
      String list = "";
      int[] id = this.getId();
      for (int i = 0; i < id.length; i++) {
        list += " " + id[i];
      }
      return "WQU" + list;
    }
    
  }
  /**
   * (seed = 608237)
   * Give the id[] array that results from the following sequence of 6 union
   * operations on a set of 10 items using the quick-find algorithm.
   * 
   *     0-1 2-1 6-1 9-0 8-0 9-5 
   *
   * Your answer should be a sequence of 10 integers, separated by whitespace.
   * 
   * Recall: our quick-find convention for the union operation p-q is to change id[p]
   * (and perhaps some other entries) but not id[q].
   */
  public void question1() {
    int N = 10;
    QuickFindUF qf = new QuickFindUF(N);
    int[][] unionList = {
        {0, 1}, {2, 1}, {6, 1}, {9, 0}, {8, 0}, {9, 5}
    };
    for (int[] is : unionList) {
      qf.union(is[0], is[1]);
    }
    int[] id = qf.getId();
    StdOut.print("QF ");
    for (int i = 0; i < N; i++) {
      StdOut.print(id[i] + " ");
    }
    StdOut.println();
  }
  
  /**
   * (seed = 768982)
   * Give the id[] array that results from the following sequence of 9 union
   * operations on a set of 10 items
   * using the weighted quick-union algorithm from lecture.
   * 
   *     1-8 8-2 0-7 3-8 9-5 9-7 2-6 2-9 7-4
   * 
   * Your answer should be a sequence of 10 integers, separated by whitespace.
   * 
   * Recall: when joining two trees of equal size,
   * our weighted quick union convention is to
   * make the root of the second tree point to the root of the first tree.
   * Also, our weighted
   * quick union algorithm performs union by size (number of nodes) -
   * not union by height -
   * and does not do path compression.
   */
  public void question2() {
    int N = 10;
    int [][] unionList = {
        {1, 8}, {8, 2}, {0, 7}, {3, 8}, {9, 5},
        {9, 7}, {2, 6}, {2, 9}, {7, 4}
    };
    WQU wqu = new WQU(N, unionList);
    StdOut.println(wqu);
  }
  
  /**
   * (seed = 219657)
   * Which of the following id[] array(s) could be
   * the result of running the weighted quick union
   * algorithm on a set of 10 items? Check all that apply.
   * 
   * Recall that our weighted quick union algorithm
   * uses union by size (number of nodes)
   * and not union by height.
   * 
   * 8 7 0 7 7 7 5 2 7 8 Contains a circle
   * 3 5 3 3 3 2 4 2 3 3 OK
   * 0 0 2 9 7 1 9 5 5 0 Height of the forest 4 > lg N = lg(10)
   * 6 4 6 6 4 9 4 6 2 4 Size of tree rooted at parent of 6 < 
   *                     twice the size of tree rooted at 6
   * 0 9 2 3 4 5 3 7 9 9 OK
   */
  public void question3() {
    int N = 10;
    int[][] case1 = {
        {3, 0}, {3, 8}, {3, 9}, {4, 6}, {3, 4},
        {5, 1}, {2, 7}, {2, 5}, {3, 2}
    };
    WQU wqu1 = new WQU(N, case1);
    StdOut.println(wqu1);
    int[][] case2 = {
        {3, 6}, {9, 1}, {9, 8}
    };
    WQU wqu2 = new WQU(N, case2);
    StdOut.println(wqu2);
  }
  
  public static void main(String[] args) {
    UnionFind uf = new UnionFind();
    uf.question1();
    uf.question2();
    uf.question3();
  }
}
