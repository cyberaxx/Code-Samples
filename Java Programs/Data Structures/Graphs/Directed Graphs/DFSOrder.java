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
  // vertex index array of intergers to maintain number visited vertex visited before v (rank of v)
  private int[] preRank;
  private int[] postRank;

  // to perform dfs in Digraph:
  // vertex index array of boolean to keep track of visited vertices
  private boolean[] marked;

  // Constructor:
  public DFSOrder(Digraph G) {
    marked=new boolean[G.V()]; // vertex index array
    preRank=new int[G.V()]; // vertex index array
    postRank=new int[G.V()]; // vertex index array
    pre=new ArrayDeque<Integer>(); // an empty q for vertices
    post=new ArrayDeque<Integer>(); // an empty q for vertices

    // going over all directed connected components of G
    for(int v=0; v<G.V(); v++)
      // if vertex v has not explored yet
      if(!marked[v]) 
        dfsIterative(G, v);
	//dfs(G,v);
  }

  private void dfsIterative(Digraph G, int s) {
    // explict stack of vertices:
    Deque<Integer> stack=new ArrayDeque<Integer>();
    boolean isBlack=true;
    // add the source to the stack
    stack.push(s);

    // recursive call stack:
    while(!stack.isEmpty()) {
      // sneak peek at the top of the call stack
      int v=stack.peek();

      // if vertex v has not been marked visited already
      if(!marked[v]) {
        // mark v visited
        marked[v]=true;
        // add it to pre q
      	pre.offer(v);
	preRank[v]++;
      }
    
      // set isBlack to true
      isBlack=true;
      // recursively push all UNvisited vertices w adjacent to v into the stack
      for(Integer w:G.adj(v)) {
        // if w has not been visited from other DFS calls from other vertices of G:
        if(!marked[w]) {
          // add w to the call stack
          stack.push(w);
	  // v is not black yet
          isBlack=false;
        }
      } 
      // UnFOLD recursive call:
      // if all adjacent of v has been visited already pop it out of the stack
      if(isBlack) {
        int u=stack.pop();
        post.offer(u);
	postRank[u]++;
      }
    }
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
  }

  public int preRank(int v) {
    validate(v);
    return preRank[v];
  }
  public int postRank(int v) {
    validate(v);
    return postRank[v];
  }
  public Iterable<Integer> pre() {return pre;}
  public Iterable<Integer> post() {return post;}
  public Iterable<Integer> reversePost() {
    Deque<Integer> reversePost=new ArrayDeque<Integer>(); //an empty stack  
    for(Integer vertex:post)
      reversePost.push(vertex);
    return reversePost;
  }

  private void validate(int v) {
    int V=marked.length;
    if(v<0||v>=V) throw new IndexOutOfBoundsException();
  }
}
