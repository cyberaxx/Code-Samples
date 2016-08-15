import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class caterpillars {
  public static void main (String [] args) {
    int leafCount;
    leafCount = 10; 
    int [] jumps = new int []{2,4,5};
    int uneatenLeavesCount = uneatenLeaves(leafCount, jumps);
    //    System.out.println(uneatenLeavesCount);
    List<Integer> divisors = new ArrayList<Integer>();
    
    divisors = fastMultipleListRec(1, 10, 2, divisors);
    divisors = fastMultipleListRec(1, 10, 4, divisors);
    divisors = fastMultipleListRec(1, 10, 5, divisors);
    
    divisors = fastMultipleList(10, 2, divisors);
    divisors = fastMultipleList(10, 4, divisors);
    divisors = fastMultipleList(10, 5, divisors);
    
    //for(int i=0; i<divisors.size(); i++) System.out.println(divisors.get(i));
    System.out.println(10-divisors.size());
    
    /*
    long startTime = System.currentTimeMillis();
    divisors = fastMultipleListRec(1, 200000, 2, divisors);
    divisors = fastMultipleListRec(1, 200000, 4, divisors);
    divisors = fastMultipleListRec(1, 200000, 5, divisors);
    long endTime   = System.currentTimeMillis();
    long totalTime1 = endTime - startTime;
    System.out.println(totalTime1);
    
    System.out.println("\n\n");
    
    startTime = System.currentTimeMillis();
    divisors = fastMultipleList(200000, 2, divisors);
    divisors = fastMultipleList(200000, 4, divisors);
    divisors = fastMultipleList(200000, 5, divisors);
    endTime   = System.currentTimeMillis();
    long totalTime2 = endTime - startTime;
    System.out.println(totalTime2);
    
    System.out.println("\n\n");
    System.out.println(totalTime2/totalTime1);
    */
  } 

  public static int uneatenLeaves(int leafCount, int [] jumps) {
    List<Integer> eatenLeaves = new ArrayList<Integer>();
    int count = 0;
    for (int j=0; j<jumps.length; j++) {
      count += leafCount/jumps[j];

      /*
        for(int k=1; k<=leafCount/jumps[j]; k++) {
          count++;
    int i = k * jumps[j] ;
          if(!eatenLeaves.contains(i))
            eatenLeaves.add(i);
        }
       */
    }

    return leafCount - count;
  }

  public static List<Integer> fastMultipleListRec(int startNum, int endNum, int stepSize, List<Integer> divisors) {
    if ( (endNum - startNum)/2 <= stepSize ) {
      while(startNum%stepSize !=0 ) startNum++;
      for(int i=startNum; i<=endNum; i+=stepSize) {
        if (divisors.isEmpty())  divisors.add(i);
        else 
          if (!divisors.contains(i)) divisors.add(i);
      }
      return divisors;
    }

    else {
      divisors = fastMultipleListRec(startNum, startNum + (endNum-startNum)/2, stepSize, divisors);
      divisors = fastMultipleListRec(startNum+((endNum-startNum)/2)+1, endNum, stepSize, divisors);
      return divisors;        
    }

  }

 public static List<Integer> fastMultipleList(int num, int stepSize, List<Integer> divisors) {
    for(int i=stepSize; i<=num; i+=stepSize) {
      if (!divisors.contains(i))  divisors.add(i);
    }

    return divisors;

  }

  public static List<Integer> fastDivisorList(int num, List<Integer> divisors) {
    // O(sqrt(n))
    for(int i=1; i<=Math.ceil(Math.sqrt(num)); i++) {
      if (num%i == 0) {
        if (i != num/i) {
          if (!divisors.contains(i))  divisors.add(i);
          if (!divisors.contains(num/i)) divisors.add(num/i);
        }
        else   
          if (!divisors.contains(i))  divisors.add(i);
      }
    }

    return divisors;

  }


}
