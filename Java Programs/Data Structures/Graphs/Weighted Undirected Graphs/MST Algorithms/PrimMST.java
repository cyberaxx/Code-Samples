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

  // Instance variables:
  // vertex index array of booleans to keep track of which vertices are in MST so far:
  boolean[] marked;
  // vertex index array of doubles to keep track of min distance of each vertex to the MST:
  double[] distTo; // this is going to be priority associated with each vertex of a instance of a Graph data type
  // vertex index Min oriented priority queue to maintain vertices as index and their distance (of type double) to the MST as the priority:
  IndexMinPQ<Double> pq;
  // an Iterable data type to maintain the MST edges: we use queue to do so
  Deque<Edge> mst;

  // Construcor:
  public PrimMST(Graph G, int s) {
    // 1. initialize instance variables:
    marked=new boolean[G.V()];
    distTo=new double[G.V()];
    pq=new IndexMinPQ<Double>(G.V()); // V extra space required for MinPQ
    mst=new ArrayDeque<Edge>(); // empty MST tree represented by a Queue collection

    // 2. implement Prim's algorithm to find MST (by augmenting vertices by their min distance to the tree) - EAGER approach

  }



  // API:
  // Query methods:
  // MST: edges in the MST:
  public Iterable<Edge> mst() {return mst;}
  // MST: weight of the MST: sum of edge weights
  public double weight() {
    double weight=0;
    for(Edge e:mst)
      weight+=e.weight;
    return weight;
  }

  // helper methods:
  
 

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
