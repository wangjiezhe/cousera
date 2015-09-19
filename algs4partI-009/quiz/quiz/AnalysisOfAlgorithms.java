package quiz;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class AnalysisOfAlgorithms {
  /**
   * @return log of a base 2
   */
  public static double lg2(double a) {
    return Math.log(a) / Math.log(2);
  }
  
  /**
   * @return the covariance between two series
   */
  public static double covp(double[] a, double[] b) throws Exception {
    if (a.length != b.length)
      throw new Exception("The length of a is not equal to the length of b");
    double avgA = StdStats.mean(a);
    double avgB = StdStats.mean(b);
    double sum = 0;
    for (int i = 0; i < a.length; i++)
      sum += (a[i] - avgA) * (b[i] - avgB);
    return sum / a.length;
  }
  
  /**
   * (seed = 182361)
   * Suppose that you time a program as a function of N and produce
   * the following table.
   *
   *         N   seconds
   * -------------------
   *      2048     0.000
   *      4096     0.000
   *      8192     0.001
   *     16384     0.002
   *     32768     0.006
   *     65536     0.017
   *    131072     0.048
   *    262144     0.133
   *    524288     0.367
   *   1048576     1.015
   *   2097152     2.806
   *   4194304     7.753
   *   8388608    21.432
   *  16777216    59.198
   *  33554432   163.618
   *  67108864   452.312
   * 134217728  1249.879
   * 
   * 
   * Estimate the order of growth of the running time as a function of N.
   * Assume that the running time obeys a power law T(N) ~ a N^b. For your
   * answer, enter the constant b. Your answer will be marked as correct
   * if it is within 1% of the target answer - we recommend using
   * two digits after the decimal separator, e.g., 2.34.
   */
  public void question1() throws Exception {
    int len = 15;
    double[] T = {
        0.001,
        0.002,
        0.006,
        0.017,
        0.048,
        0.133,
        0.367,
        1.015,
        2.806,
        7.753,
        21.432,
        59.198,
        163.618,
        452.312,
        1249.879
    };
    double[] N = {
        8192,
        16384,
        32768,
        65536,
        131072,
        262144,
        524288,
        1048576,
        2097152,
        4194304,
        8388608,
        16777216,
        33554432,
        67108864,
        134217728,
    };
    double[] lgN = new double[len];
    double[] lgT = new double[len];
    for (int i = 0; i < len; i++) {
      lgN[i] = lg2(N[i]);
      lgT[i] = lg2(T[i]);
    }
    double b = covp(lgN, lgT) / StdStats.varp(lgN);
    double lgA = StdStats.mean(lgT) - b * StdStats.mean(lgN);
    double a = Math.pow(2, lgA);
    StdOut.println("b: " + b);
    StdOut.println("a: " + a);
    
    for (int i = 0; i < len; i++) {
      double y = a * Math.pow(N[i], b);
      double errPer = (y - T[i]) / T[i] * 100;
      StdOut.println(errPer + "%");
    }
  }

  /**
   * (seed = 619279)
   * Given the following definition of a MysteryBox object: 
   * 
   * public class MysteryBox {
   *     private final long x0, x1, x2;
   *     private final int y0;
   *     private final double z0, z1, z2;
   *     private final boolean[] a = new boolean[56];
   *     
   *     ...
   * }
   * 
   * Using the 64-bit memory cost model from lecture, how many bytes does
   * each object of type MysteryBox use? Include all memory allocated when the
   * client calls new MysteryBox().
   */
  public void question3() {
    int memory = 16 + 8*3 + 4 + 8*3 +
        8 + 24 + 1*56;
    int toPadding = memory % 8;
    if (toPadding != 0)
      memory += (8 - toPadding);
    StdOut.println(memory);
  }
  
  public static void main(String[] args) throws Exception {
    AnalysisOfAlgorithms aoa = new AnalysisOfAlgorithms();
    aoa.question3();
    aoa.question1();
  }

}
