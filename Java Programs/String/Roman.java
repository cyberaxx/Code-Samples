import java.util.*;

/*you are required to complete this function*/
public class Roman
{
    public static void convertToRoman(int n)
    {
        // Your code here	
    	String[][] roman=new String[4][];
    	roman[0]="I II III IV V VI VII VIII IX".split(" ");
    	roman[1]="X XX XXX XL L LX LXX LXXX XC".split(" ");
    	roman[2]="C CC CCC CD D DC DCC DCCC DM".split(" ");
    	roman[3]="M MM MMM MMMM MMMMM MMMMMM MMMMMMM MMMMMMMM MMMMMMMMM".split(" ");
    	
    	// Stack of roman digit from LSD to MSD
    	Deque<String> stack=new ArrayDeque<String>(); // empty stack
    	
    	// read from the number n, LSD to MSD
    	int i=0;
    	while (n>0) {
    	    // LSD digit:
    	    int d=n%10;
    	    if(d!=0)
    	        stack.push(roman[i][d-1]);
    	    
    	    n=n/10;
    	    i++;
    	
    	}
    	
    	// concatnate all roman digit from the stack:
    	String output="";
    	while(!stack.isEmpty()){
    	    output+=stack.pop();
    	}
    	
    	// print out the roman number
    	System.out.println(output);
    }
    
    public static void main(String[] args) {
      for(String num:args)
        convertToRoman(Integer.parseInt(num));
    }
}
