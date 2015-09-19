package interview;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 *  Search in a bitonic array.
 *  
 *  An array is bitonic if it is comprised of an increasing sequence of integers
 *  followed immediately by a decreasing sequence of integers.
 *  Write a program that, given a bitonic array of N distinct integer values,
 *  determines whether a given integer is in the array. 
 *  
 *  Standard version: Use ~ 3lgN compares in the worst case.
 *  Signing bonus: Use ~ 2lgN compares in the worst case
 *                 (and prove that no algorithm can guarantee to perform
 *                  fewer than ~ 2lgN compares in the worst case).
 */
public class SearchInBitomicArray {
  
  /**
   * Find the peak value in the bitonic integer array.
   * 
   * worst case: ~ lgN
   */
  public static int findMax(int[] a) {
    int N = a.length;
    if (a[0] > a[1])
      return 0;
    if (a[N-1] > a[N-2])
      return N-1;
    int lo = 1;
    int hi = N-2;
    while (lo < hi) {
      int mid = lo + (hi - lo) / 2;
      if (a[mid-1] < a[mid] && a[mid] < a[mid+1])
        lo = mid+1;
      else if (a[mid-1] > a[mid] && a[mid] > a[mid+1])
        hi = mid-1;
      else
        return mid;
    }
    if (lo == hi)
      return lo;
    else
      return -1;
  }
  
  /**
   * Find the peak value, and search one or two halves using binary search algorithm.
   * 
   * worst case: ~ lgN + 2lgN = 3lgN
   */
  public static int searchWithFindMax(int[] a, int x) {
    int m = findMax(a);
    if (x == a[m])
      return m;
    else if (x > a[m]) {
      StdOut.println(x + " is bigger than the max " + m + "th: " + a[m]);
      return -2;
    }
    else {
      int res = binarySearchUp(a, x, 0, m-1);
      if (res == -1)
        return binarySearchDown(a, x, m+1, a.length-1);
      else
        return res;
    }
  }
  
  /**
   * worst case: ~ 2lgN
   */
  public static int searchWrapper(int[] a, int x) {
    int N = a.length;
    if (x == a[0])
      return 0;
    if (x == a[N-1])
      return N-1;
    if (x < a[0] && x < a[N-1])
      return -4;
    return search(a, x, 1, N-2);
  }
  
  /**
   * Search x in [a[lo], a[hi]]
   * 
   * Key insight:
   *    if you know a[lo] <= key < a[hi]
   *    (The open inequality for a[hi] is important!),
   *    then you can use a binary search
   *    on the semi-open range [lo,hi), even if a is bitonic.
   */
  private static int search(int[] a, int x, int lo, int hi) {
    if (lo > hi)
      return -3;
    int mid = lo + (hi - lo) / 2;
    if (mid == 0)
      StdOut.println(mid + " " + lo + " " + hi + " " + a[0] + " " + a[1]);
    if (x > a[mid]) // Search one half
      if (a[mid-1] < a[mid] && a[mid] < a[mid+1])
        return search(a, x, mid+1, hi);
      else if (a[mid-1] > a[mid] && a[mid] > a[mid+1])
        return search(a, x, lo, mid-1);
      else
        return -2;
    else if (x < a[mid]) // Search one half or two halves
      if (a[mid-1] < a[mid] && a[mid] < a[mid+1]) {
        int tmp = binarySearchUp(a, x, lo, mid-1);
        if (tmp == -1)
          return binarySearchDown(a, x, mid+1, hi); // Key insight
        else
          return tmp;
      } else if (a[mid-1] > a[mid] && a[mid] > a[mid+1]) {
        int tmp = binarySearchDown(a, x, mid+1, hi);
        if (tmp == -1)
          return binarySearchUp(a, x, lo, mid-1); // Key insight
        else
          return tmp;
      } else {
        int tmp = binarySearchUp(a, x, lo, mid-1);
        if (tmp == -1)
          return binarySearchDown(a, x, mid+1, hi);
        else
          return tmp;
      }
    else
      return mid;
  }
  
  /**
   * Search a increasing integer array using binary search algorithm.
   * 
   * worst case: ~ lgN
   */
  private static int binarySearchUp(int[] a, int x, int lo, int hi) {
    if (lo > hi)
      return -1;
    int mid = lo + (hi - lo) / 2;
    if (x < a[mid])
      return binarySearchUp(a, x, lo, mid-1);
    else if (x > a[mid])
      return binarySearchUp(a, x, mid+1, hi);
    else
      return mid;
  }
  
