import java.util.*;

public class Solution {
    private List<List<Integer>> adj;
    private int[] ans;
    private int[] lastSeen;
    private int[] originalParent;
    private String labels;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.ans = new int[n];
        this.originalParent = parent;
        this.labels = s;
        
        // Initialize lastSeen array with -1 for all 26 lowercase English letters
        this.lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);
        
        // Build the adjacency list for the original tree
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            adj.get(parent[i]).add(i);
        }
        
        // Start DFS from the root node (0)
        dfs(0);
        
        return ans;
    }

    private void dfs(int u) {
        int charIdx = labels.charAt(u) - 'a';
        
        // Remember the closest ancestor with the same character
        int newParent = lastSeen[charIdx];
        
        // Update the tracker for the current path
        lastSeen[charIdx] = u;
        
        // Every node is part of its own subtree
        ans[u] = 1;
        
        // Traverse all children in the original tree
        for (int v : adj.get(u)) {
            dfs(v);
        }
        
        // Backtrack: Restore the tracker to its previous state
        lastSeen[charIdx] = newParent;
        
        // Delegate the subtree size of u to its final destination parent
        if (newParent != -1) {
            ans[newParent] += ans[u];
        } else if (u != 0) {
            // If no matching ancestor exists, it rolls up to its original parent
            ans[originalParent[u]] += ans[u];
        }
    }
}
