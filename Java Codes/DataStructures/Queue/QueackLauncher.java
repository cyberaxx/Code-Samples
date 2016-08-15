public class QueackLauncher {
  public static void main(String [] args) {
    Queack<String> q = new Queack<String>();
    for(int i=0; i<args.length; i++) q.enqueue(args[i]);
    System.out.println(q.size());
    System.out.println(q.isEmpty());
    System.out.println();
    for(int i=0; i<args.length; i++) {
      System.out.println(q.dequeue());
    }
    System.out.println();
    System.out.println(q.size());
    System.out.println(q.isEmpty());
  } 
}
