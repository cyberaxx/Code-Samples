/*
  Transitive closure. 
  The transitive closure of a digraph G is another digraph with the same set of vertices, 
  but with an edge from v to w if and only if w is reachable from v in G.
*/

public class TransitiveClosure {

  // instance variables:
  private Digraph digraph;
  private boolean[][] tc;

  // a vertex index array of booleans
  private boolean[] marked;

  // Constructor:
  public TransitiveClosure(Digraph G) {
    // initialize instance variables:
    digraph=new Digraph(G.V()); // an empty digraph with G.V() vertices and 0 edge
    marked=new boolean[G.V()]; // vertex index array
    tc=new boolean[G.V()][G.V()];  // // vertex index matrix
    // instantiate find all vertices of G
    for(int v=0; v<G.V(); v++) {

      // reset the marked array
      marked=new boolean[G.V()];
      // run dfs from v to find all vertices w reachable from v
      dfs(G, v);

      for(int w=0; w<G.V(); w++) {
        if(marked[w]) {
          // add an edge between v and w
	  digraph.addEdge(v,w);
        }
      }
      // add a column to tc matrix:
      tc[v]=marked; // all vertices that have been explored from source vertex v
    }
  } // Complexity O(VE+V^2), Space complexity (V^2 + V + E)

  private void dfs(Digraph G, int v) {
    // mark v visited:
    marked[v]=true;
    // for all vertices w adjacent to v in G
    for(Integer w:G.adj(v)) {
      // if w has not been explored from previous recursive dfs calls
      if(!marked[w]) {
        //recursively explore w:
	dfs(G,w);
      }
    }
  }

  // API: 
  public Digraph digraph(){return digraph;}
  public boolean reachable(int v, int w) {
    validate(v);
    validate(w);
    return tc[v][w];
  }

  // helper method
  private void validate(int v) {
    int V=marked.length;
    if(v<0||v>=V) throw new IndexOutOfBoundsException();
  }
}
