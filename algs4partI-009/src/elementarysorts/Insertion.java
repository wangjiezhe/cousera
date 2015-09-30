package elementarysorts;

/**
 * Average: 1/4 * n^2 compares, 1/4 * n^2 exchanges
 * Best: n-1 compares, 0 exchanges
 * Worst: 1/2 * n^2 compares, 1/2 * n^2 exchanges
 */
public class Insertion {
  public static <T extends Comparable<T>> void sort(T[] a) {
    int N = a.length;
    for (int i = 0; i < N; i++)
      for (int j = i; j > 0; j--)
        if (less(a[j], a[j - 1]))
          exch(a, j, j - 1);
        else
          break;
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
