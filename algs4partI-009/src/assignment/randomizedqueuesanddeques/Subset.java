package assignment.randomizedqueuesanddeques;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> s = new RandomizedQueue<>();
    while (!StdIn.isEmpty()) {
      String str = StdIn.readString();
      s.enqueue(str);
    }
    int i = 0;
    for (String str : s) {
      StdOut.println(str);
      if (++i == k)
        break;
    }
  }
}
