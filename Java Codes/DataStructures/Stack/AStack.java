import java.util.Iterator;
import java.util.NoSuchElementException;

public class AStack<Item> implements Iterable<Item> {
  /*
    implement a Collection data type, structure items
    within a collection as a stack, using re-sizing array
    Array implementation challenges:
      1. Push: Array is a static data type, in order to maintain a dynamic DS with array
         we have to use resizing (avoid overflowing by doubling)
      2. Pop: (Avoid loitering) Have to nullify the elements of the array that are not being used by the stack instance, anymore.
      3. Pop: Underflowing, and shrinking
  */

  // declare an array of generic type Item, to maintain 
  // items in our collection of items: 
  private Item[] items;
  private int n;

  // n denotes: 
  // 1. number of elements in the stack 
  // 2. index of the next element to be inserted into the stack
  // stack top item is the item at the n-1
  
  // Constructor:
  public AStack() {
    // In java we cannot instantiate an array of generics
    // So, we to use UGLY casting: (Item []) Object[]
    // Casting is ugly because interferes with java complier type checking
    items = (Item[]) new Object[2]; 
    n = 0;
  }

  // API: instanct methods
  public void push (Item item) {
    if (isFull()) resize(2*items.length);
    items[n++] = item; // push the item and update n
  }

  public Item pop() { 
    if(isEmpty()) throw new NoSuchElementException("Stack Underflow!");
    
    // take the value of the item on the top of the stack instance:
    Item item = items[n-1]; // top resides at items[n-1]

    // Extra caution to avoid loitering:
    items[n-1] = null;
    // update n:
    n--;

    // check if array is quarter full, then shrink to half of its size:
    if (n > 0 && n == (items.length)/4)  resize(items.length/2);

    return item;
  }

  public boolean isEmpty() { return n==0; }
  public int size() { return n; }
  public Item peek() { 
    if(isEmpty()) throw new NoSuchElementException("Stack is Empty!");
    // current top, is the element at position n-1 in the items array
    return items[n-1];
  } 

  // private helper methods
  private void resize(int newSize) {
    // create a new array with of size newSize, and type Item (Ugly casting):
    Item[] temp = (Item[]) new Object[newSize];
    // copy over items from the items array from index 0 to n-1,  into the temporary array:
    // for(int i=0; i<n; i++) temp[i] = items[i];
    System.arraycopy(items, 0, temp, 0, n);
    // items has to maintain a reference to the new resized array:
    items = temp;
  } // O(n): creating a new array of newSize and copyinng stuff over

  private boolean isFull(){ return n==items.length; }

  // implement java iterable interface by
  // overriding its iterator() method:
  @Override
  public Iterator<Item> iterator() { return new ArrayIterator(); }

  // ArrayItrator is a concrete inner class that implements 
  // java Iterator<Item> interface by overriding its mehods:
  // next() and hasNext()
  private class ArrayIterator implements Iterator<Item> {
    private int currentIndex;
    // constructor:
    public ArrayIterator() { currentIndex = n; }
    @Override
    public boolean hasNext() { return currentIndex>0; }
    @Override
    public Item next() {
      // first check if there exist the next item:
      if(!hasNext()) throw new NoSuchElementException("Empty Stack!");
      Item item = items[--currentIndex];
      return item;
    }
  }

}
