package stacksandqueues;

public interface IBag<Item> extends Iterable<Item> {
  boolean isEmpty();

  int size();

  void add(Item item);
}
