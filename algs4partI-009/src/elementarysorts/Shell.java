package elementarysorts;

/**
 * h-sorted array: an h interleaved sorted subsequences.
 * h-sort: insertion sort, with stride with h.
 *
 * (Shell 1959) h-sort array for decreasing sequence of values of h.
 *
 * Prop. A g-sorted array remains g-sorted after h-sorting it.
 *
 * How to choose the increment sequence of h?
 *
 * @formatter: off
 * Hibbard:
 *    x <- 2x+1
 *    1, 3, 7, ..., 2^k - 1, ...
 * Knuth:
 *    x <- 3x+1
 *    1, 4, 13, 40, 121, 364, ..., (3^k - 1) / 2, ...
 *    OEIS A003462
 *    Suggested when n < 1000
 * Sedgewick:
 *    x <- 9*(4^k – 2^k) + 1, 2^(k+2)*(2^(k+2) – 3 ) + 1
 *    1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, ...
 *    OEIS A033622
 *    Best on big values
 * Ciura:
 *    1, 4, 10, 23, 57, 132, 301, 701, 1750
 *    OEIS A102549
 *    Optimal (best known) sequence of increments for shell sort algorithm
 *@formatter:on
 *
 * Worst: O(n^(3/2)) compares with the 3x+1 increment
 * Actual: maybe c * n * lg(n) compares
 */
public class Shell {
  public static <T extends Comparable<T>> void sort(T[] a) {
    int N = a.length;

    int h = 1;
    while (h < N / 3)
      h = 3 * h + 1;  // 3x+1 increment sequence

    while (h > 1) {
      for (int i = h; i < N; i++) // insertion sort with stride with h
        for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
          exch(a, j, j - h);

      h = h / 3;  // move to next increment
    }
  }

  private static <T extends Comparable<T>> boolean less(T v, T w) {
    return v.compareTo(w) < 0;
  }

  private static <T extends Comparable<T>> void exch(T[] a, int i, int j) {
    T tmp = a[i];
    a[i] = a[j];
    a[j] = tmp;
  }
}
