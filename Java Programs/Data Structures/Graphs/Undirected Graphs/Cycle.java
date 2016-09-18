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
      if(hasCycle()) return ;
      // if the vertex v has not been visited in previous search attempts
      if(!marked[v])
        // start the dfs search from v and set the ancestor vertex to -1
        //dfs(G, -1, v);
	dfsIterative(G, v);
    } 
  }

  // API: processed results for client
  public boolean hasCycle(){return cycle!=null;}
  public Iterable<Integer> cycle(){return cycle;}

  // Iterative dfs:
  private void dfsIterative(Graph G, int s) {
    // s is the starting vertex for search

    // add s to the recursion stack:
    Deque<Integer> stack=new ArrayDeque<Integer>();
    stack.push(s);
    int parent;

    // while the recursion stack is not empty (search for a back edge)
    while(!stack.isEmpty() && !hasCycle()) {
      // Perfom DFS:
      // sneek peek at the top of the stack:
      int v=stack.peek();
      // visit the vertex v
      marked[v]=true;

      // flag to keep track of vertices with no unvisited adjacent vertex
      boolean flag=false;

      // visit all UNvisited vertices adjacent to v in G
      for(Integer w:G.adj(v)) {
        // if a cycle has been already found terminate the recursion
        if(hasCycle()) return ;

	// define the parent:
        parent = (v==s) ? -1 : edgeTo[v];

        if(!marked[w]) {
	  // set the parent pointer:
 	  edgeTo[w]=v;
	  // push w to the recursive stack
 	  stack.push(w);
          flag=true;	
        }

        // else if w has been visited already and w is not the parent vertex of v
        else if(w!=parent) {
          // v-w is a back edge that connects two connected vertices and creates a cycle:
	  cycle=new ArrayDeque<Integer>(); // empty stack of vertices
          // starting from v follow parent pointer until reaching w
          int x=v;
	  while(x!=w) {
	    cycle.push(x);
	    x=edgeTo[x]; // x move up to it caller vertex
	  }
          // add edge v-w to the path
          cycle.push(w);
          cycle.push(v);
	}
      }
      // remove the vertex v from the stack
      if(flag==false) stack.pop();
    }
  }

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
         
         // start from the vertex v and go back to w by following parent pointers
	 int x=v;
         while(x!=w) {
  	   cycle.push(x);
	   x=edgeTo[x]; // move to the parent of v in DFS Tree
	 }
         // add back edge v-w to the cycle:
         // add w the cycle
         cycle.push(w);
         // add v to the cycle
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
  private boolean hasParallelEdge(Graph G) {
    // parallel edges from the adjacency list of vertex v:
    // if vertex w appears more than once on v's adjacency list v-w is a parallel edge

    int n=G.V();
    // use the marked array to find out duplicates in the adjacency list:
    marked=new boolean[n];

    // for each vertex v in G:
    for(int v=0; v<n; v++) {
      // search the adajency list of v
      for(Integer w:G.adj(v)) {
        // w has been already seen in the adjacency list
        if(marked[w]) {
	  // there is a parallel edge and therefore a cycle:
          cycle=new ArrayDeque<Integer>(); // empty stack of integer
	  cycle.push(v);
	  cycle.push(w);
	  cycle.push(v);
	  return true;
	}
	
	else {
	  // marked w as visited and move forward
          marked[w]=true;
	}
      }

      // reset the marked array: this is more efficient that re-instantiating the marked array before the inner foreach loop
      for(Integer w:G.adj(v)) {
	marked[w]=false;
      }
    }
    // parallel edge has not been found
    return false;
  } 

}
