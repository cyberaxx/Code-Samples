import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.IllegalArgumentException;

// This is the lazy approach in which each object points to its parent node (the leader of its connected component)
// The union operation, update the parent of the leader of the connected components, rather than all its elements (unlike the eager approach)
// This approach is emperically faster than the eager approach because of less number of parent updates, however, in the worse case, trees might end up
// being flat, and it take O(n) parent update for n objects for the Union Operation. The Find operation is also worse case O(n).
public class QuickUnionUF {
  private int N;
  // pointers to the root nodes:
  private int [] parent;
  // the number of connected components:
  private int count;
  
  public QuickUnionUF (int n) {
    this.N = n;
    this.count = n;
    this.parent = new int [N];
    // each node is the parent of itself:
    for(int i=0; i<N; i++) parent[i] = i;
  }
 
  private void validate (int p) {
    if (p<0 || p>=N) throw new IllegalArgumentException("Index" + p + " is not within the range of [0 to " + (N-1) + "]");
  } 

  // find the leader node of the connected component that a given node i belongs to:
  public int find (int p) {
    validate(p);
    while (parent[p] != p)  p = parent[p];
    return p;
  }

  public boolean connected (int p, int q) {
    return find(p) == find(q);// O(n)
  }

  public void union (int p, int q) {
    int rootP = find(p); //O(n)
    int rootQ = find(q); //O(n)
    // if p and q belong to the same connected component, the do nothing, return
    if (rootP == rootQ) return;
    // otherwise, arbitrarily, take the first node's root, and make the second node's root its child by making the lead node of the p, parent of the lead node of q
    parent[rootQ] = rootP;
    count--;
  }
   
  public int count() {return count;}

  // Union Find client
  public static void main (String[] args) throws IOException { 
    // read data from standard input:
    // Number of objects:
    // FileReader fr = new FileReader ("largeUF.txt");
    FileReader fr = new FileReader ("tinyUF.txt");
    BufferedReader br = new BufferedReader (fr);
    // read the first line: the number of objects
    int N = Integer.parseInt(br.readLine());
    QuickUnionUF uf = new QuickUnionUF(N);
    String line;

    while ( (line = br.readLine()) != null ) {
      int p = Integer.parseInt(line.split(" ")[0]);
      int q = Integer.parseInt(line.split(" ")[1]);
      if ( !uf.connected(p,q) )  {
        uf.union(p,q);
        System.out.println(p + " " + q);
      }
    }
    System.out.println(uf.count() + " components");
  }
}
