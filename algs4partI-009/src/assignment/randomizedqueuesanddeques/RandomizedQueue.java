package assignment.randomizedqueuesanddeques;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] s;
  private int N;

  @SuppressWarnings("unchecked")
  public RandomizedQueue() { // construct an empty randomized queue
    s = (Item[]) new Object[1];
    N = 0;
  }

  public boolean isEmpty() { // is the queue empty?
    return N == 0;
  }

  public int size() { // return the number of items on the queue
    return N;
  }

  private void resize(int capacity) {
    @SuppressWarnings("unchecked")
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < N; i++)
      copy[i] = s[i];
    s = copy;
  }

  public void enqueue(Item item) { // add the item
    if (N == s.length)
      resize(2 * s.length);
    s[N++] = item;
  }

  public Item dequeue() { // remove and return a random item
    int r = StdRandom.uniform(N);
    Item item = s[r];
    s[r] = s[--N];
    s[N] = null;
    if (N > 0 && N == s.length / 4)
      resize(s.length / 2);
    return item;
  }

  public Item sample() { // return (but do not remove) a random item
    int r = StdRandom.uniform(N);
    return s[r];
  }

  @Override
  public Iterator<Item> iterator() { // return an independent iterator over
                                     // items in random order
    return new RandomArrayIterator();
  }

  private class RandomArrayIterator implements Iterator<Item> {
    private int[] indices;
    private int p;

    public RandomArrayIterator() {
      indices = new int[N];
      for (int i = 0; i < N; i++)
        indices[i] = i;
      StdRandom.shuffle(indices);
      p = 0;
    }

    @Override
    public boolean hasNext() {
      return p < N;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      return s[indices[p++]];
    }

  }

  public static void main(String[] args) { // unit testing
    RandomizedQueue<Integer> s = new RandomizedQueue<>();
    StdOut.println(s.size());
    s.enqueue(1);
    StdOut.println(s.dequeue());
    s.enqueue(2);
    s.enqueue(3);
    s.enqueue(4);
    for (int i : s)
      for (int j : s) {
        StdOut.print(i);
        StdOut.print(j);
        StdOut.println();
      }
  }
}
