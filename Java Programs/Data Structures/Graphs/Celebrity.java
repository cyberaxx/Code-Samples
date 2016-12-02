public class Celebrity
{
    // The task is to complete this function
    int getId(int M[][], int n)
    {
        // Your code here
        /* celebrity is a vertex with 0 outdegree and n-1 indegree: */
        int[] inDegree=new int[n];
        int[] outDegree=new int[n];
        
        /* row by row traverse the adjacency matrix: */
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                /* check there is an edge from i to j: */
                if(M[i][j]==1) {
                    outDegree[i]++;
                    inDegree[j]++;
                }
            }
        }
        
        /* find the celerity: */
        for(int i=0; i<n; i++) {
            if(inDegree[i]==n-1 && outDegree[i]==0)
                return i;
        }
        return -1;
    }
}
