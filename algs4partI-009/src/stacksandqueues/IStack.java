package stacksandqueues;

public interface IStack<Item> extends Iterable<Item> {
  boolean isEmpty();

  void push(Item item);

  Item pop();
}
