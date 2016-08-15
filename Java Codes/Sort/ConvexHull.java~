//import java.awt.geom.Point2D;
//import java.awt.geom.Point2D.Double;
import edu.princeton.cs.algs4.Point2D;

import java.util.Arrays;
import java.util.Collections;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ConvexHull {

  // takes a file and returns an Array of Point2D
  public static Point2D [] fileReader(String path) throws IOException {
    FileReader fr = new FileReader(path);
    BufferedReader br = new BufferedReader(fr);
    String line;
    int length = Integer.parseInt(br.readLine());
    // Array of Point:
    Point2D [] points = new Point2D[length];
    int index = 0;
    String [] temp;
    while( (line=br.readLine()) != null ) {
      points[index] = new Point2D(Double.parseDouble(line.split(" ")[0].trim()), Double.parseDouble(line.split(" ")[1].trim()));
      index++;
    }
    return points;
  }

  public static void main (String [] args) throws IOException {
    // Sort points based on their y-coordinate:
    Point2D [] points = fileReader("rs1423.txt");
  }

  // Given an array of points returns an array of points
  // on characterizing convex hull vertices:
  public static Point2D [] findConvexHull(Point2D [] points) {
    //1. Sort Point based on the y-coordinate:
    // System uses Merge Sort to sort an Array of Point2D objects, 
    // using Y_ORDER comparator  
    Arrays.sort(points, Point2D.Y_ORDER); 
    
    // Call the point with the lowest y-coordinate, p:
    Point2D p = points[0];

    // 2. Sort all points based on their ploar angle
    // with respect to p:
    Arrays.sort(points, p.polarOrder()); // System uses the Merge sort with polarOrder() Comparator



    return points;
  }
  
}
