import java.util.List;
import java.util.LinkedList;

import java.util.Scanner;
import java.io.File;

public class Digraph {

  // instance varibles:
  private int V; // number of veritces
  private int E; // number of directed edges

  // Digraph is characterize by a set of vertices (V) and their directed connections
  // Digraph is represented in an vertex index array of adjacency list
  private List<Integer>[] adj;

  // adj only maintain the outdegree
  // vertex index array of indegrees for each vertex:
  private int[] indegree;

  // Constructor
  // 1. create an empty Digraph from set of V vertices:
  public Digraph(int V) {
    if(V<=0) throw new IllegalArgumentException("Number of vertices in a Digraph must be a positive integer.");
    this.V=V;
    this.E=0;

    // initialize the adjacency lists:
    adj=new List[V];
    for(int v=0; v<V; v++)
      adj[v]=new LinkedList<Integer>(); // empty linked list

    // initialize indegree array:
    indegree=new int[V];
  }

  // 2. create a Digraph from a given Digraph
  public Digraph(Digraph G) {
    this.V=G.V();
    this.E=0;

    // initialize the adj lists:
    adj=new List[V];
    for(int v=0; v<V; v++)
      adj[v]=new LinkedList<Integer>();

    // initialize the indegree array
    indegree=new int[V];

    // populate the graph:
    for(int v=0; v<V; v++) {
      for(Integer w:G.adj(v)) {
	addEdge(v, w);
      }
    }
  }

  // 3. create a graph from an input file
  public Digraph(File file, String delimiter) {
    try {
      Scanner scanner=new Scanner(file); // scan the input file
      // read number of vertices and edges from input file
      this.V=scanner.nextInt();
      this.E=0;
      int e=scanner.nextInt();

      // initialize the adj lists
      adj=new List[V];
      for(int v=0; v<V; v++)
	adj[v]=new LinkedList<Integer>();

      // initialize the indegree (vertex index array of indegrees)
      indegree=new int[V];

      // build the graph from input file
      while(scanner.hasNextInt()) {
        int u=scanner.nextInt();
        int w=scanner.nextInt();
	addEdge(u,w); // add a directed edge from u->w
      }
      if(e!=E) throw new RuntimeException("Failed to build the graph from input file");
    }
    catch(Exception e) {
      System.out.println("Failed to build a Digraph from the input file due to "+e.getMessage());
    }
  }
 
  // API: 1. getter methods, 2. modification queries

  // 1. getter methods:
  public int V(){return V;} // number of vertices
  public int E(){return E;} // number of directed edges
  public Iterable<Integer> adj(int v) {
    // validate v:
    validate(v);
    return adj[v]; // retrun the adj ist associated to vertex v
  }
  // indegree and out degree of vertex v
  public int indegree(int v) {
    // validate v:
    validate(v);

    if(E==0) return 0;
    return indegree[v];
  }
  public int outdegree(int v) {
    validate(v);
    return adj[v].size();
  }

  // 2. modification queries:
  public void addEdge(int v, int w) {
    // validate v and w:
    validate(v); validate(w);

    // add a directed edge FROM v TO w by adding w to adjacency list of v
    adj[v].add(w);
    // increase the indegree of w
    indegree[w]++;
    // increase the E
    E++;
  }   

  // 3. query methods:
  public int selfLoopCount() {
    int count=0;
    for(int v=0; v<V; v++)
      for(Integer w:adj[v])
	if(v==w)
	  count++;
    return count;// unlike undirected graphs there is not need to divide this number by 2 (because in addEdge method the directed selfLoop was only added once
  }
  
  public int maxIndegree() {
    if(E==0) return 0;
    int max=indegree[0];
    for(int v=1; v<V; v++)
      if(indegree[v]>max)
	max=indegree[v];
    return max;
  }
  public int minIndegree() {
    if(E==0) return 0;
    int min=indegree[0];
    for(int v=1; v<V; v++)
      if(indegree[v]<min)
	min=indegree[v];
    return min;
  }

  public int maxOutdegree() {
    if(E==0) return 0;
    int max=adj[0].size();
    for(int v=1; v<V; v++)
      if(adj[v].size()>max)
	max=adj[v].size();
    return max;
  }
  public int minOutdegree() {
    if(E==0) return 0;
    int min=adj[0].size();
    for(int v=1; v<V; v++)
      if(adj[v].size()<min)
	min=adj[v].size();
    return min;
  }

  public int avgIndegree() {
    if(E==0) return 0;
    int sum=0;
    for(int v=0; v<V; v++)
      sum+=indegree[v];
    return sum/V;
  }

  public int avgOutdegree() {
    return E/V;
  }
  
  public int parallelEdges() {
    return 0;
  }

  public Digraph reverse() {
    // construct an empty Graph with V vertices:
    Digraph reverse=new Digraph(V);

    // add all edges of G to reverse Digraph
    for(int v=0; v<V; v++) {
      for(Integer w:adj[v]) {
	reverse.addEdge(w,v);  // reverse the directed edge v->w to w->v 
      }
    }
    // return the digraph with reverse direction of edges
    return reverse;
  }

  // hepler methods:
  private void validate(int v) {
    if(v<0||v>=V) throw new IndexOutOfBoundsException();
  }
}
