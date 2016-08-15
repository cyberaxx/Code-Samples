import java.util.Iterator;

public class StackLauncher {
  // client for Stack class:
  public static void main(String [] args) {
    Stack<String> stack = new Stack<String>();
    for(int i=0; i<args.length; i++) stack.push(args[i]);
    // for(String item : stack) System.out.println(item);
    Iterator<String> it = stack.iterator();
    while(it.hasNext()) System.out.println(it.next());
    System.out.println();

    for(int i=0; i<args.length; i++) System.out.println(stack.pop());
    System.out.println();

    System.out.println(stack.isEmpty());
    System.out.println(stack.size());

    System.out.println();
    System.out.println();
    System.out.println();

    AStack<String> astack = new AStack<String>();
    for(int i=0; i<args.length; i++) astack.push(args[i]);
//    for(String item : astack) System.out.println(item);
    Iterator<String> ait = astack.iterator();
    while(ait.hasNext()) System.out.println(ait.next());
    System.out.println();

    for(int i=0; i<args.length; i++) System.out.println(astack.pop());
    System.out.println();

    System.out.println(astack.isEmpty());
    System.out.println(astack.size());
  }
} 
