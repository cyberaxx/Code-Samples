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

    Digraph Gr=Digraph.reverse(G);
    for(int v=0; v<Gr.V(); v++)
      System.out.println(v+": "+ Gr.adj(v));
    System.out.println("Edges: "+ Gr.E());

  }

}
