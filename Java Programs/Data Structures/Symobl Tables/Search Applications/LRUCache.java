/*You are required to complete below class */
class LRUCache {
    /* Structure of LRU ADT: */
    private HashMap<Integer, Integer> cacheMap;
    private LinkedList<Integer> cacheSchedule;
    private int capacity;
    
    /*Inititalize an LRU cache with size N */
    public LRUCache(int N) {
       //Your code here
       cacheMap=new HashMap<Integer, Integer>(); // empty HashMap
       cacheSchedule=new LinkedList<Integer>(); // scheduling list
       capacity=N;
    }
    
    /*Returns the value of the key x if 
     present else returns -1 */
    public int get(int x) {
       //Your code here
       if(cacheMap.isEmpty()) return -1;
       else if(!cacheMap.containsKey(x)) return -1;
       else {
           int y=cacheMap.get(x);
           refresh(x,y);
           return y;
       }
    }
    
    /*Sets the key x with value y in the LRU cache */
    public void set(int x, int y) {
       //Your code here
       /* 1. check if x is already in the table: */
       if(cacheMap.containsKey(x)) {
           /* remove it from the scheduling list and re-insert it: */
           refresh(x,y);
           return ;
       }
       /* 2. check if cacheMap is not full: */
       if(cacheMap.size()==capacity) { 
           /* page eviction is required */
           /* evict a page from the cacheMap using the LRU policy: */
           evict();
       }
        /* a. insert the key value pair to the cacheMap: */
        cacheMap.put(x,y);
        /* b. add the key to the scheduling Q: */
        cacheSchedule.offerFirst(x);
    }
    
    private void evict() {
        /* 1. remove the head of cacheSchedule queue: */
        int key=cacheSchedule.pollLast();
        /* 2. remove the key-value pair associated with the give key from cacheMap: */
        cacheMap.remove(key);
    }
    
    private void refresh(int x, int y) {
        int index=cacheSchedule.indexOf(x);
        cacheSchedule.remove(index);
        cacheMap.put(x,y);
        cacheSchedule.offerFirst(x);
    }
}
