import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;

public class DFSIterative {

  // instance variables:
  // 1. vertex index array of boolean to keep track of visited vertices
  private boolean[] marked;
  // 2. vertex index array of parent pointers required for path reconstruction
  private int[] edgeTo;
  // 3. the source vertex of the search
  private int s;


  // Constructor
  public DFSIterative(Graph G, int s) {
    // initialize instance memebers:
    this.s=s;
    // extract number of vertices:
    int v=G.V();

    marked=new boolean[v];
    edgeTo=new int[v];
  }

}
