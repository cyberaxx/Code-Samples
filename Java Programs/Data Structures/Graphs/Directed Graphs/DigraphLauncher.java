import java.util.List;
import java.util.LinkedList;

import java.io.File;

public class DigraphLauncher {

  public static void main(String[] args) {

    File file=new File("tinyDG.txt");
    Digraph G=new Digraph(file, "  ");
    for(int v=0; v<G.V(); v++)
      System.out.println(v+": "+ G.adj(v));
    System.out.println("Edges: "+ G.E());

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


    DirectedDFS ddfs=new DirectedDFS(G, 0);
    System.out.println("Is 0 connected to 4: "+ ddfs.hasPathTo(4));
    if(ddfs.hasPathTo(4))
      System.out.println("The path from 0 to 4 is: "+ ddfs.pathTo(4));
    
  }

}
