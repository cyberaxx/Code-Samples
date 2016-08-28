import java.util.PriorityQueue;

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

public class LazyPrimMST{
  // instance variables:
  boolean[] marked; // vertex index array that keeps track of tree and non-tree vertices
  Deque<Edge> mst; // a collection of edges in a form of FIFO queue
  PriorityQueue<Edge> pq; // min oriented priority queue of weighted edges

  // Constructor: Find a Minimum Spanning Tree in a Connected Undirected Graph with DISTINCT edge weights
  public LazyPrimMST(Graph G) {
    // initialize instance fields:
    int V=G.V(); // number of vertices of a graph G
    marked=new boolean[V];
    mst=new ArrayDeque<Edge>(); // create an empty queue for edges in MST
    pq=new PriorityQueue<Edge>(); // create an empty min oriented priority queue to process edges based on their corresponding weight

    // Find MST: For unconnected graph we have to find the Min Spanning Forest:
    for(int v=0; v<V; v++)
      if(!marked[v]) // if v is not in any min spanning tree
	prim(G,v); // start prim algorithm from vertex v
  }

  // API:
  // Query methods:
  // MST: edges in the MST:
  public Iterable<Edge> mst() {return null;}

  // MST: weight of the MST: sum of edge weights
  public double weight() {return -1;}

  // helper methods:
  private void prim(Graph G, int s){
    // 1. add vertex s to the tree
    marked[s]=true; 
    // 2. add all edges incident to s to the min oriented priority queue (growing the mole)
    for(Edge e:G.adj(s))
      pq.offer(e); // add edge e to MinPQ

    // while the pq is not empty or we have not V-1 edges added to the mst
    while(!pq.isEmpty() && mst.size()<G.V()-1) {
      // remove the head of the min oriented prioriy (the edge with min edge weight)
      Edge e=pq.poll();
      // find out end points of edge e:
      int v=e.either();
      int w=e.other(v);
      // check if v and w both are not in the Tree already:
      if(marked[v]==true && marked[w]==true)  continue; // Do nothing, because we are looking for crossing edges from tree vertices to non-tree vertices
      if(marked[v]==true && marked[w]==false) {
        // add the end point that has not been in the tree to the tree:
        marked[w]=true;
        // add edge v-w to the mst:
        mst.offer(e);
        // add all edges incident to it to the min oriented pq
        for(Edge edge:G.adj(w))
	  pq.offer(edge);
      }
      if(marked[w]==true && marked[v]==false) {
        // add the end point that has not been in the tree to the tree:
        marked[v]=true;
        // add edge v-w to the mst:
        mst.offer(e);
        // add all edges incident to it to the min oriented pq
        for(Edge edge:G.adj(v))
	  pq.offer(edge);
      }
    }
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
