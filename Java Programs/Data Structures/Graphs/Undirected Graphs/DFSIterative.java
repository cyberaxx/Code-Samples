import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;

public class DFSIterative {

  // instance variables:
  // 1. vertex index array of boolean to keep track of visited vertices
  private boolean[] marked;
  // 2. vertex index array of parent pointers required for path reconstruction
  private int[] edgeTo;
  // 3. the source vertex of the search
  private int s;

  // Constructor
  public DFSIterative(Graph G, int s) {
    // initialize instance memebers:
    this.s=s;
    // extract number of vertices:
    int n=G.V();
    // initialize instance arrays:
    marked=new boolean[n];
    edgeTo=new int[n];

    // Do DFS iteratively by explicitly usint stack
    Deque<Integer> stack=new ArrayDeque<Integer>();
   
    // 1. first recursive call on the source:
    // add the source vertex to the recursion stack
    marked[s]=true;
    stack.push(s);

    // 2. recurse until the recursion stack is empty:
    while(!stack.isEmpty()) {
      // remove the top of the recursive stack
      int v=stack.pop();
      // for each vertex w adjacent to v:
      for(Integer w:G.adj(v)) {
        // if w has not been visited yet
        if(!marked[w]) {
          // mark w as visited
	  marked[w]=true;
	  edgeTo[w]=v;
          stack.push(w);
        }
      }
      // all vertices adjacent to v has been visited!
    }
  }

  // API:
  public boolean hasPathTo(int v) {
    validate(v);
    return marked[v];
  }
  public Iterable<Integer> path(int v) {
    validate(v);
    if(!hasPathTo(v)) return null;
    Deque<Integer> path=new ArrayDeque<Integer>();
    path.push(v);
    while(edgeTo[v]!=s) {
      path.push(edgeTo[v]);
      // parent pointer:
      v=edgeTo[v];
    }
    path.push(s);
    return path;
  }
  public boolean isConnected(){
    for(int v=0; v<marked.length; v++)
      if(!marked[v])
	return false;
    return true;
  }

  // helper method:
  private void validate(int v) {
    int n=marked.length;
    if(v<0 || v>=n) throw new IndexOutOfBoundsException();
  }

  // test client for the graph API
  public static void main(String[] args) {
    Graph G=new Graph(5);

    G.addEdge(1, 2);
    G.addEdge(0, 3);
    G.addEdge(4, 1);
    G.addEdge(4, 3);
    G.addEdge(1, 0);
    G.addEdge(3, 2);

    for(int i=0; i<5; i++)
      System.out.println(i+": "+G.adj(i));

    System.out.println();
    System.out.println();

    DFSIterative dfs=new DFSIterative(G, 0);
    System.out.println("is 4 connect to the source? "+dfs.hasPathTo(4));
    System.out.println("The path to 4: "+dfs.path(4));
    System.out.println("is G connected? "+dfs.isConnected());
  }   
}
