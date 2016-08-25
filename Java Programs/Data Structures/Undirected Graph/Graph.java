import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Graph {
  // instance variable:
  private int V; // number of Vertices of the graph
  private int E; // number of Edges of the graph
  private List<Integer>[] adj; // adjacency List of the Graph: private ArrayList<List<Integer>> adj

  // Constructor:
  public Graph(int v) {
    this.V=v;
    this.E=0;
    // instantiate the adjacency list:
    adj=(List<Integer>[]) new Object[V]; // UGLY CASTIN: no generic array creation
    for(int i=0; i<V; i++) {
      adj[v]=new LinkedList<Integer>();
    } 
  }

  // API:
  // instance methods:
  public int V(){return V;}
  public int E(){return E;}
  public void addEdge(int v, int w) {
    // undirected graph: add v to the adj[w] and add w to adj[v]:
    adj[v].add(w); // adds w to the tail of DList adj[v] in constant time
    adj[w].add(v); // adds v to the tail of DList adj[w] in constant time
  }

  // returns an Iterable data type that represents all vertices adjacent to vertex v
  public List<Integer> adj(int v){return adj[v];}

  // static methods:
  public static int degree(Graph G, int v) {
    // compute the degree of vertex v in graph G:
    int degree=0;
    for(Integer vertex:G.adj(v))  degree++;
    return degree;
  }

  public static int maxDegree(Graph G) {
    int maxDegree=degree(G, 0);
    for(int v=1; v<G.V(); v++)
      if (maxDegree<degree(G, v))  maxDegree=degree(G, v);
    return maxDegree;
  }

  public static int minDegree(Graph G) {
    int minDegree=degree(G, 0);
    for(int v=1; v<G.V(); v++)
      if (minDegree>degree(G, v))  minDegree=degree(G, v);
    return minDegree;
  }

  // Sum of degrees of all vertices in an undirected graph is 2*E (twice the number of edges because each edge counts twice (once from each end point)
  public static int averageDegree(Graph G) {return (2*G.E())/G.V();}

  public static int SelfLoopCount(Graph G) {
    int count=0;
    for(int v=0; v<G.V(); v++)
      if(G.adj(v).contains(v)) count++;
    return count;
  }

  
}
