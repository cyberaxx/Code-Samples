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
    // Heap.sort(strs);
  }
  
}
