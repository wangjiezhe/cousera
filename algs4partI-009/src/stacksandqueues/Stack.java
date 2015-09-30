package stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item> implements IStack<Item> {
  private Node first = null;

  private class Node {
    private Item item;
    private Node next;
  }

  @Override
  public boolean isEmpty() {
    return first == null;
  }

  @Override
  public void push(Item item) {
    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
  }

  @Override
  public Item pop() {
    Item item = first.item;
    first = first.next;
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
