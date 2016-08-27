import edu.princeton.cs.algs4.IndexMinPQ;

public class PrimMST{

  // define two other top-level classes (Graph, Edge) here as a static nested classes (For packaging convenience)
  private static class Graph{}
  /*
    Define explict representation of undirected weigthed edges.
    weigthed Edge abstraction is java Comparable type (Edge instances have total order).
    It should override compareTo method by providing implementation for how to compare
    two instances of Edge data type.
  */
  private static class Edge implements Comparable<Edge>{
    // instance variable:
    // 1. Edge end points (integer vertex indeces:
    private final int v; // immutable
    private final int w; // immutable
    private final double weigth; // weigths can be real numbers

    // Constructor: initialize instance field of the Edge class
    public Edge(int v, int w, double weigth) {
      // sanity check arguments passed to the Edge constructor: End points cannot be negative integers, and weigth cannot be NaN
      if(v<0) throw new IllegalArgumentException("Vertex index must be non-negative!");
      if(w<0) throw new IllegalArgumentException("Vertex index must be non-negative!");
      if(Double.isNaN(weigth)) throw new IllegalArgumentException("Edge weigth can not be NaN!");

      // initialize Edge instance fields:
      this.v=v;
      this.w=w;
      this.weigth=weigth;
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
    // Edge weigth:
    public double weigth(){return this.weigth;}

    @Override 
    public int compareTo(Edge e){return Double.compare(this.weigth, e.weigth);} // compare edges based on their corresponding weigth  
    }
}
