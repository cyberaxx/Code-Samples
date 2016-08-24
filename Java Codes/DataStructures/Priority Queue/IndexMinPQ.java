import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> {
  // instance variables:
  // Parallel array to maintain keys, indeces, heap indeces, number of elements in the heap and max number of elements:
  private Key[] items; // maintain a collection of comparable keys indexed by external index [0 MAX-1]
  private int[] pq; // 1-based indeces of EXTENALLY INDEXED item in the Min oriented priority queue array representation [0  MAX-1] => pq[0 MAX-1]:[1   N]
  private int[] qp; // external indeces for a given 1-BASED index in min orieted priortiy queue (1-base array representation) [1  N] => qp[1  N]:[0  Max-1]

  private int N; // number of items in the MinPQ
  private final int MAX; // maximum number of element to put in MinPQ specified by the client

  // Constructor: clinet should specify the max number of elements wanted to maintain in IndexMinPQ
  public IndexMinPQ(int max) {
    // initialize the instance variables
    this.MAX=max;
    N=0;
    items=(Key[]) new Comparable[MAX]; // UGLY CASTING: java does not allow generic array creation 
    pq=new int[MAX]; // indeces of the 1-based array represntation of the Min oriented PQ (represented by a binary heap DS)
    qp=new int[MAX+1]; // external indeces [0 MAX-1] corresponding to each MinPQ interal index [1  N]: qp[pq[i]]=i;
    for(int i=1; i<=MAX; i++)  qp[i]=-1; // initialize them -1
  }

  // API:

  // NEW functionalities added to MinPQ:
  // insertion by external index:
  public void insert(int index, Key key){
    // OVERFLOW: indexe must be within [0 MAX-1] range:
    if(index<0 || index>=MAX) throw new IndexOutOfBoundsException("Failed to perform insert(index, key) because the given index was out of bounds.");
    // if the index is valid:
    // 1. increase the number of elements in the MinPQ:
    N++;
    // 2. add the key to the collection keys index by EXTERNAL indeces:
    items[index]=key;
    // find out index of the new item in MinPQ binary heap [1  N]
    swim(index);
  }

  // change the key at external index i:
  public void change(int index, Key key){
    // check if IndexMinPQ is not empty() or any element at the given index exist in the MinPQ using contains method
    if(isEmpty()) throw new NoSuchElementException("Failed to perform change(index,key) because the MinPQ instance is empty!");
    if(!contains(index)) throw new NoSuchElementException("Failed to perform change(index,key) because there is no key in MinPQ instance associated with "+index+" index!");
    // Otherwise:
    // 1. delete old key associated with the index from MinPQ:
    delete(index);
    // 2. add the new key to the MinPQ
    insert(index, key);
  } 

  // delete an item at external index i from the MinPQ (maintain the DS invariance -> min item always is at the head of queue)
  public void delete(int index) {
    // check if IndexMinPQ is not empty() or any element at the given index exist in the MinPQ using contains method:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform delete(index) because the MinPQ instance is empty!");
    if(!contains(index)) throw new NoSuchElementException("Failed to perform delete(index) because there is no key in MinPQ instance associated with "+index+" index!");

    // remove the item at the given index from MinPQ
    exch(index, qp[N]); // swap it with the last item in MinPQ
    // reduce the number of elements in MinPQ:
    N--;
    // sink the item to its rightful level of competence:
    sink(index);

    // loitering prevention:
    items[index]=null;
    // remove it from binary heap indeces:
    pq[index]=-1;
  }

  // check if index i is associated with any key in MinPQ
  public boolean contains(int index){return pq[index]>0;}

  // return the external index of the min item:
  public int minIndex(){
    return qp[1];
  }
  
  // delete the Min element in the MinPQ instance and return its external index
  public int delMin() {
    // check if MinPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform min() operation because the MinPQ instance is empty!");
    
    // 1. extract the index of the MinPQ head:
    int index=qp[1]; // index of the head of the MinPQ instance
    
    // 2. exchange the MinPQ head with its tails:
    int head=qp[N];
    exch(index, head);
    // 3. reduce the number of element is MinPQ
    N--;

    // 4. sink the new head to its proper level of comptence:
    sink(head);

    // 5. loitering prevention:
    items[index]=null;
    pq[index]=-1;

    return index;
  }

  // MinPQ functionalies:
  public int size(){return N;}
  public boolean isEmpty(){return N==0;}
  public Key min(){
    // check if MinPQ instance is not empty:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform min() operation because the MinPQ instance is empty!");
    return items[minIndex()];
  }

  // Helper methods:
  // swim up newly added item to the tail of the MinPQ (during insertion) to its rightful level of competence: logN operation
  private void swim(int index){
    // put the EXTERNAL index in the qp array associate with the pq indeces:
    qp[pq[index]]=index;
  }

  // sink down newly placed item as a HEAD of the MinPQ (during deletion) to its rightful level of competence: logN operation
  private void sink(int index) {
  }

  // generic comparison:
  private boolean greater(Comparable v, Comparable w){return v.compareTo(w)>0;}
  // exchange method: exchange happens withing the pq array:
  private void exch(int i, int j) {
    // index of the item in the MinPQ instance
    int temp=pq[i];
    pq[i]=pq[j];
    pq[j]=temp;
  }
}

