package assignment.randomizedqueuesanddeques;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
  private Node first;
  private Node last;
  private int N;

  private class Node {
    private Item item;
    private Node prev = null;
    private Node next = null;
  }

  public Deque() { // construct an empty deque
    first = null;
    last = first;
    N = 0;
  }

  public boolean isEmpty() { // is the deque empty?
    return first == null || last == null;
  }

  public int size() { // return the number of items on the deque
    return N;
  }

  public void addFirst(Item item) { // add the item to the front
    if (item == null)
      throw new NullPointerException();
    // Node oldfirst = first;
    // first = new Node();
    // first.item = item;
    // first.next = oldfirst;
    // if (isEmpty())
    // last = first;
    // else
    // oldfirst.prev = first;
    Node node = new Node();
    node.item = item;
    node.next = first;
    if (isEmpty()) {
      first = node;
      last = first;
    } else {
      first.prev = node;
      first = node;
    }
    N++;
  }

  public void addLast(Item item) { // add the item to the end
    if (item == null)
      throw new NullPointerException();
    // Node oldlast = last;
    // last = new Node();
    // last.item = item;
    // last.prev = oldlast;
    // if (isEmpty())
    // first = last;
    // else
    // oldlast.next = last;
    Node node = new Node();
    node.item = item;
    node.prev = last;
    if (isEmpty()) {
      last = node;
      first = last;
    } else {
      last.next = node;
      last = node;
    }
    N++;
  }

  public Item removeFirst() { // remove and return the item from the front
    if (isEmpty())
      throw new NoSuchElementException();
    Item item = first.item;
    first = first.next;
    if (isEmpty())
      last = null;
    else
      first.prev = null;
    N--;
    return item;
  }

  public Item removeLast() { // remove and return the item from the end
    if (isEmpty())
      throw new NoSuchElementException();
    Item item = last.item;
    last = last.prev;
    if (isEmpty())
      first = null;
    else
      last.next = null;
    N--;
    return item;
  }

  @Override
  public Iterator<Item> iterator() { // return an iterator over items in order
                                     // from front to end
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node curr = first;

    @Override
    public boolean hasNext() {
      return curr != null;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      Item item = curr.item;
      curr = curr.next;
      return item;
    }

  }

  public static void main(String[] args) { // unit testing
    Deque<Integer> s = new Deque<>();
    StdOut.println(s.size());
    s.addFirst(1);
    StdOut.println(s.size());
    StdOut.println(s.removeFirst());
    // StdOut.println(s.removeFirst());
    s.addLast(2);
    StdOut.println(s.removeLast());
    // StdOut.println(s.removeLast());
    s.addFirst(3);
    s.addFirst(4);
    s.addLast(5);
    StdOut.println(s.size());
    for (int i : s)
      for (int j : s) {
        StdOut.print(i);
        StdOut.print(j);
        StdOut.println();
      }
    StdOut.println(s.removeFirst());
    StdOut.println(s.removeLast());
    StdOut.println(s.size());
  }
}
