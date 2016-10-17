public class Suffix{
  // instance variables: Object structure
  private String[] suffixes;
  // Constructor
  public Suffix(String key) {
    // create all suffixes of the string key:
    suffixes=new String[key.length()];
    for(int i=0; i<key.length(); i++)
    	suffixes[i]=key.substring(i);
  }
  // API: Behaviours of suffix object
  public String[] suffixes(){return suffixes;}
}
