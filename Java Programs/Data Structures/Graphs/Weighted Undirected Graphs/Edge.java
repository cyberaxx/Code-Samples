public class Edge implements Comparable<Edge>{
  // instance variable
  private int v; // one end of an edge instance
  private int w; // another end of an edge instance
  private Comparable weight; // edge weight of generic Comparable type (a type with a total order)

  // Constructor
  public Edge(int v, int w, Comparable weight) {
    this.v=v;
    this.w=w;
    this.weight=weight;
  }

  // API: instance methods:
  @Override
  public int compareTo(Edge e) {
    if(this.weight.compareTo(e.weight) < 0) return -1; // less than
    if(this.weight.compareTo(e.weight) >0) return 1;  // greater than
    return 0; // equal
  }
  
  // return the weight associated with a given Edge instance:
  public Comparable weight(){return this.weight;}

}
