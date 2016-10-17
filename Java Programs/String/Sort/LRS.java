import java.util.Arrays;

public class LRS {

  public static void main(String[] args){
    System.out.println(lrs("aaaaaaaaa"));
  }

  /* find the longest reapted substring in a string */
  public static String lrs(String key) {
    // 1. create an array of all suffixes of the string key
    Suffix suffix=new Suffix(key);
    String[] suffixes=suffix.suffixes();
    
    // 2. sort the array of suffixes to get close suffixes together
    QuickRadix.sort(suffixes);
    
    // 3. sorted array of suffixes, scan one pass from top to the bottom and find the longest common prefix
    int index=0;
    int tail=0;
    for(int i=0; i<suffixes.length-1; i++) {
      // compare suffixes[i] and suffixes[i+1]
      int m=Math.min(suffixes[i].length(), suffixes[i+1].length()); 
      for(int d=0; d<m; d++) {
        if(suffixes[i].charAt(d)==suffixes[i+1].charAt(d)) {index=i; tail=d;}
        else break;
      }
    }
    return suffixes[index].substring(0, tail+1);
  } 

}
