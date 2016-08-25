import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Graph {
  // instance variable:
  private final int V; // number of Vertices of the graph
  private int E; // number of Edges of the graph
  private List<Integer>[] adj; // adjacency List of the Graph: private ArrayList<List<Integer>> adj

  // Constructor:
  public Graph(int v) {
    // number of vertices can not be negative
    if(v<0) throw new IllegalArgumentException();

    this.V=v;
    this.E=0;

    // instantiate the adjacency list:
    adj=(List<Integer>[]) new Object[V]; // UGLY CASTIN: no generic array creation
    for(int i=0; i<V; i++) {
      adj[i]=new LinkedList<Integer>();
    } 
  }

  public Graph(Graph G) {

    // initialize number of vertices and edges
    this.V=G.V();
    this.E=G.E();

    // instantiate the adjacency list:
    adj=(List<Integer>[]) new Object[V]; // UGLY CASTIN: no generic array creation
    for(int v=0; v<V; v++) {
      adj[v]=new LinkedList<Integer>(G.adj(v));
    } 
  }

  // API:
  // instance methods:
  public int V(){return V;}
  public int E(){return E;}
  public void addEdge(int v, int w) {
    // validate vertices:
    validateVertex(v);
    validateVertex(w);
    // undirected graph: add v to the adj[w] and add w to adj[v]:
    adj[v].add(w); // adds w to the tail of DList adj[v] in constant time
    adj[w].add(v); // adds v to the tail of DList adj[w] in constant time
    // update the edge count:
    E++;
  }

  // returns an Iterable data type that represents all vertices adjacent to vertex v
  public List<Integer> adj(int v){return adj[v];}


  // Helper methods:
  private void validateVertex(int v){
    if(v<0 || v>=this.V) throw new IllegalArgumentException();
  }

  // static methods:
  public static int degree(Graph G, int v) {
    // compute the degree of vertex v in graph G:
    int degree=0;
    for(Integer vertex:G.adj(v))  degree++;
    return degree;
  }

  // find the max degree among vertice of a given instance of Graph type
  public static int maxDegree(Graph G) {
    int maxDegree=degree(G, 0);
    for(int v=1; v<G.V(); v++)
      if (maxDegree<degree(G, v))  maxDegree=degree(G, v);
    return maxDegree;
  }

  // find the min degree among vertice of a given instance of Graph type
  public static int minDegree(Graph G) {
    int minDegree=degree(G, 0);
    for(int v=1; v<G.V(); v++)
      if (minDegree>degree(G, v))  minDegree=degree(G, v);
    return minDegree;
  }

  // sum of degrees of all vertices in an undirected graph is 2*E (twice the number of edges because each edge counts twice (once from each end point)
  public static int averageDegree(Graph G) {return (2*G.E())/G.V();}

  // count number of self loops in a given instance of Graph type
  public static int SelfLoopCount(Graph G) {
    int count=0;
    for(int v=0; v<G.V(); v++)
      if(G.adj(v).contains(v)) count++;
    return count;
  }
}
