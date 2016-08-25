import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Digraph {

  // instance variables:
  private final int V; // number of vertices of a directed graph instance
  private int E; // number of edges of a directed graph instance
  private List<Integer>[] adj; // adjacency list of a directed graph instance

  // Constructor:
  public Digraph(int v){
    if(v<0) throw new IllegalArgumentException("Number of vertices of a Digraph instance must be non-negative!");
    // initialzie instance variables;
    this.V=v;
    E=0; // no edge
    // initialize the adjacency list of a Digraph instance:
    adj=(List<Integer>[])new Object[V];// UGLY CASTING: java does not allow generic array creation
    for(int i=0; i<V; i++)
      adj[i]=new LinkedList<Integer>(); // list of vertices connected to the vertex v by an edge from v to them
  }

  // instance methods:
  public int V(){return V;} // return number of vertices in a Digraph instance
  public int E(){return E;} // return number of directed edges in a Digraph instance
  public int inDegree(int v){return -1;}
  public int outDegree(int v){return -1;}
  public void addEdge(){return ;}

  // helper methods:
  // vertices must be integer within [0 V-1] range:
  private void validateVertex(int v) {
    if(v<0 || v>=V-1) throw new IndexOutOfBoundsException("The vertex index is out of legal bounds!");
  }
}
