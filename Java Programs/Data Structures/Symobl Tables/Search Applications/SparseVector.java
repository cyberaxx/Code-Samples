import java.util.*;

public class SparseVector{
  /* ADT structure: */
  private HashMap<Integer, Integer> vector; /* maintain a collection of non-zero elements of the input array */
  private int len; /* the length of the input array (used for validation of indexes */
  
  /* Constrcutor: */
  public SparseVector(Integer[] input) {
    /* instantiate a HashMap object: */
    this.vector=new HashMap<Integer, Integer>(); /* an empty hash Hash Table */
    this.len=input.length; /* number of elements in the input array */
    /* insert all non-zero elements to the hash table: */
    for(int i=0; i<len; i++) {
      if(input[i]!=0)
        vector.put(i, input[i]);
    } /* O(n) */
  }
  
  public SparseVector(int n) {
    /* instantiate a HashMap object: */
    this.vector=new HashMap<Integer, Integer>(); /* an empty hash Hash Table */
    this.len=n; /* number of elements in the input array */
  }  
  
  /* ADT Behaviors: */
  
  /* get a value of an array: */
  public int get(int i) {
    validate(i);
    /* check if i is in the table as a key: */
    if(vector.containsKey(i)) return vector.get(i); /* return the non-zero value associated to it */
    else return 0; /* 0 element if not in the table */
  }
  
  /* insert or update an array element: */
  public void set(int key, int value) {
    if(value==0)  {
      /* check if key was in the table, remove it */
      if(!vector.containsKey(key)) return ; /* Do nothing */
      /* otherwise: remove the key and value associated with it */
      vector.remove(key);
      return ;
    }
    
    /* otherwise: put the key-value pair: insert if new, modify the value if already in the table: */
    vector.put(key, value);
  }
  
  /* return the number non-zero elements: */
  public int size(){return vector.size();}
  
  /* dot product to another array: */
  public int dot(Integer[] that) {
    /* sanity check: */
    if(that==null) throw new NullPointerException();
    if(len!=that.length) throw new IllegalArgumentException("Dimension mismatch!");
    int product=0;
    for(int i=0; i<len; i++) {
      /* if input[i] is non-zero: */
      if(vector.containsKey(i)) product+=that[i]*vector.get(i);
      /* otherwise 0 contribution to the product */
    }
    
    /* result: */
    return product;
  }
  
  /* toString method override:*/
  @Override
  public String toString() {return vector.toString();} 
   
  /* helper methods: */
  private void validate(int index) {
    if(index<0 || index>=len) throw new IndexOutOfBoundsException("Invalid index!");
  }
  
  /* main method: */
  public static void main(String[] args) {
    Integer [] array=new Integer[]{1,3,2,4,0,0,0,0,9,0,0,8,0};
    SparseVector vector1=new SparseVector(array);
    SparseVector vector2=new SparseVector(5);
    System.out.println(vector1);
    System.out.println(Arrays.deepToString(array));
    System.out.println(vector1.dot(array));
    System.out.println(vector2);
  } 

}
