import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Arrays;

/*
Find the largest M items in a stream of N items.
*/

public class TopM {
  public static void main(String [] args) {
    // Find the largest M items in a stream of N items.
    int M=15;
    Integer [] items=new Integer[10000];
    int N=items.length;
    Random randomEntry=new Random();
    Random randomRepetition=new Random();
    int maxInt=9999999;
    Integer entry;
    int repetition;

    // Instantiate form java MinPQ: java.util.ProrityQueue:
    PriorityQueue<Integer> pq=new PriorityQueue<Integer>();

    // generate a random stream of N items of Comparable type (Intergers):
    for(int i=0; i<items.length;i++) {
      entry=randomEntry.nextInt(maxInt);
      repetition=randomRepetition.nextInt(4-1)+1; // [1 4)
      for(int j=0; j<=repetition; j++) {
        /* To maintain top M items:
	   Check if the Min Oriented Priority Queue contains less than M item
	      a. If so, add the new item to the Collection type
	      b. if not:
                 1. remove the Min from the current Collection
		 2. add the new item to the collection
	*/

        if(pq.size()<M)  pq.offer(entry); // O(logM)
        else {
  	  // remove smallest items from the MinPQ to make some room for new items
          while(pq.size()>=M)  pq.poll(); // O(logM)
          pq.offer(entry);
        }
      }
    } 
        
    System.out.print(Arrays.toString(pq.toArray()));
    System.out.println();

    System.out.print(pq);
    System.out.println();

    // clear the PriorityQueue
    pq.clear();
    pq=new PriorityQueue<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
    System.out.println();
    System.out.print(pq);
    System.out.println();
    
    Integer[] input=new Integer[]{10, 9 , 8 , 7, 1, 0};
    MaxPQ<Integer> maxpq=new MaxPQ<Integer>(input);
    System.out.print(Arrays.toString(maxpq.toArray()));
    System.out.println();
  }
}
