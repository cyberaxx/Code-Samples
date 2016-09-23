/*
  Find all Strongly Connected Components (directed cycles) in a Digraph using two passes of DFS: O(V+E)

  Following graph algorithms design pattern by decoupling the Digraph representation from Digraph processing
  Pass a Digraph to the processing algorithm and ask queries from the processing abstraction to fulfill client queries
*/

import java.util.Deque;
import java.util.ArrayDeque;

public class SCC {
  // instance variables
  // 1. number of scc (number of directed cycles)
  private int scc;
  // 2. a vertex index array of scc ids
  private int[] id;
  // 3. an array of scc sizes
  private int[] size;

  // dfs:
  // a vertex index array of booleans to keep track of explored vertices
  private boolean[] marked;

  // Constructor:
  public SCC(Digraph G) {
    // initializes the instance variables:
    scc=0; // number of scc
    id=new int[G.V()]; // vertex index array
    size=new int[G.V()];
    
    // 1. compute dfs on G's reverese graph and compute its reverse post order traversal of it vertices 
    Digraph Gr=G.reverse();
    DFSOrder dfs=new DFSOrder(Gr); // run dfs on Gr vertice in no particular order
    Iterable<Integer> order=dfs.reversePost();

    // run dfs again on G's vertices in reverse post order:
    for(Integer v:order) {
      // if vertex v has not been explored from previous calls already
      if(!marked[v]) {
	// explor all vertices reachable from vertex v in Digraph G:
        dfs(G, v);
        // increase number of scc
	scc++;
      }
    }
  }

  // dfs: recursive
  private void dfs(Digraph G, int v) {
    // visite vertex v
    marked[v]=true;
    // set the scc that v belongs to
    id[v]=scc;
    // set the size of scc that v belogns to
    size[id[v]]++;

    // for all vertices like w adjacent to vertex v in G
    for(Integer w:G.adj(v)) {
      // if w has not been explored already from previous dfs calls
      if(!marked[w]) {
        // explore the graph from w onward:
 	dfs(G, w);
      }
    }
  }
  
  // API:
  // return an Iterable collection of all vertices in scc with id "sccId"
  public Iterable<Integer> scc(int sccId) {
    // a queue of vertices that belong to a same scc:
    Deque<Integer> q=new ArrayDeque<Integer>(); // empty q
    // iterates over the vertex index array of scc ids:
    for(Integer v:id) {
      if(id[v]==sccId)
        q.offer(v);
    }
    return q;
  }
  // given a vertex v returns a scc id that vertex v belongs to:
  public int id(int v) {
    validate(v);
    return id[v];
  }
  // given the vertex v return the number of vertices in v's scc
  public int size(int v) {
    validate(v);
    return size[id[v]];
  }

  // number of scc in a given digraph instance:
  public int sccCount(){return scc;}
  // if a digraph  instance is strongly connected: it has only one connected commponent
  public boolean isStronglyConnected() {return scc==1;}

  // helper methods:
  private void validate(int v) {
    int V=id.length;
    if(v<0||v>=V) throw new IndexOutOfBoundsException();
  }
}
