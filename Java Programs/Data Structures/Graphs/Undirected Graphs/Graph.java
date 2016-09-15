/*
  Undirected Graph Abstraction
*/
import java.util.List; // List ADT specification
import java.util.LinkedList; // List interface implementation using Doubly Linked List

public class Graph {

  // instance member: Graph Representation
  // Adjacency List: 
  // A vertex index array of Lists
  private List<Integer>[] adj;
  // number of vertices
  private int V;
  // number of edges
  private int E;

  // Constructor:
  // takes v and construct a graph with v vertices and 0 edge
  public Graph(int v) {
    // initialize instance variables:
    V=v;
    E=0;    
    // Adjancency list:
    adj=new List[V];
    // initialize adjacency list of each vertex v:
    for(int i=0; i<V; i++) {
      adj[i]=new LinkedList<Integer>(); // an empty linked list
    }
  }

  // API: 1. getter methods 2. modification methods 3. query methods

  // 1. getter methods
  public int V(){return V;}
  public int E(){return E;}
  public List<Integer> adj(int v) {
    // validate v:
    validate(v);
    // returns a list vertices incident to v
    return adj[v]; 
  }

  // 2. modification methods
  public void addEdge(int v, int w) {
    // validate v, w:
    validate(v);
    validate(w);

    // NOTE: parallel edges and selfloops are allowed
    //       in this implementation

    // since G is an undirected graph:
    // 1. add v to the adj list of w
    adj[w].add(v);
    // 2. add w to the adj list of v
    adj[v].add(w);

    // increase the number of edges:
    E++;
  }

  // 3. query methods
  public int degree(int v) {
    //  validate v
    validate(v);
    // degree of vertex v: number of edges incident to v
    return adj[v].size();
  }

  public int minDegree(){
    int min=adj[0].size();
    for(int v=1; v<V; v++)
      if(adj[v].size()<min)
        min=adj[v].size();
    return min;
  }

  public int maxDegree(){
    int max=adj[0].size();
    for(int v=1; v<V; v++)
      if(adj[v].size()>max)
        max=adj[v].size();
    return max;
  }

  public int selfLoopCounter(){
    int count=0;
    for(int v=0; v<V; v++)
      for(Integer w:adj[v])
	if(w==v)
          count++;
    return count/2; // we count each self loop twice (once iserting a selfloop addEdge(v,v) it got inserted twice
  }

  // average degree is the average number of edges
  // incident to each vertec of the graph: Sum(degree(v))/v
  public int avgDegree(){
    // sum(degree(v)) for all v's: 2*E (each edge is incident to its both endpoints)
    return (2*E)/V;
  }

  // helper methods: vertex validator
  private void validate(int v) {
    if(v<0 || v>=V) throw new IndexOutOfBoundsException("Vertex index is out of legal bounds!");
  }

}
