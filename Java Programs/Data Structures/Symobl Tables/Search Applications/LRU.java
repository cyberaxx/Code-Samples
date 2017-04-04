/*You are required to complete below class */
class LRU {
    
    /* Structure: instance variables: */
    private HashMap<Integer, Integer> lookUpTable;
    private LinkedList<Integer> history; /* to keep track of LRU */
    private final int capacity; /* immutable */
    
    
    /* Modularity: encapsulation - private helper methods helping delivering API operations: */
    private boolean cacheFull(){return lookUpTable.size()==capacity;} /* check if the lookup table is full or not */
    private boolean cacheMiss(int key){return !lookUpTable.containsKey(key);}
    private int pageValue(int key) {return lookUpTable.get(key);}
    /* history manipulation: */
    private void removeFromHistory(int key){history.removeLastOccurrence(key);}
    private void addToHistory(int key){history.addFirst(key);}
    private int getLastPage(){return history.getLast();}
    /* look up manipulation: */
    private void lookUpValueUpdate(int key, int value){lookUpTable.put(key, value);}
    private void evict(int key) {lookUpTable.remove(key);}
    private void hitHistoryUpdate(int key) {
        /* remove it from the history: */
        removeFromHistory(key);
        /* move it to the front of the histoy queue*/
        addToHistory(key);   
    }
    
    /*Inititalize an LRU cache with size N */
    public LRUCache(int N) {
       //Your code here
       this.capacity=N;
       this.lookUpTable=new HashMap<Integer, Integer>(); /* empty hash table */
       this.history=new LinkedList<Integer>(); /* empty DList */
    }
    
    /*Returns the value of the key x if 
     present else returns -1 */
    public int get(int x) {
       //Your code here
       if(cacheMiss(x)) {
           return -1;
       }
       /* Search hit: */
       else {
            /* update the history queue: */
            hitHistoryUpdate(x);
            return pageValue(x);
       }
       
    }
    
    /*Sets the key x with value y in the LRU cache */
    public void set(int x, int y) {
       //Your code here
       if(cacheMiss(x)) {
           if(cacheFull()){
               /** 
                * 1. Find which page has get evicted
                * 2. remove it from the queue
                * 3. evict the corresponding page from the lookup table
               */
               int key=getLastPage();
               removeFromHistory(key); /* the last item in the history queue */
               try{evict(key);}
               catch(Exception e){System.out.println(e.getMessage()+ " failed to evict the page!");}
           }
           
           /**
            * 1. add the new page to the look up table
            * 2. add the pag to the front of history
            */
           lookUpValueUpdate(x,y);
           addToHistory(x);
       }
       
       /* Search hit: */
       else {
            /* update the history queue: */
            hitHistoryUpdate(x);
            /* update the value on the lookup table: */
            lookUpValueUpdate(x, y);
       }
    }
}
