public class Diameter {

/*
Parallel edge detection. 
Devise a linear-time algorithm to count the parallel edges in a graph.

Hint: maintain a boolean array of the neighbors of a vertex, and reuse this array by only reinitializing the entries as needed.

Two-edge connectivity. 
A bridge in a graph is an edge that, if removed, would separate a connected graph into two disjoint subgraphs. 
A graph that has no bridges is said to be two-edge connected. 
Develop a DFS-based data type Bridge.java for determining whether a given graph is edge connected.

Center of a tree.
Given a graph that is a tree (connected and acyclic), find a vertex such that its maximum distance from any other vertex is minimized.

Hint: find the diameter of the tree (the longest path between two vertices) and return a vertex in the middle.

Diameter of a tree. 
Given a graph that is a tree (connected and acyclic), find the longest path, i.e., a pair of vertices v and w that are as far apart as possible. Your algorithm should run in linear time.

Hint. Pick any vertex v. Compute the shortest path from v to every other vertex. Let w be the vertex with the largest shortest path distance. Compute the shortest path from w to every other vertex. Let x be the vertex with the largest shortest path distance. The path from w to x gives the diameter.

Bridges with union-find. 
Let T be a spanning tree of a connected graph G. Each non-tree edge e in G forms a fundamental cycle consisting of the edge e plus the unique path in the tree joining its endpoings. 
Show that an edge is a bridge if and only if it is not on some fundamental cycle. Thus, all bridges are edges of the spanning tree. Design an algorithm to find all of the bridges (and bridge components) 
using E + V time plus E + V union-find operations.

Shortest path in complement graph. 
Given a graph G, design an algorithm to find the shortest path (number of edges) between s and every other vertex in the complement graph G'. The complement graph contains the same vertices as G but includes an edge v-w if and only if the edge v-w is not in G. Can you do any better than explicitly computing the complement graph G' and running BFS in G'?

Delete a vertex without disconnecting a graph. 
Given a connected graph, design a linear-time algorithm to find a vertex whose removal (deleting the vertex and all incident edges) does not disconnect the graph.
Hint 1 (using DFS): run DFS from some vertex s and consider the first vertex in DFS that finishes.
Hint 2 (using BFS): run BFS from some vertex s and consider any vertex with the highest distance.

Spanning tree. 
Design an algorithm that computes a spanning tree of a connected graph is time proportional to V + E. Hint: use either BFS or DFS.

All paths in a graph. 
Write a program AllPaths.java that enumerates all simple paths in a graph between two specified vertices. Hint: use DFS and backtracking. Warning: there many be exponentially many simple paths in a graph, so no algorithm can run efficiently for large graphs.

*/


  public static int diameter(List[] adj) {

  }

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

  }


}
