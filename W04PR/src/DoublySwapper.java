public class DoublySwapper {

  class Node {
    Integer data;
    Node next;
    Node prev;
  }

  class DoublyLinkedList  {
    Node first;
    Node last;
    int length;
  }

  // O(1)
  public void swapToFirst(DoublyLinkedList list, Node node) {
    if (list.length == 1) {
      return;
    }
    if (node.next == null) {
      Node second = list.first.next;
      Node preSwap = node.prev;
      Node first = list.first;
      node.prev = null;
      node.next = second;
      preSwap.next = first;
      second.prev = node;
      first.prev = preSwap;
    }
    Node second = list.first.next;
    Node preSwap = node.prev;
    Node postSwap = node.next;
    Node first = list.first;
    node.prev = null;
    node.next = second;
    postSwap.prev = first;
    preSwap.next = first;
    second.prev = node;
    first.next = postSwap;
    first.prev = preSwap;
  }
}
