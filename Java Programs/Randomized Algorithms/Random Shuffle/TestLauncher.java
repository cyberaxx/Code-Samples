public class TestLauncher {
  public static void main (String [] args) {
    Test.testmessage();
    for(int i=0; i<args.length; i++)  System.out.print(args[i] + " ");
    System.out.println();
  }
}