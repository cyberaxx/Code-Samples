public class GCD {
   
  public static int gcd(int big, int small) {
    // recursive approach to comput great common denominator (GCD)
    // 1. Base case, where to stop recursion:
    if (small == 0) return big;
    
    // gcd recursion by using the remainder of BIG devided by SMALL as
    // as new SMALL, and the old small as the new BIG:
    return gcd(small, big % small);  
  }

  public static int gcd(int a, int b, int c) {
    int temp;
    if (a>=b) temp = gcd(a,b);
    else temp = gcd (b,a);

    if (temp>=c) return gcd(temp, c);
    else return gcd(c, temp);
  }

  // this method computs the lowest common multiple
  public static int lcm (int a, int b) { 
    if (a==0) return 0;
    if (b==0) return 0;
    if (a>=b) return (a*b)/gcd(a,b);
    return (a*b)/gcd(b,a);
  }

  public static int lcm(int a, int b, int c) {
    if(a==0) return 0;
    if(b==0) return 0;
    if(c==0) return 0;
    return lcm( lcm(a,b), c);
  }

  public static void main (String [] args) {
    System.out.println(gcd(15,330,65));
    System.out.println(lcm(15,330,65));
  }
}
