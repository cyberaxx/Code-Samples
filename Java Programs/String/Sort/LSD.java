import java.io.File;
import java.util.*;

public class LSD {
  public static void main(String[] args) {
    File file=new File("words3.txt");
    try{
    	Scanner scanner=new Scanner(file);
    	List<String> list=new ArrayList<String>();
    	while(scanner.hasNext())
    		list.add(scanner.next());
    	String[] keys=list.toArray(new String[0]);
    	lsd(keys, 3);
    	
      for(int i=0; i<keys.length; i++)
    	  System.out.println(keys[i]);
      System.out.println();
    	
    	scanner.close();
    } catch(Exception e){System.out.println("Failed to read from the input file: "+ e.getMessage());} 
  }
  
	/* Sort N fixed-length Strings, defined on the set of symbol with radix R
     using key-index counting from the LSD (right most char of the string) to the
     MSD (left most char) for all N string:
     Performance : for N strings of fixed length M
     Time: O(M*(N+R)) Space: O(N+R)
	*/
	public static void lsd(String[] keys, int m) {
		// radix of extended ascii
		int R=256;
		// aux array of strings:
		String[] aux=new String[keys.length];
		
		// from the right most char in the string to the left most one
		for(int d=m-1; d>=0; d--) {
			/* perform key-index counting sort on dth digit of all n keys:*/
			int[] count=new int[R+1]; // array of frequencies
			
			// iterate over the array of N strings
			for(int i=0; i<keys.length; i++) {
				// count the occurrence of each char of the alphabet at the dth digit of all strings
				count[keys[i].charAt(d)+1]++;
			}
			
			// accumulate the character counts for each index, to get the rightful position of that index in the keys array (sorted by digit d onward)
			for(int r=0; r<R; r++) {
				count[r+1]=count[r+1]+count[r];
			}
			
			// aux array is array of m-digit strings, sorted from dth digit onward
			for(int i=0; i<aux.length; i++) {
				aux[count[keys[i].charAt(d)]++]=keys[i];
			}
			
			// copy the aux array back to the keys array
		  System.arraycopy(aux, 0, keys, 0, aux.length);
		}
	}
}
