import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.Arrays;

public class GraphLauncher {

  // test client for the graph API
  public static void main(String[] args) {


    Graph G=new Graph(5);

    G.addEdge(0, 0);
    G.addEdge(1, 1);
    G.addEdge(1, 2);
    G.addEdge(0, 3);
    G.addEdge(4, 1);
    G.addEdge(4, 3);
    G.addEdge(1, 0);
    G.addEdge(3, 2);

    for(int i=0; i<5; i++)
      System.out.println(i+": "+G.adj(i));

    System.out.println("Degree of vertex "+4+": "+G.degree(4));
    System.out.println("Max Degree: "+G.maxDegree());
    System.out.println("Min Degree: "+G.minDegree());
    System.out.println("Average Degree: "+G.avgDegree());
    System.out.println("Number of Self Loops: "+G.selfLoopCounter());

    System.out.println();
    System.out.println();

    Graph g=new Graph(G);
    System.out.println("Degree of vertex "+4+": "+g.degree(4));
    System.out.println("Max Degree: "+g.maxDegree());
    System.out.println("Min Degree: "+g.minDegree());
    System.out.println("Average Degree: "+g.avgDegree());
    System.out.println("Number of Self Loops: "+g.selfLoopCounter());

    for(int i=0; i<5; i++)
      System.out.println(i+": "+g.adj(i));

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
