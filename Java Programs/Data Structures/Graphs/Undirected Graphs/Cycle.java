import java.util.Deque;
import java.util.ArrayDeque;

/*
  Cycle detection: 
  Is a given graph acyclic? 
  Use depth-first search to determine whether a graph has a cycle, 
  and if so return one in time proportional to V + 2E in the worst case.
*/

public class Cycle {
  // Graph algorithm design pattern: 
  // separate graph representation from graph processing

  // instance variables:
  // 1. a sequence of integer vertices that characterizes the cycle
  private Iterable<Integer> cycle;
  // 2. a vertex index array of booleans to maintain which vertices has been visited so far
  private boolean[] marked;
  // 3. a vertex index array of parent pointers to reconstruct the cylce (if exist)
  private int[] edgeTo;

  // Constructor:
  // processing is done within the constructor
  public Cycle(Graph G) {

    // 1. initialization
    // extract the number of vertices in the graph:
    int n=G.V();
    // initialize instance members:
    marked=new boolean[n];
    edgeTo=new int[n];

    // 2. processing: finding the cycle

  }

  // API: processed results for client
  public boolean hasCycle(){return cycle!=null;}
  public Iterable<Integer> cycle(){return cycle;}

  // helper methods:
  private void dfs(Graph G, int u, int v){}
  private boolean hasSelfLoop(Graph G){return true;}
  private boolean hasParallelEdge(Graph G){return true;} 

}



