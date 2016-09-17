import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.io.File;

public class GraphLauncher {

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

    
    G=new Graph(new File("tinyG.txt"), " ");
    for(int i=0; i<5; i++)
      System.out.println(i+": "+G.adj(i));

    System.out.println();
    System.out.println();



    BFS bfs=new BFS(G, 0);
    System.out.println("is 4 connect to the source? "+bfs.hasPathTo(4));
    System.out.println("Degree of separation from the source: "+bfs.distTo(4));
    System.out.println("The path to 4: "+bfs.path(4));
    System.out.println("is G connected? "+bfs.isConnected());

    System.out.println();
    System.out.println();

    CC cc=new CC(G);
    System.out.println("is G connected? "+cc.isConnected());
    System.out.println("is 3 connect to 4 "+cc.isConnected(3,4));
    System.out.println("Number of nodes in 2's cc: "+cc.size(2));
    System.out.println("Number of conencted components: "+cc.count());

    // nodes in each connected component:
    // array of queues:
    Deque[] components=new Deque[cc.count()];
    for(int c=0; c<cc.count(); c++)
      components[c]=new ArrayDeque<Integer>();
    for(int v=0; v<G.V(); v++)
      components[cc.id(v)].offer(v);

    System.out.println("G's conennted componenets are: ");
    for(int c=0; c<cc.count(); c++)
      System.out.println(components[c]);

    System.out.println();
    System.out.println();

    File file=new File("routes.txt");
    SymbolGraph sbG=new SymbolGraph(file, " ");
    Graph graph=sbG.graph();

    for(int i=0; i<graph.V(); i++)
      System.out.println(i+": "+graph.adj(i));

    System.out.println();
    System.out.println();

    for(int i=0; i<graph.V(); i++)
      System.out.println(sbG.nameOf(i)+": "+sbG.adj(sbG.nameOf(i)));

    System.out.println();
    System.out.println();


    file=new File("movies.txt");
    String source="Bacon, Kevin";
    DegreeOfSeparation dos=new DegreeOfSeparation(file, "/", source);
    String[] names=new String[]{"Kidman, Nicole", "Grant, Cary"};
    for(String name:names) {
      System.out.println("Is "+name+" Connected to "+source+"? \n"+dos.isConnected(name));
      if(dos.isConnected(name)) {
        System.out.println("The degress of separation between "+name+ " and "+source+" is: \n"+dos.degreeOfSeparation(name));
        System.out.println("The path from "+name+ " to "+source+" is: \n"+dos.path(name));
      }
      System.out.println();
    }

    System.out.println();
    System.out.println();

    file=new File("routes.txt");
    source="JFK";
    dos=new DegreeOfSeparation(file, " ", source);
    names=new String[]{"LAS", "DFW"};
    for(String name:names) {
      System.out.println("Is "+name+" Connected to "+source+"? \n"+dos.isConnected(name));
      if(dos.isConnected(name)) {
        System.out.println("The degress of separation between "+name+ " and "+source+" is: \n"+dos.degreeOfSeparation(name));
        System.out.println("The path from "+name+ " to "+source+" is: \n"+dos.path(name));
      }
      System.out.println();
    }





/*
Cycle test:
 *  % java Cycle tinyG.txt
 *  3 4 5 3 
 * 
 *  % java Cycle mediumG.txt 
 *  15 0 225 15 
 * 
 *  % java Cycle largeG.txt 
 *  996673 762 840164 4619 785187 194717 996673 
*/

/*
  Graph g3=new Graph(new File("largeG.txt"), " ");
  Cycle c3=new Cycle(g3);
  System.out.println("Does large graph have a cycle: "+ c3.hasCycle());
  if(c3.hasCycle())
    System.out.println("The large graph's cycle is: "+c3.cycle());
*/


    // Graph processing api:
    Graph g1=new Graph(new File("tinyG.txt"), " ");
    Cycle c1=new Cycle(g1);
    System.out.println("Does tiny graph have a cycle: "+ c1.hasCycle());
    if(c1.hasCycle())
      System.out.println("The tiny graph's cycle is: "+c1.cycle());      

    System.out.println();

    Graph g2=new Graph(new File("mediumG.txt"), " ");
    Cycle c2=new Cycle(g2);
    System.out.println("Does medium graph have a cycle: "+ c2.hasCycle());
    if(c2.hasCycle())
      System.out.println("The medium graph's cycle is: "+c2.cycle());


/*

    file=new File("movies.txt");
    sbG=new SymbolGraph(file, "/");
    graph=sbG.graph();

    for(int i=0; i<graph.V(); i++)
      System.out.println(i+": "+graph.adj(i));

    System.out.println();
    System.out.println();

    for(int i=0; i<graph.V(); i++)
      System.out.println(sbG.nameOf(i)+": "+sbG.adj(sbG.nameOf(i)));


*/

/*
    List[] adj=new List[5];
    for(int i=0; i<adj.length; i++) {
      adj[i]=new LinkedList<Integer>();
    }

    // 0-1-2-3-4-0
    adj[0].addAll(Arrays.asList(1));
    adj[1].addAll(Arrays.asList(2,0));
    adj[2].addAll(Arrays.asList(3,1));
    adj[3].addAll(Arrays.asList(4,2));
    adj[4].addAll(Arrays.asList(3));

    // extract number of vertices:
    int v=adj.length;
    // create an array of boolean to keep track of visited vertices:
    boolean[] visited=new boolean[v];
    // create an array of integer to keep track of vertices on the DFS tree
    int[] edgeTo=new int[v];
    // perform dfs:
    DFS.dfs(adj, 0, visited, edgeTo);
    System.out.println("is Grapg G connected:" + isConnected(visited));
*/

  }

  public static boolean isConnected(boolean[] visited) {
    for(int v=0; v<visited.length; v++) 
      if(!visited[v])
	return false;
    return true;
  }

}
