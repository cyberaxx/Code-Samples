import java.util.Deque;
import java.util.ArrayDeque;

public class ShortestDirectedCycle {
  // intance variables
  // a collection of vertices that represent a cycle
  private Deque<Integer> cycle; // use stack to construct cycle from parent pointers
  // length of the shortest directed cycle
  private int length;

  // Constuctor:
  // finds a shortest directed cycle in a digraph in O(V * (E + V))
  public ShortestDirectedCycle(Digraph G) {
    // A Digraph has a directed cycle from v to w iff v~>w and w~>v

    // 1. compute the revese graph of G (to check if such a path in reverse direction exists):
    Digraph Gr=G.reverse(); // Gr is G with all its directed edges reversed

    // 2. the length of the largest directed cycle in a Digraph is 1+number of its vertices:
    length=G.V()+1;

    // from all vertices of the graph G
    for(int v=0; v<G.V(); v++) {
      // perform bfs on the reverse graph Gr to see if the reverse path exist
      DirectedBFSPaths bfs=new DirectedBFSPaths(Gr, v); // POWER OF ABSTRACTION (resue)
      
      // from all vertices w adjacent to vertex v in graph G
      for(Integer w:G.adj(v)) // there exists a direct path from v to w
      { 
        // check if there exist a path from v to w in the reverse graph (equivalent to a path from w to v in G) (this would indicate the existance of a cycle)
        if(bfs.hasPathTo(w)) {
	  // if such a cycle exisits, check if it in fact the shortest cycle:
          if(bfs.distTo(w)+1<length) {
	    // update the length of the shortest directed cycle
	    length=bfs.distTo(w)+1;
	    // since its a directed cycle it does not matter to traverse it from v to w or w to v
	    cycle=new ArrayDeque<Integer>();
	    for(Integer x:bfs.pathTo(w))
	      cycle.push(x);
	    cycle.push(v);
	  }	
	}
      }
    }
  }

  // API: 
  // Is the given Digraph acyclic?
  public boolean hasCycle(){return cycle!=null;}
  // If the given Digraph have a directed cycle return one:
  public Iterable<Integer> cycle(){
    if(!hasCycle()) return null;
    return cycle;
  }
}
