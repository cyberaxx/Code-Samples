public class Date implements Comparable<Date> {
  // Fields:
  private final int year;
  private final int month;
  private final int day;

  public Date (int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }
  
  public static boolean isValid(int year, int month, int day) {
    if (year < 0) return false;
    if (month > 12 || month < 1) return false;
    if (day > 31 || day < 1) return false;
    return true;
  }

  // by implementing the compareTo method, the DEFINITION of 
  // comparison between intances the closing class 
  // is part of the DEFINITION of the class itself.
  public int compareTo(Date that) {
    // CompareTo
    // 1. must be a total order relation
    // 2. must return -integer, + integer, and 0 if < > = respectively
    // compareTo provides a "natural order" among instances of Date class:
    
    // Compare from the Most Significant to the Least Significant
    // portion of the Date:
    // 1. compare year:
    if (this.year < that.year) return -1;
    if (this.year > that.year) return 1;
    // if years are equal, then:
    // 2. Compare month
    if (this.month < that.month) return -1;
    if (this.month > that.month) return 1;
    // if years are equal, and months are equal then:
    // 3. Compare days:
    if (this.day < that.day) return -1;
    if (this.day > that.day) return 1;
    // if years and months and days are equal, then:
    // two Date instances are equal:
    return 0;
  }

  // helper function that can be used inside the sort method
  // to exchange the position of item at index i with an item
  // at the index j
  private static void exch (Comparable [] items, int i, int j) {
    Comparable temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }   

  public static void main (String [] args) {
    Date d1 = new Date(1986,9,17);
    Date d2 = new Date(2016,7,21);
    
    System.out.println(less(d1,d2));
   
  }

  // we use another static method to hide the implementation of 
  // comparing using compareTo method from the client of the class
  public static boolean less (Date d1, Date d2) {
    return d1.compareTo(d2) < 0;
  }
  
}
