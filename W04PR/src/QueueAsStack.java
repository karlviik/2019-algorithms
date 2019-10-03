import java.util.Stack;

public class QueueAsStack {

  private Stack<Integer> mainStack;
  private Stack<Integer> helperStack;

  public QueueAsStack() {
    mainStack = new Stack<>();
    helperStack = new Stack<>();
  }

  // complexity O(1)
  public void enqueue(Integer element) {
    mainStack.push(element);
  }

  // complexity O(n)
  public Integer dequeue() {
    while (!mainStack.isEmpty()) {
      helperStack.push(mainStack.pop());
    }
    Integer returnElement = helperStack.pop();
    while (!helperStack.isEmpty()) {
      mainStack.push(helperStack.pop());
    }
    return returnElement;
  }
}
