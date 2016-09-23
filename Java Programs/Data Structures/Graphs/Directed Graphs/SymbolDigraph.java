/*
  Creates a Digraph representation with non-integer vertices
*/

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
public class SymbolDigraph {

  // instance variables
  // 1. underlying Digraph wth integer vertices
  private Digraph digraph;
  // 2. Symbol table that maps string vertices into integer vertices
  private HashMap<String, Integer> vertexMap;
  // 3. A vertex index array of Strings that inverse-map integer vertices into string vertices
  private String[] vertexKeys;

  // Constructor:
  public SymbolDigraph(File file, String delimiter) {	
    // creat a hash table:
    vertexMap=new HashMap<String, Integer>(); // an empty hash table
    try{
      // create a Scanner object to read from the given file
      Scanner scanner=new Scanner(file);
      int V=0;
      // 1. One pass over the entire file to populate the vertex map and String array of vertices:
      while(scanner.hasNextLine()) {
	String line=scanner.nextLine();
        String[] segs=(line.trim()).split(delimiter);
        for(String str:segs) {
          // check if the current string has been already inserted into the symbol table
          if(!vertexMap.containsKey(str)) {
            // put the new key value pair into the symbol table:
            vertexMap.put(str, V);
	    V++; // increase the vertex counter
          }
        }
      }
      scanner.close();

      // create the vertex index array of Strings from vertices of digraph
      vertexKeys=new String[V]; // a vertex index array
      // Iterate over symbol table keys
      for(String key:vertexMap.keySet()) {
	// extract the integer vertex associated with String key
        int v=vertexMap.get(key);
	vertexKeys[v]=key;
      }

      // Now we have all vertices lets build the underlying digraph
      digraph=new Digraph(V); // an empty graph with V vertices and 0 edge
    
      // 2. Run a second pass over the input file to add edges into the underlying digraph
      scanner=new Scanner(file);
      // read the input file line by line:
      while(scanner.hasNextLine()) {
	String line=scanner.nextLine();
        String[] segs=(line.trim()).split(delimiter);
        // first string is a vertex and all the rest are vertices adjacent to it
        String vertex=segs[0];
	int v=vertexMap.get(vertex);
	for(int i=1; i<segs.length; i++) {
	  // add a directed edge from v to i
	  digraph.addEdge(v, vertexMap.get(segs[i]));
        }
      }
      scanner.close();

    } catch(Exception e) {
        System.out.println("Failed to read from input file " + e.getMessage());
    }
  }

  // API:
  // return a vertex value associated with the string key
  public int indexOf(String vertex) {
   validate(vertex);
   return vertexMap.get(vertex); 
  }
  // return a string value of vertex associated with a given integer
  public String nameOf(int v) {
    validate(v);
    return vertexKeys[v];
  }
  // returns an underlying digraph:
  public Digraph graph() {
    return digraph;
  }

  // helper methods:
  private void validate(String vertex) {
    if(!vertexMap.containsKey(vertex))
      throw new IllegalArgumentException(); 
  }
  private void validate(int v) {
    int V=vertexKeys.length;
    if(v<0 || v>=V) throw new IndexOutOfBoundsException();
  }
}
