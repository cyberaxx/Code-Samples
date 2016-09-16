import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 
/*
Symbol graphs. 
Typical applications involve processing graphs using strings, not integer indices, to define and refer to vertices. 

To accommodate such applications, we define an input format with the following properties:
	Vertex names are strings.
	A specified delimiter separates vertex names (to allow for the possibility of spaces in names).
	Each line represents a set of edges, connecting the first vertex name on the line to each of the other vertices named on the line.
*/

public class SymbolGraph {
  // instance variables:
/*
  REUSE! :)
  private int V;
  private int E;
  private List<Integer>[] adj;
*/
  private Graph graph;

  // For non-integer vertices we need a Map and Map-1
  // A Map to map a non-integer vertex to an integer between 0 to V-1
  // A Map-1 to invert the mapping and gives us a non-integer key for any index between 0 to V-1
  private String[] vertexKeys;
  private HashMap<String, Integer> vertexMap;
  
  public SymbolGraph(File file, String delimiter) {
    try {
      // reading from input file:
      BufferedReader br=scan(file);
      String line;
      
      // 1. read from input file and populate the symbol table of vertices:
      // this would create a Symbol table of KeyString to vertex indexes: 
      // st[StringKey]=vertexIndex
      vertexMap=new HashMap<String, Integer>();
      while((line=br.readLine())!=null) {
        String[] vertices=(line.trim()).split(delimiter);
        for(String vertex:vertices) {
	  if(!vertexMap.containsKey(vertex)) {
	    vertexMap.put(vertex, vertexMap.size());
	  }
        }
      }
      // create a vertex index array of vertex keys: a[vertexIndex]=StringKey;
      vertexKeys=new String[vertexMap.size()];  
      // iterate over keys in the HashMap
      for(String key: vertexMap.keySet()) {
	// get the vertex index associated with the given key and put it in that index of the array
        vertexKeys[vertexMap.get(key)]=key;
      }

      // 2. initialize graph parameters:
      
      /* 
	REUSE! :)

      V=vertexKeys.length;
      E=0;
      adj=new List[V]; // array of linkedlists
      // initialize all lists to empty lists
      for(int v=0; v<V; v++)
        adj[v]=new LinkedList<Integer>();// empty list of intergers (vertices are all integers)

      */

      int V=vertexKeys.length;
      // instantiate an empty graph with V vertices
      graph=new Graph(V);
      
      // 3. read from input file and construct the graph (construct connections in the graph)
      // reading from input file:
      br=scan(file);
      while((line=br.readLine())!=null) {
        String[] vertices=(line.trim()).split(delimiter);
        int v1=vertexMap.get(vertices[0]);
        for(int v=1; v<vertices.length; v++) {
	  // 1. find the integer vertex index v:
	  int v2=vertexMap.get(vertices[v]);
	  // add an edge between v1 and v2
	  graph.addEdge(v1, v2);
        }
      }

    }
    catch(Exception e) {
      System.out.println("Failed to read from the input file.");
    }
  }


  // API:
  
  // getter methods:
  // returns the underlying graph with integer vertices
  public Graph graph(){return graph;}
  public int indexOf(String vertex) {
    validate(vertex);
    return vertexMap.get(vertex);
  }

  public String nameOf(int v) {
    validate(v);
    return vertexKeys[v];
  }

  // returns all vertices adjacent to the given vertex in string
  public Iterable<String> adj(String vertex) {
    // validate vertex
    validate(vertex);

    // create a list of string
    List<String> list=new LinkedList<String>();
    // find the integer associated to the vertex
    int v=vertexMap.get(vertex);
    // for each vertex adjacent to v in the underlying graph
    for(Integer w:graph.adj(v)) {
      // find the string value associated with w and added to the list
      list.add(vertexKeys[w]);
    }
    return list;
  }

/*
  REUSE! :)

  public int V(){return V;}
  public int E(){return E;}
  
  public Iterable<String> adj(String vertex) {
    // look up the integer index associated to the vertex string
    int v=vertexMap.get(vertex);

    // find adjacency list associated to the vertex v:
    List<String> list=new LinkedList<String>(); // adjacency list of v
    for(Integer i:adj[v])
      // find the string key associated with the vertex index and add it to adjacency list
      list.add(vertexKeys[i]);

    return list;
  }

/*
   REUSE! :)
  
  public void addEdge(String vertex1, String vertex2) {
    // 1a. find the integer vertex index v1:
    int v1=vertexMap.get(vertex1);
    // 1b. find the integer vertex index v2:
    int v2=vertexMap.get(vertex2);

    // 2a. add v1 to adjacency list of v2
    adj[v2].add(v1);
    // 2b. add v2 to adjacency list of v1
    adj[v1].add(v2);

    // 3. increase the number of edges:
    E++;
  }

*/

  private BufferedReader scan(File file) throws IOException {
    FileReader fr=new FileReader(file);
    BufferedReader br=new BufferedReader(fr);
    return br;
  }

  private void validate(int v) {
    // if the given vertex is not in the vertexMap is invalid
    if(v<0 || v>=graph.V()) throw new IndexOutOfBoundsException();
  }
  private void validate(String vertex) {
    // if the given vertex is not in the vertexMap is invalid
    if(!vertexMap.containsKey(vertex)) throw new IllegalArgumentException();
  }

}
