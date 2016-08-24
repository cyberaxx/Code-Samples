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

    // 3. find out index of the new item in MinPQ binary heap [1  N] (update the pq array)
    swim(index); // restore heap order condition

    // 4. update the qp array by putting the EXTERNAL index in the qp array associate with the pq indeces:
    qp[pq[index]]=index;
  }

  // change the key at external index i:
  public void change(int index, Key key){
    // check if IndexMinPQ is not empty() or any element at the given index exist in the MinPQ using contains method
    if(isEmpty()) throw new NoSuchElementException("Failed to perform change(index,key) because the MinPQ instance is empty!");
    if(!contains(index)) throw new NoSuchElementException("Failed to perform change(index,key) because there is no key in MinPQ instance associated with "+index+" index!");
    // Otherwise:
    // 1. delete old key associated with the index from MinPQ:
    delete(index); // logN
    // 2. add the new key to the MinPQ
    insert(index, key); // logN
  } 

  // delete an item at external index i from the MinPQ (maintain the DS invariance -> min item always is at the head of queue)
  public void delete(int index) {
    // check if IndexMinPQ is not empty() or any element at the given index exist in the MinPQ using contains method:
    if(isEmpty()) throw new NoSuchElementException("Failed to perform delete(index) because the MinPQ instance is empty!");
    if(!contains(index)) throw new NoSuchElementException("Failed to perform delete(index) because there is no key in MinPQ instance associated with "+index+" index!");

    // remove the item at the given index from MinPQ
    int i=qp[N]; // i is the external index of the last item in MinPQ
    exch(index, i); // swap the item at the given index with the last item in MinPQ
    // reduce the number of elements in MinPQ:
    N--;

    // sink the new item to its rightful level of competence in the MinPQ instance:
    sink(i); // restore heap order condition

    // loitering prevention:
    items[index]=null;
    // remove it from binary heap indeces:
    pq[index]=-1;
    // update the qp for the i
    qp[pq[i]]=i;
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
    qp[pq[head]]=head; // update the qp array

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
    /* NOTE: 
       1. index denotes the EXTERNAL index of the given key:
       2. pq[index]/2 denotes the parent index in the min oriented binary heap
       3-1. pq[index]*2 denotes the left child index in the min oriented binary heap
       3-2. pq[index]*2+1 denotes the right child in the min oriented binary heap
    */

    // While not reaching the root of binary heap at index 1 AND its heap order condition is violated:
    while(pq[index]<1 && greater(items[qp[(pq[index])/2]], items[index])) {
      // promote the key in binary heap:
      int parentIndex=qp[(pq[index])/2];
      exch(index, parentIndex); // exchange values in pq array
      index=parentIndex;
    }
  }

  // sink down newly placed item as a HEAD of the MinPQ (during deletion) to its rightful level of competence: logN operation
  private void sink(int index) {
    /* NOTE:
       1. index denote an EXTERNAL index
       2. N specifies the binary heap index boundary
       3-0. pq[index]/2 denotes the parent index in the min oriented binary heap (MinPQ instance)
       3-1. pq[index]*2 denotes the left child index in the min oriented binary heap (MinPQ instance)
       3-2. pq[index]*2+1 denotes the right child in the min oriented binary heap (MinPQ instance)
    */
    
     // sink down:
     while(2*pq[index]<=N) {
       int left=2*pq[index];
       int right=2*pq[index];
       int candidate=left;// initially candidate the left child to replace the new boss

       // check if it has a right child and right child is inface the better subordinate:
       if(right<=N && greater(items[qp[left]], items[qp[left]]))  candidate=right;

       // compare the candidate subordinate with the boss:
       // if boss is at its rightfull level of competence do nothing
       if(greater(items[qp[candidate]], items[index]))  break;
       
       // otherwise: replace the boss with the candiate suboridnate and sink the boss down to a lower level:
       exch(index, qp[candidate]);
       index=qp[candidate];
     }
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
