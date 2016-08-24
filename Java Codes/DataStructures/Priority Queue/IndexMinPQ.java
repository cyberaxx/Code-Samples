

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
  public void insert(int index, Key key){}
  // change the key at external index i:
  public void change(int index, Key key){} 
  // check if index i is associated with any key in MinPQ
  public boolean contains(int index){return false;}
  // delete an item at external index i from the MinPQ (maintain the DS invariance -> min item always is at the head of queue)
  public Key delete(int i){return null;}
  // return the external index of the min item:
  public int minIndex(){return -1;}

  // MinPQ functionalies:
  public Key delMin() {return null;}
  public int size(){return N;}
  public boolean isEmpty(){return N==0;}
  public Key min(){return items[minIndex()];}

  // Helper methods:
  // swim up newly added item to the tail of the MinPQ (during insertion) to its rightful level of competence: logN operation
  private void swim(int i){}
  // sink down newly placed item as a HEAD of the MinPQ (during deletion) to its rightful level of competence: logN operation
  private void sink(int i){}

  // generic comparison:
  private boolean greater(int i, int j){return false;}
  // exchange method:
  private void exch(int i, int j){}
}

