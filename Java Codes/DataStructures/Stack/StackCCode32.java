/*
How would you design a stack which, in addition to push and pop, also has a function
min which returns the minimum element? Push, pop and min should all operate in
O(1) time.
__________
*/

public class StackCCode32<Item extends Comparable<Item>> {
  private Node top;
  private int size;

  private class Node {
    private Item data;
    private Item min;
    private Item max;
    private Node next;
    
  }

  public StackCCode32() {
    top=null;
    size=0;
  }

  public void push(Item item) {

    if(isEmpty()) {
      top = new Node();
      top.data = item;
      top.min = item;
      top.max = item;
      top.next = null;
      size++;
    }

    else {

      Node oldTop = top;
      Item oldMin = top.min;
      Item oldMax = top.max;

      top = new Node();
      top.data = item;
      top.next = oldTop;
      size++;

      // update min and max:
      if(item.compareTo(oldMin) < 0) top.min = item;
      else top.min = oldMin;

      if(item.compareTo(oldMax) > 0) top.max = item;
      else top.max = oldMax;
    }
  }
  
  public Item pop() {
    // check if stack is empty:
    if(isEmpty()) return null;
    Item item = top.data;
    top = top.next;
    size--;
    return item;

  }

  public Item min() {
    if(isEmpty()) return null;  
    return top.min;
  }

  public Item max() {
      if(isEmpty()) return null;  
      return top.max;
  }

  public boolean isEmpty(){return top==null;}
  public int size(){return size;}


  public static void main(String [] args) {
    StackCCode32<Integer> st = new StackCCode32<Integer>();
    System.out.println(st.size());
    System.out.println(st.isEmpty());
    System.out.println(st.min()); 
    System.out.println(st.max());

    System.out.println();

    st.push(0);
    st.push(8);
    st.push(1);
    st.push(2);
    st.push(-3);
    st.push(1);
    st.push(4);
    st.push(4);
    st.push(6);
    System.out.println(st.size());
    System.out.println(st.isEmpty());
    System.out.println(st.min()); 
    System.out.println(st.max());

    System.out.println();


    System.out.println("Poped:" + st.pop());
    System.out.println(st.size());
    System.out.println(st.isEmpty());
    System.out.println(st.min()); 
    System.out.println(st.max());

    System.out.println();


    System.out.println("Poped:" + st.pop());
    System.out.println("Poped:" + st.pop());
    System.out.println("Poped:" + st.pop());
    System.out.println("Poped:" + st.pop());
    System.out.println(st.size());
    System.out.println(st.isEmpty());
    System.out.println(st.min()); 
    System.out.println(st.max());
  }
}
