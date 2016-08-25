public class Edge implements Comparable<Edge>{
  // instance variable
  private final int v; // one end of an edge instance
  private final int w; // another end of an edge instance
  private final double weight;

  // Constructor
  public Edge(int v, int w, double weight) {
    if(v<0) throw new IndexOutOfBoundsException("vertex index must be non-negative!");
    if(w<0) throw new IndexOutOfBoundsException("vertex index must be non-negative!");
    if(Double.isNaN(weight)) throw new IllegalArgumentException();
    this.v=v;
    this.w=w;
    this.weight=weight;
  }

  // API: instance methods:
  @Override
  public int compareTo(Edge e) {
    // NOTE: < is NOT a total order for Double data type because it does not satisfy TOTALITY condition: NaN is not < or > than NaN
    return Double.compare(this.weight, e.weight);
  }

/*
  < is NOT a total order for Double data type because it does not satisfy TOTALITY condition: NaN is not < or > than NaN
  @Override
  public int compareTo(Edge e) {
    if(this.weight<e.weight) return -1; // less than
    if(this.weight>e.weight) return 1;  // greater than
    return 0; // equal
  }
*/

  // return the weight associated with a given Edge instance:
  public double weight(){return this.weight;}
  // return either end points of an Edge instance: 
  public int either(){return v;}
  // return other end points of an Edge instance: 
  public int other(int v){
    if(this.v==v)  return w;
    else if(this.w==v)  return v;
    else throw new IllegalArgumentException("no such an end point exists!");
  }

/*
  private Comparable weight; // edge weight of generic Comparable type (a type with a total order)
  public Edge(int v, int w, Comparable weight){...}
  @Override
  public int compareTo(Edge e) {
    if(this.weight.compareTo(e.weight) < 0) return -1; // less than
    if(this.weight.compareTo(e.weight) >0) return 1;  // greater than
    return 0; // equal
  }
  public Comparable weight(){return this.weight;}
*/
  
}
