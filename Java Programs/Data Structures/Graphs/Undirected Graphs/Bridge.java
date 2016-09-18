/*
   Bridge: A bridge (or cut-edge) is an edge whose deletion increases the number of connected components. 
   
   Equivalently, an edge is a bridge if and only if it is not contained in any cycle. 

   DFS finds bridges in a graph.

   Solutions: back edges (all edges participating in cycles are not bridges)
              all edges on a simple path from DFS are bridge edges.
*/

import java.util.Deque;
import java.util.ArrayDeque;

public class Bridge {
  private Deque<String> bridges;
  private int bridgeCount;

  // 1. vertex index array that determines how may vertex have been visited before visitin vertex (v)
  private int[] pre; // preorder
  // 2. vertex index array that keep track of vertices on an alternative path to vertex v from the DFS source
  private int[] low;
  // 3. a counter that keeps track of how many vertices have been visited so far
  private int count;
 
  // Constructor:
  public Bridge(Graph G) {
    // extract number of vertices:
    int n=G.V();

    // initialize instance fields:
    count=0;
    bridgeCount=0;
    bridges=new ArrayDeque<String>();
    pre=new int[n];
    low=new int[n];
    for(int v=0; v<n; v++) {
      pre[v]=-1;
      low[v]=-1;
    }

    // Perform DFS on all connected components of graph G to find cut edges (bridge)
    for(int v=0; v<n; v++) {
      // if vertex v has not been visited already
      if(pre[v]==-1)
        dfs(G, v, v); // run DFS from vertex v, where its parent is itself (its the source)
    }
  }

  // API:
  public boolean isEdgeConnected(){return bridgeCount==0;}
  public int bridgeCount(){return bridgeCount;}
  public Iterable<String> bridges(){
    if(bridges.isEmpty()) return null;
    return bridges;
  }

  // helper method: dfs
  // similar to cycle checking implementation of DFS
  private void dfs(Graph G, int parent, int v) {
    // increase the number of visited vertices:
    count++;
    // visit vertex v:
    pre[v]=count; // setting pre[v] to number of vertices have been visited before visiting v
    low[v]=pre[v]; // initially set low[v] to pre[v]



  }
}
