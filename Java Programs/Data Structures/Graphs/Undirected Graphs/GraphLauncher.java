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

    BFS bfs=new BFS(G, 0);
    System.out.println("is 4 connect to the source? "+bfs.hasPathTo(4));
    System.out.println("Degree of separation from the source: "+bfs.distTo(4));
    System.out.println("The path to 4: "+bfs.path(4));
    System.out.println("is G connected? "+bfs.isConnected());


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
