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

    System.out.println("Degree of vertex "+4+": "+G.degree(4));
    System.out.println("Max Degree: "+G.maxDegree());
    System.out.println("Min Degree: "+G.minDegree());
    System.out.println("Average Degree: "+G.avgDegree());
    System.out.println("Number of Self Loops: "+G.selfLoopCounter());
  }

}
