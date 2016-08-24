import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.IllegalArgumentException;

public class UF {
  private int N;
  // array of parent pointes pointing to root nodes:
  private int [] parent;
  // array of rank of each node in the UF tree data structure:
  private byte [] rank;
  // number of connected components:
  private int count;

  // class construnctor
  public UF (int n) {
    if (n < 0) throw new IllegalArgumentException();
    this.N = n;
    this.count = n;
    this.parent = new int [N];
    this.rank = new byte [N];
    // each node is root of itself:
    for(int i=0; i<N; i++) {
      parent[i] = i;
      rank[i] = 0;
    }
  }
  
  // number of conected components in a uf(ADT) object:
  public int count() { 
    return count;
  }

  // find which connected commponent p belogs to:
  public int find (int p) {
    // first validate if p is a valid index (node)
    validate(p);
    while (p != parent[p]) {
      parent[p] = parent[parent[p]]; // path halving to get log* performance
      p = parent[p];
    }
    return p; // the root will be the label of the connected component
  }  

  // class helper function to validate that p is a valid array index:
  private void validate (int p) {
    if (p < 0 || p >= N) throw new IllegalArgumentException("Index " + p + " is not between 0 and " + (N-1) );
  }
  
  public boolean connected (int p, int q) {
    return find(p) == find(q); // check if p and q belong to the same connected component
  }

  public void union (int p, int q) {
    int rootP = find(p);
    int rootQ = find(q);
    if(rootP == rootQ) return; // if two nodes already belong to the same cc do nothing

    // Union by Rank to keep the tree as bushy as possible then the max(rank) will be logarithmic:
    // 1. Only the rank of the root changes,
    // 2. Once you become a non-root node, your rank will NEVER change,
    // 3. The rank changes, only when two roots have the same rank, then one become the subtree of the other one and rank of the NEW root increased by +1
    if (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
    else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
    else // if both roots have a same rank 
    {
      parent[rootQ] = rootP;	
      rank[rootP]++;
    }
  
    // reduce the number of connected component
    count--;   
  }

  public static void main (String[] args) throws IOException { 
    // read data from standard input:
    // Number of objects:
    FileReader fr = new FileReader ("largeUF.txt");
    BufferedReader br = new BufferedReader (fr);
    // read the first line: the number of objects
    int N = Integer.parseInt(br.readLine());
    UF uf = new UF(N);
    String line;

    while ( (line = br.readLine()) != null ) {
      int p = Integer.parseInt(line.split(" ")[0]);
      int q = Integer.parseInt(line.split(" ")[1]);
      if ( !uf.connected(p,q) )  {
        uf.union(p,q);
        // check if uf.count() is equal to 1:
        // if so, all elements belogn to the same cc (one partition)
        System.out.println(p + " " + q);
      }
    }
    System.out.println(uf.count() + " components");
  }

}
