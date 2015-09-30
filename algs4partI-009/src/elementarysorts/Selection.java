package elementarysorts;

/**
 * Worst: 1/2 * N^2 compares, N exchanges
 * Best: 1/2 * N^2 compares, 0 exchanges
 */
public class Selection {
  public static <T extends Comparable<T>> void sort(T[] a) {
    int N = a.length;
    for (int i = 0; i < N; i++) {
      int min = i;
      for (int j = i + 1; j < N; j++)
        if (less(a[j], a[min]))
          min = j;
      exch(a, i, min);
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
