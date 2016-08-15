import java.util.Iterator;

public class DListLauncher {
  // a client for the DList class:
  public static void main(String[] args) {
    DList<Double> dl = new DList<Double>();
    dl.addFirst(-2.9);
    dl.addFirst(56.4);
    dl.addFirst(92.5);
    dl.addFirst(23.8);
    dl.addFirst(45.3);
    dl.addFirst(78.1);
    dl.addFirst(16.0);


    dl.addLast(-12.4);
    System.out.println(dl.getFirst()); // 21.9
    System.out.println(dl.getLast());  // 21.9

    System.out.println();

    dl.addFirst(29.1);
    System.out.println(dl.getFirst());
    System.out.println(dl.getLast());

    System.out.println();

    dl.addLast(13.4);
    System.out.println(dl.getFirst());
    System.out.println(dl.getLast());

    System.out.println();

    System.out.println(dl.isEmpty());
    System.out.println(dl.size());
    System.out.println(dl.getFirst());
    System.out.println(dl.getAt(1));
    System.out.println(dl.getAt(2));
    System.out.println(dl.getAt(3));
    System.out.println(dl.getAt(4));
    System.out.println(dl.getAt(5));
    System.out.println(dl.getAt(6));
    System.out.println(dl.getLast());


    System.out.println();

    System.out.println(dl.removeFirst());
    System.out.println(dl.getFirst());
    System.out.println(dl.getLast());

    System.out.println();

    System.out.println(dl.isEmpty());
    System.out.println(dl.size());

    System.out.println();

    System.out.println(dl.removeLast());
    System.out.println(dl.removeLast());

    System.out.println();

    System.out.println(dl.isEmpty());
    System.out.println(dl.size());


   System.out.println();

    dl.addAt(-116.4, 0);
    dl.addAt(-14.2, 1);
    dl.addAt(3.21, 5);
    dl.addAt(65.2, 8);
   System.out.println(dl.getAt(0));
   System.out.println(dl.getAt(1));
   System.out.println(dl.getAt(5));
   System.out.println(dl.getAt(8));

   System.out.println();

   System.out.println("size: "+dl.size());
   System.out.println(dl.getAt(7));
   System.out.println(dl.getAt(9));
   System.out.println(dl.removeAt(8));
   System.out.println(dl.getAt(7));
   System.out.println(dl.getAt(8));
   System.out.println("size: "+dl.size());
   System.out.println(dl.getAt(4));
   System.out.println(dl.getAt(6));
   System.out.println(dl.removeAt(5));
   System.out.println(dl.getAt(4));
   System.out.println(dl.getAt(5));
   System.out.println("size: "+dl.size());
   System.out.println();

   System.out.println();

   for(Double item : dl)
     System.out.println(item);

   System.out.println();

   System.out.println();
   dl.addLast(-98.7231);
   System.out.println(dl.contains(-98.7231));
   System.out.println(dl.indexOf(-98.7231));
   System.out.println("size: "+dl.size());
   System.out.println(dl.remove(-98.7231));
   System.out.println("size: "+dl.size());
   System.out.println(dl.getLast());
   System.out.println(dl.getAt(7));
   System.out.println(dl.contains(-98.7231));
   System.out.println(dl.indexOf(-98.7231));
   System.out.println("size: "+dl.size());
   System.out.println();

   System.out.println();
   Iterator<Double> it = dl.iterator();
   while(it.hasNext())
     System.out.println(it.next());

   System.out.println();
  }

}
