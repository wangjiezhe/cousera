package stacksandqueues;

public class ResizingArrayQueueOfStrings {
  private String[] s;
  private int head = 0;
  private int tail = 0;

  public ResizingArrayQueueOfStrings() {
    s = new String[1];
  }

  private void resize(int capacity) {
    String[] copy = new String[capacity];
    for (int i = head; i < tail; i++)
      copy[i - head] = s[i % capacity];
    s = copy;
  }

  public boolean isEmpty() {
    return tail - head == 0;
  }

  public void enqueue(String item) {
    if (tail - head == s.length)
      resize(2 * s.length);
    s[tail++ % s.length] = item;
  }

  public String dequeue() {
    String item = s[head % s.length];
    s[head++ % s.length] = null;
    if (tail - head > 0 && tail - head == s.length / 4)
      resize(s.length / 4);
    return item;
  }
}
