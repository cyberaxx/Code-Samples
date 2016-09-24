/*
  Word ladders. 
  Write a program WordLadder.java that takes two 5 letter strings from the command line, 
  and reads in a list of 5 letter words from standard input, and prints out a shortest word ladder    
  connecting the two strings (if it exists). 

  Two words can be connected in a word ladder chain if they differ in exactly one letter. 

  Solution:
  Use a symbol graph:
  1. Use the first word as the source of BFS search
  2. Use the second word as the target of BFS search
  3. For all words from stdin add a vertex to a graph
  4. Add edge between Vertices of a graph such that their corresponding vertex String only differ in one characters
  5. perform bfs from the source, and if target has been visited, return the path
*/
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;

public class WordLadder {

  public static void main(String[] args) {

    // the source and target of bfs seach
    String source=args[0];
    String target=args[1];

    // read from stdin
    Scanner scanner=new Scanner(System.in);
    int num=scanner.nextInt(); // number of string to be read from stdin
    // num of vertices:
    int V=num+2;
    // vertex index
    int v=0;

    // a symbol table with String keys and Integer values
    HashMap<String, Integer> vertexMap=new HashMap<String, Integer>();
    // a vertex index array of String values of vertices
    String[] vertexArray=new String[V];
   
    // add the source vertex to the vertexMap
    vertexMap.put(source, v++);

    while(num>0) {
	String vertex=scanner.next();
        // if the given string has not been already inserted to the vertexMap
        if(!vertexMap.containsKey(vertex))
	  vertexMap.put(vertex, v++);
	num--;
    }
    // close the scanner
    scanner.close();

    // add the target vertex to the vertexMap
    vertexMap.put(target, v);
    assert v==V-1;

    // populate the vertexArray (vertex index array of Strings (vertex values)
    for(String key:vertexMap.keySet()) {
      // extract the vertex index associated with the vertex string key in the vertexMap
      int ind=vertexMap.get(key);
      vertexArray[ind]=key;
    }

    // vertex index array of adjacency lists of the underlying graph
    List[] adj=new List[V];
    // initialize the list
    for(int i=0; i<V; i++)
      adj[i]=new LinkedList<Integer>(); // an empty list on integers


    // Build the underlying graph
    System.out.println(vertexMap);
    System.out.println();

  }
}
