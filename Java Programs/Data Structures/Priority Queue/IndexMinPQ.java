import java.util.Arrays;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> {
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
  public boolean contains(int index){
    if(index<0 || index>=MAX) throw new IndexOutOfBoundsException();
    return qp[index]!=-1;
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
  private boolean greater(int i, int j){return items[pq[i]].compareTo(items[pq[i]])>0;}
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

  // test client fot IndexMinPQ
  public static void main(String[] args) {
    IndexMinPQ<String> impq=new IndexMinPQ<String>(5);
    impq.insert(1, "abcd");
    impq.insert(0, "dfed");
    impq.insert(3, "fasdfed");
    System.out.println("Min is: "+impq.min());
    System.out.println("Size is: "+ impq.size());
    System.out.println("isEmpt()? "+ impq.isEmpty());

    System.out.println();
    System.out.println();
    
    impq.change(1, "zaszz");
    System.out.println("Min is: "+impq.min());
    System.out.println("Size is: "+ impq.size());
    System.out.println("isEmpt()? "+ impq.isEmpty());

    System.out.println();
    System.out.println();
    
    impq.delete(2);
    System.out.println("Min is: "+impq.min());
    System.out.println("Size is: "+ impq.size());
    System.out.println("isEmpt()? "+ impq.isEmpty());

  }
}
