import java.util.NoSuchElementException;

// 2.2. Implement an algorithm to find the nth to last element of a singly linked list.
public class TestSList<Item> {
  private Node front;
  private int size;

  private class Node {
    private Item data;
    private Node next;
  }

  public TestSList() {
    first = null;
    size=0;
  }

  public Item findNToLast(int n) {
    Node temp;
    int size = 0;
    for(temp=front; temp.next!=null; temp=temp.next) size++;
    if(n<0 || n>=size) throw new NoSuchElementException("The n is incorrect!");
    temp = first;
    for(int i=0; i<size-n; i++) temp = temp.next;
    return temp.data;
  }

  public Item findN(int n) {
    // check if n is within range:
    if (n<0 || front==null) return null;
    // otherwise
    Node pointer1 = front;
    Node pointer2 = front;
    // move pointer2 to index n-1 (n position ahead of p1):
    for(int i=0; i<n-1; i++) {
      if(pointer2 == null) return null;
      pointer2=pointer2.next;//advance the pointer
    }

    while(pointer2.next!=null){
      // advance the both pointers:
      pointer1 = pointer1.next;
      pointer2 = pointer2.next;
    }
    
    return pointer1;
  }
