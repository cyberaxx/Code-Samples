import java.util.List;
import java.util.ArrayList;

public class LineGraph {

  public static int maxWIS(int [] weights, List<Integer> path) {
    // number of vertices of the graph
    int n=weights.length;
    // A memo table to keep track of the value of the optimal solution to subproblems (optimal substructure)
    // of the original problem:
    int[] memo=new int[n+1]; // considering subproblems in form of prefixes of 1....N sequence of nodes

    // base case: 0 node graph
    memo[0]=0; // max weight of IS for 0 node

    // Systematically solve optimal subtructures (in this case prefixes of 1...n) 
    // to be able to solve the original problem of size n -> the solution would be at memo[n]
    for(int i=1; i<=n; i++) {
      // if we are considering subgraphs with less than two nodes:
      if(i<2) memo[i]=weights[i-1]; // there is no other way rather than adding this single node to the max IS
      else // recursively choose the best choice among TWO possible optimla substructure:
        memo[i]=Math.max(memo[i-2]+weights[i-1], memo[i-1]);
    }

    // path reconstruction:
    for(int i=n; i>=2; i--)
      if(memo[i]==memo[i-2]+weights[i-1]) {path.add(i-1); i--;}

    return memo[n];
  }

  public static void main(String[] args) {
    int [] weights=new int [args.length];
    List<Integer> path=new ArrayList<Integer>(); // an empty list instance
    for(int i=0; i<weights.length; i++)
      weights[i]=Integer.parseInt(args[i]);
    System.out.println("MAX WIS: "+maxWIS(weights, path));
    System.out.println("MAX WIS vertices: "+path);
  }
}
