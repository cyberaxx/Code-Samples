/*
Min-max heap:
Design a data structure that supports min and max in constant time, insert, delete min, and delete max in logarithmic time
by putting the items in a single array of size N with the following properties:

The array represents a complete binary tree.
The key in a node at an even level is less than (or equal to) the keys in its subtree; 
the key in a node at an odd level is greater than (or equal to) the keys in its subtree.

Note that the maximum value is stored at the root and the minimum value is stored at one of the root's children.
*/
import java.util.NoSuchElementException;
import java.util.Iterator;
public class MinMaxPQ<Key extends Comparable<Key>> implements Iterable<Key>{

  // instance variables:
  private Key[] keys; // maintain a collection of comparable type keys
  private int N; // denotes the number of element in MinMaxPQ collection instance

  // constructor
  public MinMaxPQ(){
    // initialize instance variables:
    N=0;
    // keys is a resizable array of keys of generic type Key
    keys=(Key[]) new Comparable[2]; // UGLY CASTING: java does not allow GENERIC array creation
  }

  // API: insert(key), delMin(), delMax(), min(), max(), isEmpty(), size()
  public void insert(Key key) {
    // OVERFLOW: double the size of the array that represents heap
    if(N==keys.length-1)  resize(2*keys.length);
    // 1. add the newly inserted item to the tail of the MinMax heap
    keys[++N]=key;
    // 2. Restore the MinMax heap order condition by swim up the newly added node to its rightful level of comptenece:
    swim(N);
  } // preserve heap ordered condition (DS invariance)

  // Query methods:
  public Key delMax(){
    // UNDERFLOW:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform delMax()");
    
    // 1. copy over the max value:
    Key max=max();
    // 2. exchange the heap position of the head (max item) with the heap last node (tail node at position N)
    exch(1, N);
    // 3. decrease the size of the binary heap by 1:
    N--;
    // 4. sink the new root to its level of competence: restore MinMax heap order condition
    sink(1);

    // SHRINK the array if necessary: if array was only quarter full shrink it to its half size
    if(N>0 && N==(keys.length-1)/4) resize(keys.length/2);

    return max;
  }

  public Key delMin(){
    // UNDERFLOW:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform delMin()");
    
    // 1. retrieve the min element and heap position of the min element in MinMaxPQ instance:
    Key min=min();
    int index=minIndex();

    // 2. Exchange the min element with the tail of the heap:
    exch(index, N);

    // 3. decrease the size of binary MinMax heap:
    N--;

    // 4. restore MinMax heap order condition:
    swim(index);
    sink(index);
    // heap order condition restored

    // check if it is required to shrink the resizable array:
    if(N>0 && N==(keys.length-1)/4)  resize(keys.length/2); // shrink the array to half of its original size

    return min;
  }

  public Key min(){
    // UNDERFLOW:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform min()");
    return keys[minIndex()];
  }

  // sneek peek at the head of MinMaxPQ instance:
  public Key max(){
    // UNDERFLOW:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform max()");
    return keys[1]; // return the head of MinMaxPQ instance at heap position 1
  }

  public int size(){return N;}
  public boolean isEmpty(){return N==0;}

  // Helper methods:
  private void resize(int capacity){
    Key[] temp=(Key[]) new Comparable[capacity]; // UGLY CASTING: java does not allow GENERIC array creation
    for(int i=1; i<=N; i++)  temp[i]=keys[i];
    keys=temp;
  }

  private void swim(int k){}
  private void sink(int k){}

  // retrieve the heap position of the min item
  private int minIndex() {
    if(isEmpty()) throw new NoSuchElementException("Failed to perform minIndex()");

    // if the MinMaxPQ instance has only one item in it: min and max are the same:
    if(N==1)  return 1;
    // otherwise:
    if(less(2,3))  return 2;
    return 3;
  }

  // for a given position k in the binary heap find out it corresponding level:
  private int level(int k){
    // we need to take a log base 2 of k:
    double level=(Math.log10(k)/Math.log10(2))+1;
    return (int) level; // UGLY CASTING
  }

  // generic comparison: given their heap position
  private boolean less(int i, int j){return keys[i].compareTo(keys[j])<0;}
  private boolean greater(int i, int j){return keys[i].compareTo(keys[j])>0;}

  // exchange items in the heap given their heap position:
  private void exch(int i, int j){
    Key temp=keys[i];
    keys[i]=keys[j];
    keys[j]=temp;
  }

  /* MinMaxPQ class implements java Iterable interface by
     overriding its abstract iterator() method:
  */
  @Override
  // iterate over items in MinMaxPQ instance in no particular order:
  public Iterator<Key> iterator(){return new ListIterator();}

  /* ListIterator class implements java Iterator interface by
     overriding its abstract hasNext() and next() methods:
  */
  private class ListIterator implements Iterator<Key>{
    private int current;
    public ListIterator(){current=N;}// initialize the current pointer
    @Override
    public boolean hasNext(){return current>0;}
    public Key next(){
      if(!hasNext()) throw new NoSuchElementException("Failed to iterate over MinMaxPQ instance!");
      return keys[current--];
    } 
  }
}
