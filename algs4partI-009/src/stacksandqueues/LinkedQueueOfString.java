package stacksandqueues;

public class LinkedQueueOfString {
  private Node first = null;
  private Node last = null;

  private class Node {
    private String item;
    private Node next;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public void enqueue(String item) {
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    if (isEmpty())
      first = last; // special case for empty queue
    else
      oldlast.next = last;
  }

  public String dequeue() {
    String item = first.item;
    first = first.next;
    if (isEmpty())
      last = null; // special case for empty queue
    return item;
  }
}
