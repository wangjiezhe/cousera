package stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
  private Node first = null;
  private int N = 0;

  private class Node {
    private Item item;
    private Node next;
  }

  public void add(Item item) {
    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
    N++;
  }

  public int size() {
    return N;
  }

  @Override
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }
}
