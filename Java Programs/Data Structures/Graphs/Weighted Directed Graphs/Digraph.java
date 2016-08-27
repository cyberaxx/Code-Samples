import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Digraph{
  // instance variables:
  private final int V; // number of vertices
  private int E; // number of edges
  private int [] inDegree; // the indegree of all vertices of the graph
  private List<Edge>[] adj; // adj list of the a graph instance

  // Constructor:
  public Digraph(int v) {
    if(v<0) throw new IllegalArgumentException("Number of vertices of a Digraph instance must be non-negative!");
    // initialize instance fields:
    this.V=v;
    this.E=0;
    inDegree=new int[V];
    adj=(List<Edge>[])new List[V]; // UGLY CASTING
    for(int i=0; i<V; i++)
      adj[i]=new LinkedList<Edge>(); // instantiate a LinkedList collection of generic type Edge
  }

  public Digraph(Digraph G) {
    this.V=G.V();
    this.E=G.E();
    this.inDegree=new int[V];
    this.adj=(List<Edge>[])new List[V]; // UGLY CASTING
    for(int v=0; v<V; v++) {
      this.inDegree[v]=G.inDegree(v);
      this.adj[v]=new LinkedList<Edge>(G.adj(v));
    }
  }

  // API:
  // instance methods:
  // validateVertex(v)
  public int V(){return V;}
  public int E(){return E;}
  public int inDegree(int v){
    validateVertex(v);
    return inDegree[v];
  }
  public int outDegree(int v) {
    validateVertex(v);
    return adj[v].size();
  }
  public List<Edge> adj(int v){
    validateVertex(v);
    return adj[v];
  }
  public void addEdge(int from, int to, double weight){
    validateVertex(from);
    validateVertex(to);
    if(Double.isNaN(weight)) throw new IllegalArgumentException();
    Edge edge=new Edge(from, to, weight);
    adj[from].add(edge);
    inDegree[to]++;
    E++;
  }
  public void addEdge(Edge e){
    int from=e.from();
    int to=e.to();
    validateVertex(from);
    validateVertex(to);
    if(Double.isNaN(e.weight)) throw new IllegalArgumentException();
    adj[from].add(e);
    inDegree[to]++;
    E++;
  }

  // helper methods:
  // vertices must be integer within [0 V-1] range:
  private void validateVertex(int v) {
    if(v<0 || v>=V-1) throw new IndexOutOfBoundsException("The vertex index is out of legal bounds!");
  }

  /* define the edge abstraction as an inner class:*/
  private class Edge implements Comparable<Edge>{
    private final int from;
    private final int to;
    private final double weight;

    public Edge(int from, int to, double weight) {
      validateVertex(from);
      validateVertex(to);
      if(Double.isNaN(weight)) throw new IllegalArgumentException();
      this.from=from;
      this.to=to;
      this.weight=weight;
    }

    public int from(){return this.from;}
    public int to(){return this.to;}
    public double weight(){return this.weight;}

    @Override
    public int compareTo(Edge e){return Double.compare(this.weight, e.weight);}
  }
}
