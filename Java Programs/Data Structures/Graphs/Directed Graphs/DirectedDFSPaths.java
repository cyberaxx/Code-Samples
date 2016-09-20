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
public class DirectedDFSPaths {
  // instance variable:
  // 1.a vertex index array of boolean to keep track of visited vertices (avoid exploring the same vertex more than once)
  private boolean[] marked;
  // 2. a vertex index array of parent pointers to recontstruct DFS path
  private int[] edgeTo;
  // 3. the source of DFS exploration
  private int source; 

  // Constructor:
  // constructor takes a digraph and a source vertex as input
  public DirectedDFSPaths(Digraph G, int s) {
    // initialize the visited array and the edgeTo array
    marked=new boolean[G.V()]; // vertex index array of booleans
    edgeTo=new int[G.V()]; // vertex index array of vertices (vertex indexes)
    this.source=s; // source vertex of DirectedDFS exploration

    // run on pass of DFS from the source vertex
    DFSVisit(G, s);
  }

  // Recursive DFS:
  /* takes a graph (or a vertex index array of adjacency lists that representes a graph)
     and a vertex, visit the vertex and recursively explore all UNvisited vertex adjacent to it (depth-first)
  */

  private void DFSVisit(Digraph G, int v) {
    // 1. visit vertex v
    marked[v]=true;
    
    // 2. for all vertices like w in v's adjacency list
    for(Integer w:G.adj(v)) {
      // 3. if w has not been explored already (from previous recusrive calls on othe vertices of the G)
      if(!marked[w]) {
        // 4. add v as w's parent in DFS path from the source vertex to w
        edgeTo[w]=v; // denotes v->w directed edge
        // 5. recursively explor w
        DFSVisit(G, w);
      }
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
    while(v!=source) {
      path.push(v);
      // move to the parent of v in DFS tree
      v=edgeTo[v];
    }
    // add the sources vertex to the path:
    path.push(source);

    return path;
  }
  
  // vlidate the vertex index:
  private void validate(int v) {
    // number of vertices of the given graph:
    int n=marked.length;
    if(v<0||v>=n) throw new IndexOutOfBoundsException();
  }

}
