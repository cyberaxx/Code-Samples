import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import java.util.Deque;
import java.util.ArrayDeque;

/* Graph and Edge classes (both top-level classes) has been defined as
 static nested classes of PrimMST class (For packaging convenience) */

/*
  Simplifying assumptions:
  1. Edge weights are distinct => MST is UNIQUE
  2. Graph is Connected => MST exist

  MST: G'=(V,T) is a subgraph of graph G=(V,E) such that:
  1. Spanning over all vertices of the Graph G
  2. Tree: Connected, with No cycle
  3. Optimal: sum of edge weights is minimum
*/

public class PrimMST{
  /* Vertex index array of booleans to partition vertices of a graph to: 
  Tree vertices, and Not Tree vertices (disjoint sets) -> (Tree, Not Tree) is a CUT aslong as both sets are non-empty */
  private boolean[] marked;
  private double[] distTo; // Vertex indexed array of maintain weights (distance between Tree vertex to non-Tree vertex)
  private Edge[] edgeTo; // Vertex index array of Edges (edge between Tree vertex to non-Tree vertex)
  private IndexMinPQ<Double> pq; // Vertex index min oriented priority queue of edge weights

  // Constructor: Find a Minimum Spanning Tree in a Connected Undirected Graph with DISTINCT edge weights
  public PrimMST(Graph G) {
    // initialize instance fields:
    int V=G.V();
    if(V<=0) throw new IllegalArgumentException();
    marked=new boolean[V];
    distTo=new double[V];
    edgeTo=new Edge[V];
    pq=new IndexMinPQ<Double>(V);   
    // initialize the distance array:
    for(int v=0; v<V; v++) distTo[v]=Double.POSITIVE_INFINITY;

    // Find MST:
    // perform prim's algorithm starting from vertex 0
    prim(G, 0); 
    // ElogV for scan(G, v)
    // VlogV min extraction in prim's while loop
    // Total: O(ElogV)
    // Extra space: V for IndexMinPQ

    /* if G is not Connected => to Prim must find the Min Spanning Tree:
    for(int v=0; v<V; v++) {
      // if v has not been added to a tree
      if(!marked[v])
        prim(G, v);
    }
    */
  }

  // API:
  // Query methods:
  // MST: edges in the MST:
  public Iterable<Edge> mst() {
    Deque<Edge> mst=new ArrayDeque<Edge>();
    for(Edge e:edgeTo)  
      if(e!=null)
        mst.offer(e);
    return mst;
  }

  // MST: weight of the MST: sum of edge weights
  public double weight() {
    double weight=0;
    for(Edge e:mst()) weight+=e.weight();
    return weight;
  }

  // helper methods:
  private void prim(Graph G, int s){
    // sanity check the vertex:
    if(s<0 || s>=G.V()) throw new IndexOutOfBoundsException("The vertex index is out of legal bounds!");

    // set the distance of s:
    distTo[s]=0;
    // insert s vertex to the priority
    pq.insert(s, distTo[s]);

    // while the vertex index min order priority queue is not empty
    while(!pq.isEmpty()) {
      int v=pq.delMin(); // remove the vertex with min edge weight associated with it from pq and return the vertex associated with it
      scan(G, v); // visit the vertex
    }
  }

  private void scan(Graph G, int v) {
    // sanity check the vertex:
    if(v<0 || v>=G.V()) throw new IndexOutOfBoundsException("The vertex index is out of legal bounds!");

    // add the vertex to the tree partion:
    marked[v]=true;

    // check all edges incident to v:
    for(Edge e:G.adj(v)) {
      // for each edge incident to V check if its other end point is not in the tree partition
      int w=e.other(v);

      // if the other end point was in the tree partition do NOTHING (adding such an edge to the tree will creates a CYCLE)
      if(marked[w]==true) continue;

      // otherwise: if w is not in tree partition:
      // check if this edge to the vertex w shorten the distance from w to MST:
      if(e.weight()<distTo[w]) {
        // 1. add this edge to the MST (so far)
        edgeTo[w]=e;
	// 2. update the dinstance to w
        distTo[w]=e.weight();

        // check if IndexMinPQ instance contains a key associated with index vertex w
        if(pq.contains(w)) {
          // 3. update the weight associate with vertex w in IndexMinPQ
          pq.decreaseKey(w, distTo[w]);
        }
        // Vertex w is not in the IndexMinPQ (has not been discovered yet)
        else {
          // 3. add the index w and distance associated with it to the indexMinPQ
          pq.insert(w,distTo[w]);
        }

      } // otherwise: if the new edge is not shorter than the one already in the MinPQ, do nothing

    }
    /* vertex v has been visited! */
  }

  /*
    Undirected weighted graph abstraction
  */
  private static class Graph {
    // instance variables:
    private final int V; // number of vertices of an instance of Graph data type
    private int E; // number of Edges of an instance of Graph data type
    private List<Edge>[] adj; // adjacency list: a vertex index array of edge lists incident to each vertex

