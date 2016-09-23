/*
  Creates a Digraph representation with non-integer vertices
*/

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
public class SymbolDigraph {

  // instance variables
  // 1. underlying Digraph wth integer vertices
  private Digraph G;
  // 2. Symbol table that maps string vertices into integer vertices
  private HashMap<String, Integer> st;
  // 3. A vertex index array of Strings that inverse-map integer vertices into string vertices
  private String[] dic;

  // Constructor:
  public SymbolDigraph(File file, String delimiter) {	
    // creat a hash table:
    st=new HashMap<String, Integer>(); // an empty hash table
    try{
      // create a Scanner object to read from the given file
      Scanner scanner=new Scanner(file);
      int V=0;
      // 1. One pass over the entire file to populate the st map and String array of vertices:
      while(scanner.hasNextLine()) {
	String line=scanner.nextLine();
        String[] segs=(line.trim()).split(delimiter);
        for(String str:segs) {
          // check if the current string has been already inserted into the symbol table
          if(!st.containsKey(str)) {
            // put the new key value pair into the symbol table:
            st.put(str, V);
	    V++; // increase the vertex counter
          }
        }
      }
      scanner.close();

      // create the vertex index array of Strings from vertices of digraph
      dic=new String[V]; // a vertex index array
      // Iterate over symbol table keys
      for(String key:st.keySet()) {
	// extract the integer vertex associated with String key
        int v=st.get(key);
	dic[v]=key;
      }

      // Now we have all vertices lets build the underlying digraph
      Digraph G=new Digraph(V); // an empty graph with V vertices and 0 edge
    
      // 2. Run a second pass over the input file to add edges into the underlying digraph
      scanner=new Scanner(file);
      // read the input file line by line:
      while(scanner.hasNextLine()) {
	String line=scanner.nextLine();
        String[] segs=(line.trim()).split(delimiter);
        // first string is a vertex and all the rest are vertices adjacent to it
        String vertex=segs[0];
	int v=st.get(vertex);
	for(int i=1; i<segs.length; i++) {
	  // add a directed edge from v to i
	  G.addEdge(v, st.get(segs[i]));
        }
      }
      scanner.close();

    } catch(Exception e) {
        System.out.println("Failed to read from input file " + e.getMessage());
    }

  }

}