  /**
   * Search a decreasing integer array using binary search algorithm.
   * 
   * worst case: ~ lgN
   */
  private static int binarySearchDown(int[] a, int x, int lo, int hi) {
    if (lo > hi)
      return -1;
    int mid = lo + (hi - lo) / 2;
    if (x < a[mid])
      return binarySearchDown(a, x, mid+1, hi);
    else if (x > a[mid])
      return binarySearchDown(a, x, lo, mid-1);
    else
      return mid;
  }
  
  public static void reverseArray(int[] a, int fromIndex, int toIndex) {
    for (int i = 0; i < (toIndex - fromIndex) / 2; i++) {
      int tmp = a[fromIndex+i];
      a[fromIndex+i] = a[toIndex-1-i];
      a[toIndex-1-i] = tmp;
    }
  }
  
  public static int[] getBitonicArray(int N) {
    int[] a = new int[N];
    int peak = StdRandom.uniform(N);
//    StdOut.println(peak);
    for (int i = 0; i < N; i++)
      a[i] = i;
    StdRandom.shuffle(a);
    Arrays.sort(a, 0, peak);
    Arrays.sort(a, peak, N);
    reverseArray(a, peak, N);
    return a;
  }
  
  public static void printList(int[] a) {
    int N = a.length;
    for (int i = 0; i < N; i++)
      StdOut.print(String.format("%1$2d ", a[i]));
    StdOut.println();
  }
  
  public static boolean testSearchWrapper(int N) {
    int[] a = getBitonicArray(N);
    boolean res = true;
    for (int i = 0; i < N; i++) {
      int s = searchWrapper(a, a[i]);
      if (s != i) {
        res = false;
        StdOut.println("SW: " + a[i] + " " + i + "th " + s);
      }
    }
    return res;
  }
  
  public static boolean testSearchWithFindMax(int N) {
    int[] a = getBitonicArray(N);
    boolean res = true;
    for (int i = 0; i < N; i++) {
      int s = searchWithFindMax(a, a[i]);
      if (s != i) {
        res = false;
        StdOut.println("FM: " + a[i] + " " + i + "th " + s);
      }
    }
    return res;
  }
  
  public static boolean testBinarySearchUp(int N) {
    int[] a = new int[N];
    boolean res = true;
    for (int i = 0; i < N; i++)
      a[i] = i;
    for (int i = 0; i < N; i++) {
      int s = binarySearchUp(a, a[i], 0, N-1);
      if (s != i) {
        res = false;
        StdOut.println(a[i] + " " + i + "th " + s);
      }
    }
    return res;
  }
  
  public static boolean testBinarySearchDown(int N) {
    int[] a = new int[N];
    boolean res = true;
    for (int i = 0; i < N; i++)
      a[i] = N-i;
    for (int i = 0; i < N; i++) {
      int s = binarySearchDown(a, a[i], 0, N-1);
      if (s != i) {
        res = false;
        StdOut.println(a[i] + " " + i + "th " + s);
      }
    }
    return res;
  }
  
  public static double testWithTime(int N, int count) {
    Stopwatch stopwatch = new Stopwatch();
    for (int i = 0; i < count; i++)
      testSearchWrapper(N);
    double time1 = stopwatch.elapsedTime();
    StdOut.println("SW: " + time1);
    stopwatch = new Stopwatch();
    for (int i = 0; i < count; i++)
      testSearchWithFindMax(N);
    double time2 = stopwatch.elapsedTime();
    StdOut.println("FM: " + time2);
    return time1 - time2;
  }
  
  public static void main(String[] args) {
//    int[] a = {
//        1, 3, 5, 7, 9, 10, 8, 6, 4, 2, 0
//    };
//    int key = 2;
//    StdOut.println(SearchInBitomicArray.searchWrapper(a, key));
//    StdOut.println(SearchInBitomicArray.findMax(a));
//    StdOut.println(SearchInBitomicArray.searchWithFindMax(a, key));
//    SearchInBitomicArray.printList(SearchInBitomicArray.getBitonicArray(100));
//    testWithTime(100, 100);
//    testWithTime(1000, 100);
//    testWithTime(10000, 100);
//    testWithTime(100000, 10);
//    testWithTime(100000, 100);
    int[][] testSuite = {
        {100, 100}, {1000, 100}, {10000, 100},
        {100, 1000}, {1000, 1000}, {10000, 1000},
        {10000, 10}, {10000, 100},
        {100000, 10}, {100000, 100},
        {1000000, 10}, {1000000, 100}
    };
    for (int i = 0; i < testSuite.length; i++) {
      StdOut.println(i + "th: " + testWithTime(testSuite[i][0], testSuite[i][1]));
    }
  }
}
