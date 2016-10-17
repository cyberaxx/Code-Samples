import java.io.File;
import java.util.*;

public class QuickRadix{
  public static void main(String[] args) {
    File file=new File("shells.txt");

    try{

    	Scanner scanner=new Scanner(file);
    	List<String> list=new ArrayList<String>();

    	while(scanner.hasNext())
    		list.add(scanner.next());

    	String[] keys=list.toArray(new String[0]);
    	sort(keys);
    	
      for(int i=0; i<keys.length; i++)
    	  System.out.println(keys[i]);
      System.out.println();
    	
    	scanner.close();
    } catch(Exception e){System.out.println("Failed to read from the input file: "+ e.getMessage());}   
  
  }
  
	/* Sort strings of variable length using 3-way Quick Radix Sort recursively*/ 
	public static void sort(String[] keys) {
	 /* call the recursive helper function */
	 sort(keys,0, keys.length-1, 0);
	}
	
	/* recursive helper method that sorts array of strings with variable lengths from the left most digit (MST) index 0 to index right most digits */
	private static void sort(String[] keys, int lo, int hi, int d) {
	  // termination condition:
	  if(hi<=lo) return ;
	  
	  // use 3-way partitioning
	  int lt=lo; // always points to the pivot element
	  int gt=hi;
	  int i=lo+1; // moving pointer on the sub-array
	  while(i<=gt) {
	    // 1. if the given key at digit d is less than the pivot, move the key to the left of the pivot, advance the pointer and the pivot
	    if(charAt(keys[i],d) < charAt(keys[lt],d)) exch(keys, i++, lt++);
 	    // 2. if the given key at digit d is greater than the pivot, move the key to the end of sub-array, advance the gt pointer
 	    if(charAt(keys[i],d) > charAt(keys[lt],d)) exch(keys, i, gt--);
 	    // 3. if equal, advance the i pointer
 	    else i++;
	  }
	  
	  // sort 3-sub-arrays recursively:
	  sort(keys, lo, lt-1, d);
	  if(charAt(keys[lt], d+1) >=0) sort(keys, lt, gt, d+1);
	  sort(keys, gt+1, hi, d);

	}
	
	/* overload of charAt behaviour of the String library such that return -1 once d is out of bound, to specify the end of string */
	private static int charAt(String key, int d) {
		if(d<key.length()) return key.charAt(d);
		else return -1;
  }	
  
  /* swap two keys in the keys array */
  private static void exch(String[] keys, int i, int j) {
    String temp=keys[i];
    keys[i]=keys[j];
    keys[j]=temp;
  }

}
