import java.util.Collection; // java public interface (Collection data type specifications)
import java.util.Deque; // java public interface (Deque data type specifictations)
import java.util.ArrayDeque; // java implementation of Deque interface using resizable array
import java.util.Collections; // a class with a set of static methods operating on Collections 
import java.util.NoSuchElementException;

/* Queue with two stacks:
 Implement a queue with two stacks so that 
 each queue operations takes a constant amortized 
 number of stack operations. */
public class Queack<Item> {
  // instance fields of Queack instance
  // 1. Two stack that maintain element of Queack Collection:
  // they must have the same TYPE PARAMETER as the Queack
  private Deque<Item> inStack;
  private Deque<Item> outStack;
  private int size; 

  public Queack(){
    // initialize all the instance fields:
    inStack = new ArrayDeque<Item>(); // Array implementation of the Deque to mimic Stack
    outStack = new ArrayDeque<Item>(); // Array implementation of the Deque to mimic Stack
    size = 0;
  }

  // API: instance methods:
  public void enqueue(Item item){
    // always push to the inStack:
    if(!inStack.offerLast(item)) throw new NoSuchElementException("Failed to insert into the Stack!");
    // take a copy of the top item and remove it
    size++;
  }

  public Item dequeue() {
    // check if inStack is not empty: (Underflow) 
    if(inStack.isEmpty()) throw new NoSuchElementException("Queack is already empty!");

    Item item;

    // uses the aux stack to maintain collection of element in
    // FIFO order:

    // 1. reverse the order of the item in the inStack using outStack
    while(!inStack.isEmpty()) {
      if(!outStack.offerLast(inStack.pollLast())) throw new RuntimeException("Failed to insert into the aux Stack!");
    }

    // 2. pop the item from the outStack
    if((item = outStack.pollLast()) == null) throw new RuntimeException("Failed to insert to the Queack!");

    // b. push items from the outStack and push them back into the inStack
    while(!outStack.isEmpty()) {
      if(!inStack.offerLast(outStack.pollLast())) throw new RuntimeException("Failed to insert into the Queack!");
    }      

    // update size:
    size--;
    return item; 
  }

  public int size () { return size;}
  public boolean isEmpty() { return inStack.isEmpty();}

}
