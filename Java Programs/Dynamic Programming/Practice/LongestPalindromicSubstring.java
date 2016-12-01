import java.util.*;
import java.lang.*;
import java.io.*;

public class LongestPalindromicSubstring {
	public static void main (String[] args) {
		//code
		Scanner scanner=new Scanner(System.in);
		int tests=scanner.nextInt();
		for(int i=0; i<tests; i++) {
		    lps(scanner.next());
		}
		scanner.close();
	}
	
	/* this method returns the length of lps-substring: */
	private static void lps(String x) {
	    /* memo table to keep track of the length lps: */
	    int[][] lps=new int[x.length()][x.length()];
	    int max=0;
	    String result="";
	   
	    /* 3. relaxation: in topological order of subproblems: */
	    for(int len=1; len<=x.length(); len++) {	        
	        for(int i=0; i<=x.length()-len; i++) {
	   	        /* for all substring ending at j */
    	        int j=i+len-1;
    	        
    	        /* 2. initialization step: */
    	        if(len==1)
	                lps[i][j]=1; /* the length of lps start and ends at i */
    	        else if(x.charAt(i)==x.charAt(j) && len==2) {
    	            lps[i][j]=2;
    	        }
    	        else if(x.charAt(i)==x.charAt(j) && lps[i+1][j-1]>0) {
    	            lps[i][j]=lps[i+1][j-1]+2;
    	        }
    	        if(lps[i][j]>max) {
    	            max=lps[i][j];
    	            result=x.substring(i, j+1);
                }
	        }
	    }
	    
	    System.out.println(result);
	}
}
