import java.util.Deque;
import java.util.ArrayDeque;

/*
  Cycle detection: 
  Is a given graph acyclic? 
  Use depth-first search to determine whether a graph has a cycle, 
  and if so return one in time proportional to V + 2E in the worst case.

  NOTE: Undirected graph only has TREE EDGE and BACK EDGE
 
  NOTE: having a cycle is equivalent to DFS having a BACK EDGE
  BACK EDGE: an edge from a visited vertex to its visited ancestor!

*/

public class Cycle {
  // Graph algorithm design pattern: 
  // separate graph representation from graph processing

  // instance variables:
  // 1. a sequence of integer vertices that characterizes the cycle
  private Deque<Integer> cycle;
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
    // trivial cases: a. selfloop, b. parallel edges
    if(hasSelfLoop(G)) return ; // terminate the search for the cycle
    if(hasParallelEdge(G)) return ; // terminate the search for the cycle

    // USE DFS to find cycle based on presence of a BACK EDGE
    // pass both the source vertex for searach and its visited ancestor
    // since a graph can be disconnected (have many connected components)
    for(int v=0; v<G.V(); v++) {
      // if the vertex v has not been visited in previous search attempts
      if(!marked[v])
        // start the dfs search from v and set the ancestor vertex to -1
        dfs(G, -1, v);
    } 
  }

  // API: processed results for client
  public boolean hasCycle(){return cycle!=null;}
  public Iterable<Integer> cycle(){return cycle;}

  // helper methods:
  private void dfs(Graph G, int parent, int v) {
    // visit the vertex:
    marked[v]=true;

    // recursively visit all UNvisited vertices adjacent to v in graph G:
    for(Integer w:G.adj(v)) {
      // if a cycle has been already found terminate the recursion
      if(hasCycle()) return ;

      // if w has not been visited: normal dfs
      // any edge from visited to unvisited node in the graph is a TREE EDGE
      if(!marked[w]) {
        // add v to the parent pointer of array for w
        edgeTo[w]=v;
        // recurse on w:
        dfs(G, v, w); // set v as w's ancestor
      }

      // Otherwise: if w has been visited already and w is not the same node as v's parent: 
      // an edge between two distinct visited vertice (v and w) is a back edge
      else if(parent!=w) {
 	 // construct the found cycle
	 cycle=new ArrayDeque<Integer>(); // empty stack
         
         // start from the vertex w and go back to v by following parent pointers
         cycle.push(w);
         while(edgeTo[w]!=v) {
  	   cycle.push(edgeTo[w]);
	   w=edgeTo[w]; // move to the parent of w in DFS Tree
	 }
         // add v the cycle
         cycle.push(v);
      }
    }
  }

  // returns true if G has any vertex with a selfloop
  private boolean hasSelfLoop(Graph G) {
    // extract the number of vertices in G
    int n=G.V();    
    // for each vertex in G:
    for(int v=0; v<n; v++) {
      // traverse the adjacency list associated to the vertex v
      for(Integer w:G.adj(v)) {
        // if any vertex in the list was the same as v return true
        if(w==v) {
	  // construct the cycle:
	  cycle=new ArrayDeque<Integer>();
	  cycle.push(w);
	  cycle.push(v);
	  // return true
          return true;
	}
      } 
    }
    return false;
  } // O(V+2E) to go through the enitre graph representation

  // returns true if G has any parallel edges
  private boolean hasParallelEdge(Graph G){return true;} 

}
