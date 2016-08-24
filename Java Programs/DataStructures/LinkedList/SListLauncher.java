import java.util.Iterator;

public class SListLauncher {
  public static void main(String [] args) {
     SList<Integer> sl = new SList<Integer>();
     for(int i=0; i<5; i++) sl.addFront(i+1);
     // sl.addAt(2, 6);
     System.out.println(sl.size());
     System.out.println(sl.isEmpty());
     System.out.println();

     System.out.println(sl.contains(5));
     System.out.println(sl.isEmpty());
     System.out.println(sl.contains(4));
     System.out.println(sl.isEmpty());

     System.out.println();


     sl.addBack(-1);
     System.out.println(sl.getBack());
     sl.addBack(-2);
     System.out.println(sl.getBack());
     sl.addBack(-3);
     System.out.println(sl.getBack());

     System.out.println();

     sl.addAt(0, 100);
     System.out.println(sl.getFront());
     sl.addAt(1, 101);
     System.out.println(sl.getFront());
     sl.addAt(2, 102);
     System.out.println(sl.getFront());

     System.out.println();

     System.out.println(sl.get(0));
     System.out.println(sl.getFront());
     System.out.println(sl.size());
     System.out.println(sl.getBack());

     System.out.println();


     System.out.println(sl.indexOf(5));
     System.out.println(sl.indexOf(4));
     System.out.println(sl.indexOf(3));

     System.out.println();
     
     int n = sl.size()-1;
     for(int i=0; i<=n; i++) {
       sl.addAt(i, -i);
       System.out.println(sl.get(i));
     }


     System.out.println();
     
     for(Integer item : sl) {
       System.out.println(item);
     }

     System.out.println();
     System.out.println(sl.remove(-8));
     System.out.println(sl.remove(-18));
     System.out.println(sl.remove(-100));
     System.out.println(sl.remove(100));
     System.out.println();


     Iterator<Integer> it = sl.iterator();
     while(it.hasNext()) {
       System.out.println(it.next());
     }     

     System.out.println();

     System.out.println(sl.size());
     System.out.println(sl.isEmpty());
  }  
}
