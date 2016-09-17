/*
  DFS API:
  Decouling graph representation from graph processing algorithm 
*/

import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Random;

public class DFS {
  // instance varaibles:

  // a vertex index array to keep track of visited vertices
  private boolean[] marked;
  // a vertex index array to keep parent pointer (to reconstruct DFS tree)
  private int[] edgeTo;
  // the source vertex of the search
  private int s;

  // Constructor:
  // Like all other graph processing algorithm, the processing part
  // will be done in the construtor:
  public DFS(Graph G, int s) {
    // extract number of vertices of the graph G
    int v=G.V();
    // initialize the marked array:
    marked=new boolean[v]; // initially all vertices are not unvisited
    // initialze the parent poniter array
    edgeTo=new int[v];
    // initialize the source of the search
    this.s=s;

    // recursively visit all vertices REACHABLE from vertex s (source vertex)
    // that has not been visited previously:
    DFSVisit(G, s);
  }
  
  // recursive DFS method: similar to preorder traversal of binary trees
  private void DFSVisit(Graph G, int v) {
    // marked vertex v visited:
    marked[v]=true;

    // RECURRENCE: 
    // on all vertices reachable from v (all vertices on v's adjacency list)
    // that has not been visited yet:
    for(Integer w: G.adj(v)) {
      if(!marked[w]) {
	// visit w:
	DFSVisit(G,w);
	// once recurrence on w unfold (completed)
        // add v-w edge to the parent pointer array:
        edgeTo[w]=v;
       }
    }
    // once all vertices are marked visited, the recursive algorithm terminates
  }

  // API: graph processing methods for clients:

  // return true if the exist a path between the source vertex and v
  // true if v was reachable from the source vertex
  // false if v and the source vertex belong to two different connected components
  public boolean hasPathTo(int v) {
    validate(v);
    return marked[v];
  }

  // if there exist a simple path from the source vertex
  // to v, return vertices on such a path
  // null, otherwise
  public Iterable<Integer> path(int v){
    validate(v);
    // check if the source has visited vertex v or not:
    if(marked[v]==false) return null;

    // Otherwise:
    Deque<Integer> stack=new ArrayDeque<Integer>();
    while(v!=s) {
      stack.push(v);
      v=edgeTo[v]; // follow the parent pointer
    }
    // add the source vertex to the path:
    stack.push(s);

    // return the path
    return stack;
  }

  // return all vertices on the path from the source of
  // the search to all vertices reachable from the source
  public Iterable<Integer> dfsTree(){
    // queue of vertex indeces:
    Deque<Integer> path=new ArrayDeque<Integer>();
    for(int v=0; v<marked.length; v++)
      // if the vertex has been visited, it blongs to the DFS tree
      if(marked[v])
        path.offer(v);
    return path;
  }

  // G is connected if the source can reach all vertices:
  public boolean isConnected() {
    for(int v=0; v<marked.length; v++)
      if(marked[v]==false)
        return false;
    return true;
  }

  // helper methods
  private void validate (int v) {
      int V=marked.length;
      if(v<0 || v>=V) throw new IndexOutOfBoundsException(v+" is not a within the legal range for a vertex index.");
  }

  // Define undirected graph abstraction as a Static nested class
  private static class Graph{
    // instance variables:
    // To charaterize and represent a Graph instance we need a set of vertices
    private int V; // number of vertices
    private int E; // number of edges
    // representation of graph
    // using adjacency list (for all vertices)
    private List[] adj; // vertex index array of linked list (adjacency lists)

    // Graph constuctor:
    // creates a graph with vertices and 0 edge
    public Graph(int v) {
      // initialize instance variables:
      V=v;
      E=0;
      // initialize adjecency list of the graph with
      // empty list assoicated to all vertices:
      adj=new List[V];
      for(int i=0; i<V; i++)
        adj[i]=new LinkedList<Integer>();
    }

    // Constructor
    public Graph(Graph G) {
      // initialize instance variables:
      this.V=G.V();
      this.E=G.E();

      // initialize array of adj lists
      adj=new List[V];
      // for each vertex, construct its corresponding adjacency list
      for(int v=0; v<V; v++)
        adj[v]=new LinkedList<Integer>(G.adj(v));
    }

    // API: 1. getter methods, 2. modification queries

    // 1. getter methods
    public int V(){return V;}
    public int E(){return E;}
    // returns adjacency list corresponds to the vertex v
    public List<Integer> adj(int v){
      // validate vertex v
      validate(v);
      // return the array entry corresponds to vertex v of the graph
      return adj[v];
    }

    // 2. modification queries
    public void addEdge(int v, int w) {
      // validate v and w
      validate(v);
      validate(w);
      
      // add v to adjacency list of w
      adj[w].add(v);
      // add w to adjacency list of v
      adj[v].add(w);

      // increase the number of edges in the graph
      E++;
    }
    
    // helper methods:
    private void validate (int v) {
      if(v<0 || v>=V) throw new IndexOutOfBoundsException(v+" is not a within the legal range for a vertex index.");
    }
  }

  // DFS implementation using Adjacency List representation of Graph (without using graph object)
  public static void dfs(List<Integer>[] adj, int s, boolean[] visited, int[] edgeTo) {
    if(s<0 || s>=adj.length) throw new IndexOutOfBoundsException("Source vertex is out of legal bounds");

    // extract number of vertices:
    int v=adj.length;

    // recursively visit vertices of the graph (strating from s)
    // mark each vertex that has been visited so far

    // Since vertices are marked during exploration, each vertex would be visited
    // at most once, and each edge would be visited at most twice (once from each endpoint)
    dfsVisit(adj, s, visited, edgeTo); // Linear time and space complexity :O(V+2E)
  }

  private static void dfsVisit(List<Integer>[] adj, int v, boolean[] visited, int[] edgeTo) {
    // mark vertex v as visited:
    visited[v]=true;

    // for all vertex incident to v
    for(Integer w:adj[v]) {
      // if w has NOT been visited yet
      if(!visited[w]) {
	// recursively explore w:
	dfsVisit(adj, w, visited, edgeTo);
	// add edge v-w to the DFS tree
	edgeTo[w]=v;
      }
    }
  }

  // test client for the graph API
  public static void main(String[] args) {
    Graph G=new Graph(5);

    G.addEdge(1, 2);
    G.addEdge(0, 3);
    G.addEdge(4, 1);
    G.addEdge(4, 3);
    G.addEdge(1, 0);
    G.addEdge(3, 2);

    for(int i=0; i<5; i++)
      System.out.println(i+": "+G.adj(i));

    System.out.println();
    System.out.println();

    DFS dfs=new DFS(G, 0);
    System.out.println("Visited vertices on DFS: "+dfs.dfsTree());
    System.out.println("is 4 connect to the source? "+dfs.hasPathTo(4));
    System.out.println("The path to 4: "+dfs.path(4));
    System.out.println("is G connected? "+dfs.isConnected());

 }   

}

