import java.util.Comparator;
import java.io.File;

public class Merge {
  // merge sort with comparator object as input
  private static void sort (Object[] src, Object[] dst, int lo, int hi, Comparator comparator) {
    if (hi <= lo) return ;
    int mid = (hi+lo)/2;

    sort(dst, src, lo, mid, comparator);
    assert isSorted(dst, lo, mid, comparator);

    sort(dst, src, mid+1, hi, comparator);
    assert isSorted(dst, mid+1, hi, comparator);

    if( !(less(src[mid+1], src[mid], comparator)) ) {
      System.arraycopy(src, lo, dst, lo, hi-lo+1);
      return ;
    }
    merge(src, dst, lo, mid, hi, comparator);
    assert isSorted(src, lo, hi, comparator);
  }
 
  private static void merge (Object[] src, Object[] dst, int lo, int mid, int hi, Comparator comparator) {
    assert isSorted(src, lo, mid, comparator); // LEFT
    assert isSorted(src, mid+1, hi, comparator);  // RIGHT


    int i = lo;
    int j = mid+1;

    for(int k=lo; k<=hi; k++) { 
      if(i>mid) dst[k] = src[j++];
      else if (j>hi) dst[k] = src[i++];
      else if (less(src[j], src[i], comparator)) dst[k] = src[j++];
      else dst[k] = src[i++];
    }
    assert isSorted(dst, lo, hi, comparator);
  }

  public static void sort (Object [] src, Comparator comparator) {
    Object[] dst = src.clone();
    sort(dst, src, 0, src.length-1, comparator); 
    assert isSorted(src, comparator);
  }

  private static boolean isSorted(Object[] items, Comparator comparator) {
    return isSorted(items, 0, items.length-1, comparator);
  }

  private static boolean isSorted(Object[] src, int lo, int hi, Comparator comparator) {
    for(int i=lo+1; i<=hi; i++)
      if (less(src[i], src[i-1], comparator)) return false;
    return true;
  }

  private static boolean less (Object v, Object w, Comparator comparator) {
    return comparator.compare(v,w) < 0;
  }

  private static void exch (Object[] src, int i, int j) {
    Object temp = src[i];
    src[i] = src[j];
    src[j] = temp;
  }

  private static void sort (Comparable [] src, Comparable [] dst, int lo, int hi) {
    if(hi<=lo) return ; 
    int mid = (lo+hi)/2;
    
    sort(dst, src, lo, mid);
    sort(dst, src, mid+1, hi);

    if (!less(src[mid+1], src[mid])) {
      System.arraycopy(src, lo, dst, lo, hi-lo+1);
      return ;
    }

    mergeCombine(src, dst, lo, mid, hi);
  }


  private static void mergeCombine (Comparable [] src, Comparable [] dst, int lo, int mid, int hi) {
    assert isSorted(src, lo, mid);
    assert isSorted(src, mid+1, hi);

    int i=lo;
    int j=mid+1;

    for (int k=lo; k<=hi; k++) {
      if(i>mid) dst[k] = src[j++];
      else if(j>hi) dst[k] = src[i++];
      else if(less(src[j], src[i])) dst[k] = src[j++];
      else dst[k] = src[i++];
    }
    assert isSorted(dst, lo, hi);
  }

  public static void sort (Comparable [] src) {
    Comparable [] dst = src.clone();
    sort (dst, src, 0, src.length-1);
    assert isSorted(src);
  }

  private static boolean less (Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }
  
  private static boolean isSorted (Comparable [] src, int lo, int hi) {
    for(int i=lo+1; i<=hi; i++) 
      if(less(src[i], src[i-1])) return false;
    return true;
  }

  private static boolean isSorted (Comparable [] items) {
    return isSorted(items, 0, items.length-1);
  }

  public static void main (String [] args) {
    // Sort with Comparable example:
    File directory = new File (args[0]);
    File [] files = directory.listFiles();
    // sort the array of files
    Merge.sort(files);
    for(int i=0; i<files.length;i++) System.out.println(files[i].getName());

    System.out.println();

    // Sort with Comparator example:
    String [] str = {"Shervin", "sas", "Nice", "dUde", "Dude"};
    // sort the array of files
    Merge.sort(str, String.CASE_INSENSITIVE_ORDER);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);

    System.out.println();

    // Sort with Comparable example:
    Merge.sort(str);
    for(int i=0; i<str.length;i++) System.out.println(str[i]);
  }

}
