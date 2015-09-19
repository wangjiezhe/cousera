package analysisofalgorithms;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ThreeSum {
  /**
   * Brute-force algorithm
   * 
   * finding the situations that the sum of three integers in the array vanish.
   * runtime: N^3
   */
  public static int count(int[] a) {
    int N = a.length;
    int count = 0;
    for (int i = 0; i < N; i++)
      for (int j = i+1; j < N; j++)
        for (int k = j+1; k < N; k++)
          if (a[i] + a[j] + a[k] == 0)
            count++;
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
