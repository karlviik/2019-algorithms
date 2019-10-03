public class SinglySwapper {

  class Node {
    Integer data;
    Node next;
  }

  class SinglyLinkedList {
    Node first;
    int length;
  }

  // O(n)
  public void swapToFirst(SinglyLinkedList list, Node node) {
    Node first = list.first;
    Node preSwap = first;
    while (!preSwap.next.equals(node)) {
      preSwap = preSwap.next;
    }
    preSwap.next = list.first;
    Node second = first.next;
    first.next = node.next;
    node.next = second;
    list.first = node;
  }
}
