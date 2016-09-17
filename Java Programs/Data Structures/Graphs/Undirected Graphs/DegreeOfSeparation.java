import java.io.File;
import java.util.Deque;
import java.util.ArrayDeque;

public class DegreeOfSeparation {
  // instance variabe:
  private SymbolGraph symbolGraph;
  private Graph graph;
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
  public int degreeOfSeparation(String name) {return 0;}
  public Iterable<String> path(String name) {return null;}

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
