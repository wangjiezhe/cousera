package stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements IQueue<Item> {
  private Node first = null;
  private Node last = null;

  private class Node {
    private Item item;
    private Node next;
  }

  @Override
  public boolean isEmpty() {
    return first == null;
  }

  @Override
  public void enqueue(Item item) {
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    if (isEmpty())
      first = last; // special case for empty queue
    else
      oldlast.next = last;
  }

  @Override
  public Item dequeue() {
    Item item = first.item;
    first = first.next;
    if (isEmpty())
      last = null; // special case for empty queue
    return item;
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
