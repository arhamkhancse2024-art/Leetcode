class Solution {
    public int countServers(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;
        
        int[] rows = new int[m];
        int[] cols = new int[n];
        
        // First pass: count servers in each row and column
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    rows[i]++;
                    cols[j]++;
                }
            }
        }
        
        // Second pass: count servers that can communicate
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && (rows[i] > 1 || cols[j] > 1)) {
                    ans++;
                }
            }
        }
        
        return ans;
    }
}
