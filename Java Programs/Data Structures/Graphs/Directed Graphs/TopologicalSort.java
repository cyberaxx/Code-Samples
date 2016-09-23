/* Given a Directed Acyclic Graph, linearize the graph from it SOURCE vertex to the TARGET vertex.
   It's simple to prove:
   1. Any DAG has at least ONE source vertex (a vertex with 0 edges going into it)
   2. Any DAG has at least ONE target vertex (a vertex with 0 edges going out of it)

   Using DFS a DAG can be linearized such that all edge goes from the source to the target, from left to right.

   The source vertex in the DAG that represent dependecy constraints can be interpreted as a vertex that all vertices 
   reachable from it, depending on it, so it must get processed first.

   Reverse Post ORDER DFS traversal of a Digraph with no cycle is a topological sort of its vertices.

   Proposition. A digraph has a topological order if and only if it is a DAG.
   Proposition. Reverse postorder in a DAG is a topological sort.
   Proposition. With depth-first search, we can topologically sort a DAG in time proportional to V + E.
*/

public class TopologicalSort {
  // instance variable
  private Iterable<Integer> order; // a stack of DAG vertices
  private int[] rank; // a vertex index array of vertex ranks in topological order 
  // Constructor
  public TopologicalSort(Digraph G) {
    rank=new int[G.V()]; // vertex index array of vertex ranks wrt to topological order
    // Give a digraph G:
    // 1. check if G is a directed acyclic graph (has no back edge)
    // 2. if so compute reverse DFS post order of its vertices
    DirectedCycle dc=new DirectedCycle(G);
    // if G is acyclic:
    if(!dc.hasCycle()) {
      // DFS traverse the DAG 
      DFSOrder dfs=new DFSOrder(G);
      order=dfs.reversePost();
      for(Integer v:order)
        rank[v]++; // rank[v] number of vertices before v in the topological ordering of vertices of G
    }
    // if G has a cycle, it's impossible to sort its vertices in topological order
  }

  // API:
  public Iterable<Integer> order(){return order;}
  public int rank(int v) {
    validate(v);
    if(hasOrder()) 
      return rank[v];
    else
      return -1;
  }
  public boolean hasOrder(){return order!=null;}

  // helper methods:
  private void validate(int v) {
    int V=rank.length;
    if(v<0||v>=V) throw new IndexOutOfBoundsException();
  }
}
