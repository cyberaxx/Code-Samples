/*
  DFS Ordering of visited vertices:
  Preorder: Put the vertex on a queue before the recursive calls.
  Postorder: Put the vertex on a queue after the recursive calls.
  Reverse postorder: Put the vertex on a stack after the recursive calls.
*/

import java.util.Deque;
import java.util.ArrayDeque;

public class DFSOrder {

  // instance variables:
  private Deque<Integer> pre; // q of visited vertices
  private Deque<Integer> post; // q of visited vertices
  private Deque<Integer> reversePost; // stack of visited vertices

  // to perform dfs in Digraph:
  // vertex index array of boolean to keep track of visited vertices
  private boolean[] marked;

  // Constructor:
  public DFSOrder(Digraph G) {
    marked=new boolean[G.V()]; // vertex index array
    pre=new ArrayDeque<Integer>(); // an empty q for vertices
    post=new ArrayDeque<Integer>(); // an empty q for vertices
    reversePost=new ArrayDeque<Integer>(); // an empty stack for vertices

    // going over all directed connected components of G
    for(int v=0; v<G.V(); v++)
      // if vertex v has not explored yet
      if(!marked[v]) 
        dfs(G, v);
  }

  // recursive dfs visit:
  private void dfs(Digraph G, int v) {
    // visit vertex v:
    marked[v]=true;
    // add v to the pre queue;
    pre.offer(v);

    // for all vertices w adjacent to v (in v's adjacency list in digraph G)
    for(Integer w:G.adj(v)) {
      // if w has not been marked yet
      if(!marked[w]) {
        // call dfs from vertex w:
	dfs(G, w);
      }
    }
    // now that all adjacent vertices to v has been marked
    post.offer(v);
    reversePost.push(v);
  }

  public Iterable<Integer> pre() {return pre;}
  public Iterable<Integer> post() {return post;}
  public Iterable<Integer> reversePost() {return reversePost;}
}
