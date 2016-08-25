public class Edge implements Comparable<Edge> {
  // instance variables
  private final int from;
  private final int to;
  private final double weight;

  // Constructor:
  public Edge(int from, int to, double weight){
    if(from<0) throw new IndexOutOfBoundsException("Given vertex is out of legal bounds!");
    if(to<0) throw new IndexOutOfBoundsException("Given vertex is out of legal bounds!");
    if(Double.isNaN(weight)) throw new IllegalArgumentException();
    this.from=from;
    this.to=to;
    this.weight=weight;
  }

  public int from(){return this.from;}
  public int to(){return this.to;}
  public double weight(){return this.weight;}

  /*
    Edge class implements java Comparable interface by
    characterizing a total order through overriding compareTo method
    for its instances:
  */
   // <> are not total order relations for Double instances (because NaN, Totality condition is violated. 
   public int compareTo(Edge e){return Double.compare(this.weight, e.weight);} 
}
