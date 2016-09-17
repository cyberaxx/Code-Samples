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

/*
  follow graph algorithm design pattern and separate
  the graph representation from graph processing algorithms
*/


public class Bipartite {
  // instance variable
  // 1. vertex index array of booleans to keep track of visited vertices in from the source vertex in DFS
  private boolean[] marked;
  // 2. verex index array of parent pointers (to reconstruct the cycle)
  private int[] edgeTo;
  // 3. vertex index array of vertex colors (2 colors represented by a boolean array)
  private boolean[] colors;
  // 4. odd length cycle with both end having same vertex color
  private Deque<Integer> cycle;
  

  public Bipartite(Graph G) {}

  // API: query methods for clients
  public boolean isBipartite(){return cycle==null;}
  public Deque<Integer> cycle(){return cycle;}

  // helper method
  private void dfs(Graph G, int v) {

  }
}
