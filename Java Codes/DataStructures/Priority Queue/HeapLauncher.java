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
    System.out.println("Max is :" + names.max());
    System.out.println(names.delMax());
    System.out.println("Max is :" + names.max());
    System.out.println("Min is :" + names.min());


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

    System.out.println("Max is :" + names.max());
    System.out.println("Min is :" + names.min());

    System.out.println();
    System.out.println();

    // HEAP SORT:
    Heap.sort(strs);
    System.out.println();
    System.out.println();
    System.out.print(Arrays.toString(strs));
    System.out.println();

    Integer[] ints = new Integer[]{23,2432,1243,12,121,11, 12, 33, 23, 23, 5423, 1234};
    MaxPQ<Integer> pq = new MaxPQ<Integer>(ints);
    System.out.println();
    System.out.println();
    for(Integer item : pq)
      System.out.println(item);

    System.out.println();
    Heap.sort(ints);
    System.out.println();
    System.out.println();
    System.out.print(Arrays.toString(ints));
    System.out.println();


    System.out.println("MinPQ");
    System.out.println();
    MinPQ<String> mpq=new MinPQ<String>(new String[]{"sadasd", "asdsad", "sdsad", "gdgdf", "nngfdf", "sdfsdf"});
    System.out.println();
    System.out.println(mpq.size());
    System.out.println(mpq.isEmpty());

    System.out.println();
    mpq.add("aashedfrvidfsn");
    mpq.add("cshaeasdrvsaidsfn");
    mpq.add("zsahsadefdsrsdfvfdidfn");
    System.out.println();
    System.out.println(mpq.size());
    System.out.println(mpq.isEmpty());

    System.out.println();
    System.out.println(mpq.min());
    System.out.println(mpq.delMin());
    System.out.println(mpq.max());

    System.out.println();
    System.out.println(mpq.min());
    System.out.println(mpq.delMin());
    System.out.println(mpq.max());
    System.out.println();
    System.out.println(mpq.size());
    System.out.println(mpq.isEmpty());



    MinPQ<String> mpqq=new MinPQ<String>(new String[]{"sadasd", "asdsad", "sdsad", "gdgdf", "nngfdf", "sdfsdf"});
    System.out.println();
    System.out.print(Arrays.toString(mpqq.toArray()));
    System.out.println();
    PriorityQueue<String> pqq= new PriorityQueue<String>(Arrays.asList("sadasd", "asdsad", "sdsad", "gdgdf", "nngfdf", "sdfsdf"));
    System.out.print(pqq);
    System.out.println();

    for(String item:mpqq)  System.out.println(item);
    System.out.println(mpqq.size());
    System.out.println(mpqq.isEmpty());	

    System.out.println();
    System.out.println("Heap sort:");
    Integer[] numbers=new Integer[]{234,12,34,56,123,786,2123,54656,56,897,1234,5645,8979,34, 234, 4534, 2312, 234, 897};
    MinPQ.sort(numbers);
    System.out.print(Arrays.toString(numbers));
    System.out.println();
    System.out.println("System sort:");
    numbers=new Integer[]{234,12,34,56,123,786,2123,54656,56,897,1234,5645,8979,34, 234, 4534, 2312, 234, 897};
    Arrays.sort(numbers);
    System.out.print(Arrays.toString(numbers));
    System.out.println();

  }
 
}
