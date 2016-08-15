import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;


public class ThreeSum {
  public static int count (int [] input) {
  // assuming the input array consists DISTINCT integers:
  // Objective: find how many tuple of (a,b,c) exists in the array such that a+b+c=0
  // Brute force:
  int count = 0;
  for (int i=0; i<input.length; i++)
    for (int j=i+1; j<input.length;j++)
      for (int k=j+1; k<input.length;k++)
        if (input[i] + input[j] + input[k] == 0) count++;

  return count;

  }

  public static void main(String[] args) throws IOException {
    FileReader fr = new FileReader("1Kints.txt");
    BufferedReader br = new BufferedReader(fr);
    String line;
    int[] input = new int [1000];
    int i = 0;
    while( ( line = br.readLine() ) != null) {
      input[i] = Integer.parseInt(line.trim());
      i++;
    }

    // time the performance of the algorithm:
    System.out.println(count(input));

  } 
}
