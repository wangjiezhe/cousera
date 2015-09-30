package stacksandqueues;

public interface IQueue<Item> extends Iterable<Item> {
  boolean isEmpty();

  void enqueue(Item item);

  Item dequeue();
}
