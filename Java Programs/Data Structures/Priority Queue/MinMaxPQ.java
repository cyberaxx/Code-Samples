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

public class MinMaxPQ<Key extends Comparable<Key>> {

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
  public void insert(Key key) {} // preserve heap ordered condition (DS invariance)
  public Key delMin(){return null;}
  public Key delMax(){return null;}
  public Key min(){return null;}
  public Key max(){return null;}
  public int size(){return N;}
  public boolean isEmpty(){return N==0;}

  // Helper methods:
  private void resize(int capacity){}
  private void swim(int k){}
  private void sink(int k){}

  // for a given position k in the binary heap find out it corresponding level:
  private int level(int k){
    // we need to take a log base 2 of k:
    double level=Math.log10(k)/Math.log10(2);
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
}
