import java.io.File;
import java.util.*;

public class MSD {
  public static void main(String[] args) {
    File file=new File("shells.txt");

    try{

    	Scanner scanner=new Scanner(file);
    	List<String> list=new ArrayList<String>();

    	while(scanner.hasNext())
    		list.add(scanner.next());

    	String[] keys=list.toArray(new String[0]);
    	msd(keys);
    	
      for(int i=0; i<keys.length; i++)
    	  System.out.println(keys[i]);
      System.out.println();
    	
    	scanner.close();
    } catch(Exception e){System.out.println("Failed to read from the input file: "+ e.getMessage());}   
  
  }
  
	/* Sort strings of variable length using key-index (character) counting sort recursively*/ 
	public static void msd(String[] keys) {
	 /* create an aux array of strings and call the recursive helper function */
	 String[] aux=new String[keys.length];
	 msd(keys,aux,0, 0, keys.length-1);
	}
	
	/* recursive helper method that sorts array of strings with variable lengths from the left most digit (MST) index 0 to index right most digits */
	private static void msd(String[] keys, String[] aux, int d, int lo, int hi) {
	  // termination condition:
	  if(hi<=lo) return ;
	
	  /* sort all strings in the keys sub-array [lo hi] wrt the character at the digit d, using key index counting */
	  
	  // radix of alphabet:
	  int R=256; // for extended ascii
		// instantiate the count array: -1 is now an especial char, denotes the end of string therefore the radix is R+1
		int[] count=new int[R+2]; // the count[0] would denote the number of string that has been terminated (have -1 as their dth character)
		
		// 1. populate the key-index count array by counting number of occurrences of each index (character) in the keys sub-array at digit d
		for(int i=lo; i<=hi; i++){
		  count[charAt(keys[i], d)+2]++;
		}
		
		// 2. compute the cumulative count for each symbol of the alphabet (key-index), -1 included
		for(int r=0; r<R+1; r++) {
			count[r+1]=count[r+1]+count[r];
		}
		
		// 3. put each string key at its rightful position in the aux array wrt its dth digit:
		for(int i=lo; i<=hi; i++) {
		  aux[count[charAt(keys[i],d)+1]++]=keys[i];
		}
		
		// 4. copy over sorted strings in the sub-array wrt their dth digit to the original array of strings:
		for(int i=lo; i<=hi; i++){
			keys[i]=aux[i-lo];
		}
		
		// 5. recursively sort strings in each batch of characters [0 r-1], one digit deeper d+1, ignoring strings that have been terminated
		for(int r=0; r<R; r++) {
		  msd(keys,aux, d+1, lo+count[r], lo+count[r+1]-1);
		}
	}
	
	/* overload of charAt behaviour of the String library such that return -1 once d is out of bound, to specify the end of string */
	private static int charAt(String key, int d) {
		if(d<key.length()) return key.charAt(d);
		else return -1;
	}

}
