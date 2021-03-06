import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.IllegalArgumentException;

// Quick Find AKA EAGER approach: Find takes O(1), Union takes O(n), n union find operation takes O(n^2)
public class QuickFindUF {
  private int N;
  private int count;
  private int [] id;
  // constructor: takes the number of items as input
  // and initialize the class variable N
  public QuickFindUF(int n) { 
    this.N = n; 
    this.count = n; // number of connect components
    this.id = new int [N];
    for (int i=0; i<N; i++) id[i] = i;
  }

  // count the number of connected components
  public int count () {return count;}
 
  // union the p's connect component to q's connected component
  public void union (int p, int q) {
    int pid = find(p); // the id (corresponding connected component) of object p
    int qid = find(q); // the id (corresponding connected component) of object q
    if (pid == qid) return; // do nothing, if p and q belogn to the same connected component
    for (int i=0; i<N; i++)
      if (id[i] == pid) id[i] = qid;
    count--; // reduce the number of connected components (each union, reduce two cc's into one)
  }

  // check if p and q are connected, i.e., belong to the same connected component
  public boolean connected (int p, int q) {
    // Quick Find approach:
    return find(p) == find(q);
  }

  // find the connected component that p belongs to:
  public int find (int p) {
    validate(p);
    return id[p]; // id of p is the connected component p belongs to
  }

  // check if the node, refere to the valid label within the range of [0 N-1]
  private void validate (int p) { 
    if (p<0 || p>=N) throw new IllegalArgumentException("Index " + p + " is not a valid index in the range of [0 to " + (N-1) + "]");
  }

  public static void main (String[] args) throws IOException { 
    // read data from standard input:
    // Number of objects:
    //FileReader fr = new FileReader ("largeUF.txt");
    FileReader fr = new FileReader ("tinyUF.txt");
    BufferedReader br = new BufferedReader (fr);
    // read the first line: the number of objects
    int N = Integer.parseInt(br.readLine());
    QuickFindUF uf = new QuickFindUF(N);
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
