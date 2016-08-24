import java.util.Comparator;

public class Point implements Comparable<Point> {
  // memebers of the Point class:
  // 1. Instance varaibale to keep track of Point instances' state:
  private final double x;
  private final double y;

  // Comparator as a Class variable:
  // Point.comparator specifies the implementation of the compare
  // method to compare to instances of the Point class:
  public static final Comparator<Point> X_ORDER = new XOrder();
  public static final Comparator<Point> Y_ORDER = new YOrder();

  // Here is the nested class that implement the Comparator interface:
  private static class XOrder implements Comparator<Point> {
    public int compare(Point p1, Point p2) {
      if(p1.getX() > p2.getX()) return 1;
      if(p1.getX() < p2.getX()) return -1;
      return 0;
    } 
  }

  // Here is the nested class that implement the Comparator interface:
  private static class YOrder implements Comparator<Point> {
    public int compare(Point p1, Point p2) {
      if(p1.getY() > p2.getY()) return 1;
      if(p1.getY() < p2.getY()) return -1;
      return 0;
    } 
  }

  // Constructor:
  public Point(double x, double y){
    this.x = x;
    this.y = y;
  }

  public double getX(){ return this.x; }
  public double getY(){ return this.y; }
 
  // Since Point class implements the Comparable interface
  // it has to implement the compareTo method for its instances:
  public int compareTo(Point p) {
    if(this.y > p.getY())  return 1;
    if(this.y < p.getY())  return -1;
    if(this.x > p.getX())  return 1;
    if(this.x < p.getX())  return -1;
    return 0;
  }

}
