import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.LinkedList;

public class BFS {

  // instance variables:
  // 1. a vertex index array of booleans to keep track of visited vertices:
  private boolean[] marked;
  // 2. a vetex index array of integers (vertex indexes) for parent pointers
  private int[] edgeTo;
  // 3. a vertex index array of integers (vertex indexes) for distances from the source (number of edges)
  private int[] distTo;
  // 4. source vertex index
  private int s;

  // Constructor:
  // following graph algorithm design pattern:
  // separating the graph processing from graph representation
  // processing is done in constructor and API provides methods for client queries
  public BFS(Graph G, int s) {
    // sanity check:
    if(G==null) throw new NullPointerException();

    // initialize the source vertex
    this.s=s;
    // to initialize vertex index arrays we need the number of vertices in the input graph
    int n=G.V();
    marked=new boolean[n];
    edgeTo=new int[n];
    distTo=new int[n];

    // instantiate a queue that contains vertices:
    Deque<Integer> q=new ArrayDeque<Integer>();
    // starting from the source vertex:
    // 1. visit the source vertex:
    marked[s]=true;
    // 2. set the distance to source from the source to 0
    distTo[s]=0;
    // 3. add s to the queue
    q.offer(s);

    // while all vertices has not been visited yet
    while(!q.isEmpty()) {
      // remove the head of the queue
      int v=q.poll();

      // check all vertices adjacent to v in graph G that has NOT been visited yet
      for(Integer w:G.adj(v)) {
        if(!marked[w]) {
	  // 1. marked w as visited
          marked[w]=true;
          // 2. set its parent pointer
 	  edgeTo[w]=v;
	  // 3. set its distance from the source:
	  distTo[w]=distTo[v]+1;
          // 4. add w to the queue
	  q.offer(w);
	}
      }	
    } // O(V+2E) if graph represented by a vertex index array of adjacency lists

  }

  // API client queries:
  // 1. check if the graph is connected (G is its only connected component)
  public boolean isConnected() {
    // if all nodes have been visited from the source, G is connected
    for(int v=0; v<marked.length; v++)
      if(!marked[v])
	return false;
    return true;
  }

  // 2. check if the source vertex is connected to the given vertex:
  public boolean hasPathTo(int v){
    validate(v);
    return marked[v];
  }

  // 3. returns the distance of the given vertex to the source vertex is connected, -1 otherwise:
  public int distTo(int v){
    validate(v);
    if(marked[v])
      return distTo[v];
    return -1;
  }

  // 4. returns a path from the source to the given vertex, if connected, null otherwise:
  public Iterable<Integer> path(int v) {
    validate(v);
    if(!hasPathTo(v)) return null;

    // use stack to reconstruct the path from edgeTo array
    Deque<Integer> path=new ArrayDeque<Integer>();
    // add v to the path:
    path.push(v);
    while(edgeTo[v]!=s) {
      path.push(edgeTo[v]);
      v=edgeTo[v];
    }
    // add the source to the path
    path.push(s);

    // return the path
    return path;
  }

  // helper method
  private void validate(int v) {
    int n=marked.length;
    if(v<0||v>=n) throw new IndexOutOfBoundsException();
  }

}
    
