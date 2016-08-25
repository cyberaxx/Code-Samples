import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Digraph {

  // instance variables:
  private final int V; // number of vertices of a directed graph instance
  private int E; // number of edges of a directed graph instance
  private List<Integer>[] adj; // adjacency list of a directed graph instance
  private int[] inDegree; // keep track of inDegrees' in constant time

  // Constructor:
  public Digraph(int v){
    if(v<0) throw new IllegalArgumentException("Number of vertices of a Digraph instance must be non-negative!");
    // initialzie instance variables;
    this.V=v;
    E=0; // no edge
    // initialize the adjacency list of a Digraph instance:
    adj=(List<Integer>[])new Object[V];// UGLY CASTING: java does not allow generic array creation
    inDegree=new int[V]; // initialize the array of inDegrees' for all vertices
    for(int i=0; i<V; i++)
      adj[i]=new LinkedList<Integer>(); // list of vertices connected to the vertex v by an edge from v to them
  }

  public Digraph(Digraph G) {
    this.V=G.V();
    this.E=G.E();
    this.inDegree=new int[V];
    adj=(List<Integer>[])new Object[V];
    for(int v=0; v<V; v++) {
      adj[v]=new LinkedList<Integer>(G.adj(v));
      inDegree[v]=G.inDegree(v);
    }
  }

  // instance methods:
  public int V(){return V;} // return number of vertices in a Digraph instance
  public int E(){return E;} // return number of directed edges in a Digraph instance

  // add an edge from vertex "from" to vertex "to"
  public void addEdge(int from, int to){
    validateVertex(from);
    validateVertex(to);

    adj[from].add(to);
    inDegree[to]++; 
    E++;
  }

  // return a list of vertices that are directly reachable from v
  public List<Integer> adj(int v) {
    // validate the given vertex v:
    validateVertex(v);
    return adj[v];
  }

  // returns the number of edges coming out of vertex v
  public int outDegree(int v){
    // validate the given vertex v:
    validateVertex(v);
    return adj[v].size();
  }

  public int inDegree(int v){
    // validate the given vertex v:
    validateVertex(v);
    return inDegree[v];
  }

  /*
  // returns the number of edges going into vertex v
  public int inDegree(int w){
    // validate the given vertex w:
    validateVertex(w);
    int inDegree=0;
    for(int v=0; v<V; v++)
      if(adj[v].contains(w))
        inDegree++;
    return inDegree;
  }
  */

  // helper methods:
  // vertices must be integer within [0 V-1] range:
  private void validateVertex(int v) {
    if(v<0 || v>=V-1) throw new IndexOutOfBoundsException("The vertex index is out of legal bounds!");
  }
}
