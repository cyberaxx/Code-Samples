public class QueueLauncher {
  public static void main(String [] args) {
    // client for the Queue class:
    Queue<String> q = new Queue<String>();
    for(int i=0; i<args.length; i++) q.enque(args[i]);
    for(String item : q) System.out.println(item);
    System.out.println();
    for(int i=0; i<args.length; i++) System.out.println(q.deque());

    AQueue<String> aq = new AQueue<String>();
    for(int i=0; i<args.length; i++) aq.enque(args[i]);
    for(String item : aq) System.out.println(item);
    System.out.println();
    for(int i=0; i<args.length; i++) System.out.println(aq.deque());

  }
}
