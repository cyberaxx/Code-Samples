import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;

/*
  Follwoing graph aglorithms design pattern
  by decoupling the graph representation from grapg processing.

  Graph abstraction provides an effiecient representation of Digraphs and
  all basic operations that allows graph processing algorithms process the graph

  Graph processing algorithm takes a Graph as an input, and process it using its instance method
  and provide solutions for cliet of graph processing applications 
*/
public class DirectedBFSPaths {
  // instance variable:
  // 1.a vertex index array of boolean to keep track of visited vertices (avoid exploring the same vertex more than once)
  private boolean[] marked;
  // 2. a vertex index array of parent pointers to recontstruct BFS path (THE shortest path from source to any reachable vertex from source)
  private int[] edgeTo;
  // 3. a vertex index array of distances from the source vertex
  private int[] distTo;
  // 4. INFINITY: we need to initialize the distTo array to INFINITY (for vertices that are not reachable from the source)
  private final int INFINITY=Integer.MAX_VALUE;

  /* We do NOT explicitly need have the source vertex
     the distTo array implicity characterize the source vertex
     the source vertex is the one with distTo[x] is 0
  // 4. the source of DFS exploration
  private int source; 
  */ 

  // Constructor: Single source shortest path (BFS)
  public DirectedBFSPaths(Digraph G, int s) {
    validate(s); // validate if the s is a valid source vertex

    // initialize the visited array and the edgeTo array
    marked=new boolean[G.V()]; // vertex index array of booleans
    edgeTo=new int[G.V()]; // vertex index array of vertices (vertex indexes)
    distTo=new int[G.V()]; // vertex index array of distances from the source 
    
    // initlialize the distTo array
    for(int v=0; v<G.V(); v++)
      distTo[v]=INFINITY;

    // run on pass of DFS from the source vertex
    bfs(G, s);
  }

  // Constuctor: Multiple source shortest path
  public DirectedBFSPaths(Digraph G, List<Integer> sources) {
    // perfrom BFS from a set of search sources:
    // initialization is similar to the bfs form the single source:
    marked=new boolean[G.V()];
    distTo=new int[G.V()];
    edgeTo=new int[G.V()];

    // initlialize the distTo array
    for(int v=0; v<G.V(); v++)
      distTo[v]=INFINITY;

    // call bfs with a set of search sources
    bfs(G, sources);
  }

  // Multi-Source shortest path  
  private void bfs(Digraph G, List<Integer> sources) {
    // Queue of marked vertices:
    Deque<Integer> q=new ArrayDeque<Integer>();

    // 1. add all source vertices v to the q
    for(Integer v:sources) {
      q.offer(v);
      // marked v
      marked[v]=true;
      // set the distTo source 0
      distTo[v]=0;
    }

    // 2. while the processing q is not empty
    while(!q.isEmpty()) {
      // 3. remove the head of the q
      int v=q.poll(); // v has been already processed
      // 4. for all vetices w adjacent to v
      for(Integer w:G.adj(v)) {
        // 5. if w has not been explored yet (it is unmarked)
        if(!marked[w]) {
          // add w to the end of q
          q.offer(w);
          // marked w
	  marked[w]=true;
	  // set its distance to source
	  distTo[w]=distTo[v]+1;
          // add a bfs tree edge from v to w
    	  edgeTo[w]=v;
        }
      }
    }
  }
  
  // BFS: use an explicit queue to maintain unmarked vertices
  private void bfs(Digraph G, int s) {
    // Queue to maintain a collection of unmarked vertices:
    Deque<Integer> q=new ArrayDeque<Integer>(); // an empty q of vertices
    
    // 1. add the source vertex to the q:
    q.offer(s);
    // 2. visit the source vertex and process it:
    marked[s]=true;
    distTo[s]=0;

    // 3. while the queue of unmarked vertices is not empty
    while(!q.isEmpty()) {
      // remove the head of the queue (it has been processed already)
      int v=q.poll();
      // for all vertices w adjacent to v that has not been marked yet
      for(Integer w:G.adj(v)) {
        if(!marked[w]) {
          // 1. add w to the queue
          q.offer(w);
	  // 2. mark w as visited
          marked[w]=true;
	  // 3. add a BFS tree edge from v to w
          edgeTo[w]=v;
	  // 4. set the distance of w to the source,  one + distance v to the source:
          distTo[w]=1+distTo[v];
        }
      }
      // once q contained all UNMARKED vertices adjacent to v
      // from the head of the q, remove them one after and another and check their
      // corresponding adjcency list
    }
  }

  // API: Single source searchability, connectivity
  // 1. is there a directed path from the source to the given vertexv: s~>v
  public boolean hasPathTo(int v){
    validate(v);
    return marked[v];
  }
  // 2. if there exists a path FROM s TO v return such a path
  public Iterable<Integer> pathTo(int v){
    validate(v);
    // if there is no directed path from the source vertex to vertex v
    if(!hasPathTo(v))  return null;
    
    // Otherwise: reconstruct the path by following parent pointer from v to s backwards (using stack)
    Deque<Integer> path=new ArrayDeque<Integer>(); // an empty stack of vertices (vertex indexes)
    
    // starting from vertex v:
    while(distTo[v]!=0) {
      path.push(v);
      // move to the parent of v in DFS tree
      v=edgeTo[v];
    }
    // add the sources vertex to the path:
    path.push(v);

    return path;
  }
  // 3. the distance of the shortest path from v to the source
  public int distTo(int v) {
    // validate vertex v:
    validate(v);
    return distTo[v];
  }

  // vlidate the vertex index:
  private void validate(int v) {
    // number of vertices of the given graph:
    int n=marked.length;
    if(v<0||v>=n) throw new IndexOutOfBoundsException();
  }

}