    // Constructor:
    public Graph(int v){
      // sanity check the number of vertices:
      if(v<0) throw new IllegalArgumentException("Number of vertices must be non-negative!");
      
      // initialize instance fields:
      this.V=v; // v vertices
      this.E=0; // 0 edges

      // Initialize the adjacency list (index array of list of incident edges instances)
      adj=(List<Edge>[])new List[V]; // UGLY CASTING: java does not allow generic array creation (array of Lists with generic type Edge as its type parameter)
      for(int i=0; i<V; i++)  adj[i]=new LinkedList<Edge>(); // initialize an empty LinkedList for each vertex of the graph  
    }

    public Graph(Graph G){
      this.V=G.V();
      this.E=G.E();

      adj=(List<Edge>[])new List[V]; // UGLY CASTING
      for(int i=0; i<V; i++)
        adj[i]=new LinkedList<Edge>(G.adj(i));
    }

    // instance method:
    // 1. Getter methods:
    // Vertex counter:
    public int V(){return this.V;}
    // Edge counter:
    public int E(){return this.E;}
    // Vertex adj List
    public List<Edge> adj(int v){
      // validate the given vertex index:
      validateVertex(v);
      return this.adj[v]; // list of Edges incident to the vertex v (in an instance of Graph data type)
    }

    // 2. Query methods:
    // Edge addition: add an edge to an instance of Graph data type
    public void addEdge(Edge e) {
      // validate edge end points and edge weight:
      int v=e.either();
      int w=e.other(v);
      double weight=e.weight();
      validateVertex(v);
      validateVertex(w);
      validateWeight(weight);

      // add the undirected weighted edge to incident list of its both end points:
      adj[v].add(e); // add the new edge to tail of LinkedList of incident edges
      adj[w].add(e); // add the new edge to tail of LinkedList of incident edges
      
      // increament the number of Edges of the graph:
      E++;
    }
    // Edge addition: add an edge to an instance of Graph data type
    public void addEdge(int v, int w, double weight) {
      Edge e=new Edge(v, w, weight);
      addEdge(e);
    }
    // Vertex Degree:
    public int degree(int v) {
      // validate vertex index:
      validateVertex(v);
      return adj[v].size(); // size of the incident list to vertex v (number of edges incident to the vertex v)
    }
    // List of all edges of an instance of Graph data type:
    public List<Edge> edges() {
      ArrayList<Edge> edges=new ArrayList<Edge>(); // instantiate an AList collection that can contain Edge instances
      for(int v=0; v<V; v++)
        for(Edge e:adj[v])
          edges.add(e);

      // an ArrayList<Edge> of edges of an instance of a Graph data type
      return edges;
    }

    // helper methods:
    // Vertex index validation:
    private void validateVertex(int v){if(v<0||v>=V) throw new IndexOutOfBoundsException("The given vertex index is out of legal bounds!");}
    // Edge Weight validation:
    private void validateWeight(double weight){if(Double.isNaN(weight)) throw new IllegalArgumentException("Edge weight cannot be NaN!");}
  }

  /*
    Define explict representation of undirected weighted edges.
    weighted Edge abstraction is java Comparable type (Edge instances have total order).
    It should override compareTo method by providing implementation for how to compare
    two instances of Edge data type.
  */
  private static class Edge implements Comparable<Edge>{
    // instance variable:
    // 1. Edge end points (integer vertex indeces:
    private final int v; // immutable
    private final int w; // immutable
    private final double weight; // weights can be real numbers

    // Constructor: initialize instance field of the Edge class
    public Edge(int v, int w, double weight) {
      // sanity check arguments passed to the Edge constructor: End points cannot be negative integers, and weight cannot be NaN
      if(v<0) throw new IllegalArgumentException("Vertex index must be non-negative!");
      if(w<0) throw new IllegalArgumentException("Vertex index must be non-negative!");
      if(Double.isNaN(weight)) throw new IllegalArgumentException("Edge weight can not be NaN!");

      // initialize Edge instance fields:
      this.v=v;
      this.w=w;
      this.weight=weight;
    }

    // API:
    // Getter methods for private instance fields:
    // Vertices:
    public int either(){return v;} // return either end point of the wieghted undirected edge instance
    // return the other vertex of an edge instance (the vertex which is not the once that has been specified as the input arguement)
    public int other(int v) {
      // sanity check for the input argument:
      if(v<0) throw new IllegalArgumentException("Vertex index must be non-negative!");

      // return the other vertex:
      if(this.v==v) return w;
      else if(this.w==v) return v;

      // if the given vertex is not equal to the either end points of the an edge instance:
      else throw new IllegalArgumentException("There is no vertex with given index incident to this edge!");
    }
    // Edge weight:
    public double weight(){return this.weight;}

    @Override 
    public int compareTo(Edge e){return Double.compare(this.weight, e.weight);} // compare edges based on their corresponding weight  
    }
}
