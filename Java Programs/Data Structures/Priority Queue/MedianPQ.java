/*
Dynamic-median finding:
Design a data type that supports insert in logarithmic time, find the median in constant time, and remove the median in logarithmic time.

Solution. Keep the median key in v; 
use a max-oriented heap for keys less than the key of v; 
use a min-oriented heap for keys greater than the key of v. 
To insert, add the new key into the appropriate heap, replace v with the key extracted from that heap.

http://algs4.cs.princeton.edu/24pq/
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.MinPQ;
import edu.princeton.cs.MaxPQ;

public class MedianPQ<Key extends Comparable<Key>> implements Iterable<Key> {
  // instance field of MedianPQ:
 }
