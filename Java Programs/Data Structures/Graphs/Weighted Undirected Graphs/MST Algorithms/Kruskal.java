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

public class Kruskal{
  private double weight;
  private Deque<Edge> mst;

  // Constructor: Find a Minimum Spanning Tree in a Connected Undirected Graph with DISTINCT edge weights
  public Kruskal(Graph G) {
    // initialize instance fields:
    int V=G.V(); // number of vertices
    mst=new ArrayDeque<Edge>(); // an empty collection for Edge data type in a form of queue

    PriorityQueue<Edge> pq; // min oriented priority queue that keeps track of min weighted edges as we progress
    UF uf; // a disjoint set data structure to keep track of cc dynamically
    pq=new PriorityQueue<Edge>();
    uf=new UF(V);

    // fill up min priority queue by adding edges of Graph G:
    for(Edge e:G.edges()) pq.offer(e);

    // Find MST:
    while(!pq.isEmpty() && mst.size()!=V-1) {
      // remove an edge with min edge weight from the priority queue
      Edge edge=pq.poll(); // remove the head of min oriented priority queue
      // check if its end points are already connect => adding this edge would create a cycle:
      int v=edge.either();
      int w=edge.other(v);

      if(uf.connected(v,w)) continue; // do nothing
      // add v-w edge to mst:
      mst.add(edge);
      // add the edge weight to the totoal weight:
      weight+=edge.weight();
      // connect these two end point (both now belong to a same cc)
      uf.union(v,w);
    }
  }

   /* Performance characteristics:
      insertion edges to min oriented priority queue: ElogE
      cycle checking using UF (disjoint set data structure) takes log*V
      total: ElogE (using sort or min oriented priority queue) + ElogV*
      extra space requirement: E for priority queue and V for union find data structure 
   */

  // API:
  // Query methods:
  // MST: edges in the MST:
  public Iterable<Edge> mst() {return mst;}
  // MST: weight of the MST: sum of edge weights
  public double weight() {return weight;}

  // helper methods:

  /* Union Find Data structure to maintain connected components of a graph G with V number of vertices */
  private static class UF {
    // instance variables:
    private int N; // number of elements of a disjoint set instance
    private int [] roots; // integer index array that maintains leader of cc for each member of a disjoint set instance
    private int[] rank; // the rank of each member of the disjoint set instance (number of edges from the leaves)
    private int count; // number of cc

    // constructor:
    public UF(int n) {
      // sanity check n (the number of items in a disjoint set instance):
      if(n<=0) throw new IllegalArgumentException("Number of items must be a positive integer!");

      // initialize the instance members based on number of items in a disjoint set instance
      N=n;
      count=n;
      roots=new int[N];
      rank=new int[N];
      for(int i=0; i<N; i++) {
        // initially each member is the leader of its cc (we have N cc each has only one item in it):
        roots[i]=i;
        rank[i]=0;
      }
    }

    // API:
    // instance methods:
    //find the leader member of v's connected component (with path compression)
    public int find(int v) {
      // sanity check v:
      validate(v);
      // find v is leader by moving up in the UF tree until to get to the root node at which roots[v]=v (root points to itself as a root of itself)
      while(roots[v]!=v) {
	v=roots[roots[v]]; // path halfing => this change leads to AMORTIZED log*N performance
      }
      // return the leader:
      return v;
    }

    // check if v and w belongs to a same cc or not: 
    public boolean connected(int v, int w){
      // sanity check v and w
      validate(v);
      validate(w);
      // check if v and w have a same leader
      return find(v)==find(w); // under union by rank and path compression in find, this operation takes 2xlog*N
    }
    // UNION: connect two nodes together (add them to the same cc)
    public void union(int v, int w){
      // sanity check v and w
      validate(v);
      validate(w);
      // find v's and w's leaders:
      int rootV=find(v);
      int rootW=find(w);
      // check if w and v are alreay connected:
      if(rootV==rootW) return ; // if so do nothing
      // otherwise: compare the rank of leader nodes and make the one with a higher rank the root of the other leader
      if(rank[rootV]==rank[rootW]) {
         // if both leader have a same rank (increase the height of UF tree by one and make the the leader of the first node the new root:
         roots[rootW]=rootV;
         rank[rootV]++;
         // once rootW becomes a non-root node it rank would never change again!
      }
      // if leaders having different rank the height of the tree would not change (this makes the UF tree bushy and leads to a logN height)
      else if(rank[rootV]>rank[rootW])  roots[rootW]=rootV;
      else roots[rootV]=rootW;

      // decrease the number of cc:
      count--;
    }

    // helper methods
    // validate v to be an integer within 0 to N-1 range
    private void validate(int v) {if(v<0||v>=N)  throw new IndexOutOfBoundsException("given index is out of legal bounds!");}
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
