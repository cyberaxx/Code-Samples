import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Key>{
  // instance variables:
  private Key[] items; // maintains a collection of comparable keys indexed by external index [0 MAX-1]
  private int[] pq; // maintains external index for each heap position [1...N]->[0 MAX-1]
  private int[] qp; // maintains heap position of each external index  [0 MAX-1]->[1...N]

  private int N; // number of items in the IndexMinPQ
  private final int MAX; // maximum number of element to put in MinPQ specified by the client

  // Constructor: clinet should specify the max number of elements wanted to maintain in IndexMinPQ
  public IndexMinPQ(int max) {
    // check if max is non-negative value
    if(max<0) throw new IllegalArgumentException();

    // initialize the instance variables
    this.MAX=max;
    N=0;
    pq=new int[MAX+1];
    items=(Key[]) new Comparable[MAX]; // UGLY CASTING: java does not allow generic array creation 
    qp=new int[MAX];
    for(int i=0; i<MAX; i++) qp[i]=-1; // initialize heap positons of all indeces to be -1
  }

  // API:
  // NEW functionalities added to MinPQ:
  // insertion by external index:
  public void insert(int index, Key key){
    // OVERFLOW: indexe must be within [0 MAX-1] range:
    if(index<0 || index>=MAX) throw new IndexOutOfBoundsException("Failed to perform insert(index, key) because the given index was out of bounds.");

    // 1. increase the size of binary heap:
    N++;
    // 2. add the new item to the array of keys:
    items[index]=key;
    // 3. initially add the new item to the tail of heap
    qp[index]=N;
    pq[N]=index;
    // 4. swim it up to it rightful level of competence it the heap:
    swim(N);
  }

  // change the key at external index i:
  public void change(int index, Key key){
    // check if IndexMinPQ is not empty() or any element at the given index exist in the MinPQ using contains method
    if(isEmpty()) throw new NoSuchElementException("Failed to perform change(index,key) because the MinPQ instance is empty!");
    // check if the given index is within [0 MAX-1] range:
    if(index<0 || index>=MAX) throw new IndexOutOfBoundsException();
    // check if there exist any key associated with the given index
    if(!contains(index)) throw new NoSuchElementException("Failed to perform change(index,key) because there is no key in MinPQ instance associated with "+index+" index!");

    /*
    // 1. delete old key associated with the index from MinPQ:
    delete(index); // logN
    // 2. add the new key to the MinPQ
    insert(index, key); // logN
    */

    // Otherwise:
    // 1. update the key priority:
    items[index]=key;
    // 2. retrieve heap position associated with the given index:
    int i=qp[index];
    // 3. restore the heap-ordered condition:
    swim(i); // swim up the node i (with its new value) if possible (compare with its corresponding parent)
    sink(i); // sink down the node i (with its new value) if possible (compare with both its children if avaiable)
    // heap ordered condition restored

  } 

  // delete an item at external index i from the MinPQ (maintain the DS invariance -> min item always is at the head of queue)
  public void delete(int index) {
    // check if IndexMinPQ is not empty() or any element at the given index exist in the MinPQ using contains method:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform delete(index) because the MinPQ instance is empty!");
    // check if the given index is within [0 MAX-1] range:
    if(index<0 || index>=MAX) throw new IndexOutOfBoundsException();
    // check if there exist any key associated with the given index
    if(!contains(index)) throw new NoSuchElementException("Failed to perform delete(index) because there is no key in MinPQ instance associated with "+index+" index!");

    // remove the item at the given index from MinPQ:
    // 1. find its corresponding heap position:
    int i=qp[index];
    // 2. exchange its heap position with the last node in the heap (tail node):
    exch(i, N); 
    // 3. decrease the size of min heap:
    N--;

    // put node currently at heap position i at its rightful position: restore heap order condition
    swim(i); // swim it up if possible
    sink(i); // sink it down if applicable
    // heap ordered restored!

    // loitering prevention:
    items[index]=null;
    // remove it from binary heap indeces:
    qp[index]=-1;
  }

  // check if index i is associated with any key in MinPQ
  public boolean contains(int index){
    if(index<0 || index>=MAX) throw new IndexOutOfBoundsException();
    return qp[index]>0;
  }

  // return the external index of the min item:
  public int minIndex(){
    // check if MinPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform minIndex() operation because the MinPQ instance is empty!");
    return pq[1];
  }

  public Key min(){
    // check if MinPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform min() operation because the MinPQ instance is empty!");
    return items[minIndex()];
  }
  
  // delete the Min element in the MinPQ instance and return its external index
  public Key delMin() {
    // check if MinPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform min() operation because the MinPQ instance is empty!");
    
    // 0. copy the min value:
    Key min=min();
    // 1. extract the index of the MinPQ head:
    int index=minIndex();
    // 2. exchange the head of MinPQ with its tails:
    exch(1, N);
    // 3. reduce the number of elements in MinPQ
    N--;
    // 4. sink the new head to its proper level of comptence:
    sink(1);

    // 5. loitering prevention:
    items[index]=null;
    // pq[index]=-1;
    qp[index]=-1;

    return min;
  }

  // MinPQ functionalies:
  public int size(){return N;}
  public boolean isEmpty(){return N==0;}

  // Helper methods:
  // swim up newly added item to the tail of the MinPQ (during insertion) to its rightful level of competence: logN operation
  // swim based on heap position:
  private void swim(int k){
    // While not reaching the root of binary heap at index 1 AND its heap order condition is violated:
    while(k>1 && greater(k/2, k)) {
      // promote the key in binary heap to its parnet level
      exch(k, k/2);
      k=k/2;
    }
  }

  // sink down newly placed item as a HEAD of the MinPQ (during deletion) to its rightful level of competence: logN operation
  // sink based on heap position k
  private void sink(int k) {  
     // loop until node k has no child (there is no lower level in the binary heap):
     while(2*k<=N) {
       int j=2*k;

       // check if node k has a right child and right child is infact the better subordinate:
       if(j+1<=N && greater(j, j+1))  j++;

       // compare the candidate subordinate with the boss:
       // if boss is at its rightfull level of competence do nothing
       if(!greater(k, j))  break;
       
       // otherwise: replace the boss with the candiate suboridnate and sink the boss down to a lower level:
       exch(k, j);
       k=j;// demote the boss to one of its child's level
     }
  }

  // generic comparison: base on heap positions
  private boolean greater(int i, int j){return items[pq[i]].compareTo(items[pq[j]])>0;}
  // exchange method: based on heap positions
  private void exch(int i, int j) {
    // exchange indeces:
    int temp=pq[i];
    pq[i]=pq[j];
    pq[j]=temp;
   
    // exchange heap positions:
    qp[pq[i]]=i;
    qp[pq[j]]=j;
  }

  // iterator:
  @Override
  public Iterator<Key> iterator(){return new HeapIterator();}
  private class HeapIterator implements Iterator<Key>{
    // instance variable:
    private IndexMinPQ<Key> copy;
    // Constructor:
    public HeapIterator() {
      // instantiate from IndexMinPQ class:
      copy=new IndexMinPQ<Key>(IndexMinPQ.this.MAX);
      for(int i=1; i<=N; i++)  copy.insert(pq[i], items[pq[i]]);
    }
    @Override
    public boolean hasNext(){return !copy.isEmpty();}
    @Override
    public Key next() {
      if(!hasNext()) throw new NoSuchElementException("Failed to iterate over IndexMinPQ instance!");
      return copy.delMin(); // iterate over items in the IndexMinPQ in a sorted order
    }
    
  }
  // test client fot IndexMinPQ
  public static void main(String[] args) {
    IndexMinPQ<String> impq=new IndexMinPQ<String>(5);
    impq.insert(1, "abcd");
    impq.insert(0, "dfed");
    impq.insert(3, "fasdfed");
    impq.insert(2, "asd");
   
    System.out.println("Min is: "+impq.min());
    System.out.println("Size is: "+ impq.size());
    System.out.println("isEmpty()? "+ impq.isEmpty());

    System.out.println();
    System.out.println();
    
    impq.change(1, "zaszz");
    System.out.println("Min is: "+impq.min());
    System.out.println("Size is: "+ impq.size());
    System.out.println("isEmpty()? "+ impq.isEmpty());

    System.out.println();
    System.out.println();
    
    impq.delete(2);
    System.out.println("Min is: "+impq.min());
    System.out.println("Size is: "+ impq.size());
    System.out.println("isEmpty()? "+ impq.isEmpty());

    System.out.println();
    System.out.println();

    for(String item:impq) System.out.println(item);
    System.out.println();

    System.out.println();
    System.out.println();

    System.out.println("Min is: "+impq.min());
    System.out.println("delMin: "+impq.delMin());
    System.out.println("Min is: "+impq.min());
    System.out.println("Size is: "+ impq.size());
    System.out.println("isEmpty()? "+ impq.isEmpty());
  }
}
