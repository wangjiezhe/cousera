package interview;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * 3-SUM in quadratic time.
 * 
 * Design an algorithm for the 3-SUM problem that
 * takes time proportional to N^2 in the worst case.
 * You may assume that you can sort the N integers
 * in time proportional to N^2 or better.
 */
public class ThreeSumInQuadraticTime {
  /**
   * Runtime: O(n^2)
   * 
   * i circulate from 0 to N-1;
   * j circulate from 0 to bigger, k from N-1 to smaller,
   * and stop when they meet. 
   * 
   * Use `kMax` and `kMaxChanged` to optimize:
   *    If b[i_0] + b[j_0] + b[k_0] == 0 and j_0 == i_0 + 1 or j_0 == i_0 + 2,
   *    then when i_0 steps to i_1 = i_0 + 1,
   *    j_1 > i_1 => j_1 >= i_0 + 2 => j_1 >= j_0.
   *    So for all k > k_0,
   *    b[i_1] + b[j_1] + b[k] > b[i_0] + b[j_0] + b[k_0] = 0,
   *    therefore all k that > k_0 can be omitted.
   * 
   * This is faster than the official one given below, about 0.01s for 8K ints.
   */
  public static int count(int[] a) {
    int N = a.length;
    int[] b = a.clone();
    Arrays.sort(b);
    int count = 0;
    int kMax = N-1;
    for (int i = 0; i < N-2; i++) {
      boolean kMaxChanged = false;
      for (int j = i+1, k = kMax; j < k; j++) {
        if (b[i] + b[j] + b[k] < 0)
          continue;
        while (b[i] + b[j] + b[k] > 0 && j < k)
          k--;
        if (j == k)
          continue;
        if (b[i] + b[j] + b[k] == 0) {
          count++;
          if (j <= i+2 && !kMaxChanged) {
            kMaxChanged = true;
            kMax = k;
          }
        }
      }
    }
    return count;
  }
  
  /**
   * Official solution
   * 
   * Reference: https://en.wikipedia.org/wiki/3SUM
   *            http://www.ti.inf.ethz.ch/ew/courses/CG09/materials/v12.pdf
   */
  public static int countOfficial(int[] a) {
    int N = a.length;
    int[] b = a.clone();
    Arrays.sort(b);
    int count = 0;
    for (int i = 0; i < N-2; i++) {
      int j = i+1;
      int k = N-1;
      while (j < k) {
        if (b[i] + b[j] + b[k] == 0) {
          count++;
          j++;
          k--;
        } else if (b[i] + b[j] + b[k] > 0)
          k--;
        else
          j++;
      }
    }
    return count;
  }
  
  public static void main(String[] args) {
    int[] a = new In(args[0]).readAllInts();
    Stopwatch stopwatch = new Stopwatch();
    StdOut.println(count(a));
    double time = stopwatch.elapsedTime();
    StdOut.println(time);
  }
}
