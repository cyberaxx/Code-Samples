import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.IllegalArgumentException;

// ADT Union Find, to maintain a set of partitioned objects
public class WeightedQuickUnionUF {
  private int N; // number of elements in the set
  private int count; // number of cc (partitions)
  private int [] parent;
  private int [] size; // number of object under each partition
  
  // class constructor:
  public WeightedQuickUnionUF (int n) {
    this.N = n;
    this.count = n;
    this.parent = new int [n];
    this.size = new int [n];
    for (int i=0;i<N;i++) {
      // each node is its own parent initially:
      parent[i] =i;
      // the size of subtree, rooted at i, is 1 initially:
      size[i] =1;
    }
  }

  // validate the label of point p
  // to be in the valid range [0 N-1]
  private void validate (int p) {
   if (p<0 || p>=N) throw new IllegalArgumentException("Index" + p + " is not within [0 to " + (N-1) + "]");
  }
  
  public int count() {return count;} // return the number of connected components

  // find the root of the tree which p bleongs to (find the leader of p's connected component)
  public int find (int p) {
    // check if p is valid:
    validate(p);
    // while p is not the root:
    while (parent[p] != p)
      p = parent[p]; // move up one level in the tree
    return p;
  }

  public boolean connected (int p, int q) {
    // if p and q has the same root AKA leading node in the connected component
    return find(p) == find(q);
  }
 
  public void union (int p, int q) {
    // first we have to find the leading node of p and q:
    int rootP = find(p);
    int rootQ = find(q);
    // if p and q belong to the same cc (partition) do nothing:
    if (rootP == rootQ) return;
 
    // if the number of nodes in the tree rooted at rootP is more than the tree rooted at rootQ: make the rootP the new root   
    if (size[rootP] > size[rootQ]) {
       parent[rootQ] = rootP;
       size[rootP] += size[rootQ];
    }
    else {
      parent[rootP] = rootQ;
      size[rootQ] += size[rootP];
    }
    count--;
  }

  // client:
  public static void main (String [] args) throws IOException {
    FileReader fr = new FileReader ("tinyUF.txt");
    BufferedReader br = new BufferedReader (fr);
    // reading the first line of the input file:
    int N = Integer.parseInt(br.readLine());
    // create a Union Find data type
    WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
    String line;
    while ( ( line = br.readLine() ) != null) {
      int p = Integer.parseInt(line.split(" ")[0]);
      int q = Integer.parseInt(line.split(" ")[1]);
      if (uf.connected(p, q)) continue;
      uf.union(p, q);
      System.out.println(p + " " + q);
    }
    System.out.println(uf.count() + " components");
  }
}
