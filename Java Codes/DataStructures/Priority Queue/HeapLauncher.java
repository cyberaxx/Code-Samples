import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Collections;

public class HeapLauncher {

  // MaxPQ clinet:
  public static void main(String [] args) {
    MaxPQ<String> names = new MaxPQ<String>();
    names.add("sdfdsf");
    names.add("dlafdf");
    names.add("sacccaac");
    System.out.println(names.max());
    System.out.println(names.delMax());
    System.out.println(names.max());

    System.out.println();
    System.out.println();

    names.add("aashedfrvidfsn");
    names.add("cshaeasdrvsaidsfn");
    names.add("zsahsadefdsrsdfvfdidfn");
    System.out.println(names.max());
    System.out.println(names.delMax());
    System.out.println(names.max());

    System.out.println();
    System.out.println(names.size());
    System.out.println(names.isEmpty());


    System.out.println();
    System.out.println("MaxPQ members with no particular order:");
    Iterator<String> it=names.iterator();
    String[] strs = new String[names.size()];
    int i = 0;
    while(it.hasNext()) {
      strs[i]=it.next();
      System.out.println(strs[i]);
      i++;
    }

    // HEAP SORT:
    Heap.sort(strs);
    System.out.println();
    System.out.println();
    System.out.print(Arrays.toString(strs));
    System.out.println();


    Integer[] ints = new Integer[]{23,2432,1243,12,121,11, 12, 33, 23, 23, 5423, 1234};
    Heap.sort(ints);
    System.out.println();
    System.out.println();
    System.out.print(Arrays.toString(ints));
    System.out.println();
  }
  
}
