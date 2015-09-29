package stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayQueue<Item> implements Iterable<Item> {
  private Item[] s;
  private int head = 0;
  private int tail = 0;

  @SuppressWarnings("unchecked")
  public ResizingArrayQueue() {
    s = (Item[]) new Object[1];
  }

  private void resize(int capacity) {
    @SuppressWarnings("unchecked")
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = head; i < tail; i++)
      copy[i - head] = s[i % capacity];
    s = copy;
  }

  public boolean isEmpty() {
    return tail - head == 0;
  }

  public void enqueue(Item item) {
    if (tail - head == s.length)
      resize(2 * s.length);
    s[tail++ % s.length] = item;
  }

  public Item dequeue() {
    Item item = s[head % s.length];
    s[head++ % s.length] = null;
    if (tail - head > 0 && tail - head == s.length / 4)
      resize(s.length / 4);
    return item;
  }

  @Override
  public Iterator<Item> iterator() {
    return new ArrayIterator();
  }

  private class ArrayIterator implements Iterator<Item> {
    private int i = head;

    @Override
    public boolean hasNext() {
      return i < tail;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      return s[i++ % s.length];
    }

  }
}
