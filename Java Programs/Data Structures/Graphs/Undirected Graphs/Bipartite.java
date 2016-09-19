/*
  Two-colorability: 
  Can the vertices of a given graph be assigned one of two colors in such a way that
  no edge connects vertices of the same color? 

  DFS determines whether a graph has a bipartition;
  if so, return one; 
  if not, return an odd-length cycle.

  Solution: do the DFS, check the color of vertex and compare it with the color of caller vertex
*/

import java.util.Deque;
import java.util.ArrayDeque;

//  Follow graph algorithm design pattern and separate
//  the graph representation from graph processing algorithms
public class Bipartite {
  // instance variable
  // 1. vertex index array of booleans to keep track of visited vertices in from the source vertex in DFS
  private boolean[] marked;
  // 2. verex index array of parent pointers (to reconstruct the cycle)
  private int[] edgeTo;
  // 3. vertex index array of vertex colors (2 colors represented by a boolean array)
  private boolean[] color;
  // 4. boolean flag
  private boolean isBipartite; 
  // 5. odd length cycle with both end having same vertex color
  private Deque<Integer> cycle;
  
  // Constructor:
  public Bipartite(Graph G) {
    // extract number of vertices in G:
    int n=G.V();
    // initialize instance memebers of the class:
    marked=new boolean[n];
    edgeTo=new int[n];
    color=new boolean[n];
    // initially set bipartite to be true
    isBipartite=true;

    // the given graph may not be connected: so perform dfs to explor all connected components of the graph
    for(int v=0; v<n; v++) {
      // CHECK if g is bipartite:
      if(!isBipartite) return ;
      // if vertex v has not been visited yet
      if(!marked[v])
        dfs(G, v); // perform dfs from vertex v
    }
  }

  // API: query methods for clients
  public boolean isBipartite(){return isBipartite;}
  public Deque<Integer> oddCycle(){return cycle;}

  // helper method
  private void dfs(Graph G, int v) {
    // mark vertex v as visited:
    marked[v]=true;
   
    // recursively visit all UNvisited vertices adjacent to v in graph G
    for(Integer w:G.adj(v)) {

      // CHECK if g is bipartite:
      if(!isBipartite) return ;

      // if w has not been visited yet:
      if(!marked[w]) {
	// add v to w's parent pointer
	edgeTo[w]=v;
	// color w, the compliment of v
        color[w]=!color[v];
        // recursively visit w
        dfs(G, w);
      }

      // Otherwise: if w has been already visited from other vertices (or w is inface the parent pointer of v)
      // compare v and w's colors: for G to be bipartite color[v] and color[w] must be different
      else if(color[v]==color[w]) {
	// G is not bipartite because two connected vertices have a same color:
        isBipartite=false;
        // set up the odd cyle
        cycle=new ArrayDeque<Integer>(); // empty stack of vertices
        // reconstruct the path from v to w by following parent pointers
        int x=v;
        while(x!=w) {
 	  cycle.push(x);
	  // move to the parent:
 	  x=edgeTo[x];
	}
        // add v-w to the cylce
        cycle.push(w);
        cycle.push(v);
      }
    }
  }
}
