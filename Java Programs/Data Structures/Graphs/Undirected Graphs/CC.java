import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ArrayDeque;

public class CC {

  // vertex index array of boolean to keep track of visited vertices:
  private boolean[] marked;
  // vertex index array of cc id
  private int[] id;
  private int[] size;
  // number of cc's
  private int count;
   
  public CC(Graph G) {
    // number of veritces:
    int n=G.V();
    marked=new boolean[n];
    size=new int[n];
    id=new int[n];
    count=0;

    // from all vertices of the G
    for(int v=0; v<n; v++) {
      // if vertex v has not been visited yet
      if(!marked[v]) {
        dfs(G, v); // visit all vertices REACHABLE from v
	count++; // number of connected componenets
      }
    }
  }

  // dfs method (recursive)
  private void dfs(Graph G, int v) {
    // mark v visited:
    marked[v]=true;
    id[v]=count;
    size[count]++;
    // recursively visit all UNvisited vertices adjacent to v in graph G
    for(Integer w:G.adj(v))
      if(!marked[w])
	dfs(G, w);
  }

  // API:
  // number of connected components:
  public int count(){return count;}
  public int size(int v){
    validate(v);
    return size[id[v]];
  }
  public int id(int v){
    validate(v);
    return id[v];
  }
  public boolean isConnected(){return count==1;}
  // check v and w belongs to the same connected component (v and w are connected, or there exists a path between v and w)
  public boolean isConnected (int v, int w) {
    validate(v); validate(w);
    return id[v]==id[w];
  }

  // helper method
  private void validate(int v) {
    int n=marked.length;
    if(v<0||v>=n) throw new IndexOutOfBoundsException();
  }
}
