import java.util.Iterator;
import java.util.NoSuchElementException; 


public class AQueue<Item> implements Iterable<Item> {
  // instance varibales:
  private int first; // keeps track of the beginning of the Q
  private int last; // keeps track of the end of the the Q
  private Item[] items; // an array ofgeneric type Item
  private int size; // keeps track of number of elements in the Q instance

  // Constructor
  public AQueue() {
    // Cannot instantiate an array of generics in Java:
    // UGLY CASTING:
    items = (Item[]) new Object[2];
    first=0;
    last=0;
    size=0;
  }

  // API: instance methods:
  public void enque(Item item) {
    // 1. first check if the items array is not full already
    // if full, double the size the array:
    if(isFull()) resize(2*items.length); // Double the size
    // 2. insert the item into an array
    items[last] = item;
    // 3. advance the last index pointer
    //    in a circular fashion
    last = (last + 1)%items.length;
    // 4. update the size field
    size++;
  }

  public Item deque() {
    // 1. first we have to check if Queue is not empty already:
    if(isEmpty()) throw new NoSuchElementException("Queue is already empty!");
    // 2. copy the value at items[first] to get returned
    Item item = items[first];
    // 3. Avoid loitering by nullifying that portion of the array
    items[first] = null;
    // 4. advance the first pointer index (circular)
    first = (first + 1)%items.length;
    // 5. update the size:
    size--;
    // 6. check if array is only 1/4 full, then shrink it to half:
    if (size > 0 && size == items.length/4)  resize(items.length/2);
    // finallly, return the item:
    return item;
  }

  public boolean isEmpty() { return size==0; }
  public int size() { return size; }

  // helper methods
  private void resize(int newSize){
    // 1. create a new array of newSize and generic type Item
    // UGLY CASTING AGAIN!
    Item [] temp = (Item[]) new Object[newSize];

    // 2. copy over all items, from the items array into temp array
    // from first index to last index in a circular array:
    for(int i=0; i<size; i++) temp[i] = items[(first+i)%(items.length)];

    // 3. update the items reference:
    items = temp;

    // 4. update the first and last inpdex pointers:
    first=0;
    last=size; 
  }

  private boolean isFull(){ return size==items.length; }

  // AQueue has to implement java Iterable<Item> interface
  // by overriding its abstract method iterator():
  @Override
  public Iterator<Item> iterator(){ return new ArrayIterator(); }

  // helper inner class:
  // ArrayIterator is a concrete class that implements java 
  // Iterator<Item> interface by overriding its abstract methods
  // next() and hasNext()
  private class ArrayIterator implements Iterator<Item> {
    // instance field(s):
    private int current; // index pointer to the current element at the time
    public ArrayIterator() { current=first; }
    @Override 
    public boolean hasNext() { return current!=last; }
    @Override
    public Item next() {
      // check if Q current has not reached the last element in Q:
      if(!hasNext()) throw new NoSuchElementException("No more element left!");
      Item item = items[current];
      current = (current+1)%items.length; // advance the current pointer circularly 
      return item;
    }
  }
}
