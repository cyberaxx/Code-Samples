import java.util.Deque;
import java.util.ArrayDeque;

/*
  Follwoing graph aglorithms design pattern
  by decoupling the graph representation from grapg processing.

  Graph abstraction provides an effiecient representation of Digraphs and
  all basic operations that allows graph processing algorithms process the graph

  Graph processing algorithm takes a Graph as an input, and process it using its instance method
  and provide solutions for cliet of graph processing applications 
*/
public class DirectedDFS {
  // instance variable:
  // 1. a vertex index array of booleans to keep track on visited vertices (this gaurantees the linear time performance of DFS)
  private boolean [] marked;
  // 2. a vertex index array of vertices (parent pointer) to reconstruct the path
  private int[] edgeTo;
  // 3. the sources vertex of the search (specified by DFS caller)
  private int source;

  
  // Constructor: all processing would be done here
  public DirectedDFS (Digraph G, int s) {
    // initialize the source of DFS search
    this.source=s;

    // initialize all vertex index arrays required to perform DFS efficiently:
    marked=new boolean[G.V()]; // G.V() returns number of vertices in graph G
    edgeTo=new int[G.V()];

    /* on pass of DFS from the source vertex to reach to any vertex in G
       that is reachable by thes source vertex
    */

     DFSVisit(G, s);
  }

  // API: Single source searchability, connectivity
  // is there a directed path from the source to the given vertex:
  public boolean hasPathTo(int v){
    validate(v);
    return marked[v];
  }
  // is source and v are connected through a directed path from source to v
  // return such a path:
  public Iterable<Integer> pathTo(int v){
    validate(v);
    if(!hasPathTo(v)) return null;
   
    // reconstruct the path using a stack (because we have to follow parent pointers backwards)
    Deque<Integer> path=new ArrayDeque<Integer>(); // an empty stack of vertices (integer indexes)

    // start from v, following parent pointer backward util reaching the source vertex:
    while(v!=source) {
       path.push(v); // add vertex v to the path
       v=edgeTo[v]; // move up to v's parent in DFS tree
    }
    // add the source to the path
    path.push(source);

    return path;
  }

  // DFS:
  /* takes a graph (or a vertex index array of adjacency lists that representes the graph)
     and a vertex, visit the vertex and recursively visit all UNvisited vertex adjacent to it
  */
  private void DFSVisit(Digraph G, int v) {
    // visite v:
    marked[v]=true;

    // visit all vertices w adjacent to v (in graph G) that has not been visited yet
    for(Integer w:G.adj(v)) {
      if(!marked[w]) {
	// add v to the path to visit w
        edgeTo[w]=v;
        // visit w and explore its adjacent vertices
        DFSVisit(G, w);
      }
    }
  }
  
  // vlidate the vertex index:
  private void validate(int v) {
    // number of vertices of the given graph:
    int n=marked.length;
    if(v<0||v>=n) throw new IndexOutOfBoundsException();
  }

}
