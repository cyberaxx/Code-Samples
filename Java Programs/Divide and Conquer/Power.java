public class Power {

  // this method retruns the value x^n 
  // Using linear number of operations: O(n)
  public static int exponent(int x, int n) {
    int result = 1;
    for(int i=0; i<n; i++) result *= x;
    return result;
  }

  // this method implements exponent method
  // recursively for EVEN values of n:
  // Using logarithmic number of operations: O(logn)
  public static int exp(int x, int n) {
    // use a recursive method on N, by dividing n to n/2
    // 1. base case n==1 retrun x
    if (n==0) return 1;
    if (n==1) return x;
    
    // 2. break the problem to subproblems of size n/2
    // here since both subproblems exp(x,n/2) * exp(x,n/2) are identical
    // there is no need for recusring on both subproblems:
    int sqrt = exp(x,n/2);

    // 3. Combine solutions of subproblems to solve 
    // the problem of size n:
    return sqrt*sqrt;
    // Note: T(n) = 1T(n/2) + O(1) {constant multiplication} => Master Method: T(n) = O(logn)

    // NOTE: Correct implementation of an idea/Algorithm matters the most in order to fullfil
    // promised perfomance guarantees!!!

    // if we instead, have returned sqrt exp(x,n/2)*exp(x,n/2) we would have made following mistakes:
    // i. The T(n) is no longer logarithmic, it is Linear instead: 
    // T(n) = 2T(n/2) + O(1) {constant multiplication} => Master Method Case 3: T(n) = O(n)
    // ii. We have done redundant work by recursing on the right subproblem, since it was identical
    // to the left subproblem, we could have simply used the solution for the left subproblem as the
    // solution for both left and right subproblem (Since they were identical)
  }

  public static int expHelper(int x, int n) {
    if (n%2 == 1) return x*exp(x, n-1); // odd
    else return exp(x,n); // even
  }

  public static void main (String [] args) {
    System.out.println(exponent(2,5));
    System.out.println(exponent(5,3));
    System.out.println(exponent(10,4));

    System.out.println();

    System.out.println(expHelper(2,5));
    System.out.println(expHelper(5,3));
    System.out.println(expHelper(10,4));   
  }

}
