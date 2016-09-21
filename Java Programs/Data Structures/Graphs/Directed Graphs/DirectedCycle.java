/*
  In directed graphs there are 4 types of edges:
  1. Tree edge: An edge FROM a MARKED vertex TO an UNMARKED vertex (DFS edge) 
  2. Back egde: An edge FROM a MARKED vertex TO its ascendant MARKED vertex in the same scc (Cycle edge)
  3. Forward egde: An edge FROM a MARKED vertex TO its descendant MARKED vertex in the same SCC 
  4. Cross edge: An edge FROM a MARKED vertex TO another MARKED vertex in the different SCC 

  Cycle decetion algorithm based on DFS use this edge classification implicitly in order to find
  a directed cycle in a directed graph.
*/
import java.util.Deque;
import java.util.ArrayDeque;

public class DirectedCycle {
  // intance variables
  // A. For cycle itself:
  // a collection of vertices that represent a cycle
  private Deque<Integer> cycle; // use stack to construct cycle from parent pointers

  // B. For cycle detection subroutine:
  // 1. vertex index array of boolean to keep track of visited vertices in DFS
  private boolean[] marked;
  // 2. vertex index array of parent pointer to reconstruct the cycle from
  private int[] edgeTo;
  // 3. vertex index array of vertices (currently under process in recursive stack)
  private boolean[] inStack; // undirected graph does NOT need this one (because non-tree edges were simply back edge) unlike here 
  // we have freaking 4 different types, once we encounter a marked vertex in our search we have to find out if it is back edge or any of those
  // other two types to identify a cycle 

  // Constuctor:
  public DirectedCycle(Digraph G) {
    // Given an efficient representation of a Digraph (as a vertex index array of adjacency lists)
    // initialize the instance variables for cycle detection:
    marked=new boolean[G.V()]; // vertex index array
    edgeTo=new int[G.V()]; // vertex index array
    inStack=new boolean[G.V()]; // vertex index array

    // trivial cases: 1. selfloops and 2. parallel edges
    if(hasSelfLoop(G)) return ;

    // Otherwise:
    // from one arbitary vertex start exploring the Digraph and explor all its SCC util a cycle was found
    for(int v=0; v<G.V(); v++) {
      // if a cycle has been found
      if(cycle!=null) return ; // terminate the loop

      // if vertex v has not been already explored from previous DFS calls
      if(!marked[v]) {
        dfs(G, v); // unlike the undirecte graph we don't need to pass v's direct parent to the dfs (because edges are directed and v's parent is not on its adjacency list (not necessarily)
      }
    }
  }

  private void dfs(Digraph G, int v) {
    // start exploring the vertex v:
    // 1. mark v
    marked[v]=true;
    // 2. pass v to the recursive stack
    inStack[v]=true;

    // 3. recursively EXPLORE all vertices w adjacent to vertex v
    for(Integer w:G.adj(v)) {
      // if a cycle has been found
      if(cycle!=null) return ; // terminate the loop

      // A. if w has not been marked: recursively explore w dfs
      if(!marked[w]) {
        // v->w edge is a tree edge, add it the parent poniters
        edgeTo[w]=v;
        // explore w
	dfs(G, w);
      }

      /* if w has been previously marked, there would be a couple of possible cases:
  	 1. w's explorer was in the same SCC as v (this means a back edge and a directed cyle):
	    if so, then w must be in the recursive stack of the same component.
	 2. w's explorer was in a different SCC than v (so w has been visited previously but now for the first time from a vertex in v's SCC) - not a cycle
      */
      else if(inStack[w]) {
        // v->w is a back edge so it's participating in a cycle:
        cycle=new ArrayDeque<Integer>();
        // starting from w follow parent pointers backward to reach v
	int x=v;
        while(x!=w) {
	  cycle.push(x);
	  x=edgeTo[x];
	}
	// add egde v->w to the cycle
        cycle.push(w);
        cycle.push(v);
      }
    }

    // v is fully explored and ready to get popped out of the stack
    inStack[v]=false;
  }

  // API: 
  // Is the given Digraph acyclic?
  public boolean hasCycle(){return cycle!=null;}
  // If the given Digraph have a directed cycle return one:
  public Iterable<Integer> cycle(){
    if(!hasCycle()) return null;
    return cycle;
  }

  // O(V+E)
  private boolean hasSelfLoop(Digraph G) {
    // for all vertices in G check their corresponding adj list
    for(int v=0; v<G.V(); v++) {
      // for all vertices w adjacent to v
      for(Integer w:G.adj(v)) {
        // if v==w : selfloop
        if(v==w) {
          // build the cycle:
          cycle=new ArrayDeque<Integer>();
 	  cycle.push(v);
 	  cycle.push(w);
	  // it has a selfloop
          return true;
        }
      }
    }
    return false;
  }
}
