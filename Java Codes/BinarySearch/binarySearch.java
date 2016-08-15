import java.util.Arrays;

/*
Egg drop. Suppose that you have an N-story building (with floors 1 through N) and plenty of eggs. An egg breaks if it is dropped from floor T or higher and does not break otherwise. Your goal is to devise a strategy to determine the value of T given the following limitations on the number of eggs and tosses:
Version 0: 1 egg, ≤T tosses.
Version 1: ∼1lgN eggs and ∼1lgN tosses.
Version 2: ∼lgT eggs and ∼2lgT tosses.
Version 3: 2 eggs and ~2sqrt(N) tosses.
Version 4: 2 eggs and ≤ c*sqrt(T) tosses for some fixed constant c.


Solution:
Version 0: drop the egg from the first floor and go up level by level and toss it until the egg breaks, with <=T tosses we can find determine the value of the T
Version 1: use binary search approach, take the first egg, drop it from N/2 floor, if it breaks, drop the nex egg from middle of 1 to N/2-1 floor, if not from the middle
N/2+1 and N floor, and so on, with lgN tosses and logN eggs we can determine T.

Version 2: Start from the 1st floor and exponentially go up by powers of 2, if breaks, binary search.

Version 3,4: Use one egg for range search, and the second egg to determine T;
*/

public class binarySearch {
  public static void main (String [] args) {
    int [] input = new int[]{2,11,4,7,10,9,8,5,3,6};
    Arrays.sort(input);
    int indx = IndexOf(input, 12);
//    System.out.println(indx);

    int [] seq = new int []{10,34,56, 76, 87, 80, 70, 66, 56, 30, 28, 25, 20, 15, 11};
//    System.out.println(bitonicMax(seq, 0, seq.length-1));
    System.out.println(bitonicSearch(seq, 14));
    System.out.println(bitonicSearch(seq, 80));
    System.out.println(bitonicSearch(seq, 56)); // First occurence
    System.out.println(bitonicSearch(seq, 76));


  }
/*
  Search in a bitonic array:
  An array is bitonic if it is comprised of an increasing sequence of integers followed immediately by a decreasing sequence of integers. 
  
  Write a program that, given a bitonic array of N distinct integer values, determines whether a given integer is in the array.

  Standard version: Use ∼3lgN compares in the worst case.
  Signing bonus: Use ∼2lgN compares in the worst case (and prove that no algorithm can guarantee to perform fewer than ∼2lgN compares in the worst case).
*/
  // unlike binary search, the input array is not sorted, but it is consist of 
  // two sorted subarrays:
  // assuming input array consist of bitonic sequence of DISTINCT integers:
  public static int bitonicSearch (int [] input, int key) {

    // This is a 3logN approach:
    // Find the index of Max in the bitonic seq:
    int maxIndex = bitonicMax(input, 0, input.length-1); // O(logN)
    // Do binary search on the Ascending portion of the array
    int ascBS = binarySearch(input,0, maxIndex, key, true);
    // Do binary search on the Descending portion of the array
    int descBS = binarySearch(input, maxIndex+1, input.length-1, key, false);

    // if both search failed:
    if (ascBS == -1 && descBS == -1) return -1;
    else if (ascBS == -1) return descBS;
    else return ascBS;
  }

  public static int bitonicMax (int [] input, int lo, int hi) {
    if (lo == hi) return lo;
 
    else {
      // look at the middle element:
      int mid = (lo+hi)/2; 

      // 1. if the middle element is smaller that its next element:
      // a) we might be on the decreasing portion of the array, so, to find the MAX
      // we have to look at the left subarray (lo:mid)
      if (input[mid] > input[mid+1]) return bitonicMax(input, lo, mid);

      // b) we might be on the increasing side of the array, Max must be on right side
      else if (input[mid] < input[mid+1]) return bitonicMax(input, mid+1, hi); 

      // c) if input[mid] == input[mid+1] 
      else  return mid;
    }
   
  }

  public static int binarySearch (int [] input, int lo, int hi, int key, boolean ascending) {
    // recursive solution:
    int mid = (lo+hi)/2;
    // 1. Base cases:
    if (lo > hi) return -1;
    // 2. recurrence:
    else {
      if (ascending) {
	if (key < input[mid]) return binarySearch (input, lo, mid-1, key, true);
	else if (key > input[mid]) return binarySearch (input, mid+1, hi, key, true);
        else return mid;
      }
      else {
	if (key > input[mid]) return binarySearch (input, lo, mid-1, key, false);
	else if (key < input[mid]) return binarySearch (input, mid+1, hi, key, false);
        else return mid;
      }
    }
  }

/*
  // assuming array elements can have duplicates but ordering is strict
  public static int bitonicMax (int [] input) {
    int lo,hi, mid;
    lo=0; hi=input.length-1;
    mid = 0; // initialize to 0

    while (lo < hi) {
      mid = (lo+hi)/2;
      if (input[mid] < input[mid+1]) lo = mid + 1;
      else if (input[mid] > input[mid+1]) hi = mid;
      else return input[mid];
    }

    return input[lo];
  }
*/

  public static int IndexOf(int [] input, int key) {
    // input array assumed to be sorted:
    int mid, start, end;
    start = 0;
    end = input.length - 1;
    mid = (start + end)/2;

    while (start <= end) {
      if (key == input[mid]) return mid;
      else if (key > input[mid])
        start = mid+1;
      else
	end = mid - 1;

      mid = (start + end)/2; 
    }

    return -1;

  }
}
