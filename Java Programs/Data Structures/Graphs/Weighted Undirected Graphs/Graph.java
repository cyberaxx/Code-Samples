import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Graph {

  // instance variables:
  private final int V; // number of vertices
  private int E; // number of Edges
  private List<Edge>[] adj; // adjacency list of the Graph instance

  // Constructor:
  public Graph(int v) {
    if(v<0) throw new IllegalArgumentException("Number of vertices of a Graph instance must be non-negative!");
    // initialize instance variables:
    this.V=v;
    E=0;
    // intialize adj list of the Graph instance:
    adj=(List<Edge>[]) new List[V]; // UGLY CASTING: Java does not allow generic array creation
    for(int i=0; i<V; i++)  adj[v]=new LinkedList<Edge>();
  }

  public Graph(Graph G){
    this.V=G.V();
    this.E=G.E();
    adj=(List<Edge>[])new List[V];
    for(int v=0; v<V; v++)
      adj[v]=new LinkedList<Edge>(G.adj(v));
  }

  // Define Edge as a inner class of Graph for packaging convenience (both classes are top-level classes)
  private class Edge implements Comparable<Edge> {
    // instance variables:
    // endpoints:
    private final int v;
    private final int w;
    private final double weight;

    // constructor:
    public Edge(int v, int w, double weight) {
      // check if input arguments are all valid: since Edge is an inner class it has direct access to instance member of its enclosing class
      validateVertex(v);
      validateVertex(w);
      if(Double.isNaN(weight)) throw new IllegalArgumentException();
      // initialize edge fields
      this.v=v;
      this.w=w;
      this.weight=weight;
    }

    /* implement java Comparable interface by overriding
       its abstract compareTo method: generic comparison based on edge weights
    */
    @Override
    // < > are not total oreder operation for type Double: because they do NOT satisfy TOTALITY condition 
    public int compareTo(Edge e){return Double.compare(this.weight, e.weight);}
    public int either(){return this.v;}
    public int other(int v){
      if (this.v==v)  return this.w;
      if (this.v==w)  return this.v;
      else throw new IllegalArgumentException("No such an end point exists!");
    }
    public double wieght(){return this.weight;}
  }

  // Graph API: 
  // instace methods
  public int V(){return V;} // number of vertices of a Graph instance
  public int E(){return E;} // each has been counted twice (once from each endpoints)
  public List<Edge> adj(int v) {
    validateVertex(v);
    return adj[v];
  }

  public void addEdge(int v, int w, double weight) {
    validateVertex(v);
    validateVertex(w);
    if(Double.isNaN(weight)) throw new IllegalArgumentException();

    // instantiate form the Edge data type:
    Edge e=new Edge(v, w, weight);
    // add the edge to the adjacency list of v and w
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  public void addEdge(Edge e) {
    // retrieve its endpoints:
    int v=e.either();
    int w=e.other(v);
    validateVertex(v);
    validateVertex(w);
    // add the edge to the adjacency list of v and w
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  public int degree(int v) {
    validateVertex(v);
    return adj[v].size();
  }

  // Helper methods:
  private void validateVertex(int v) {
    if(v<0 || v>=this.V) throw new IndexOutOfBoundsException("The given vertex index is out of legal bounds!");
  }
}

