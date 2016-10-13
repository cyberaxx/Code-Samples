public class RabinKarp {
  public static void main(String[] args) {
    String text="abacadabrabracabracadabrabrabracad";
    String pattern="abracadabra";
    System.out.println(indexOf(text, pattern, 256));
    System.out.println(text.indexOf(pattern));
  }

  // Substring search, using rolling hashes
  public static int indexOf(String text, String pattern, int R) {
    // text length, and pattern length
    int n=text.length();
    int m=pattern.length();

    // take a big prime number
//    long Q=bigPrime();
    long Q=3001;

    // the coefficient of the MSD in base R number
    long RM=1;// R^m-1
    for(int j=0; j<m; j++) RM=(RM*R)%Q; // shift RM to the left m time

    // compute the hash of m-digits of the pattern string:
    long pHash=hash(pattern, m, R, Q);
   
    // compute the hash of first m chars of text:
    long tHash=hash(text, m, R, Q);

    // compare tHash and pHash: equal hash means a high chance of actual equality
    if(pHash==tHash) return 0; // the index of first occurence of pattern on the text

    // scan the text, one pass from the left to right: compute hashes on m-digit substrings and compare it with pHash
    for(int i=m; i<n; i++) {
      // compute the rolling hash of the m-digit subtring of the text, by peeling of the MSD digit:
      tHash=(tHash+Q-(RM*text.charAt(i-m))%Q)%Q;
      // and add a new char to the end of the substring:
      tHash=(tHash*R)%Q+text.charAt(i);

      // compare tHash with the pattern hash:
      if(pHash==tHash) return i-m+1;
    }

    return n;
  }

  // reduce a string into a long integer (keep it 64 bits so that basic operation on it 
  // on 64-bits processor takes only constant time
  private static long hash(String str, int length, int R, long Q) {
    // use Horner's method, interpret the string as a n-digit number in base R(radix)
    long hash=0;
    // from MSD digit of the string to the LSD digit (left to right)
    for(int i=0; i<length; i++) {
      // shift the hash computed already one to the left and add the new char to it
      hash=((hash*R)%Q+str.charAt(i))%Q;
    }
    return hash;
  }

}
