public final class String implements Comparable<String>{
  // Structure: instance fields
  private char[] value;
  private int length; // get string length in constant time
  private int offset; // useful to make substring method constant in both time and space
  private int hashCode; // to cache the hashCode and access it in constant time
  
  // Constructor: basically, String is a glorified array of characters
  public String(char[] value, int length, int offset) {
    this.value=value;
    this.length=length;
    this.offset=offset;
  }

  // API: behaviours of string object
  public int length(){return length;}
  public int hashCode() {
    // if hashCode has been already computed return it:
    if(hashCode!=0) return hashCode();
    // Otherwise: use Horner's method to compute a 32-bits hashCode
    for(int i=0; i<length; i++)
      hashCode=hashCode*31+value[i]; 
    // hashCode is a base-32 integer
    return hashCode;
  }
  public char[] toCharArray(){return value;}
  public char charAt(int index){
    if(index<0 || index>=length) throw new IndexOutOfBoundsException();
    return value[index];
  }
  public String substring(int lo, int hi) {
    return new String(value, hi-lo+1, lo);
  } // O(1)
  
  public String concat(String that) {
    // length of the new string:
    int len=this.length+that.length;
    // instantiate a new array of characters:
    char[] newString=new char[len]; // O(N) initializing the new char array
    
    // return the new string:
    return new String(newString, len, 0);
  }
    
  @Override
  public int compareTo(String that){
    int len=Math.min(this.length, that.length);
    // compare strings char by char up to the common length:
    for(int i=0; i<len; i++) {
      if(this.charAt(i)<that.charAt(i)) return -1;
      else if(this.charAt(i)>that.charAt(i)) return 1;
    }
    return this.length-that.length;
  }
}
