import java.io.File;
import java.util.Deque;
import java.util.ArrayDeque;

public class DegreeOfSeparation {
  // instance variabe:
  private SymbolGraph symbolGraph;
  private Graph G;
  // BFS required follwoing fields:
  // 1. vertex index array of boolean to maintain visited vertices:
  private boolean[] marked;
  // 2. vertex index array of distance to the source vertex
  private int[] distTo;
  // 3. vertex index array of parent pointers
  private int[] edgeTo;
  // 4. bfs source
  private int s;

  // Constructor:
  public DegreeOfSeparation(File file, String delimiter, String source) {
    // initialize the instance variable:
    symbolGraph=new SymbolGraph(file, delimiter);
    // extract the underlying graph:
    Graph G=symbolGraph.graph();
 
    // initialize all BFS required fields
    marked=new boolean[G.V()];
    edgeTo=new int[G.V()];
    distTo=new int[G.V()];   
    // find the index of source vertex
    this.s=symbolGraph.indexOf(source);

    // run bfs from the source:
    bfs(G, s);
  }

  // API: methods for client queries:
  public boolean isConnected(String name) {
    // extract the vertex index of the given string from the SymbolGraph instance:
    int v=symbolGraph.indexOf(name);
    return marked[v];
  }
  public int degreeOfSeparation(String name) {
    // extract the vertex index of the given string from the SymbolGraph instance:
    int v=symbolGraph.indexOf(name);
    // if v has not been visited: -1
    if(!marked[v]) return -1;
    // otherwise:
    return distTo[v];
  }
  public Iterable<String> path(String name) {
    // extract the vertex index of the given string from the SymbolGraph instance:
    int v=symbolGraph.indexOf(name);
    // if v has not been visited return null
    if(!marked[v]) return null;
   
    // Otherwise:
    // return a bfs path from s to v by following parent pointers
    Deque<String> path=new ArrayDeque<String>(); // empty stack
    // starting from v going backward:
    while(v!=s) {
      // extraxt the string value of v and push it to the stack
      path.push(symbolGraph.nameOf(v));
      // move up to v's parent
      v=edgeTo[v];
    }
    path.push(symbolGraph.nameOf(s));
    return path;
  }

  // helper methods
  private void bfs(Graph G, int source) {

    // Queue to keep the visited vertices
    Deque<Integer> q=new ArrayDeque<Integer>(); // empty queue of integer (vertex indexes)
    
    // process the source vertex
    marked[source]=true; // visit the source
    distTo[source]=0; // set the distance to the source
    q.offer(source); // add the source to the queue

    // while the processing queue is not empty
    while(!q.isEmpty()) {
      // remove the head of the queue:
      int v=q.poll();
      // process (visit) all UNvisited vertices adjacent to v in graph G
      for(Integer w:G.adj(v)) {
        // if has not been visited already:
        if(!marked[w]){
	  // mark it visited:
	  marked[w]=true;
	  distTo[w]=distTo[v]+1;
	  edgeTo[w]=v;
          // add w to the processing queue
	  q.offer(w);
        }
      }
    }
  }
 
}
