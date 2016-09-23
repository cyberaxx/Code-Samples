import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.io.File;

public class DigraphLauncher {

  public static void main(String[] args) {

    File file=new File("tinyDG.txt");
    Digraph G=new Digraph(file, "  ");
    for(int v=0; v<G.V(); v++)
      System.out.println(v+": "+ G.adj(v));
    System.out.println("Edges: "+ G.E());

/*
    System.out.println("Max indegree in G is: "+ G.maxIndegree());
    System.out.println("Min indegree in G is: "+ G.minIndegree());
    System.out.println("Max outdegree in G is: "+ G.maxOutdegree());
    System.out.println("Min outdegree in G is: "+ G.minOutdegree());
    System.out.println("Average indegree in G is: "+ G.avgIndegree());
    System.out.println("Average outdegree in G is: "+ G.avgOutdegree());


    Digraph Gr=Digraph.reverse(G);
    for(int v=0; v<Gr.V(); v++)
      System.out.println(v+": "+ Gr.adj(v));
    System.out.println("Edges: "+ Gr.E());

    System.out.println("Max indegree in Gr is: "+ Gr.maxIndegree());
    System.out.println("Min indegree in Gr is: "+ Gr.minIndegree());
    System.out.println("Max outdegree in Gr is: "+ Gr.maxOutdegree());
    System.out.println("Min outdegree in Gr is: "+ Gr.minOutdegree());
    System.out.println("Average indegree in Gr is: "+ Gr.avgIndegree());
    System.out.println("Average outdegree in Gr is: "+ Gr.avgOutdegree());
*/

   /* These test cases are directly taken from:
   http://algs4.cs.princeton.edu/42digraph/DirectedDFS.java.html
   http://algs4.cs.princeton.edu/42digraph/DepthFirstDirectedPaths.java.html 
   http://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/DirectedCycle.java.html  */

/*
   List<Integer> sources = new LinkedList<Integer>(Arrays.asList(1,2,6));   

   // multiple-source reachability
   DirectedDFS dfs = new DirectedDFS(G, sources);

   // print out vertices reachable from sources
   for (int v = 0; v < G.V(); v++)
     if (dfs.marked(v)) System.out.print(v + " ");
    System.out.println();


    // use vertex 3 as a source:
    int s=3;
//    DirectedDFSPaths ddfs = new DirectedDFSPaths(G, s);
//    DirectedDFSPathsIterative ddfs = new DirectedDFSPathsIterative(G, s);
//    DirectedBFSPaths bfs = new DirectedBFSPaths(G, s);
    DirectedBFSPaths bfs = new DirectedBFSPaths(G, Arrays.asList(3));
    for (int v = 0; v < G.V(); v++) {
      System.out.print("From "+s+" to "+v+": \t");
      if (bfs.hasPathTo(v))  
        System.out.println(bfs.pathTo(v)+"\t distance: "+bfs.distTo(v));
      else
         System.out.println(s+" is not connected to "+v);
    }

*/
//    DirectedCycle finder = new DirectedCycle(G);
//    ShortestDirectedCycle finder = new ShortestDirectedCycle(G);
    DirectedCycleIterative finder = new DirectedCycleIterative(G);
    if (finder.hasCycle()) {
      System.out.println("Directed cycle: \t"+ finder.cycle());
      System.out.println();
    }
    else {
      System.out.println("No directed cycle");
    }

    Digraph dag=new Digraph(new File("tinyDAG.txt"), " ");
    DFSOrder dfs=new DFSOrder(dag);
    System.out.println("pre:\t"+ dfs.pre());
    System.out.println("post:\t"+ dfs.post());
    System.out.println("reversePost:\t"+ dfs.reversePost());


  }
}
