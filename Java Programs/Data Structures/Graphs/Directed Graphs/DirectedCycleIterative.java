/*
  In directed graphs there are 4 types of edges:
  1. Tree edge: An edge FROM a MARKED vertex TO an UNMARKED vertex (DFS edge) 
  2. Back egde: An edge FROM a MARKED vertex TO its ascendant MARKED vertex in the same directed CC (Cycle edge)
  3. Forward egde: An edge FROM a MARKED vertex TO its descendant MARKED vertex in the same directed CC 
  4. Cross edge: An edge FROM a MARKED vertex TO another MARKED vertex in the different directed CC 

  Cycle decetion algorithm based on DFS use this edge classification implicitly in order to find
  a directed cycle in a directed graph.
*/
import java.util.Deque;
import java.util.ArrayDeque;

public class DirectedCycleIterative {
  // intance variables
  // A. For cycle itself:
  // a collection of vertices that represent a cycle
  private Deque<Integer> cycle; // use stack to construct cycle from parent pointers

  // B. For cycle detection subroutine:
  // 1. vertex index array of boolean to keep track of visited vertices in DFS
  private boolean[] marked;
  // 2. vertex index array of parent pointer to reconstruct the cycle from
  private int[] edgeTo;

  // Constuctor:
  public DirectedCycleIterative(Digraph G) {
    // Given an efficient representation of a Digraph (as a vertex index array of adjacency lists)

    // initialize the instance variables for cycle detection:
    marked=new boolean[G.V()]; // vertex index array
    edgeTo=new int[G.V()]; // vertex index array

    // Otherwise:
    // from one arbitary vertex start exploring the Digraph and explore all its directed CC util a cycle was found
    for(int v=0; v<G.V(); v++) {
      // if a cycle has been found
      if(cycle!=null) return ; // terminate the loop

      // if vertex v has not been already explored from previous DFS calls
      if(!marked[v]) {
        dfs(G, v); // unlike the undirecte graph we don't need to pass v's direct parent to the dfs (because edges are directed and v's parent is not on its adjacency list (not necessarily)
      }
    }
  }

  private void dfs(Digraph G, int s) {
    // DFS stack:
    Deque<Integer> stack=new ArrayDeque<Integer>(); // empty stack
    boolean[] inStack=new boolean[G.V()]; // vertex index array to keep track of nodes in the stack
    boolean black=true; // all vertices adjacent to the top of stack has been already marked

    // start expolring the digraph G from vertex s:
    // push the vertex s in to the explicit recursive stack
    stack.push(s); // equivalent to dfs(G,s)
    
    while(!stack.isEmpty()) {
      // sneak peek at the top of the recursion stack:
      int v=stack.pop();
      // mark v (GREY)
      marked[v]=true;
      // makr v in stack
      inStack[v]=true;

      // for all vertices w adjacent to v
      for(Integer w:G.adj(v)) {

        // if a cycle has been found
        if(cycle!=null) return ; // terminate the loop

        // if w has been NOT marked already v->w is a Tree edge
	if(!marked[w]) {
          edgeTo[w]=v;
	  // push w to the stack
	  stack.push(w);
          // set black flag to false
	  black=false;
	}

        /* if w has been previously marked, unlike undirected graph, there would be 3 different possible cases:
    	 1. Back edge (Cycle edge): w is v's anscestor, so there must be a tree path from w to v like w->......->v and because of the paranthesization rule (w must be still in the stack)
	    while we are exploring v.
         2. Forward edge: if w is a descendant of v (so it has been already visited and get popped out of the recursive stack)
         3. Cross Edge: if w has been visited from other directed CC of a graph or from its sibilings
        */
        else if(inStack[w]) {
	  // w is an ancestor of of v so v->w is a back edge
	  cycle=new ArrayDeque<Integer>();

          // start from v follow parent pointer backward to reach w:
	  int x=v;
	  while(x!=w) {
	    cycle.push(x);
	    // follow parent pointers
	    x=edgeTo[x];
	  }
	  // add edge v->w to the cycle:
          cycle.push(w);
          cycle.push(v);
        }
      }
      
      // if all adjacent's of vertex v has been explored: pop it from the recursion stack
      if(black) {
	inStack[stack.pop()]=false; // pop the vertex from the recursion stack
      }
    }
  }

  // API: 
  // Is the given Digraph acyclic?
  public boolean hasCycle(){return cycle!=null;}
  // If the given Digraph have a directed cycle return one:
  public Iterable<Integer> cycle(){
    if(!hasCycle()) return null;
    return cycle;
  }

}
