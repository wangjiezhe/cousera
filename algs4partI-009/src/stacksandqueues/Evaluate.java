package stacksandqueues;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Dijkstra's two-stack algorithm.
 */
public class Evaluate {

  public static void main(String[] args) {
    Stack<String> ops = new Stack<String>();
    Stack<Double> vals = new Stack<Double>();

    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      // StdOut.println(s);
      if (s.equals("(")) {
      } else if (s.equals("+"))
        ops.push(s);
      else if (s.equals("*"))
        ops.push(s);
      else if (s.equals(")")) {
        String op = ops.pop();
        if (op.equals("+"))
          vals.push(vals.pop() + vals.pop());
        else if (op.equals("*"))
          vals.push(vals.pop() * vals.pop());
      } else
        vals.push(Double.parseDouble(s));
    }

    StdOut.println(vals.pop());
  }

}
