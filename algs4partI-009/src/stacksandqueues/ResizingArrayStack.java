package stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayStack<Item> implements Iterable<Item> {
  private Item[] s;
  private int N = 0;

  @SuppressWarnings("unchecked")
  public ResizingArrayStack() {
    s = (Item[]) new Object[1];
  }

  public boolean isEmpty() {
    return N == 0;
  }

  private void resize(int capacity) {
    @SuppressWarnings("unchecked")
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < N; i++)
      copy[i] = s[i];
    s = copy;
  }

  public void push(Item item) {
    if (N == s.length)
      resize(2 * s.length); // repeat doubling -> amortize cost
    s[N++] = item;
  }

  public Item pop() {
    Item item = s[--N];
    s[N] = null;
    if (N > 0 && N == s.length / 4)
      resize(s.length / 2); // halve when one-quarter full
    return item;
  }

  @Override
  public Iterator<Item> iterator() {
    return new ReversedArrayIterator();
  }

  private class ReversedArrayIterator implements Iterator<Item> {
    private int i = N;

    @Override
    public boolean hasNext() {
      return i > 0;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      return s[--i];
    }
  }
}
