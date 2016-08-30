public interface ST<Key, Value> {
  // insert: Associate given value with the given value
  void put(Key key, Value value);
  // delete a value associated with the given key from ST
  void delete(Key key);
  // search: return a value associated with the given key
  Value get(Key key);
  // contains: check if ST contains any value associated with a given key
  boolean contains(Key key);
  // check if ST is empty:
  boolean isEmpty();
  // number of key-value pairs in the ST
  int size();
  // Iterable collection of ST keys
  Iterable<Key> keys();
}
