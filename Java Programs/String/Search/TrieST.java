import java.util.*;

public class TrieST<Value> {
  private static final int R=256; // radix of the allphabet
  
  // instance variables: structure of trie object (symbol table with string keys)
  // a trie is characterize by a reference to its root node
  private Node root;
  private int size;
  
  // Constructor:
  public TrieST(){
    root=null;
    size=0;
  }
  // Node class is a nested static class:
  private static class Node {
    // each trie node is consist of a value and R null links to all possible trie nodes
    private Object value;
    private Node[] next=new Node[R];
  }

  // API: Symbol Table behaviors:
  public void put(String key, Value value) {
    // check if the key is not null:
    if(key==null) throw new NullPointerException("Key cannot be null");
    if(value==null) {remove(key); return ;} // lazy remove
    
    // insert a key-value pair associted with the given key to the trie rooted at root
    root=put(root, key, value, 0);
  }
  // recursive helper method that traverse the trie and modify its structure and return the link to the modifed trie back up:
  private Node put(Node x, String key, Value value, int d) {
    // termination condition:
    if(x==null) x=new Node();
    
    // if all characters from the key string has been inserted to the trie already terminated the recursion:
    if(d==key.length()) {
      if(x.value==null) size++;
      x.value=key;
      return x;
    }

    // Otherwise:
    // 1. scan the next char from the key:
    char c=key.charAt(d);
    // 2. check if there exist a link from node x to a node through char c
    x.next[c]=put(x.next[c], key, value, d+1);
    
    // 3. return a link back up:
    return x;
  }
  public Value get(String key) {
    // if the kye is null:
    if(key==null) throw new NullPointerException();
    
    // Recursively search the trie rooted ant node root for a path which sums up to the given String key, return the value associated to the leaf of such a path
    Node x=get(root, key, 0); // from the MSD letter to LSD letter
    if(x==null) return null;
    
    // cast the value:
    return (Value) x.value;
  }
  private Node get(Node x, String key, int d) {
    // termination condition:
    if(x==null) return x;
    
    // if all characters of the given string has been read already:
    if(d==key.length()) return x;
    
    // read the next character from the key string:
    char c=key.charAt(d);
    return get(x.next[c], key, d+1);
  }  
  public boolean contains(String key){return get(key)!=null;}
  
  public void remove(String key) {
    // check if the key is null
    if(key==null) throw new NullPointerException();
    
    // search for the given key in the R-way trie, and remove the value associated to it
    root=remove(root, key, 0);  
  }
  // private recursive helper method:
  private Node remove(Node x, String key, int d) {
    // termination condition:
    // failed to find such a key in the trie
    if(x==null) throw new NoSuchElementException();
    if(d==key.length()){x.value=null; size--;} // set the value associated with the key to null
    
    else {
      // Otherwise: search for the given key by looking for its next character:
      char c=key.charAt(d);
      x.next[c]=remove(x.next[c], key, d+1);
    }
    
    // On the way back up if node x is null remove it:
    if(x.value!=null) return x;
    // if x.value is null, check if next is null for all symbols in alphabet:
    for(char ch=0; ch<R; ch++) {
      if(x.next[ch]!=null) return x;
    }
    return null;
  }
  
  public int size(){return size;}
  public boolean isEmpty(){return root==null;}
  
  // iterate over trie keys in sorted order:
  public Iterable<String> keys() {
    // perform inorder traversal of the trie for all R possible symbols from left to right:
    Deque<String> q=new ArrayDeque<String>(); // empty queue
    String key="";
    // start off from the root node to all leaves from left to right:
    keys(root, key, q);
    
    // retrun the Iterable:
    return q;
  }
  
  // private recursive trie traversal in sorted order:
  private void keys(Node x, String key, Deque<String> q) {
    // termination conditon:
    if(x==null) return ;
    // if the given node have a value associated with it:
    if(x.value!=null) q.offer(key);
    // from left to right
    for(char c=0; c<R; c++)
      keys(x.next[c], key+c, q);
  }

  // keys with prefix: as the given prefix:
  public Iterable<String> keysWithPrefix(String prefix) {
    // search for the given string in the trie:
    Node x=get(root, prefix, 0);
    // traverse the trie rooted at node x:
    Deque<String> q=new ArrayDeque<String>();
    keys(x, prefix, q);
    return q;
  }
  
  // longest prefix of the given string in the collection of strings:
  public String longestPrefixOf(String key) {
    // if key is null:
    if(key==null) throw new NullPointerException();
    
    // starting from the root of the trie, search for the given key, return the longest prefix of the given in the collection if key itself is not in the collection:
    int tail=lengthOfPrefix(root, key, 0, 0);
    
    return key.substring(0, tail);
  }
  private int lengthOfPrefix(Node x, String key, int d, int len) {
    // termination condition: 
    if(x==null) return len;
    if(x.value!=null) len=d; // this string in the collection could be a candidate for the longest prefix of key
    if(d==key.length()) return len;
    // Otherwise:
    // recursively look for a longer prefix:
    return lengthOfPrefix(x.next[key.charAt(d)], key, d+1, len);
  }
}

