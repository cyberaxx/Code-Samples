/* Find longest common substring in two given strings*/
import java.util.*;
import java.lang.*;
import java.io.*;

public class LCSS {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        	public static void main (String[] args) {
		//code
		// open the scanner
		Scanner scanner=new Scanner(System.in);
		// 1. LINEAR many (t) TEST CASES:
		int testCount=scanner.nextInt();
		for(int i=0; i<testCount; i++) {
		    int l1=scanner.nextInt();
		    int l2=scanner.nextInt();
		    String x=scanner.next();
		    String y=scanner.next();
		    System.out.println(lcss(x.toCharArray(), y.toCharArray()));
		}
		// close the scanner
		scanner.close();		
	}
	
	private static int lcss(char[] x, char[] y) {
	    int[][] lcss=new int[x.length+1][y.length+1]; // +1 for empty substring
	    
	    /* 1. initialization step: */
	    int max=0;
	    for(int i=0; i<=x.length; i++) lcss[i][0]=0;
	    for(int j=1; j<=y.length; j++) lcss[0][j]=0;
	    
	    /* 2. Relaxation Step (in Topological order of state space): */
	    for(int i=1; i<=x.length; i++) {
	        for(int j=1; j<=y.length; j++) {
	            if(x[i-1]==y[j-1]) /* index adjacement*/
	                lcss[i][j]=lcss[i-1][j-1]+1;
	            
	            if(lcss[i][j]>max)
	                max=lcss[i][j];
	        }
	    }
	    
	    /* 3. final optimal solution: */
	    return max;
	}

}
